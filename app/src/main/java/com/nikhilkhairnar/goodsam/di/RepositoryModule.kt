package com.nikhilkhairnar.goodsam.di

import com.nikhilkhairnar.goodsam.data.repository.AuthRepositoryImpl
import com.nikhilkhairnar.goodsam.data.repository.UserRepositoryImpl
import com.nikhilkhairnar.goodsam.domain.repository.AuthRepository
import com.nikhilkhairnar.goodsam.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}