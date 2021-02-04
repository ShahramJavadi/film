package hsj.shahram.film.data.remote

import hsj.shahram.film.data.model.RequestObject
import hsj.shahram.film.data.model.ResponseObject
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

@POST("request.asmx/GetContentList")
 fun contentList(@Body contentListReq: RequestObject.ContentListReq)
 : Single<ResponseObject.ContentListResponse>




 @POST("request.asmx/GetContent")
 fun contentDetail(@Body contentDetialReq: RequestObject.ContentDetailReq)
         : Single<ResponseObject.ContentDetailResponse>


}