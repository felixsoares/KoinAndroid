package com.felix.nasa.architecture.mvp

import com.felix.nasa.architecture.model.ApodModel

interface MainContract {
    interface Presenter {
        fun getAPOD()
        fun dispose()
    }

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showError()
        fun showData(model: ApodModel)
        fun hideSubViews()
    }
}