package com.example.shopinglist.domain

class RemoveShopItemUseCase (private val shopItemRepository: ShopListRepository) {
    fun removeItem(item: ShopItem){
        shopItemRepository.removeItem(item)
    }
}