package com.example.appnewkool.data.database.entities

import com.example.appnewkool.data.model.Product

fun ProductEntity.toProduct(): Product {
    return Product(
        id = this.id,
        tenXe = this.tenXe,
        hangXe = this.hangXe,
        kinhLai = this.kinhLai,
        suonTruoc = this.suonTruoc,
        suonSau = this.suonSau,
        khoangSau = this.khoangSau,
        kinhHau = this.kinhHau,
        tamGiac = this.tamGiac,
        noc = this.noc,
        image = this.image
    )
}

fun Product.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        tenXe = this.tenXe,
        hangXe = this.hangXe,
        kinhLai = this.kinhLai,
        suonSau = this.suonSau,
        suonTruoc = this.suonTruoc,
        khoangSau = this.khoangSau,
        kinhHau = this.kinhHau,
        tamGiac = this.tamGiac,
        noc = this.noc,
        image = this.image
    )
}


fun List<ProductEntity>.toListProduct(): List<Product>{
    return this.map { it.toProduct() }
}

fun List<Product>.toListProductEntity(): List<ProductEntity>{
    return this.map { it.toProductEntity() }
}

