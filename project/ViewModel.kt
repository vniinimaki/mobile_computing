package com.example.composetutorial

import androidx.lifecycle.MutableLiveData

class ViewModel {
    val currentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}