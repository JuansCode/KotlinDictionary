package com.jpimentel.kotlindic.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


open class CustomViewModel : ViewModel() {
    val dataLoading = MutableLiveData(false)
    var errorMessage: String = ""
    var clicked : Boolean = false
}