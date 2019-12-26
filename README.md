
# KoinAndroid
Testes com Koin utilizando MVP e MVVP

## Bibliotecas

Para este estudo foram utlizadas as seguintes bibliotecas:
1) Glide : para renderezização de imagens e cacheamento

    ```
    implementation 'com.github.bumptech.glide:glide:4.10.0'  
	annotationProcessor 'com.github.bumptech.glide:compiler:4.10.0'
	```

2) RX + Retrofit + Gson : para realizar requests assíncronas, desserializador das respostas e configurações para chamadas de api

    ```
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'  
	implementation 'io.reactivex.rxjava2:rxjava:2.2.9'  
	implementation 'com.squareup.retrofit2:retrofit:2.5.0'  
	implementation 'com.squareup.retrofit2:adapter-rxjava2:2.5.0'  
	implementation 'com.squareup.retrofit2:converter-gson:2.5.0'  
	implementation 'com.google.code.gson:gson:2.8.5'  
	implementation 'com.squareup.okhttp3:logging-interceptor:3.12.0'
	```

3) Android lifecycle : para criação de ViewModels

    ```
    implementation "android.arch.lifecycle:extensions:1.1.1"
    ```

4) Koin : para injeção de dependencias

    ```
    implementation 'org.koin:koin-android:2.0.1'  
	implementation "org.koin:koin-android-scope:2.0.1"  
	implementation "org.koin:koin-java:2.0.1"  
	implementation "org.koin:koin-androidx-viewmodel:2.0.1"
	```

5) Mockk : para testes unitários

    ```
    testImplementation "io.mockk:mockk:1.9.3"
    ```

## Configurações

1) Chamar o método Koin para inicializar no projeto

```
startKoin {  
  androidContext(this@MyApplication)  
    modules(  
        baseModule  
  )  
}
```

2) Criar os módulos com seus respectivos escopos

```
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
 ```

3) Fazer o `inject` na View

``` 
private val mPresenter by lazy {  
	this.currentScope.get<MainContract.Presenter> {  
	  parametersOf(this@MainActivity)  
	}  
}
```

ou em caso de ViewModel

``` 
private val viewModel by this.currentScope.viewModel(this, SecondaryViewModel::class)
```
