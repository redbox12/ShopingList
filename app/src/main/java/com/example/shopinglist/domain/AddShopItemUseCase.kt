package com.example.shopinglist.domain

class AddShopItemUseCase (private val shopListRepository: ShopListRepository){
    fun addShopItem(item: ShopItem){
        shopListRepository.addShopItem(item)
    }
}