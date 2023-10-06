package com.nextgen.beritaku.core.utils

import com.nextgen.beritaku.core.data.source.local.entity.NewsDataEntity
import com.nextgen.beritaku.core.data.source.local.entity.NewsEntity
import com.nextgen.beritaku.core.data.source.remote.response.ArticlesItem
import com.nextgen.beritaku.core.data.source.remote.response.ResultsItem
import com.nextgen.beritaku.core.data.source.remote.response.Source
import com.nextgen.beritaku.core.domain.model.NewsDataItem
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.domain.model.SourceModel

object DataMapper {

    fun mapNewsDataResponseToNewsData(input: ResultsItem): NewsDataItem {
        return NewsDataItem(
            country = input.country,
            creator = input.creator.toString(),
            keywords = input.keywords.toString(),
            imageUrl = input.imageUrl.toString(),
            link = input.link,
            description = input.description,
            language = input.language,
            title = input.title,
            pubDate = input.pubDate,
            content = input.content,
            articleId = input.articleId,
            videoUrl = input.videoUrl.toString(),
            sourcePriority = input.sourcePriority,
            sourceId = input.sourceId,
            category = input.category
        )
    }

    fun mapNewsDataItemToNewsDataEntity(item: NewsDataItem): NewsDataEntity {
        return NewsDataEntity(
            articleId = item.articleId.orEmpty(),
            creator = item.creator,
            imageUrl = item.imageUrl,
            link = item.link,
            description = item.description,
            title = item.title,
            pubDate = item.pubDate,
            content = item.content,
            sourceId = item.sourceId,
            category = item.category,
        )
    }
    fun mapNewsDataEntityToNewsDataItem(item: NewsDataEntity): NewsDataItem {
        return NewsDataItem(
            articleId = item.articleId,
            creator = item.creator,
            imageUrl = item.imageUrl,
            link = item.link,
            description = item.description,
            title = item.title,
            pubDate = item.pubDate,
            content = item.content,
            sourceId = item.sourceId,
            category = item.category,
        )
    }

    fun mapResponseToEntity(input: List<ArticlesItem>, category: String? = null): List<NewsEntity>{
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
                category = category,
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
                author = it.author ?: "No Author",
                urlToImage = it.urlToImage.toString(),
                description = it.description ?: "No Description",
                source = it.source.let {data->
                    SourceModel(
                        data!!.name ?: "No Name" ,
                        data.id.toString()
                    ) },
                title = it.title ?: "No Title",
                url = it.url.toString(),
                content = it.content ?: "No Content",
                category = it.category.toString(),
                isFavorite = it.isFavorite
            )
        }

    fun domainToEntity(input: NewsModel): NewsEntity =
        NewsEntity(
            publishedAt = input.publishedAt,
            author = input.author,
            urlToImage = input.urlToImage,
            description = input.description,
            source = input.source.let { Source(name = it.name, id = it.id) },
            title = input.title,
            url = input.url,
            content = input.content,
            category = input.category,
            isFavorite = false
        )

    fun mapResponseToModel(input: List<ArticlesItem>, category: String? = null): List<NewsModel>{
        val newsList = ArrayList<NewsModel>()
        input.map {
            val news = NewsModel(
                publishedAt = it.publishedAt,
                author = it.author.toString(),
                urlToImage = it.urlToImage.toString(),
                description = it.description.toString(),
                source = it.source.let {soure -> SourceModel(soure?.name.toString(), soure?.id.toString()) },
                title = it.title.toString(),
                url = it.url.toString(),
                content = it.content.toString(),
                category = category.toString(),
                isFavorite = false
            )
            newsList.add(news)
        }
        return newsList
    }
}