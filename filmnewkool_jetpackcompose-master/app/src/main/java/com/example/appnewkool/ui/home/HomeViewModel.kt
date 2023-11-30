package com.example.appnewkool.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.appnewkool.data.base.BaseViewModel
import com.example.appnewkool.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    BaseViewModel() {

    var token by mutableStateOf<String?>("")

    var state by mutableStateOf(ProductsState())



    init {
        fetchData()
    }

    override fun fetchData() {
        parentJob = viewModelScope.launch(handler) {
            isLoading = true
            token = homeRepository.getToken()
            homeRepository.getListProduct().collect {
                state = state.copy(listProduct = it)
            }
        }
        onJobFinish()
    }

//    fun execute(products: List<Product>, query: String): List<Product> {
//        if (query.isBlank()) {
//            return products
//        }
//        return products.filter {
//            it.tenXe.trim().lowercase().contains(query.lowercase()) ||
//                    it.hangXe?.trim()?.lowercase()?.contains(query.lowercase()) == true
//        }
//    }


    fun onEvent(event: ProductsListingsEvent) {
        when (event) {
            is ProductsListingsEvent.Refresh -> {
                parentJob = viewModelScope.launch(handler) {
                    toast = null
                    val products = homeRepository.getProductAndSaveFromRemote()
                    if (products.isNotEmpty()) {
                        homeRepository.getListProduct().collect {
                            state = state.copy(listProduct = it)
                        }
                    }

                }
                onJobFinish({toast = "dữ liệu đã được đồng bộ"})
            }
            is ProductsListingsEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                parentJob?.cancel()
                parentJob = viewModelScope.launch {
                    getProductList(state.searchQuery.lowercase(), event = EventGetData.Search)
                }
                onJobFinish()
            }

            is ProductsListingsEvent.OnSortQueryChange -> {
                state = state.copy(sortQuery = event.query)
                parentJob?.cancel()
                parentJob = viewModelScope.launch {
                    getProductList(state.sortQuery.lowercase(), EventGetData.Sort)
                }
                onJobFinish()
            }
        }
    }

    private fun getProductList(
        query: String,
        event: EventGetData
    ) {
        viewModelScope.launch {
            homeRepository
                .searchData(query, event)
                .collect { state = state.copy(listProduct = it) }
        }
    }
}


data class ProductsState(
    val listProduct: List<Product> = emptyList(),
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val sortQuery: String = ""
)

sealed class ProductsListingsEvent {
    object Refresh : ProductsListingsEvent()
    data class OnSearchQueryChange(val query: String) : ProductsListingsEvent()
    data class OnSortQueryChange(val query: String) : ProductsListingsEvent()
}

sealed class EventGetData {
    object Search : EventGetData()
    object Sort : EventGetData()
}