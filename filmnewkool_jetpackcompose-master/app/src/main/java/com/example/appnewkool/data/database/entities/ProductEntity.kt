package com.example.appnewkool.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(@PrimaryKey val id: Int?,
                         val tenXe: String,
                         val hangXe: String?,
                         val kinhLai: String?,
                         val suonTruoc: String?,
                         val suonSau: String?,
                         val khoangSau: String?,
                         val kinhHau: String?,
                         val tamGiac: String?,
                         val noc: String?,
                         val image: String?,
) {
}