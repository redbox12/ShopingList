package com.example.shopinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.shopinglist.R
import com.example.shopinglist.domain.ShopItem
import com.example.shopinglist.presentation.viewModel.ShopItemViewModal
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {
    private lateinit var viewModel: ShopItemViewModal
    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etText: TextInputEditText
    private lateinit var etCount: TextInputEditText
    private lateinit var buttonSave: Button

    private var screenMode = UNKNOWN_MODE
    private var shopItemId = ShopItem.UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        checkIntent() //проверка на переданые парметры в intent
        viewModel = ViewModelProvider(this)[ShopItemViewModal::class.java]
        initViews()
        addTextChangeListner() //вывод ошибки при вводе
        launchRightMode() //выбор mode для экрана
        observeViewModel() //подписывааемся на LiveData из ViewModel
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun observeViewModel() {
        viewModel.errorInputText.observe(this) {
            if (it) {
                tilName.error = getString(R.string.error_text)
            } else {
                tilName.error = null
            }
        }

        viewModel.errorInputCount.observe(this) {
            if (it) {
                tilCount.error = getString(R.string.error_count)
            } else {
                tilCount.error = null
            }
        }

        viewModel.finishedActivity.observe(this) {
            finish()
        }
    }

    private fun addTextChangeListner() {
        etText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputText()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            viewModel.addShopItem(etText.text.toString(), etCount.text.toString())
        }
    }


    private fun launchEditMode() {
        viewModel.getShopItemForId(shopItemId)
        viewModel.shopItem.observe(this) {
            etText.setText(it.text)
            etCount.setText(it.count.toString())
        }

        buttonSave.setOnClickListener{
            viewModel.editShopItem(etText.text.toString(), etCount.text.toString())
        }
    }

    private fun initViews() {
        tilName = findViewById(R.id.til_name)
        tilCount = findViewById(R.id.til_count)
        etText = findViewById(R.id.et_name)
        etCount = findViewById(R.id.et_count)
        buttonSave = findViewById(R.id.button_save)
    }

    private fun checkIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("No params to screen")
        }

        screenMode = intent.getStringExtra(EXTRA_SCREEN_MODE).toString()
        if (screenMode != MODE_ADD && screenMode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode: $screenMode")
        }
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_ID_SHOP_ITEM)) {
                throw RuntimeException("No param id to shop item")
            }
            shopItemId = intent.getIntExtra(EXTRA_ID_SHOP_ITEM, ShopItem.UNDEFINED_ID)
        }

    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "screen_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val EXTRA_ID_SHOP_ITEM = "extra_id_shop_item"
        private const val UNKNOWN_MODE = ""

        fun newIntentAddShopItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditShopItem(context: Context, idShopItem: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_ID_SHOP_ITEM, idShopItem)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            return intent
        }
    }
}



