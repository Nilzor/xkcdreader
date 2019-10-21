package xkcdreader.nilsnett.com.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_browse.*
import retrofit2.Call
import retrofit2.Response
import xkcdreader.nilsnett.com.R
import xkcdreader.nilsnett.com.domainmodels.ComicInfo
import xkcdreader.nilsnett.com.helpers.XkcdPageAdapter
import xkcdreader.nilsnett.com.networking.content.XkcdContentApi
import xkcdreader.nilsnett.com.networking.content.XkcdContentNetworkData

class BrowseActivity : AppCompatActivity() {
    val TAG = "Browse"
    val contentApi = XkcdContentApi.createInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)

        // Call the API to get the Number of the latest published comic
        contentApi.getMostRecentXkcd().enqueue(object: retrofit2.Callback<XkcdContentNetworkData> {
            override fun onResponse(call: Call<XkcdContentNetworkData>, response: Response<XkcdContentNetworkData>) {
                val data = ComicInfo.fromNetworkModel(response.body())
                viewPager.adapter = XkcdPageAdapter(supportFragmentManager, data.number)
            }

            override fun onFailure(call: Call<XkcdContentNetworkData>, t: Throwable) {
                Toast.makeText(this@BrowseActivity, getString(R.string.fatal_error), Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Loading XKCD data failed", t)
            }
        })
    }
}
