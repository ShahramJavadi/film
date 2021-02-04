package hsj.shahram.film.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import hsj.shahram.film.ui.ContentFragment
import hsj.shahram.film.ui.FavoriteContentFragment

class ViewPagerAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {


    companion object {

        const val TAB_COUNT = 2

    }

    override fun getCount(): Int {


        return TAB_COUNT
    }

    override fun getItem(position: Int): Fragment {

        return if(position == 0)
            ContentFragment()
        else
            FavoriteContentFragment()

    }

    override fun getPageTitle(position: Int): CharSequence? {

        return if(position == 0)
            "Contents"
        else
            "Favorite"

    }
}