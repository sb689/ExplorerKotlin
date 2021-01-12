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
        val href: String,
)

fun List<DatabaseItem>.asDomainModel(): List<Item> {

    val g_data = map {
      Data(
                nasa_id = it.nasa_id,
                date_created = it.date_created,
                description = it.description,
                title = it.title,
                location = it.location,
                secondary_creator = it.secondary_creator

        )}.toList()

    val g_links = map{Link(href = it.href)}.toList()

    return map { Item(
            data = g_data,
            links = g_links
    ) }

}
