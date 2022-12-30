package com.nextgen.beritaku.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nextgen.beritaku.core.data.source.local.entity.NewsEntity
import com.nextgen.beritaku.core.utils.SourceConverter

@Database(
    entities = [NewsEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(SourceConverter::class)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
}