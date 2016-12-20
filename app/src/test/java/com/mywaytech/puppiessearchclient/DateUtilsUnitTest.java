package com.mywaytech.puppiessearchclient;

import android.os.Build;
import android.util.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nemesis1346 on 5/12/2016.
 */
//@RunWith(MockitoJUnitRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class DateUtilsUnitTest {

    @Test
    public void date_isCorrect() throws Exception {

        ShadowLog.setupLogging();
        ShadowLog.stream = System.out;

        //SECOND TEST
        long timestamp = Long.parseLong("1021161600") * 1000L;
        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date netDate = (new Date(timestamp));
        Log.i("date1: ", "" + sdf.format(netDate));

    }


    @Test
    public void test_isCorrect()throws  Exception{

        ShadowLog.setupLogging();
        ShadowLog.stream = System.out;

        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        long timestamp = Long.parseLong(ts) * 1000L;
        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date netDate = (new Date(timestamp));

        Log.i("date2: ", "" + sdf.format(netDate));

    }


}
