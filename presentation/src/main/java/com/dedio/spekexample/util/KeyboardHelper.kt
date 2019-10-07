package com.dedio.spekexample.util

import android.view.View
import android.view.inputmethod.InputMethodManager

class KeyboardHelper(private val inputManager: InputMethodManager) {

    fun hideKeyboard(view: View?) = inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
}