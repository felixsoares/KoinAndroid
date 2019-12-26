package com.felix.nasa.data.connection

import com.felix.nasa.data.model.Response
import io.reactivex.Observable
import retrofit2.http.GET

interface API {

    @GET("apod")
    fun getAPOD(): Observable<Response>

}