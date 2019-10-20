package xkcdreader.nilsnett.com

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_browse.*
import retrofit2.Call
import retrofit2.Response
import xkcdreader.nilsnett.com.networking.content.XkcdContentApi
import xkcdreader.nilsnett.com.networking.content.XkcdContentNetworkData

class BrowseActivity : AppCompatActivity() {
    val TAG = "Browse"

    val contentApi = XkcdContentApi.createInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)

        loadDefaultImage()
    }

    private fun loadDefaultImage() {
        contentApi.getMostRecentXkcd().enqueue(object: retrofit2.Callback<XkcdContentNetworkData> {
            override fun onResponse(call: Call<XkcdContentNetworkData>, response: Response<XkcdContentNetworkData>) {
                try {
                    val uri = Uri.parse(response.body()!!.img)
                    loadImage(uri)
                } catch (ex: Exception) {
                    Log.e(TAG, "Erronous image data from service:", ex)
                }
            }

            override fun onFailure(call: Call<XkcdContentNetworkData>, t: Throwable) {
                Log.e(TAG, "Loading XKCD data failed", t)
            }
        })
    }

    private fun loadImage(imageUri: Uri) {
        try {
            Glide.with(this).load(imageUri).into(xkcdImage)
        } catch (ex: Exception) {
            Log.e(TAG, "Loading XKCD image failed", ex)
        }
    }
}
