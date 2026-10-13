package com.ilyakrn.cardexpulseoximeterapp.domain.usecases

import com.ilyakrn.cardexpulseoximeterapp.domain.repos.MeasureRepo

class DeleteAllMeasuresUseCase(private val measureRepo: MeasureRepo) {

    fun invoke(){
        measureRepo.deleteMeasures()
    }

}