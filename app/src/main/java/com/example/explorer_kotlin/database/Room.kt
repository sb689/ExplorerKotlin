package com.example.explorer_kotlin.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDao {

    @Query("select * from databaseitem")
    fun getVideos(): LiveData<List<DatabaseItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg video: DatabaseItem)

}


@Database(entities = [DatabaseItem::class], version = 1)
abstract class ResultDatabase : RoomDatabase() {
    abstract val itemDao: ItemDao
}


private lateinit var INSTANCE: ResultDatabase

fun getDatabase(context: Context): ResultDatabase {

    synchronized(ResultDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                    ResultDatabase::class.java,
                    "videos").build()
        }
    }
    return INSTANCE


}