package com.example.explorer_kotlin.model

import android.os.Parcelable
import com.example.explorer_kotlin.database.DatabaseItem

import kotlinx.parcelize.Parcelize

@Parcelize
data class Item (
        val data: List<Data>,
        val links: List<Link>

        ) : Parcelable

data class NetworkResultContainer(val itemList: List<Item>)


fun NetworkResultContainer.asDatabaseModel(): Array<DatabaseItem> {

        return itemList.map {
                DatabaseItem(
                        nasa_id = it.data[0].nasa_id,
                        date_created = it.data[0].date_created,
                        description =  it.data[0].description,
                        title = it.data[0].title,
                        location = it.data[0].location,
                        secondary_creator = it.data[0].secondary_creator,
                        href = it.links[0].href
                        )
        }.toTypedArray()

}
