package com.ilyakrn.cardexpulseoximeterapp.domain.repos

import com.ilyakrn.cardexpulseoximeterapp.domain.models.MeasureModel

interface MeasureRepo {
    fun createMeasure(measure: MeasureModel)
    fun getMeasures(): ArrayList<MeasureModel>
    fun deleteMeasures()
}