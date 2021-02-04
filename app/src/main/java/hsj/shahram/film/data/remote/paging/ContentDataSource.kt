package hsj.shahram.film.data.remote.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PageKeyedDataSource
import hsj.shahram.film.data.model.RequestObject
import hsj.shahram.film.data.model.ResponseObject
import hsj.shahram.film.repository.MainRepo
import hsj.shahram.film.util.Resources
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ContentDataSource @Inject constructor(
    private val mainRepository: MainRepo, private val disposable: CompositeDisposable
) :
    PageKeyedDataSource<Int, ResponseObject.ContentList>() {


    private var liveData: MutableLiveData<Resources<ResponseObject.ContentListResponse>>

    private var sourceIndex: Int? = null

    init {

        liveData = MutableLiveData()
    }


    fun getLiveData():MutableLiveData<Resources<ResponseObject.ContentListResponse>>{
        return liveData
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResponseObject.ContentList>
    ) {

        if (sourceIndex == null)
            sourceIndex = 1


        val body: RequestObject.ContentListReqBody =
            RequestObject.ContentListReqBody(2, 10, sourceIndex)

        liveData.postValue(Resources.loading(null))
        disposable.add(mainRepository.getContentList(RequestObject.ContentListReq(body))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
           .flatMap { response ->
                Observable.fromIterable(response.result.contentList).map {
                    mainRepository.getFavoriteItem(it.id)?.subscribeOn(Schedulers.computation())
                        ?.subscribe({ value ->
                            if (value != null) {
                                it.favoriteStatus = true

                            }
                        },

                            { throwable -> })

                    return@map it
                }.toList().map {
                    response.result.contentList = it
                    return@map response
                }
            }
            .subscribe(
                { value ->


                    liveData.postValue(Resources.success(value))

                    sourceIndex = sourceIndex!! + 1


                    callback.onResult(value.result.contentList, null, sourceIndex)
                },
                { error ->
                    liveData.postValue(Resources.error(null, error.message))
                }


            ))
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ResponseObject.ContentList>
    ) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ResponseObject.ContentList>
    ) {

        val body: RequestObject.ContentListReqBody =
            RequestObject.ContentListReqBody(2, 10, params.key)

        liveData.postValue(Resources.loading(null))
        disposable.add(mainRepository.getContentList(RequestObject.ContentListReq(body))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { value ->
                    liveData.postValue(Resources.success(value))




                    callback.onResult(
                        value.result.contentList,
                        if (params.key == value.result.totalPages) null else params.key + 1
                    )
                },
                { error -> liveData.postValue(Resources.error(null, error.message)) }


            ))
    }
}