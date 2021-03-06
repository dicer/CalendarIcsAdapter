/*
 * Copyright (C) 2014- k3b
 * 
 * This file is part of android.calendar.ics.adapter.
 * 
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details. 
 * 
 * You should have received a copy of the GNU General Public License along with 
 * this program. If not, see <http://www.gnu.org/licenses/>
 */
package de.k3b.calendar.ics;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.*;

import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.component.*;
import net.fortuna.ical4j.model.property.*;

import de.k3b.calendar.EventDto;
import de.k3b.calendar.EventDtoSimple;
import de.k3b.calendar.EventFilter;
import de.k3b.calendar.EventFilterDto;
import de.k3b.util.DateTimeUtil;

/**
 * Factory that converts generic EventDto to iCal4j-Implementation specific ics.
 * This class has no direct dependency to android so it can be run in a j2se-junit-integration test.<br/><br/>
 * 
 * @author k3b
 */
public class EventDto2IcsFactory {
	/**
	 * i.e. "-//Ben Fortuna//iCal4j 1.0//EN"
	 */
	private String applicationID;
	
	private Calendar calendar = null;

    /** used to translate timezone-name to timezone-structure */
    private final TimeZoneRegistry tzregistry = TimeZoneRegistryFactory.getInstance().createRegistry();

    private final EventFilter filter;
    /**
	 * creates a calendar
	 * @param applicationID used to identify the datasource i.e. "-//Ben Fortuna//iCal4j 1.0//EN"
	 */
	public EventDto2IcsFactory(EventFilter filter, String applicationID) {
		this.applicationID = applicationID;
        this.filter = filter;
	}
	
	/**
	 * gets the generated calendar.
	 * Use getCalendar().toString() to get the ics-file content 
	 */
	public Calendar getCalendar() {
		if (this.calendar == null) {
			this.calendar = new Calendar();
			calendar.getProperties().add(new ProdId(applicationID));
			calendar.getProperties().add(Version.VERSION_2_0);
			calendar.getProperties().add(CalScale.GREGORIAN);
		}
		return calendar;
	}

    /**
	 * adds a generic EventDto to ical4j-calendar
	 */
	public VEvent addEvent(final EventDto _eventData, long dtStart, long dtEnd) {
        final EventDtoSimple eventData = new EventDtoSimple(_eventData, filter);

        // fix recurrence rule, if requested
        if (filter.getRecurrenceType() != EventFilter.RecurrenceType.AllEvents) {
            if (dtStart != 0)
                eventData.setDtStart(dtStart);
            if (dtEnd != 0)
                eventData.setDtEnd(dtEnd);
        }

        Calendar vcalendar = getCalendar();

        String timezoneName = eventData.getEventTimezone();
        TimeZone timezone = getTimeZone(vcalendar, timezoneName);

        PropertyList eventProperties = new PropertyList(); // event.getProperties();
		if (eventData.getId() != null) eventProperties.add(new Uid(eventData.getId()));
		if (eventData.getDtStart() != 0) eventProperties.add(d(new DtStart(new DateTime(eventData.getDtStart())),timezone));
		if (eventData.getDtEnd() != 0) eventProperties.add(d(new DtEnd(new DateTime(eventData.getDtEnd())),timezone));

		if (eventData.getTitle() != null) eventProperties.add(new Summary(eventData.getTitle()));
		if (eventData.getDescription() != null) eventProperties.add(new Description(eventData.getDescription()));
		if (eventData.getEventLocation() != null) eventProperties.add(new Location(eventData.getEventLocation()));
        if (eventData.getOrganizer() != null) {
            try {
                eventProperties.add(new Organizer(eventData.getOrganizer()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

		if (eventData.getDuration() != null) eventProperties.add(new Duration(new Dur(eventData.getDuration())));

        addRRules(eventProperties, eventData.getRRule());

        addRDates(eventProperties, eventData.getRDate());

        // #11 exDate export
        addExDates(eventProperties, eventData.getExtDates());

        // #9
        ComponentList valarms = getVAlarms(eventData.getAlarmMinutesBeforeEvent());

        VEvent event = new VEvent(eventProperties, valarms);

        if (eventData.getCalendarId() != null) {
            IcsUtil.setCalId(vcalendar, eventData.getCalendarId());
        }

        vcalendar.getComponents().add(event);
		return event;
	}

    private TimeZone getTimeZone(Calendar vcalendar, final String timezoneName) {
        TimeZone result = null;
        if (timezoneName != null) {
            VTimeZone existing = IcsUtil.getTimeZone(vcalendar,timezoneName);
            if (existing != null) return new TimeZone(existing);

            result = tzregistry.getTimeZone(timezoneName);
            if ((result != null) && (result.getVTimeZone() != null)) vcalendar.getComponents().add(result.getVTimeZone());
        }
        return result;
    }

    private DateProperty d(final DateProperty date, final TimeZone timezone) {
        if (timezone != null) date.setTimeZone(timezone);
        return date;
    }

    /** returns an empty list if there are no alarms */
    // #9
    private ComponentList getVAlarms(final List<Integer> alarms) {
        final ComponentList valarms = new ComponentList();
        if ((alarms != null) && (alarms.size() > 0)) {
            for (Integer alarm : alarms) {
                Dur durationInMinutes = new Dur(0,0,- alarm, 0);
                valarms.add(new VAlarm(durationInMinutes));
            }
        }
        return valarms;
    }

    // #11 exDate export
    private void addExDates(final PropertyList eventProperties, final String strDates) {
        if (strDates != null) {
            DateList dates = getDateList(strDates);
            if (dates.size() > 0) {
                eventProperties.add(new ExDate(dates));
            }
        }
    }

    private void addRDates(final PropertyList eventProperties, final String strDates) {
        if (strDates != null) {
            DateList dates = getDateList(strDates);
            if (dates.size() > 0) {
                eventProperties.add(new RDate(dates));
            }
        }
    }

    private DateList getDateList(final String strDates) {
        DateList dates = new DateList();
        for (String exDate : strDates.split(",")) {
            try {
                dates.add(new DateTime(DateTimeUtil.parseIsoDate(exDate)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dates;
    }

    private void addRRules(final PropertyList eventProperties, final String rRule) {
        if (rRule != null) {
			try {
				eventProperties.add(new RRule(rRule));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
    }
}
