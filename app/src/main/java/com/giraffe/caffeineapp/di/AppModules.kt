package com.giraffe.caffeineapp.di

import com.giraffe.caffeineapp.screen.cupsize.CupSizeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::CupSizeViewModel)
}