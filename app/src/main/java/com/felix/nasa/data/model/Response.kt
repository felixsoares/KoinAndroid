package com.felix.nasa.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

class Response(
    val date: Date,
    val explanation: String,
    @SerializedName("hdurl")
    val urlHD: String?,
    @SerializedName("media_type")
    val type: String,
    val title: String,
    @SerializedName("url")
    val url: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Response

        if (date != other.date) return false
        if (explanation != other.explanation) return false
        if (urlHD != other.urlHD) return false
        if (type != other.type) return false
        if (title != other.title) return false
        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        var result = date.hashCode()
        result = 31 * result + explanation.hashCode()
        result = 31 * result + (urlHD?.hashCode() ?: 0)
        result = 31 * result + type.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + url.hashCode()
        return result
    }
}