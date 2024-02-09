package com.example.shopinglist.domain

class EditShopItemUseCase (private val shopListRepository: ShopListRepository){
    fun editItemShop(id :Int){
        shopListRepository.editItemShop(id);
    }

}