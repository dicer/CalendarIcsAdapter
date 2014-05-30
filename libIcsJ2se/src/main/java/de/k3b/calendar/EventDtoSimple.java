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
package de.k3b.calendar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Minimal implementation of EventDto to backup values of other EventDto-implementations.<br/>
 * This class has no direct dependency to android so it can be run in a j2se-junit-integration test.<br/><br/>
 * 
 * @author k3b
 */
public class EventDtoSimple implements EventDto {
    private String extDates;

    public EventDtoSimple() {};
    public EventDtoSimple(EventDto src) {
        if (src!= null) {
            setId(src.getId());
            setCalendarId(src.getCalendarId());
            setTitle(src.getTitle());
            setDescription(src.getDescription());
            setEventLocation(src.getEventLocation());
            setOrganizer(src.getOrganizer());

            setDtEnd(src.getDtEnd());
            setDtStart(src.getDtStart());
            setDuration(src.getDuration());
            setEventTimezone(src.getEventTimezone());

            setRRule(src.getRRule());
            setRDate(src.getRDate());
            setExtDates(src.getExtDates()); // #11
            setAlarmMinutesBeforeEvent(src.getAlarmMinutesBeforeEvent());
        }
    }

    public String getId() {
		return Id;
	}

	public EventDtoSimple setId(String id) {
		Id = id;
		return this;
	}

	public long getDtStart() {
		return dtStart;
	}

	public EventDtoSimple setDtStart(long dtStart) {
		this.dtStart = dtStart;
		return this;
	}

	public long getDtEnd() {
		return dtEnd;
	}

	public EventDtoSimple setDtEnd(long dtEnd) {
		this.dtEnd = dtEnd;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public EventDtoSimple setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public EventDtoSimple setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public EventDtoSimple setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
		return this;
	}

	public String getEventTimezone() {
		return eventTimezone;
	}

	public EventDtoSimple setEventTimezone(String eventTimezone) {
		this.eventTimezone = eventTimezone;
		return this;
	}

	public String getDuration() {
		return duration;
	}

	public EventDtoSimple setDuration(String duration) {
		this.duration = duration;
		return this;
	}

	public String getRRule() {
		return rRule;
	}

    public EventDtoSimple setRRule(String rRule) {
		this.rRule = rRule;
		return this;
	}

    @Override
    public String getRDate() {
        return rDate;
    }

    /** #11 formatted as komma seperated list of iso-utc-dates. Example: '20090103T093000Z,20110101T093000Z' */
    @Override
    public String getExtDates() {
        return this.extDates;
    }

    /** #11 formatted as komma seperated list of iso-utc-dates. Example: '20090103T093000Z,20110101T093000Z' */
    public EventDtoSimple setExtDates(String value) {
        this.extDates = value;
        return this;
    }

    public EventDtoSimple setRDate(String rDate) {
        this.rDate = rDate;
        return this;
    }

    public String getOrganizer() {
		return organizer;
	}

	public EventDtoSimple setOrganizer(String organizer) {
		this.organizer = organizer;
		return this;
	}

	public String getCalendarId() {
		return calendarId;
	}

	public EventDtoSimple setCalendarId(String calendarId) {
		this.calendarId = calendarId;
		return this;
	}

    /** #9 the alarm(s) should trigger x menutes before the event. null means no alarms. */
    public List<Integer> getAlarmMinutesBeforeEvent() {return alarmMinutesBeforeEvent;}

    public EventDtoSimple  setAlarmMinutesBeforeEvent(int...  alarmMinutesBeforeEvent) {
        if (alarmMinutesBeforeEvent != null) {
            ArrayList<Integer> items = new ArrayList<Integer>();
            for(int al : alarmMinutesBeforeEvent)
                items.add(Integer.valueOf(al));

            setAlarmMinutesBeforeEvent(items);
        }
        return this;
    }
    /** #9 the alarm(s) should trigger x menutes before the event. null means no alarms. */
    public EventDtoSimple  setAlarmMinutesBeforeEvent(List<Integer>  alarmMinutesBeforeEvent) {
        this.alarmMinutesBeforeEvent = alarmMinutesBeforeEvent;
        return this;
    }

    String Id;
	
	long dtStart;

	long dtEnd;

	String title;

	String description;

	String eventLocation;

	String eventTimezone;

	String duration;

	String rRule;

    private String rDate;

    String organizer;

	String calendarId;

    List<Integer> alarmMinutesBeforeEvent;
}