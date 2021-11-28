package com.lovoo.lovoooffice.core.domain.model

import java.io.Serializable

data class Office(
    var id: String = "",
    var name : String? = null,
    var department: String? = null,
    var type: String? = null,
    var roomNumber: String? = null,
    var officeLevel: Int? = null,
    var lovooFact: LovooFact? = null
): Serializable