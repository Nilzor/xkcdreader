package xkcdreader.nilsnett.com.networking.content

import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class XkcdContentApiTest {
    @Test
    fun testContentApiItem614() {
        val api = XkcdContentApi.createInstance()
        val result = api.getXkcd(614).execute()
        assertTrue(result.isSuccessful)
        assertEquals("Woodpecker", result.body()!!.safe_title)
    }
}
