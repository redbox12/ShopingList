package com.example.shopinglist.data

import com.example.shopinglist.domain.ShopItem
import com.example.shopinglist.domain.ShopListRepository
import java.lang.RuntimeException

private val listShopItem = mutableListOf<ShopItem>()
private var autoIncrementId: Int = 0

object ShopListRepositoryImpl: ShopListRepository {
    override fun addShopItem(item: ShopItem) {
        if (item.id == ShopItem.UNDEFINED_ID){
            item.id = autoIncrementId++
        }
        listShopItem.add(item)
    }

    override fun editShopItem(item: ShopItem) {
        val oldItem = getShopItem(item.id)
        removeShopItem(oldItem)
        addShopItem(item)
    }

    override fun getShopItem(id: Int): ShopItem {
        return listShopItem.find {
            it.id == id
        } ?: throw RuntimeException("no find itemShop for id=${id}")
    }

    override fun getShopList(): List<ShopItem> {
        return listShopItem.toList()
    }

    override fun removeShopItem(item: ShopItem) {
        listShopItem.remove(item)
    }
}