package com.nextgen.beritaku.core.utils

import com.nextgen.beritaku.core.data.source.local.entity.NewsEntity
import com.nextgen.beritaku.core.data.source.remote.response.ArticlesItem
import com.nextgen.beritaku.core.domain.model.NewsModel

object DataMapper {

    fun mapResponseToEntity(input: List<ArticlesItem>): List<NewsEntity>{
        val newsList = ArrayList<NewsEntity>()
        input.map {
            val news = NewsEntity(
                publishedAt = it.publishedAt,
                author = it.author,
                urlToImage = it.urlToImage,
                description = it.description,
                source = it.source,
                title = it.title,
                url = it.url,
                content = it.content,
                isFavorite = false
            )
            newsList.add(news)
        }
        return newsList
    }

    fun entityToDomain(input: List<NewsEntity>): List<NewsModel> =
        input.map {
            NewsModel(
                publishedAt = it.publishedAt,
                author = it.author.toString(),
                urlToImage = it.urlToImage.toString(),
                description = it.description.toString(),
                source = it.source!!,
                title = it.title.toString(),
                url = it.url.toString(),
                content = it.content.toString(),
                isFavorite = false
            )
        }

    fun domainToEntity(input: NewsModel): NewsEntity =
        NewsEntity(
            publishedAt = input.publishedAt,
            author = input.author.toString(),
            urlToImage = input.urlToImage.toString(),
            description = input.description.toString(),
            source = input.source!!,
            title = input.title.toString(),
            url = input.url.toString(),
            content = input.content.toString(),
            isFavorite = false
        )
}