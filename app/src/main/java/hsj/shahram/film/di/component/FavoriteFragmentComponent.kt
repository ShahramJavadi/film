package hsj.shahram.film.di.component

import dagger.BindsInstance
import dagger.Subcomponent
import hsj.shahram.film.di.module.MainModule
import hsj.shahram.film.di.module.ViewModelModule
import hsj.shahram.film.ui.ContentFragment
import hsj.shahram.film.ui.FavoriteContentFragment
import hsj.shahram.film.ui.adapter.ContentListAdapter
import hsj.shahram.film.ui.adapter.FavoriteListAdapter

@Subcomponent(modules = [MainModule::class])
interface FavoriteFragmentComponent {


    fun inject(fragment: FavoriteContentFragment)

    @Subcomponent.Builder
    interface Builder {

        fun build(): FavoriteFragmentComponent
        fun setFavoriteClickListener(@BindsInstance listener: FavoriteListAdapter.OnFavoriteItemListener): Builder

    }
}