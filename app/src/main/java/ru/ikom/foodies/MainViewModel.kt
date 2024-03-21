package ru.ikom.foodies

import androidx.lifecycle.ViewModel

class MainViewModel(
    private val navigation: Navigation<Screen>
) : ViewModel() {

    fun read() = navigation.read()
    fun coup() = navigation.coup()
}