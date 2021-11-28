package com.lovoo.lovoooffice.core.data.database.model.officebooking

import java.io.Serializable

data class OfficeFilters(
    var id: Int = -1,
    var values: ArrayList<String>? = null,
    var category: String = ""
) : Serializable