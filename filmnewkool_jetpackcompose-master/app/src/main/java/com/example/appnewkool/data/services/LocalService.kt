package com.example.appnewkool.data.services

import com.example.appnewkool.data.database.dao.ProductDao
import com.example.appnewkool.data.database.entities.ProductEntity
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalService @Inject constructor(private val productDao: ProductDao) {
    fun getAllProduct() = flow { emit(productDao.getAll()) }


    suspend fun getProduct(id: Int) = productDao.getProduct(id)


    suspend fun saveListProduct(product: List<ProductEntity>) =
        productDao.insertAll(product)

    suspend fun deleteAllProduct() = productDao.deleteAll()

    suspend fun insertProduct(productEntity: ProductEntity) =
        productDao.insertProduct(productEntity)

    fun sortProducts(hangXe: String) = flow { emit(productDao.sortHangXe(hangXe)) }

    fun searchProducts(query:String) = flow {emit(productDao.searchProductListing(query))  }
}