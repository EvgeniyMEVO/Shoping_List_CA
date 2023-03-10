package com.example.shopinglistca.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shopinglistca.domain.ShopItem
import com.example.shopinglistca.domain.ShopListRepository as ShopListRepository

object ShopListRepositoryImpl: ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()

    private val shopList = sortedSetOf<ShopItem>({o1,o2 -> o1.id.compareTo(o2.id)})

    private var authoIncId = 0

    init {
        for (i in 0 until 1000){
            val item = ShopItem("Name $i", i, true)
            addShopItem(item)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = authoIncId++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItem(shopItem.id)
        shopList.remove(oldShopItem)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
            // find может вернуть null значения, поэтому необходимо обработать это
            // в данном случае вызовем исключение RuntimeException
        } ?: throw RuntimeException("Element with id $shopItemId not found")
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        // возвращаем копию, т.к. если мы изменим shopList далее, здесь он не изменится
        return shopListLD
    }

    private fun updateList(){
        //сделаем копию чтобы поток данных всегда содержал снимок текущего состояния shopList, а не ссылку на исходный объект shopList
        shopListLD.value = shopList.toList()
    }
}