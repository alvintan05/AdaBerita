package com.aldev.adaberita.di

import com.aldev.adaberita.MyApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        HomeFragmentModule::class,
        CategoryFragmentModule::class,
        BookmarksFragmentModule::class
    ]
)
interface AppComponent {
    fun inject(application: MyApplication)
}