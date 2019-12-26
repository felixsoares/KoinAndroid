package com.felix.nasa.architecture.mvvm

import androidx.lifecycle.ViewModel
import com.felix.nasa.architecture.model.ApodModel
import com.felix.nasa.data.repository.ApodRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class SecondaryViewModel(
    private val repository: ApodRepository
) : ViewModel() {

    enum class StateRequest {
        SUCCESS, ERROR, INITIAL
    }

    private val compositeDisposable = CompositeDisposable()
    val state = PublishSubject.create<StateRequest>()
    val data = PublishSubject.create<ApodModel>()

    fun getAPOD() {
        state.onNext(StateRequest.INITIAL)
        compositeDisposable.add(
            repository.getAPOD()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    state.onNext(StateRequest.SUCCESS)
                    data.onNext(ApodModel((response)))
                }, {
                    state.onNext(StateRequest.ERROR)
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}