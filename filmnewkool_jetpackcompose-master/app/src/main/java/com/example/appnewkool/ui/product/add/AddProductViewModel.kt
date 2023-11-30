package com.example.appnewkool.ui.product.add

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.appnewkool.data.base.BaseViewModel
import com.example.appnewkool.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val addProductRepository: AddProductRepository) :
    BaseViewModel() {
    var isSuccess by mutableStateOf(false)

    var product by mutableStateOf(
        Product(null, "", null, null, null, null,
            null, null, null, null, null)
    )
        private set

    fun onChangeTextTenXe(text: String) {
        product = product.copy(tenXe = text)
    }
    fun onChangeTextHangXe(text: String) {
        product = product.copy(hangXe = text)
    }
    fun onChangeTextKinhLai(text: String) {
        product = product.copy(kinhLai = text)
    }

    fun onChangeTextSuonTruoc(text: String) {
        product = product.copy(suonTruoc = text)
    }

    fun onChangeTextSuonSau(text: String) {
        product = product.copy(suonSau = text)
    }

    fun onChangeTextKhoangSau(text: String) {
        product = product.copy(khoangSau = text)
    }

    fun onChangeTextKinhHau(text: String) {
        product = product.copy(kinhHau = text)
    }

    fun onChangeTextTamGiac(text: String) {
        product = product.copy(tamGiac = text)
    }

    fun onChangeTextNoc(text: String) {
        product = product.copy(noc = text)
    }

    fun createUser(){
        toast = null
        isLoading = true
        parentJob = viewModelScope.launch(handler) {
            try {
                val response = addProductRepository.createProduct(product)
                if (response.code == "200"){
                    isSuccess = true
                    toast = response.message.toString()
                }
            } catch (e: Exception){
                toast = "có lỗi xảy ra"
            }

        }
        onJobFinish()
    }
}