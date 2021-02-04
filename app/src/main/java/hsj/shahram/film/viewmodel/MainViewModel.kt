package hsj.shahram.film.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bumptech.glide.load.engine.Resource
import hsj.shahram.film.data.local.entity.FavoriteContent
import hsj.shahram.film.data.model.RequestObject
import hsj.shahram.film.data.model.ResponseObject
import hsj.shahram.film.data.remote.paging.ContentDataSource
import hsj.shahram.film.data.remote.paging.ContentDataSourceFactory
import hsj.shahram.film.repository.MainRepo
import hsj.shahram.film.util.Resources
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(val repository: MainRepo) : ViewModel() {

    private lateinit var contentListLiveData: LiveData<PagedList<ResponseObject.ContentList>>
    private val favoriteListLiveData = MutableLiveData<MutableList<ResponseObject.ContentList>>()
    private val contentDetailLiveData = MutableLiveData<Resources<ResponseObject.ContentDetail>>()
    private val favoriteItemLiveData = MutableLiveData<FavoriteContent>()
    private var dataSourceFactory: ContentDataSourceFactory
    private var contentStatusLiveData: LiveData<Resources<ResponseObject.ContentListResponse>> =
        MutableLiveData()

    private val disposable: CompositeDisposable = CompositeDisposable()


    init {

        dataSourceFactory = ContentDataSourceFactory(repository, disposable)

        configPaging()
        getFavoriteList()
    }


    private fun configPaging() {

        val config: PagedList.Config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(true)
            .build()

        contentListLiveData = LivePagedListBuilder(dataSourceFactory, config)
            .build()



        contentStatusLiveData = Transformations.switchMap(dataSourceFactory.getLiveData()) {

            it.getLiveData()
        }


    }


    private fun getFavoriteList() {


        disposable.add(repository.getFavoriteList().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { list ->
                io.reactivex.Observable.fromIterable(list)
                    .map {
                        ResponseObject.ContentList(
                            it.serverId,
                            it.image,
                            it.title,
                            it.zoneId,
                            true
                        )
                    }.toList()
            }
            .subscribe({

                    value ->
                favoriteListLiveData.postValue(value)
            },
                {
                        throwable -> throwable.message })
        )

    }


    fun getFavoriteById(serverId: Int) {


        disposable.add(
            repository.getFavoriteItem(serverId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ favoriteItemLiveData.postValue(it) }, { error ->
                    favoriteItemLiveData.postValue(
                        null
                    )
                })
        )

    }

    fun deleteFavorite(id: Int) {

        disposable.addAll(
            repository.deleteFavorite(id).subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun insertToFavorite(item: ResponseObject.ContentList) {
        // convert contentList to favorite entity
        val favoriteContent = FavoriteContent(
            serverId = item.id, image = item.image, title = item.title, zoneId = item.zoneId
        )


        disposable.add(
            repository.insertItemToFavorite(favoriteContent)
                .subscribeOn(Schedulers.io()).subscribe()
        )


    }


    fun getContentDetail(contentId: Int) {

        val body = RequestObject.ContentDetail(contentId)


        contentDetailLiveData.postValue(Resources.loading(null))

        disposable.add(
            repository.getContentDetail(RequestObject.ContentDetailReq(body))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ value -> contentDetailLiveData.postValue(Resources.success(value.result)) },
                    { error ->
                        contentDetailLiveData.postValue(
                            Resources.error(
                                null,
                                error.message
                            )
                        )
                    })
        )


    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun getLiveData(): LiveData<PagedList<ResponseObject.ContentList>> {
        return contentListLiveData
    }


    fun favoriteLiveData(): LiveData<MutableList<ResponseObject.ContentList>> {
        return favoriteListLiveData
    }


    fun contentDetailLiveData(): LiveData<Resources<ResponseObject.ContentDetail>> {
        return contentDetailLiveData
    }

    fun favoriteItemLiveData(): LiveData<FavoriteContent> {
        return favoriteItemLiveData
    }

    fun contentStatusLiveData(): LiveData<Resources<ResponseObject.ContentListResponse>> {
        return contentStatusLiveData
    }

}