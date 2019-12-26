package com.felix.nasa.mvvm

import com.felix.nasa.architecture.model.ApodModel
import com.felix.nasa.architecture.mvvm.SecondaryViewModel
import com.felix.nasa.data.model.Response
import com.felix.nasa.data.repository.ApodRepository
import com.felix.nasa.util.RxImmediateSchedulerRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date

class SeondaryViewModelTest {

    @Rule
    @JvmField
    var testScheduler = RxImmediateSchedulerRule()

    lateinit var mViewModel: SecondaryViewModel
    private val mRepository: ApodRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        mViewModel = SecondaryViewModel(mRepository)
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

        val stateTestObserver = mViewModel.state.test()
        val dataTestObserver = mViewModel.data.test()

        mViewModel.getAPOD()

        verify { mRepository.getAPOD() }

        stateTestObserver.assertValues(
            SecondaryViewModel.StateRequest.INITIAL,
            SecondaryViewModel.StateRequest.SUCCESS
        )

        dataTestObserver.assertValue(ApodModel((response)))

        stateTestObserver.dispose()
        dataTestObserver.dispose()
    }

    @Test
    fun testShowError() {
        every { mRepository.getAPOD() } returns Observable.error(Throwable("error"))

        val stateTestObserver = mViewModel.state.test()

        mViewModel.getAPOD()

        verify { mRepository.getAPOD() }

        stateTestObserver.assertValues(
            SecondaryViewModel.StateRequest.INITIAL,
            SecondaryViewModel.StateRequest.ERROR
        )

        stateTestObserver.dispose()
    }
}