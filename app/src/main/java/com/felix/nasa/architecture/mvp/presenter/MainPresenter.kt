package com.felix.nasa.architecture.mvp.presenter

import com.felix.nasa.architecture.mvp.MainContract
import com.felix.nasa.architecture.model.ApodModel
import com.felix.nasa.data.repository.ApodRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(
    private val mView: MainContract.View,
    private val mRepository: ApodRepository
) : MainContract.Presenter {

    private var composite = CompositeDisposable()

    override fun dispose() {
        composite.clear()
    }

    override fun getAPOD() {
        mView.hideSubViews()
        mView.showLoading()
        composite.add(
            mRepository.getAPOD()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    mView.hideLoading()
                    mView.showData(ApodModel(response))
                }, {
                    mView.hideLoading()
                    mView.showError()
                })
        )
    }
}