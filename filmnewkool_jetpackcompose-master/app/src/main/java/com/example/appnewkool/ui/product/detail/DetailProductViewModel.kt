package com.example.appnewkool.ui.product.detail

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
class DetailProductViewModel @Inject constructor(private val detailProductRepository: DetailProductRepository) :
    BaseViewModel() {
    var token by mutableStateOf<String?>("")

    init {
        fetchData()
    }

    override fun fetchData() {
        parentJob = viewModelScope.launch(handler) {
            token = detailProductRepository.getToken()
        }
    }

    var product by mutableStateOf<Product?>(null)
        private set

    fun getProduct(idProduct: Int) {
        parentJob = viewModelScope.launch(handler) {
            isLoading = true
            val item = detailProductRepository.getProduct(idProduct)
            product = item
        }
        onJobFinish()
    }
}