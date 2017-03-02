package com.mywaytech.puppiessearchclient;

import android.os.Build;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nemesis1346 on 2/3/2017.
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class DateSimulationTest {


    public static int daysBetween(Calendar from, Calendar to) {
        Calendar date = (Calendar) from.clone();
        int daysBetween = 0;
        while (date.before(to)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        System.out.println(daysBetween);
        return daysBetween;
    }
    @Test
    public void randomTimeStamp() throws ParseException {
        ShadowLog.setupLogging();
        ShadowLog.stream = System.out;
        //FIRST TEST

        Calendar now = Calendar.getInstance();
        Calendar min = Calendar.getInstance();
        Calendar randomDate = (Calendar) now.clone();
        int minYear = 2012;
        int minMonth = 2;
        int minDay = 18;
        min.set(minYear, minMonth-1, minDay);
        int numberOfDaysToAdd = (int) (Math.random() * (daysBetween(min, now) + 1));
        randomDate.add(Calendar.DAY_OF_YEAR, -numberOfDaysToAdd);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Log.i("now time: ",dateFormat.format(now.getTime()));
        Log.i("min time:",dateFormat.format(min.getTime()));
        Log.i("random time:", dateFormat.format(randomDate.getTime()));

        Log.i("now time timestamp: ",convertStringDateToTimestamp(dateFormat.format(now.getTime())));
        Log.i("min time timestamp:",convertStringDateToTimestamp(dateFormat.format(min.getTime())));
        Log.i("random time timestamp:", convertStringDateToTimestamp(dateFormat.format(randomDate.getTime())));

    }

    public static String convertStringDateToTimestamp(String input) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date netDate = sdf.parse(input);
        long timestampResult=netDate.getTime()/1000L;
        return String.valueOf(timestampResult);
    }
}
