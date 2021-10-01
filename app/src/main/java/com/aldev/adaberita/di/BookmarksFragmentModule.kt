package com.aldev.adaberita.di

import com.aldev.adaberita.ui.bookmarks.BookmarksFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BookmarksFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeBookmarksFragmentInjector(): BookmarksFragment
}