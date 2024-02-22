package com.example.shopinglist.domain

interface ShopListRepository {
    fun addShopItem(item: ShopItem)
    fun editShopItem(item: ShopItem)
    fun getShopItem(id: Int): ShopItem
    fun getShopList(): List<ShopItem>
    fun removeShopItem(item: ShopItem)
}