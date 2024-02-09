package com.example.shopinglist.domain

interface ShopListRepository {

    fun addShopItem(item: ShopItem)
    fun editItemShop(id: Int)
    fun getItemShopForId(id: Int): ShopItem
    fun getShopList(): List<ShopItem>
    fun removeItem(item: ShopItem)

}