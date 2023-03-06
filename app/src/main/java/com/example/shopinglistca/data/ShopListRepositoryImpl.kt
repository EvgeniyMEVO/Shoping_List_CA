package com.example.shopinglistca.data

import com.example.shopinglistca.domain.ShopItem
import com.example.shopinglistca.domain.ShopListRepository as ShopListRepository

object ShopListRepositoryImpl: ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()

    private var authoIncId = 0

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = authoIncId++
        }
        shopList.add(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItem(shopItem.id)
        shopList.remove(oldShopItem)
        shopList.add(shopItem)
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
    }

    override fun getShopList(): List<ShopItem> {
        // возвращаем копию, т.к. если мы изменим shopList далее, здесь он не изменится
        return shopList.toList()
    }
}