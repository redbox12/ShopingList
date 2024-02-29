package com.example.shopinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shopinglist.R
import com.example.shopinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    //var onShopItemClickListener: ((ShopItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val resView = when (viewType) {
            ENABLE_VIEW -> R.layout.enable_card
            DISABLE_VIEW -> R.layout.disable_card
            else -> throw RuntimeException("Unknown view: $viewType")
        }

        val view: View = LayoutInflater.from(parent.context).inflate(resView, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(shopItemViewHolder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)

        shopItemViewHolder.tvText.text = shopItem.text
        shopItemViewHolder.tvCount.text = shopItem.count.toString()

        shopItemViewHolder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

//        shopItemViewHolder.view.setOnClickListener {
//            onShopItemClickListener?.invoke(shopItem)
//        }


    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)
        return if (shopItem.enable) ENABLE_VIEW else DISABLE_VIEW
    }


    companion object {
        const val ENABLE_VIEW = 100
        const val DISABLE_VIEW = 101
        const val MAX_POOL_SIZE = 10
    }
}