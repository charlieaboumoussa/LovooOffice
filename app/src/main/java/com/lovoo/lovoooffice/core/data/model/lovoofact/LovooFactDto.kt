package com.lovoo.lovoooffice.core.data.model.lovoofact

import com.google.gson.annotations.SerializedName

class LovooFactDto {

    @SerializedName("title")
    var title : String? = null

    @SerializedName("text")
    var text: String? = null

    @SerializedName("images")
    var images: ArrayList<String>? = null
}