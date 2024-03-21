package ru.ikom.foodies

import androidx.lifecycle.ViewModel

class SplashViewModel(
    private val router: SplashRouter
): ViewModel() {

    fun openCatalog() = router.openCatalog()
}
