package xkcdreader.nilsnett.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_browse.*

class BrowseActivity : AppCompatActivity() {
    val TAG = "Browse"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)

        val maxCount = 500 // Todo: Get the number of XKCD comics
        viewPager.adapter = XkcdPageAdapter(supportFragmentManager, maxCount)
    }
}
