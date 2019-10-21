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

    init {
        arguments = Bundle().also {
            it.putParcelable("data", comicInfo)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        comic = arguments!!.getParcelable("data")
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
    }
}