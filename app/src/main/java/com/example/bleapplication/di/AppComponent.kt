package com.example.bleapplication.di

import android.app.Application
import com.example.bleapplication.di.module.BuildersModule
import com.example.bleapplication.di.module.DomainModule
import com.example.bleapplication.di.module.PresenterModule
import com.example.bleapplication.di.module.PresenterStateHolderModule
import com.example.bleapplication.presentation.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        PresenterModule::class,
        DomainModule::class,
        BuildersModule::class,
        PresenterStateHolderModule::class,
        AndroidSupportInjectionModule::class,
    ]
)

interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}