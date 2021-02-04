package hsj.shahram.film.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteContent(
    @PrimaryKey val serverId : Int,
    val image : String?,
    val title: String?,
    val zoneId: Int
)
