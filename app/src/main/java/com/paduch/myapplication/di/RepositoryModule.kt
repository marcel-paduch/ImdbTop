package com.paduch.myapplication.di

import com.paduch.myapplication.data.remote.service.ImdbService
import com.paduch.myapplication.data.repository.MoviesRepository
import com.paduch.myapplication.data.repository.VideosRepository
import dagger.Module
import dagger.Provides
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledThreadPoolExecutor
import javax.inject.Singleton
@Module
class RepositoryModule{
    @Provides
    internal fun movieRepository(
        service: ImdbService,
        executor: ExecutorService
    ): MoviesRepository {
        return MoviesRepository(service, executor)
    }

    @Provides
    internal fun videoRepository(
        service: ImdbService,
        executor: ExecutorService
    ): VideosRepository {
        return VideosRepository(service, executor)
    }

    @Singleton
    @Provides
    internal fun provideExecutor(): ExecutorService {
        return Executors.newSingleThreadExecutor()
    }
}
