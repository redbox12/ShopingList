package com.example.shopinglist.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopinglist.data.ShopListRepositoryImpl
import com.example.shopinglist.domain.AddShopItemUseCase
import com.example.shopinglist.domain.EditShopItemUseCase
import com.example.shopinglist.domain.GetShopItemUseCase
import com.example.shopinglist.domain.ShopItem

class ShopItemViewModal : ViewModel() {
    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _errorInputText = MutableLiveData<Boolean>()
    val errorInputText: LiveData<Boolean>
        get() = _errorInputText

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _finishedActivity = MutableLiveData<Unit>()
    val finishedActivity: LiveData<Unit>
        get() = _finishedActivity

    fun addShopItem(inputText: String?, inputCount: String?) {
        resetErrorInputCount()
        resetErrorInputText()

        val text = parseText(inputText)
        val count = parseCount(inputCount)
        val isValidateInput = validateInput(text, count)

        if (isValidateInput) {
            val shopItem = ShopItem(text = text, count = count, false)
            addShopItemUseCase.addShopItem(shopItem)
            finishWork()
        }
    }

    fun editShopItem(inputText: String?, inputCount: String?) {
        val text = parseText(inputText)
        val count = parseCount(inputCount)
        val isValidate = validateInput(text, count)
        if (isValidate) {
          _shopItem.value?.let{
              val item = it.copy(text = text, count = count, true)
              editShopItemUseCase.editShopItem(item)
              finishWork()
          }
        }
    }

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    fun getShopItemForId(id: Int) {
        _shopItem.value = getShopItemUseCase.getShopItem(id)
    }

    fun resetErrorInputText() {
        _errorInputText.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun validateInput(text: String, count: Int): Boolean {
        var isValidateData = true
        if (text.isBlank()) {
            _errorInputText.value = true
            isValidateData = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            isValidateData = false
        }
        return isValidateData

    }

    private fun parseText(inputText: String?): String {
        return inputText?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun finishWork() {
        _finishedActivity.value = Unit
    }

}