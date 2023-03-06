package com.example.shopinglistca.domain

data class ShopItem(
    val name: String,
    val count: Int,
    val enabled: Boolean,
    var id: Int = UNDEFINED_ID
){
    // -1 является магическим числом если его добавить напрямую, поэтому создадим константу
    companion object{
        const val UNDEFINED_ID = -1
    }
}
