package hsj.shahram.film.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import hsj.shahram.film.di.key.ViewModelKey
import hsj.shahram.film.viewmodel.MainViewModel
import hsj.shahram.film.viewmodel.ViewModelFactory

@Module
abstract class ViewModelModule {

    @Binds
   abstract fun provideFactory(v : ViewModelFactory) : ViewModelProvider.Factory

   @Binds
   @IntoMap
   @ViewModelKey(MainViewModel::class)
   abstract fun provideMainViewModel(m : MainViewModel) : ViewModel


}