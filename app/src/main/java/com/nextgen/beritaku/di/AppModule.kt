package com.nextgen.beritaku.di

import com.nextgen.beritaku.auth.login.LoginViewModel
import com.nextgen.beritaku.auth.signup.SignUpViewModel
import com.nextgen.beritaku.core.domain.model.NewsDataItem
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.domain.usecase.NewsDataInteractor
import com.nextgen.beritaku.core.domain.usecase.NewsInteractor
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import com.nextgen.beritaku.core.utils.Constants.NEWS_API
import com.nextgen.beritaku.core.utils.Constants.NEWS_DATA
import com.nextgen.beritaku.detail.DetailViewModel
import com.nextgen.beritaku.explore.ExploreViewModel
import com.nextgen.beritaku.explore.SearchViewModel
import com.nextgen.beritaku.home.HomeViewModel
import com.nextgen.beritaku.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {
    factory<NewsUseCase<NewsModel>>(named(NEWS_API)) { NewsInteractor(get(named(NEWS_API))) }
    factory<NewsUseCase<NewsDataItem>>(named(NEWS_DATA)) { NewsDataInteractor(get(named(NEWS_DATA))) }
}

val viewModelModule = module {
    viewModel{HomeViewModel(get(named(NEWS_DATA)))}
    viewModel { ExploreViewModel(get(named(NEWS_DATA))) }
    viewModel { DetailViewModel(get(named(NEWS_DATA))) }
    viewModel { SearchViewModel(get(named(NEWS_DATA))) }
    viewModel { SignUpViewModel() }
    viewModel { LoginViewModel() }
    viewModel { ProfileViewModel() }
}
