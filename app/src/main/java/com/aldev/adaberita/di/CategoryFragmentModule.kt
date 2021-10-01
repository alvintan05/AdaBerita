package com.aldev.adaberita.di

import com.aldev.adaberita.ui.category.CategoryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CategoryFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeCategoryFragmentInjector(): CategoryFragment
}