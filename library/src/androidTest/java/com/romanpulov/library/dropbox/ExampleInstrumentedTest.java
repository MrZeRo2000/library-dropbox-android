package com.romanpulov.library.dropbox;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.romanpulov.library.dropbox.test", appContext.getPackageName());
    }

    @Test
    public void accessToken() {
        Context context = InstrumentationRegistry.getTargetContext();
        DropboxHelper dropboxHelper = DropboxHelper.getInstance(context);
        assertNotNull(dropboxHelper);
        Log.d("accessToken", "AccessToken = " + dropboxHelper.getAccessToken());
    }
}
