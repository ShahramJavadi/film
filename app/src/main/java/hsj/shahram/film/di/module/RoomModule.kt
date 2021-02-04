package hsj.shahram.film.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import hsj.shahram.film.data.local.AppDatabase
import hsj.shahram.film.util.Const
import javax.inject.Singleton

@Module
class RoomModule {


    @Provides
    @Singleton
    fun provideRoomDb(c : Context) :AppDatabase{

        return Room.databaseBuilder(c  ,AppDatabase::class.java , Const.DATA_BASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    }
}