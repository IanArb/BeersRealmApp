package com.example.beersrealmapp.android.di

import com.example.beersrealmapp.BeersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BeersModule {

    @Provides
    fun provideBeersDatabase(): BeersDatabase = BeersDatabase()

}