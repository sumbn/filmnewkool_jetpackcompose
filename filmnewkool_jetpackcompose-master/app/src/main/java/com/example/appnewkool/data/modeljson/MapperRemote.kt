package com.example.appnewkool.data.modeljson

import com.example.appnewkool.data.model.Product

fun ProductRemote.toProduct(): Product {
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

fun Product.toProductRemote(): ProductRemote {
    return ProductRemote(
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


fun List<ProductRemote>.toListProduct(): List<Product>{
    return this.map { it.toProduct() }
}

fun List<Product>.toListProductEntity(): List<ProductRemote>{
    return this.map { it.toProductRemote() }
}