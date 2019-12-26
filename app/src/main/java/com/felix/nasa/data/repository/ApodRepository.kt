package com.felix.nasa.data.repository

import com.felix.nasa.data.connection.API
import com.felix.nasa.data.model.Response
import io.reactivex.Observable

interface ApodRepository {
    fun getAPOD(): Observable<Response>
}

class ApodRepositoryImpl(private val api: API) : ApodRepository {

    override fun getAPOD(): Observable<Response> {
        return api.getAPOD()
    }
}