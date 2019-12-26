package com.felix.nasa.data.modules

import com.felix.nasa.architecture.mvp.MainContract
import com.felix.nasa.architecture.mvp.presenter.MainPresenter
import com.felix.nasa.architecture.mvp.view.MainActivity
import com.felix.nasa.architecture.mvvm.SecondaryActivity
import com.felix.nasa.architecture.mvvm.SecondaryViewModel
import com.felix.nasa.data.connection.RestConfig
import com.felix.nasa.data.repository.ApodRepository
import com.felix.nasa.data.repository.ApodRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val baseModule = module {
    single { RestConfig.get() }
    single<ApodRepository> { ApodRepositoryImpl(get()) }

    scope(named<MainActivity>()) {
        factory<MainContract.Presenter> { (view: MainContract.View) ->
            MainPresenter(view, get())
        }
    }

    scope(named<SecondaryActivity>()) {
        viewModel { SecondaryViewModel(get()) }
    }
}