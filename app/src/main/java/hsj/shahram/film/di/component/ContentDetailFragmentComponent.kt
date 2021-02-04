package hsj.shahram.film.di.component

import dagger.Subcomponent
import hsj.shahram.film.ui.ContentDetailFragment

@Subcomponent
interface ContentDetailFragmentComponent {

    fun inject(contentDetailFragment: ContentDetailFragment)


    @Subcomponent.Builder
    interface Builder{

        fun build() : ContentDetailFragmentComponent

    }
}