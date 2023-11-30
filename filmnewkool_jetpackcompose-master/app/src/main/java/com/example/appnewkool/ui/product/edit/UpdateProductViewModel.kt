package com.example.appnewkool.ui.product.edit

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
class UpdateProductViewModel @Inject constructor(private val updateProductRepository: UpdateProductRepository) :
    BaseViewModel() {
    var isSuccess by mutableStateOf(false)

    var product by mutableStateOf(
        Product(
            null, "", null, null, null, null,
            null, null, null, null, null
        )
    )
        private set

    var isLaunched by mutableStateOf(false)

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

    fun updateProduct(id: Int, product: Product) {
        toast = null
        parentJob = viewModelScope.launch {
            try {
                val result = updateProductRepository.updateProductRemote(id, product)
                if(result.code == "200"){
                    isSuccess = true
                    toast = result.message.toString()
                }
            }catch (e:Exception){
                toast = "có lỗi xảy ra"
            }
        }
        onJobFinish()
    }

    fun getProduct(idProduct: Int) {
        parentJob = viewModelScope.launch(handler) {
            isLoading = true
            val item = updateProductRepository.getProduct(idProduct)
                product = item
            isLaunched = true
        }
        onJobFinish()
    }
}