package me.figo.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

public class GeneralTest {
	@Test
	public void testDateformat() throws ParseException {
		String raw = "2013-06-02T00:00:00.000+0000";
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		Date parsed = df.parse(raw);
		
		Calendar cal = new GregorianCalendar();
		cal.setTime(parsed);
		assertEquals(2013, cal.get(Calendar.YEAR));
	}
}
