package xkcdreader.nilsnett.com

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_comic.*
import retrofit2.Call
import retrofit2.Response
import xkcdreader.nilsnett.com.networking.content.XkcdContentApi
import xkcdreader.nilsnett.com.networking.content.XkcdContentNetworkData

class ComicFragment(comicNumber: Int = -1) : Fragment() {
    val contentApi = XkcdContentApi.createInstance()

    val TAG = "ComicFragment"
    val COMIC_NO_PARAM = "comic_no"
    var comicNo: Int = -1

    init {
        val args = Bundle()
        args.putInt(COMIC_NO_PARAM, comicNumber)
        setArguments(args)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        comicNo = arguments!!.getInt(COMIC_NO_PARAM)
        Log.d(TAG, "OnCreate for XKCD $comicNo")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "OnCreateView for XKCD $comicNo")
        return inflater.inflate(R.layout.fragment_comic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "OnViewCreated for XKCD $comicNo")
        super.onViewCreated(view, savedInstanceState)
        loadImage()
    }

    private fun loadImage() {
        contentApi.getXkcd(comicNo).enqueue(object: retrofit2.Callback<XkcdContentNetworkData> {
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
            Log.d(TAG, "Loading image for XKCD $comicNo")
            Glide.with(this).load(imageUri).into(xkcdImage)
        } catch (ex: Exception) {
            Log.e(TAG, "Loading XKCD image failed", ex)
        }
    }
}