package com.example.explorer_kotlin.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDao {

    @Query("select count(*) from DatabaseItem")
    fun getCount(): Int

    @Query("select * from DatabaseItem")
    fun getResults(): LiveData<List<DatabaseItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<DatabaseItem>)

    @Query("delete from DatabaseItem")
    fun deleteAll()

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
                    "searchResults").build()
            Log.d("ResultDatabase", "in side synchronized block, db initialized")
        } else {
            Log.d("ResultDatabase", "in side synchronized block, returning prev db instance")
        }
    }
    return INSTANCE

}