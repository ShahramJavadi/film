package hsj.shahram.film.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import hsj.shahram.film.data.local.entity.FavoriteContent
import hsj.shahram.film.util.Const


@Database(entities = [FavoriteContent::class] , version = Const.DATA_BASE_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getFavoriteContentDao() : FavoriteContentDao
}