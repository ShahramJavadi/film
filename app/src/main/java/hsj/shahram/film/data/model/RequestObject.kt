package hsj.shahram.film.data.model

import com.google.gson.annotations.SerializedName

object RequestObject{


    data class ContentListReq (val request : ContentListReqBody)

    data class ContentListReqBody(
        @SerializedName("RequestType") val requestType : Int,
        @SerializedName("PageSize") val pageSize : Int,
        @SerializedName("PageIndex") val pageIndex : Int?,
        @SerializedName("OrderBy") val orderBy : String = "createdate",
        @SerializedName("Order") val order : String = "desc"

        )



    data class ContentDetailReq (val request : ContentDetail)

    data class ContentDetail(
        @SerializedName("RequestID") val requestType : Int

    )



}