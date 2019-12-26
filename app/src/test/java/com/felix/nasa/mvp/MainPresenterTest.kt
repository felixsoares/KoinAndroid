package com.felix.nasa.mvp

import com.felix.nasa.architecture.model.ApodModel
import com.felix.nasa.architecture.mvp.MainContract
import com.felix.nasa.architecture.mvp.presenter.MainPresenter
import com.felix.nasa.data.model.Response
import com.felix.nasa.data.repository.ApodRepository
import com.felix.nasa.util.RxImmediateSchedulerRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date

class MainPresenterTest {

    @Rule
    @JvmField
    var testScheduler = RxImmediateSchedulerRule()

    private lateinit var mPresenter: MainContract.Presenter

    private val mRepository: ApodRepository = mockk(relaxed = true)
    private val mView: MainContract.View = mockk(relaxed = true)

    @Before
    fun setup() {
        mPresenter = MainPresenter(mView, mRepository)
    }

    @Test
    fun testShowData() {
        val response = Response(
            Date(),
            "This is explanation",
            "http://myurl.com/hd/",
            "image",
            "this is title",
            "http://myurl.com"
        )
        every { mRepository.getAPOD() } returns Observable.just(response)

        mPresenter.getAPOD()

        verifySequence {
            mView.hideSubViews()
            mView.showLoading()
            mView.hideLoading()
            mView.showData(ApodModel(response))
        }
    }

    @Test
    fun testShowError() {
        every { mRepository.getAPOD() } returns Observable.error(Throwable("error"))

        mPresenter.getAPOD()

        verifySequence {
            mView.hideSubViews()
            mView.showLoading()
            mView.hideLoading()
            mView.showError()
        }
    }
}