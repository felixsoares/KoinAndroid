package com.felix.nasa.architecture.model

import com.felix.nasa.data.model.Response

class ApodModel(private val response: Response) {

    fun getTitle() = response.title

    fun getUrl() = response.urlHD ?: response.url

    fun hasImage() = response.type == "image"

    fun getDescription() = response.explanation

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ApodModel

        if (response != other.response) return false

        return true
    }

    override fun hashCode(): Int {
        return response.hashCode()
    }
}