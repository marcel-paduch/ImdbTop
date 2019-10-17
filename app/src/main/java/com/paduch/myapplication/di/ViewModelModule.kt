package com.paduch.myapplication.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paduch.myapplication.viewmodel.TopMoviesViewModel
import com.paduch.myapplication.viewmodel.VideosViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass


@Module
abstract class ViewModelModule {
    @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    @MapKey
    internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TopMoviesViewModel::class)
    internal abstract fun TopMoviesViewModel(viewModel: TopMoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VideosViewModel::class)
    internal abstract fun VideosViewModel(viewModel: VideosViewModel): ViewModel

}

