package bm.app.data.utils

import bm.app.R

data class Logo(val id: Int, val desc: String)

data class Category(val logo: Logo, val name: String)

const val truckLogoID = R.drawable.logo

val categoryList = listOf(
    Category(Logo(truckLogoID, "Truck"), "Open Truck"),
    Category(Logo(truckLogoID, "Truck"), "Covered Truck"),
    Category(Logo(truckLogoID, "Truck"), "Cement Truck"),
    Category(Logo(truckLogoID, "Truck"), "Trailer Van")
)
