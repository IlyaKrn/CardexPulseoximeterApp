package com.ilyakrn.cardexpulseoximeterapp.domain.usecases

import com.ilyakrn.cardexpulseoximeterapp.domain.models.MeasureModel
import com.ilyakrn.cardexpulseoximeterapp.domain.repos.MeasureRepo

class GetRecentMeasuresByFilterUseCase(private val deviceMacAddress: String?, private val timestampStart: Long?, private val timestampEnd: Long?, private val measureRepo: MeasureRepo) {

    fun invoke(): ArrayList<MeasureModel>{
        return measureRepo.getMeasuresPage(0, 1000, deviceMacAddress, timestampStart, timestampEnd)
    }

}