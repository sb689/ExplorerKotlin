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

}
