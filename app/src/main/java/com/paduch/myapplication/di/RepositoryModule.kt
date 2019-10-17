package com.paduch.myapplication.di

import com.paduch.myapplication.data.remote.service.ImdbService
import com.paduch.myapplication.data.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledThreadPoolExecutor
import javax.inject.Singleton
@Module
class RepositoryModule{
    @Provides
    internal fun resultRepository(
        service: ImdbService,
        executor: ExecutorService
    ): MoviesRepository {
        return MoviesRepository(service, executor)
    }

    @Singleton
    @Provides
    internal fun provideExecutor(): ExecutorService {
        return Executors.newSingleThreadExecutor()
    }
}
