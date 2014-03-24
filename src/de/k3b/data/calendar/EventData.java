package de.k3b.data.calendar;

import java.util.Date;

/**
 * android calendar-compatible Eventdata at leas available in samsung-android 2.2 and in android 4.0 and up (and maybe in other androids that have a calendar)
 */
public interface EventData {
	public abstract long getId();
	
	public abstract long getDtstart();

	public abstract long  getDtend();

	public abstract String getTitle();

	public abstract String getDescription();

	public abstract String getEventLocation();

	public abstract String getEventTimezone();

	public abstract String getDuration();

	public abstract String getRrule();

	public String getOrganizer();

}