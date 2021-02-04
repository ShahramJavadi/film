package hsj.shahram.film

import android.app.Application
import hsj.shahram.film.di.component.AppComponent
import hsj.shahram.film.di.component.DaggerAppComponent

class AppController : Application() {


    val appComponent : AppComponent by lazy {


        DaggerAppComponent.factory().create(this)


    }


    override fun onCreate() {
        super.onCreate()
    }
}