package hsj.shahram.film.di.module

import dagger.Module
import dagger.Provides
import hsj.shahram.film.data.model.ResponseObject
import hsj.shahram.film.ui.adapter.ContentListAdapter
import hsj.shahram.film.ui.adapter.FavoriteListAdapter

@Module
class MainModule {

    @Provides
    fun provideContentAdapter(listener : ContentListAdapter.MyClickListener) : ContentListAdapter{

        return ContentListAdapter(listener)
    }


    @Provides
    fun provideFavoriteAdaper(listener : FavoriteListAdapter.OnFavoriteItemListener) : FavoriteListAdapter{

        return FavoriteListAdapter(listener)
    }
}