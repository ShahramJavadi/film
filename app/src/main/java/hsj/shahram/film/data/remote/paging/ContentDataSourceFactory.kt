package hsj.shahram.film.data.remote.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import hsj.shahram.film.data.model.ResponseObject
import hsj.shahram.film.repository.MainRepo
import io.reactivex.disposables.CompositeDisposable


class ContentDataSourceFactory(private val repo: MainRepo, private val disposable: CompositeDisposable) :
    DataSource.Factory<Int, ResponseObject.ContentList>() {


    private  var dataSourceLiveData: MutableLiveData<ContentDataSource>


    init {

        dataSourceLiveData = MutableLiveData()

    }



    fun getLiveData(): MutableLiveData<ContentDataSource>
    {
        return dataSourceLiveData
    }

    override fun create(): DataSource<Int, ResponseObject.ContentList> {
       val  dataSource  = ContentDataSource(repo , disposable)
        dataSourceLiveData.postValue(dataSource)
        return dataSource
    }
}