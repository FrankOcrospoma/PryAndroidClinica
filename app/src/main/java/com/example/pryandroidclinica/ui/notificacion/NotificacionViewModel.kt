package com.example.pryandroidclinica.ui.notificacion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificacionViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Noti Fragment"
    }
    val text: LiveData<String> = _text
}