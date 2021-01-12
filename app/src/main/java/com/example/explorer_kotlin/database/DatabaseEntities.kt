package com.example.explorer_kotlin.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.explorer_kotlin.model.Data
import com.example.explorer_kotlin.model.Item
import com.example.explorer_kotlin.model.Link

@Entity
data class DatabaseItem constructor(

        @PrimaryKey
        val nasa_id: String,
        val date_created: String,
        val description: String,
        val title: String,
        val location: String,
        val secondary_creator: String,
        val href: String
)

//fun List<DatabaseItem>.asDomainModel():List<Item>{
//
//    return map {
//        Data(
//                nasa_id = it.nasa_id,
//
//        )
////        Video(
////                url = it.url,
////                updated = it.updated,
////                title = it.title,
////                description = it.description,
////                thumbnail = it.thumbnail
////        )
//    }
//}