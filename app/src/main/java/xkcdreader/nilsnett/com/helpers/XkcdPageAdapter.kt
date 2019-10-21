package xkcdreader.nilsnett.com.helpers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import xkcdreader.nilsnett.com.views.ComicFragment

class XkcdPageAdapter(fm: FragmentManager, val maxCount: Int) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        val comicNumber = maxCount - position
        return ComicFragment(comicNumber)
    }

    override fun getCount(): Int {
        return maxCount
    }
}