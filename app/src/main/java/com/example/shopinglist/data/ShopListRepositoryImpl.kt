package com.example.shopinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shopinglist.domain.ShopItem
import com.example.shopinglist.domain.ShopListRepository
import java.lang.RuntimeException
import kotlin.random.Random

object ShopListRepositoryImpl: ShopListRepository {


    private val listLDShopItem = MutableLiveData<List<ShopItem>>()

    private val listShopItem = sortedSetOf<ShopItem>({o1, o2 -> o1.id.compareTo(o2.id)} )


    private var autoIncrementId: Int = 0

    init {
        for (i in 0 until 100){
            val item = ShopItem("Хлеб $i", i, Random.nextBoolean())
            addShopItem(item)
        }
    }

    override fun addShopItem(item: ShopItem) {
        if (item.id == ShopItem.UNDEFINED_ID){
            item.id = autoIncrementId++
        }
        listShopItem.add(item)
        updateList()
    }

    override fun editShopItem(item: ShopItem) {
        val oldItem = getShopItem(item.id)
        listShopItem.remove(oldItem)
        addShopItem(item)
    }

    override fun getShopItem(id: Int): ShopItem {
        return listShopItem.find {
            it.id == id
        } ?: throw RuntimeException("no find itemShop for id=${id}")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        updateList()
        return listLDShopItem
    }

    override fun removeShopItem(item: ShopItem) {
        listShopItem.remove(item)
        updateList()
    }

    private fun updateList(){
       listLDShopItem.value = listShopItem.toList()
        //listLDShopItem.postValue(listShopItem.toList())
    }
}