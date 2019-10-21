package xkcdreader.nilsnett.com

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ortiz.touchview.TouchImageView
import kotlinx.android.synthetic.main.fragment_comic.*
import retrofit2.Call
import retrofit2.Response
import xkcdreader.nilsnett.com.domainmodels.ComicInfo
import xkcdreader.nilsnett.com.helpers.GlideTouchImageViewTarget
import xkcdreader.nilsnett.com.networking.content.XkcdContentApi
import xkcdreader.nilsnett.com.networking.content.XkcdContentNetworkData

class ComicFragment(comicNumber: Int = -1) : Fragment() {
    val contentApi = XkcdContentApi.createInstance()

    val TAG = "ComicFragment"
    val COMIC_NO_PARAM = "comic_no"
    var comicNo: Int = -1

    lateinit var imageView: TouchImageView

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
        imageView = TouchImageView(activity)
        imageContainer.addView(imageView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        loadImage()
    }

    private fun loadImage() {
        showState(loadingView)
        contentApi.getXkcd(comicNo).enqueue(object: retrofit2.Callback<XkcdContentNetworkData> {
            override fun onResponse(call: Call<XkcdContentNetworkData>, response: Response<XkcdContentNetworkData>) {
                try {
                    val comic = ComicInfo.fromNetworkModel(response.body())
                    present(comic)
                } catch (ex: Exception) {
                    Log.e(TAG, "Erronous image data from service:", ex)
                }
            }

            override fun onFailure(call: Call<XkcdContentNetworkData>, t: Throwable) {
                Log.e(TAG, "Loading XKCD data failed", t)
            }
        })
    }

    fun present(comic: ComicInfo) {
        errorView.text = getString(R.string.load_error, comicNo.toString())
        if (comic.dataOk) {
            loadImage(comic.uri)
            title.text = comic.title
        } else {
            showState(errorView)
        }
    }

    fun showState(viewToShow: View) {
        errorView.visibility = View.INVISIBLE
        loadingView.visibility = View.INVISIBLE
        successView.visibility = View.INVISIBLE
        viewToShow.visibility = View.VISIBLE
    }

    private fun loadImage(imageUri: Uri) {
        try {
            Log.d(TAG, "Loading image for XKCD $comicNo")
            Glide.with(this)
                .load(imageUri)
                .addListener(GlideResultHandler())
                .into(GlideTouchImageViewTarget(imageView))
        } catch (ex: Exception) {
            Log.e(TAG, "Loading XKCD image failed", ex)
        }
    }

    /**
     * RequestListener that hides progress bar or shows error view after interpreting result from Glide load
     */
    inner class GlideResultHandler : RequestListener<Drawable> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            Handler().post{
                showState(errorView)
            }
            return false
        }

        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            Handler().post{
                showState(successView)
            }
            return false
        }
    }
}
