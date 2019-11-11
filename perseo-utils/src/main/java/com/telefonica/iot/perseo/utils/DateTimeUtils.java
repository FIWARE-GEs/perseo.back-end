/**
 * Copyright 2015 Telefonica Investigación y Desarrollo, S.A.U
 *
 * This file is part of perseo-core project.
 *
 * perseo-core is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License version 2 as published by the Free Software Foundation.
 *
 * perseo-core is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along with perseo-core. If not, see
 * http://www.gnu.org/licenses/.
 *
 * For those usages not covered by the GNU General Public License please contact with
 * iot_support at tid dot es
 */

package com.telefonica.iot.perseo.utils;

import ca.rmen.sunrisesunset.SunriseSunset;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * The type Date time utils.
 */
public class DateTimeUtils {


    /**
     * Gets next sunrise.
     * Uses car.men.sunrisesunset library
     * It calculates next sunrise considering provided date and time
     * If date and time provided is older than sunrise it adds +1 day to date and calculates again
     *
     * @param day       the day Calendar object on ISO format
     * @param latitude  the latitude
     * @param longitude the longitude
     * @return the next sunrise
     */
    public static Calendar getNextSunrise(Calendar day, double latitude, double longitude) {
        Calendar[] sunriseSunset = SunriseSunset.getSunriseSunset(day, latitude, longitude);
        Calendar day2 = (Calendar) day.clone();
        if (sunriseSunset[0].get(Calendar.HOUR_OF_DAY) < day.get(Calendar.HOUR_OF_DAY)) {
            day2.add(Calendar.DATE, 1);
            sunriseSunset = SunriseSunset.getSunriseSunset(day2, latitude, longitude);
        }
        return sunriseSunset[0];
    }

    /**
     * Gets next sunset.
     * Uses car.men.sunrisesunset library
     * It calculates next sunset considering provided date and time
     * If date and time provided is older than sunset it adds +1 day to date and calculates again
     *
     * @param day       the day Calendar object on ISO format
     * @param latitude  the latitude
     * @param longitude the longitude
     * @return the next sunset
     */
    public static Calendar getNextSunset(Calendar day, double latitude, double longitude) {
        Calendar[] sunriseSunset = SunriseSunset.getSunriseSunset(day, latitude, longitude);
        Calendar day2 = (Calendar) day.clone();
        if (sunriseSunset[1].get(Calendar.HOUR_OF_DAY) < day.get(Calendar.HOUR_OF_DAY)) {
            day2.add(Calendar.DATE, 1);
            sunriseSunset = SunriseSunset.getSunriseSunset(day2, latitude, longitude);
        }
        return sunriseSunset[1];
    }

    /**
     * Gets milis to next sunrise.
     * It calculates next sunrise considering provided date and time
     * If date and time provided is older than sunrise it adds +1 day to date and calculates again
     * Finally, tunrns result into miliseconds
     *
     * @param day       the day Calendar object on ISO format
     * @param latitude  the latitude
     * @param longitude the longitude
     * @return the milis to next sunrise
     */
    public static long getMilisToNextSunrise(Calendar day, double latitude, double longitude) {
        Calendar sunriseSunset = getNextSunrise(day, latitude, longitude);
        return sunriseSunset.getTimeInMillis();
    }

    /**
     * Gets milis to next sunset.
     * It calculates next sunset considering provided date and time
     * If date and time provided is older than sunset it adds +1 day to date and calculates again
     * Finally, tunrns result into miliseconds
     *
     * @param day       the day Calendar object on ISO format
     * @param latitude  the latitude
     * @param longitude the longitude
     * @return the milis to next sunset
     */
    public static long getMilisToNextSunset(Calendar day, double latitude, double longitude) {
        Calendar sunriseSunset = getNextSunset(day, latitude, longitude);
        return sunriseSunset.getTimeInMillis();
    }

    /**
     * Date to utc.
     * Converts provided calendar into UTC format
     * ISO yyyy-MM-dd'T'HH:mm:ss.SSSZ
     *
     * @param day the local time on ISO format
     * @return date on UTC format
     */

    public static Calendar dateToUTC(String day) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(day);
        ZonedDateTime zonedDateTimeUTC = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        return (Calendar) GregorianCalendar.from(zonedDateTimeUTC);
    }

    /**
     * Time to utc.
     * Converts provided time into UTC format
     * Time Format HH
     *
     * @param time the time to convert
     * @param timeZone the time zone of time to convert
     * @return hours on UTC format
     */

    public static String timeToUTC(String time, String timeZone) {
        String isoDate = "2019-11-20T" + time + ":43:01";
        LocalDateTime isoDateLDT = LocalDateTime.parse(isoDate);
        ZonedDateTime isoDateZLDT = isoDateLDT.atZone(ZoneId.of(timeZone));
        Calendar dayTimeZoned = dateToUTC(String.valueOf(isoDateZLDT));
        return String.valueOf(dayTimeZoned.get(Calendar.HOUR));
    }

}
