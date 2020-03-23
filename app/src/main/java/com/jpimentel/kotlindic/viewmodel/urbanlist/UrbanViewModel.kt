package com.jpimentel.kotlindic.viewmodel.urbanlist

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.jpimentel.kotlindic.model.ListItem
import com.jpimentel.kotlindic.model.UrbanResponse
import com.jpimentel.kotlindic.network.ApiClient
import com.jpimentel.kotlindic.util.RxSingleSchedulers
import com.jpimentel.kotlindic.viewmodel.CustomViewModel
import com.jpimentel.kotlindic.viewmodel.urbanlist.adapters.UrbanAdapter
import io.reactivex.disposables.CompositeDisposable

class UrbanViewModel(context: Context) : CustomViewModel() {

    private var disposable: CompositeDisposable? = CompositeDisposable()
    val data: ArrayList<ListItem> = ArrayList()
    private var rxSingleSchedulers: RxSingleSchedulers = RxSingleSchedulers.DEFAULT
    private var apiService: ApiClient = ApiClient()
    val adapter: UrbanAdapter = UrbanAdapter(context, data)

    @VisibleForTesting
    constructor(
        apiClient: ApiClient,
        rxSingleSchedulers: RxSingleSchedulers,
        context: Context
    ) : this(context) {
        this.apiService = apiClient
        this.rxSingleSchedulers = rxSingleSchedulers
    }


    fun loadData( term : String) {
        onLoading()
        data.clear()
        disposable?.add(
            apiService.getDefinitionList(term)
                .compose(rxSingleSchedulers.applySchedulers())
                .subscribe(this::onSuccess, this::onError)
        )
    }

    private fun onSuccess(urbanModel: UrbanResponse) {
        urbanModel.list?.forEach { items ->
            if (items != null) {
                data.add(items)
                adapter.notifyDataSetChanged()

            }
        }
        dataLoading.value = false
    }

    private fun onError(error: Throwable) {
        errorMessage = error.message ?: ""
        dataLoading.value = false
    }

    private fun onLoading() {
        dataLoading.value = true
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.run {
            clear()
            null
        }
    }

    fun sortList(){
        if(!clicked){
            clicked = true
            adapter.updateData(
                data.sortedWith(compareByDescending{it.thumbs_up}).toList()
            )
        }else{
            clicked = false
            adapter.updateData(
                data.sortedWith(compareByDescending{ it.thumbs_down }).toList()
            )
        }
    }

}
