package com.team4.sajochamchi.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.team4.sajochamchi.data.dao.ItemDao
import com.team4.sajochamchi.data.model.video.SaveItem


@Database(entities = [SaveItem::class], version = 1)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun getItemDao() : ItemDao

    companion object {
        private var INSTANCE: ItemDatabase? = null
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) { }
        }

        fun getDatabase(context: Context) : ItemDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    ItemDatabase::class.java,
                    "item_database"
                ).addMigrations(MIGRATION_1_2)
                .build()
            }
            return INSTANCE as ItemDatabase
        }
    }
}