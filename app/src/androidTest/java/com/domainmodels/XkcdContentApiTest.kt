package xkcdreader.nilsnett.com.domainmodels

import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import xkcdreader.nilsnett.com.networking.content.XkcdContentNetworkData
import java.util.*

@RunWith(AndroidJUnit4::class)
class ComicInfoTest {
    @Test
    fun testModelConversion() {
        val networkModel = XkcdContentNetworkData(
            "1",
            2,
            "",
            "2018",
            "",
            "SafeTitle",
            "",
            "Description",
            "http://www.vg.no",
            "Title",
            "4"
        )
        val result = ComicInfo.fromNetworkModel(networkModel)
        assertEquals("Title", result.title)
        assertEquals(Date(118, 0, 4), result.publishedDate)
        assertEquals(Uri.parse("http://www.vg.no"), result.uri)
        assertEquals("Description", result.description)
    }
}
