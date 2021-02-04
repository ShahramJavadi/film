package hsj.shahram.film.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hsj.shahram.film.data.local.entity.FavoriteContent
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface FavoriteContentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(f : FavoriteContent) : Completable

    @Query("Select * From FavoriteContent")
    fun getAll(): Single<List<FavoriteContent>>


    @Query("Select * From FavoriteContent Where serverId=:id")
    fun getItem(id : Int) : Single<FavoriteContent?>


    @Query("Delete From FavoriteContent WHERE serverId =:id")
    fun deleteItem(id : Int) : Completable

}