package com.nextgen.beritaku.favorite

import com.nextgen.beritaku.core.domain.model.NewsDataItem
import com.nextgen.beritaku.core.domain.usecase.NewsDataInteractor
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import com.nextgen.beritaku.core.utils.Constants
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val favoriteModule = module {
    factory<NewsUseCase<NewsDataItem>>(named(Constants.NEWS_DATA)) { NewsDataInteractor(get(
        named(
            Constants.NEWS_DATA
        )
    )) }
    viewModel { FavoriteViewModel(get(named(Constants.NEWS_DATA))) }
}