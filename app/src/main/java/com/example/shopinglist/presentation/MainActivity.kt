package com.example.shopinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinglist.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.listItem.observe(this){
            shopListAdapter.submitList(it)
        }
    }

   private fun setupRecyclerView(){
        val rvList = findViewById<RecyclerView>(R.id.rv_shop_item)
        shopListAdapter = ShopListAdapter()
        with(rvList){
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(ShopListAdapter.ENABLE_VIEW, ShopListAdapter.MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(ShopListAdapter.DISABLE_VIEW, ShopListAdapter.MAX_POOL_SIZE)
        }
        setupClickLongListener()
      //  setupClickListner()
        setupSwipeListner(rvList)
    }

    private fun setupSwipeListner(rvList: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.removeShopItem(item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvList)
    }

    private fun setupClickListner() {
//        shopListAdapter.onShopItemClickListener = {
//            Log.d("Click", it.toString())
//            viewModel.changeShopItem(it)
//        }

    }

    private fun setupClickLongListener() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeShopItem(it)
            //viewModel.removeShopItem(it)
            Log.d("ClickLong", it.toString())
        }
    }

}