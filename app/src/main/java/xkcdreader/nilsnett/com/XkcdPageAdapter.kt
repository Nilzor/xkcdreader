package xkcdreader.nilsnett.com

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class XkcdPageAdapter(fm: FragmentManager, val maxCount: Int) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        val comicNumber = position + 1
        return ComicFragment(comicNumber)
    }

    override fun getCount(): Int {
        return maxCount
    }
}