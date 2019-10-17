package com.paduch.myapplication.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paduch.myapplication.data.remote.service.ImdbService
import com.paduch.myapplication.data.repository.MoviesRepository
import com.paduch.myapplication.viewmodel.TopMoviesViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledThreadPoolExecutor
import javax.inject.Provider
import javax.inject.Singleton
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

}

