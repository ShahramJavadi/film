package hsj.shahram.film.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

object ResponseObject {


    data class ContentListResponse(@SerializedName("Result") val result : ContentResultItem)


    data class ContentResultItem(
        @SerializedName("GetContentList") var contentList: List<ContentList>,
        @SerializedName("TotalPages") val totalPages : Int

    )


    @Parcelize
    data class ContentList(
        @SerializedName("ContentID") val id: Int,
        @SerializedName("ThumbImage") val image: String?,
        @SerializedName("Title")val title: String?,
        @SerializedName("ZoneID")val zoneId: Int,
        @SerializedName("FavoriteStatus")var favoriteStatus: Boolean
    ) : Parcelable{



        companion object{

        val DIFF_CALLBACK: DiffUtil.ItemCallback<ContentList> = object :
            DiffUtil.ItemCallback<ContentList>() {


            override fun areItemsTheSame(oldItem: ContentList, newItem: ContentList): Boolean {

                return oldItem.id.equals(newItem.id)
            }

            override fun areContentsTheSame(oldItem: ContentList, newItem: ContentList): Boolean {
                return oldItem.equals(newItem)            }
        }



        }

    }

    data class ContentDetailResponse(@SerializedName("Result") val result : ContentDetail)

    data class ContentDetail(
        @SerializedName("ContentID") val contentId : Int ,
        @SerializedName("Title") val title : String ,

        @SerializedName("Body") val body : String ,

        @SerializedName("LandscapeImage") val image :String


        )


}