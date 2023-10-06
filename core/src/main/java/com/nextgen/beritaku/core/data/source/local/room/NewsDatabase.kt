package com.nextgen.beritaku.core.data.source.local.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nextgen.beritaku.core.data.source.local.entity.NewsDataEntity
import com.nextgen.beritaku.core.data.source.local.entity.NewsEntity
import com.nextgen.beritaku.core.utils.SourceConverter

@Database(
    entities = [NewsEntity::class, NewsDataEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
                     ],
    exportSchema = true
)
@TypeConverters(SourceConverter::class)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun newsDataDao(): NewsDataDao
}