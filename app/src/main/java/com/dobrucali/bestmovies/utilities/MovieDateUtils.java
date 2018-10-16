package com.dobrucali.bestmovies.utilities;

import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class MovieDateUtils {

    public static long getNormalizedUtcMsForToday() {
        long utcNowMillis = System.currentTimeMillis();
        TimeZone currentTimeZone = TimeZone.getDefault();
        long gmtOffsetMillis = currentTimeZone.getOffset(utcNowMillis);
        long timeSinceEpochLocalTimeMillis = utcNowMillis + gmtOffsetMillis;
        long daysSinceEpochLocal = TimeUnit.MILLISECONDS.toDays(timeSinceEpochLocalTimeMillis);
        return TimeUnit.DAYS.toMillis(daysSinceEpochLocal);
    }

    public static Date getNormalizedUtcDateForToday() {
        long normalizedMilli = getNormalizedUtcMsForToday();
        return new Date(normalizedMilli);
    }
}
