package com.example.shopinglist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopinglist.data.ShopListRepositoryImpl//это не должно зжесь быть
import com.example.shopinglist.domain.AddShopItemUseCase
import com.example.shopinglist.domain.EditShopItemUseCase
import com.example.shopinglist.domain.GetShopItemUseCase
import com.example.shopinglist.domain.GetShopListUseCase
import com.example.shopinglist.domain.RemoveShopItemUseCase
import com.example.shopinglist.domain.ShopItem

class MainViewModel : ViewModel(){

    //Временное решение, посколько ещё не работал с инъекцией зависимости
    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItem = AddShopItemUseCase(repository)

    var listItem = getShopListUseCase.getShopList()

    fun changeShopItem(item: ShopItem){
        val newItem = item.copy(enable = !item.enable)
        editShopItemUseCase.editShopItem(newItem)
    }

    fun removeShopItem(item: ShopItem){
        removeShopItemUseCase.removeShopItem(item)
    }

    fun addShopItem(count: Int){
        val item = ShopItem(count=count, text = "sf", enable = true)
        addShopItem.addShopItem(item)
    }


}