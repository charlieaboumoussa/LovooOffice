package com.lovoo.lovoooffice.core.domain.model

import java.io.Serializable

data class LovooFact(
    var title : String? = null,
    var text: String? = null,
    var imagesUrl: ArrayList<String>? = null
): Serializable