package com.example.appnewkool.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appnewkool.data.database.entities.ProductEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductDao {
    @Query("SELECT * FROM product ORDER BY tenXe ASC")
    fun getAll(): List<ProductEntity>

    @Insert
    suspend fun insertAll(products: List<ProductEntity>)

    @Query("SELECT * FROM product WHERE id = :id")
    suspend fun getProduct(id: Int): ProductEntity

    @Query("DELETE FROM product")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(vararg productEntity: ProductEntity)

    @Query("SELECT * FROM product WHERE hangXe LIKE '%' || :search || '%'")
    fun sortHangXe(search: String?): List<ProductEntity>

    @Query("SELECT * FROM product WHERE LOWER(tenXe) LIKE '%' || LOWER(:query) || '%' OR UPPER(:query) == hangXe")
    fun searchProductListing(query: String?): List<ProductEntity>
}