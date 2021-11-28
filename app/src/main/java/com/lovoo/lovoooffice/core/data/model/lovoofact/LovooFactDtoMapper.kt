package com.lovoo.lovoooffice.core.data.model.lovoofact

import com.lovoo.lovoooffice.core.domain.model.LovooFact
import com.lovoo.lovoooffice.core.domain.util.DomainMapper

class LovooFactDtoMapper : DomainMapper<LovooFactDto, LovooFact> {
    override fun mapToDomainModel(model: LovooFactDto): LovooFact {
        return LovooFact().apply {
            this.title = model.title
            this.text = model.text
            this.imagesUrl = model.images
        }
    }

    override fun mapFromDomainModel(domainModel: LovooFact): LovooFactDto {
        return LovooFactDto().apply {
            this.title = domainModel.title
            this.text = domainModel.text
            this.images = domainModel.imagesUrl
        }
    }
}