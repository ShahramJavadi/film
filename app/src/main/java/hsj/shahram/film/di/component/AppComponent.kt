package hsj.shahram.film.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import hsj.shahram.film.di.module.NetworkModule
import hsj.shahram.film.di.module.RoomModule
import hsj.shahram.film.di.module.ViewModelModule
import hsj.shahram.film.ui.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class , RoomModule::class , ViewModelModule::class])

interface AppComponent {


    fun contentFragmentComponent (): ContentFragmentComponent.Builder
    fun favoriteFragmentComponent() : FavoriteFragmentComponent.Builder
    fun contentDetailFragmentComponent():ContentDetailFragmentComponent.Builder
    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory
    {


        fun create(@BindsInstance context: Context) : AppComponent



    }

}