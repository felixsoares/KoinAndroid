package com.felix.nasa.model

import com.felix.nasa.architecture.model.ApodModel
import com.felix.nasa.data.model.Response
import org.junit.Assert
import org.junit.Test
import java.util.Date

class ApodModelTest {

    @Test
    fun testAllFields() {
        val urlHD = "http://myurl.com/hd/"
        val response = Response(
            Date(),
            "This is explanation",
            urlHD,
            "image",
            "this is title",
            "http://myurl.com"
        )

        val model = ApodModel(response)

        Assert.assertTrue(model.hasImage())
        Assert.assertNotNull(model.getUrl())
        Assert.assertEquals(model.getUrl(), urlHD)
        Assert.assertNotNull(model.getTitle())
        Assert.assertNotNull(model.getDescription())
    }

    @Test
    fun testNotImageResponse() {
        val url = "http://myurl.com"
        val response = Response(
            Date(),
            "This is explanation",
            null,
            "video",
            "this is title",
            url
        )

        val model = ApodModel(response)

        Assert.assertFalse(model.hasImage())
        Assert.assertNotNull(model.getUrl())
        Assert.assertEquals(model.getUrl(), url)
        Assert.assertNotNull(model.getTitle())
        Assert.assertNotNull(model.getDescription())
    }
}