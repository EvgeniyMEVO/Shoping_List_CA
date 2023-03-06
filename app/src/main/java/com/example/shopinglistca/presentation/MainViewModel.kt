package com.example.shopinglistca.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopinglistca.data.ShopListRepositoryImpl
import com.example.shopinglistca.domain.DeleteShopItemUseCase
import com.example.shopinglistca.domain.EditingShopItemUseCase
import com.example.shopinglistca.domain.GetShopListUseCase
import com.example.shopinglistca.domain.ShopItem

class MainViewModel: ViewModel() {

    // теперь мы знаем о data слое, это не хорошо, но пока так
    private val repository = ShopListRepositoryImpl

    val shopList = MutableLiveData<List<ShopItem>>()

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editingShopItemUseCase = EditingShopItemUseCase(repository)

    fun getShopList(){
        val list = getShopListUseCase.getShopList()
        shopList.value = list
    }

    fun deleteShopItem(shopItem: ShopItem){
        deleteShopItemUseCase.deleteShopItem(shopItem)
        getShopList()
    }

    fun changeEnabledState(shopItem: ShopItem){
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editingShopItemUseCase.editShopItem(shopItem)
        getShopList()
    }

}