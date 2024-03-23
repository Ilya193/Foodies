package ru.ikom.feature_catalog.presentation

import ru.ikom.common.Router

interface CatalogRouter : Router {
    fun openDetails(data: String)
    fun openBasket()
}