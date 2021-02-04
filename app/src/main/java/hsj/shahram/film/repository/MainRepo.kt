package hsj.shahram.film.repository

import hsj.shahram.film.data.local.AppDatabase
import hsj.shahram.film.data.local.entity.FavoriteContent
import hsj.shahram.film.data.model.RequestObject
import hsj.shahram.film.data.model.ResponseObject
import hsj.shahram.film.data.remote.ApiService
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainRepo @Inject constructor(private val apiService: ApiService
, private val appDatabase: AppDatabase) {




    fun insertItemToFavorite(favoriteContent: FavoriteContent) : Completable
    {

       return appDatabase.getFavoriteContentDao().insertItem(favoriteContent)

    }


    fun deleteFavorite(id : Int) : Completable
    {

      return  appDatabase.getFavoriteContentDao().deleteItem(id)
    }

    fun getFavoriteList() : Single<List<FavoriteContent>>
    {
        return appDatabase.getFavoriteContentDao().getAll()
    }

    fun getFavoriteItem(id : Int) : Single<FavoriteContent?>
    {

        return appDatabase.getFavoriteContentDao().getItem(id)

    }
    fun getContentList(params : RequestObject.ContentListReq) : Single<ResponseObject.ContentListResponse> {

        return apiService.contentList(params)
    }


    fun getContentDetail(param : RequestObject.ContentDetailReq)
    : Single<ResponseObject.ContentDetailResponse>{

        return apiService.contentDetail(param)


    }

}