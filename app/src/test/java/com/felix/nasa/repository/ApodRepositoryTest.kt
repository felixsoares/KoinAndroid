package com.felix.nasa.repository

import com.felix.nasa.data.connection.API
import com.felix.nasa.data.model.Response
import com.felix.nasa.data.repository.ApodRepository
import com.felix.nasa.data.repository.ApodRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import java.util.Date

class ApodRepositoryTest {

    lateinit var mRepository: ApodRepository
    private val api: API = mockk(relaxed = true)

    @Before
    fun setup() {
        mRepository = ApodRepositoryImpl(api)
    }

    @Test
    fun testRepositoryCall() {
        val response = Response(Date(), "", null, "", "", "")
        every { api.getAPOD() } returns Observable.just(response)

        mRepository.getAPOD()

        verify { api.getAPOD() }
    }
}