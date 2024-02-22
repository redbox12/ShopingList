package com.example.shopinglist.domain

class ShopItem (
    val text: String,
    val count: Int,
    val enable: Boolean,
    var id: Int = UNDEFINED_ID
){
    companion object{
        const val UNDEFINED_ID: Int = -1
    }
}