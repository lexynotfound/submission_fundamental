package com.acer.mygithubapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.acer.mygithubapp.FavoriteModel
import com.acer.mygithubapp.dao.FavoriteDao

@Database(entities = [FavoriteModel::class], version = 2, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        fun getDatabase(context: Context): FavoriteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java,
                    "favorite_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS favorite_users_new (" +
                            "login TEXT PRIMARY KEY NOT NULL, " +
                            "avatarUrl TEXT, " +
                            "isFavorite INTEGER NOT NULL DEFAULT 0)"
                )
                db.execSQL(
                    "INSERT INTO favorite_users_new (login, avatarUrl, isFavorite) " +
                            "SELECT login, avatarUrl, isFavorite FROM favorite_users"
                )
                db.execSQL("DROP TABLE IF EXISTS favorite_users")
                db.execSQL("ALTER TABLE favorite_users_new RENAME TO favorite_users")
            }
        }
    }
}
