package com.example.shopinglist.domain

class GetShopItemUseCase (private val shopListRepository: ShopListRepository) {
    fun getItemShopForId(id: Int) : ShopItem{
        return shopListRepository.getItemShopForId(id)
    }
}