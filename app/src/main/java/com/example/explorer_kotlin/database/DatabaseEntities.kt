package com.example.explorer_kotlin.database

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.explorer_kotlin.model.Data
import com.example.explorer_kotlin.model.Item
import com.example.explorer_kotlin.model.Link

@Entity
data class DatabaseItem constructor(

        @PrimaryKey
        val nasa_id: String,
        val date_created: String?,
        val description: String?,
        val title: String?,
        val location: String?,
        val secondary_creator: String?,
        val href: String?,
)

fun List<DatabaseItem>.asDomainModel(): List<Item> {


    val items = ArrayList<Item>()
    for(i in this)
    {
        val dataList = ArrayList<Data>()
        val data = Data(
                nasa_id = i.nasa_id,
                date_created = i.date_created,
                description = i.description,
                title = i.title,
                location = i.location,
                secondary_creator = i.secondary_creator
        )
        dataList.add(data)
        val links =  ArrayList<Link>()
        val link = Link(href = i.href)
        links.add(link)

        val item = Item(dataList, links)
        items.add(item)

    }
    return items

//    val gData = map {
//        Data(
//                nasa_id = it.nasa_id,
//                date_created = it.date_created,
//                description = it.description,
//                title = it.title,
//                location = it.location,
//                secondary_creator = it.secondary_creator
//
//        )
//    }.toList()
//
//    val gLinks = map { Link(href = it.href) }.toList()
////    if (gData.isEmpty()) {
////        Log.d("asDomainModel", "gData is empty")
////    } else {
////        for (i in gData) {
////            Log.d("asDomainModel", "nasa_id : ${i.nasa_id}")
////        }
////    }
//
//    return map {
//        Item(
//                data = gData,
//                links = gLinks
//        )
//    }

}
