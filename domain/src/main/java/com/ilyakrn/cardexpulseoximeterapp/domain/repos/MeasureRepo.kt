package com.ilyakrn.cardexpulseoximeterapp.domain.repos

import com.ilyakrn.cardexpulseoximeterapp.domain.models.MeasureModel

interface MeasureRepo {
    fun createMeasure(measure: MeasureModel)
    fun getMeasuresPage(page: Int = 0, pageSize: Int, deviceMacAddress: String?, timestampStart: Long?, timestampEnd: Long?): ArrayList<MeasureModel>
    fun deleteMeasures()
}