package de.k3b.calendar;

import net.fortuna.ical4j.data.ParserException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Checks if outcome of ics export is the same as with last release.<br/>
 * If this test fails manually check if the result is still ok and
 * copy te new result from "but was ...." into this test.
 * This test must be updated if TestDataUtils generate different(additional) data.
 * Created by k3b on 02.06.2014.
 */
public class DtoIcRegressonsTests {
    private EventDtoSimple src = null;

    @Before
    public void setup() {
        src = TestDataUtils.createTestEventDto();
    }

    private static final String lastMininal = normalize("BEGIN:VCALENDAR\n" +
            "PRODID:jUnit-Tests\n" +
            "VERSION:2.0\n" +
            "CALSCALE:GREGORIAN\n" +
            "BEGIN:VEVENT\n" +
            "DTSTART:20000501T123456\n" +
            "DTEND:20000501T171234\n" +
            "SUMMARY:test title\n" +
            "DESCRIPTION:bla bla bla\n" +
            "DURATION:P1D\n" +
            "END:VEVENT\n" +
            "END:VCALENDAR");
    private static final String lastdefault = normalize("BEGIN:VCALENDAR\n" +
            "PRODID:jUnit-Tests\n" +
            "VERSION:2.0\n" +
            "CALSCALE:GREGORIAN\n" +
            "BEGIN:VEVENT\n" +
            "UID:4711\n" +
            "DTSTART:20000501T123456\n" +
            "DTEND:20000501T171234\n" +
            "SUMMARY:test title\n" +
            "DESCRIPTION:bla bla bla\n" +
            "LOCATION:location\n" +
            "DURATION:P1D\n" +
            "END:VEVENT\n" +
            "END:VCALENDAR");
    private static final String lastMaximal = normalize("BEGIN:VCALENDAR\n" +
            "PRODID:jUnit-Tests\n" +
            "VERSION:2.0\n" +
            "CALSCALE:GREGORIAN\n" +
            "UID:3\n" +
            "BEGIN:VTIMEZONE\n" +
            "TZID:Australia/Sydney\n" +
            "TZURL:http://tzurl.org/zoneinfo/Australia/Sydney\n" +
            "X-LIC-LOCATION:Australia/Sydney\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+1100\n" +
            "TZOFFSETTO:+1000\n" +
            "TZNAME:EST\n" +
            "DTSTART:20080406T030000\n" +
            "RRULE:FREQ=YEARLY;BYMONTH=4;BYDAY=1SU\n" +
            "END:STANDARD\n" +
            "BEGIN:DAYLIGHT\n" +
            "TZOFFSETFROM:+1000\n" +
            "TZOFFSETTO:+1100\n" +
            "TZNAME:EST\n" +
            "DTSTART:20081005T020000\n" +
            "RRULE:FREQ=YEARLY;BYMONTH=10;BYDAY=1SU\n" +
            "END:DAYLIGHT\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+100452\n" +
            "TZOFFSETTO:+1000\n" +
            "TZNAME:EST\n" +
            "DTSTART:18950201T000000\n" +
            "RDATE:18950201T000000\n" +
            "END:STANDARD\n" +
            "BEGIN:DAYLIGHT\n" +
            "TZOFFSETFROM:+1000\n" +
            "TZOFFSETTO:+1100\n" +
            "TZNAME:EST\n" +
            "DTSTART:19170101T000100\n" +
            "RDATE:19170101T000100\n" +
            "RDATE:19420101T020000\n" +
            "RDATE:19420927T020000\n" +
            "RDATE:19431003T020000\n" +
            "RDATE:19711031T020000\n" +
            "RDATE:19721029T020000\n" +
            "RDATE:19731028T020000\n" +
            "RDATE:19741027T020000\n" +
            "RDATE:19751026T020000\n" +
            "RDATE:19761031T020000\n" +
            "RDATE:19771030T020000\n" +
            "RDATE:19781029T020000\n" +
            "RDATE:19791028T020000\n" +
            "RDATE:19801026T020000\n" +
            "RDATE:19811025T020000\n" +
            "RDATE:19821031T020000\n" +
            "RDATE:19831030T020000\n" +
            "RDATE:19841028T020000\n" +
            "RDATE:19851027T020000\n" +
            "RDATE:19861019T020000\n" +
            "RDATE:19871025T020000\n" +
            "RDATE:19881030T020000\n" +
            "RDATE:19891029T020000\n" +
            "RDATE:19901028T020000\n" +
            "RDATE:19911027T020000\n" +
            "RDATE:19921025T020000\n" +
            "RDATE:19931031T020000\n" +
            "RDATE:19941030T020000\n" +
            "RDATE:19951029T020000\n" +
            "RDATE:19961027T020000\n" +
            "RDATE:19971026T020000\n" +
            "RDATE:19981025T020000\n" +
            "RDATE:19991031T020000\n" +
            "RDATE:20000827T020000\n" +
            "RDATE:20011028T020000\n" +
            "RDATE:20021027T020000\n" +
            "RDATE:20031026T020000\n" +
            "RDATE:20041031T020000\n" +
            "RDATE:20051030T020000\n" +
            "RDATE:20061029T020000\n" +
            "RDATE:20071028T020000\n" +
            "END:DAYLIGHT\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+1100\n" +
            "TZOFFSETTO:+1000\n" +
            "TZNAME:EST\n" +
            "DTSTART:19170325T020000\n" +
            "RDATE:19170325T020000\n" +
            "RDATE:19420329T020000\n" +
            "RDATE:19430328T020000\n" +
            "RDATE:19440326T020000\n" +
            "RDATE:19720227T030000\n" +
            "RDATE:19730304T030000\n" +
            "RDATE:19740303T030000\n" +
            "RDATE:19750302T030000\n" +
            "RDATE:19760307T030000\n" +
            "RDATE:19770306T030000\n" +
            "RDATE:19780305T030000\n" +
            "RDATE:19790304T030000\n" +
            "RDATE:19800302T030000\n" +
            "RDATE:19810301T030000\n" +
            "RDATE:19820404T030000\n" +
            "RDATE:19830306T030000\n" +
            "RDATE:19840304T030000\n" +
            "RDATE:19850303T030000\n" +
            "RDATE:19860316T030000\n" +
            "RDATE:19870315T030000\n" +
            "RDATE:19880320T030000\n" +
            "RDATE:19890319T030000\n" +
            "RDATE:19900304T030000\n" +
            "RDATE:19910303T030000\n" +
            "RDATE:19920301T030000\n" +
            "RDATE:19930307T030000\n" +
            "RDATE:19940306T030000\n" +
            "RDATE:19950305T030000\n" +
            "RDATE:19960331T020000\n" +
            "RDATE:19970330T020000\n" +
            "RDATE:19980329T020000\n" +
            "RDATE:19990328T020000\n" +
            "RDATE:20000326T020000\n" +
            "RDATE:20010325T020000\n" +
            "RDATE:20020331T020000\n" +
            "RDATE:20030330T020000\n" +
            "RDATE:20040328T020000\n" +
            "RDATE:20050327T020000\n" +
            "RDATE:20060402T030000\n" +
            "RDATE:20070325T020000\n" +
            "END:STANDARD\n" +
            "BEGIN:STANDARD\n" +
            "TZOFFSETFROM:+1000\n" +
            "TZOFFSETTO:+1000\n" +
            "TZNAME:EST\n" +
            "DTSTART:19710101T000000\n" +
            "RDATE:19710101T000000\n" +
            "END:STANDARD\n" +
            "END:VTIMEZONE\n" +
            "BEGIN:VEVENT\n" +
            "UID:4711\n" +
            "DTSTART;TZID=Australia/Sydney:20000501T203456\n" +
            "DTEND;TZID=Australia/Sydney:20000502T011234\n" +
            "SUMMARY:test title\n" +
            "DESCRIPTION:bla bla bla\n" +
            "LOCATION:location\n" +
            "ORGANIZER:mailto:max.mustermann@url.org\n" +
            "DURATION:P1D\n" +
            "RRULE:FREQ=YEARLY;BYMONTH=3;BYDAY=-1SU\n" +
            "RDATE:19610901T045612,19630901T045612\n" +
            "EXDATE:19710901T045612,19730901T045612\n" +
            "BEGIN:VALARM\n" +
            "TRIGGER:-PT5M\n" +
            "END:VALARM\n" +
            "BEGIN:VALARM\n" +
            "TRIGGER:-PT10M\n" +
            "END:VALARM\n" +
            "END:VEVENT\n" +
            "END:VCALENDAR");
    @Test
    public void shouldBeSameMinimal() throws IOException, ParserException {
        EventDtoSimple sut = new EventDtoSimple(src,EventFilterDto.MINIMAL);
        String result = getIcs(sut);
        Assert.assertEquals(lastMininal, result);
    }
    @Test
    public void shouldBeSameDefault() throws IOException, ParserException {
        EventDtoSimple sut = new EventDtoSimple(src,EventFilterDto.DEFAULTS);
        String result = getIcs(sut);
        Assert.assertEquals(lastdefault, result);
    }

    @Test
    public void shouldBeSameMaximal() throws IOException, ParserException {
        EventDtoSimple sut = new EventDtoSimple(src,EventFilterDto.ALL);
        String result = getIcs(sut);
        Assert.assertEquals(lastMaximal, result);
    }

    /** controls, wich data elements will be exported. */
    private final EventFilter filter = EventFilterDto.ALL;

    private String getIcs(final EventDtoSimple sut) {
        return normalize(TestDataUtils.getIcs(filter, sut));
    }

    private static String normalize(String s) {
        return s
                .replace("\r\n", "\n")
                .replace("\t", " ")
                .replace("  ", " ")
                .trim();
    }
}