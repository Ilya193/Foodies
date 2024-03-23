package ru.ikom.foodies.presentation

import androidx.lifecycle.ViewModel
import ru.ikom.foodies.core.Navigation
import ru.ikom.foodies.core.Screen

class MainViewModel(
    private val navigation: Navigation<Screen>,
) : ViewModel() {

    fun read() = navigation.read()
    fun coup() = navigation.coup()
}