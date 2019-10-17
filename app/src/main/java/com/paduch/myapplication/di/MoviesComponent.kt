package com.paduch.myapplication.di

import com.paduch.myapplication.view.DetailedMovieFragment
import com.paduch.myapplication.view.TopMoviesFragment

import javax.inject.Singleton

import dagger.Component

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class, RepositoryModule::class])
interface MoviesComponent {

    fun inject(topMoviesFragment: TopMoviesFragment)
    fun injectDetailed(detailedMovieFragment: DetailedMovieFragment)
}
