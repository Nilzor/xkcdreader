package xkcdreader.nilsnett.com.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.view_details.*
import xkcdreader.nilsnett.com.R
import xkcdreader.nilsnett.com.domainmodels.ComicInfo

class DetailsFragment(comicInfo: ComicInfo? = null) : BottomSheetDialogFragment() {
    lateinit var comic: ComicInfo

    companion object {
        val DATA_KEY = "data"
    }

    init {
        arguments = Bundle().also {
            it.putParcelable(DATA_KEY, comicInfo)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        comic = arguments!!.getParcelable(DATA_KEY)!!
        return inflater.inflate(R.layout.view_details, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        present()
    }

    fun present() {
        description.text = comic.description
        title.text = comic.title
        publishedDate.text = comic.humanReadableDate()
        transcript.text = comic.transcript
    }
}