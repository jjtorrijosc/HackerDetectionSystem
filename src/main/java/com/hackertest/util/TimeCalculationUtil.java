package com.hackertest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.hackertest.Exception.DateRFC2822FormatException;

public class TimeCalculationUtil {

	public static final SimpleDateFormat sdf_RFC_2822 = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.ENGLISH);
	
	/**
	 * 
	 * @param time1
	 * @param time2
	 * @return minutes between time1 and time2
	 * @throws DateRFC2822FormatException 
	 */
	public static long minutesBetween(String time1, String time2) throws DateRFC2822FormatException {
		
		try {
			/*
			 * 1. Date.getTime() returns the number of milliseconds since January 1, 1970, 00:00:00 GMT,
			 * 						no need to worry anymore about the time zones
			 * 2. time1.getTime() - time2.getTime() = difference between both dates in milliseconds,
			 * 			it can be a negative value if time2 is greater than time1, apply Absolute Value
			 * 3. transform to Minutes. 60 seconds = 1 minute, 1000 milliseconds = 1 second
			 * 			rounded down because long cast truncate decimal content
			 */
			return Math.abs(sdf_RFC_2822.parse(time1).getTime()-sdf_RFC_2822.parse(time2).getTime())/60000;
		} catch (ParseException e) {
			throw new DateRFC2822FormatException("Invalid date formats",e);
		} 
	}

}
