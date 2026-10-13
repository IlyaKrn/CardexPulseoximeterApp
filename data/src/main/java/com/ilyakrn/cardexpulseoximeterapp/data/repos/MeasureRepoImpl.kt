package com.ilyakrn.cardexpulseoximeterapp.data.repos

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.ilyakrn.cardexpulseoximeterapp.data.sqlite.SQLiteHelper
import com.ilyakrn.cardexpulseoximeterapp.domain.models.MeasureModel
import com.ilyakrn.cardexpulseoximeterapp.domain.repos.MeasureRepo

class MeasureRepoImpl(private val context: Context) : MeasureRepo {

    override fun createMeasure(measure: MeasureModel) {
        try {
            val db = SQLiteHelper(context).writableDatabase
            val id =  db.insert(SQLiteHelper.MEASURE_TABLE_NAME, null, ContentValues().apply {
                put(SQLiteHelper.MEASURE_COLUMN_DEVICE_ADDRESS, measure.deviceMacAddress)
                put(SQLiteHelper.MEASURE_COLUMN_DEVICE_NAME, measure.deviceName)
                put(SQLiteHelper.MEASURE_COLUMN_TIMESTAMP, measure.timestamp)
                put(SQLiteHelper.MEASURE_COLUMN_PR, measure.PR)
                put(SQLiteHelper.MEASURE_COLUMN_SPO2, measure.SpO2)
            })
            db.close()
            Log.i(this.javaClass.name, "save measure to database successful (measureId=$id, $measure)")
        } catch (e: Exception){
            Log.e(this.javaClass.name, "save measure to database failed: ${e.message}, ($measure)")
        }
    }

    override fun getMeasuresPage(page: Int, pageSize: Int, deviceMacAddress: String?, timestampStart: Long?, timestampEnd: Long?): ArrayList<MeasureModel> {
        try {

            val db = SQLiteHelper(context).readableDatabase
            val cursor = db.query(
                SQLiteHelper.MEASURE_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                "${SQLiteHelper.MEASURE_COLUMN_TIMESTAMP} DESC",
                "$pageSize, ${page*pageSize}"
            )
            val measures = ArrayList<MeasureModel>()
            with(cursor) {
                while (moveToNext()) {
                    measures.add(MeasureModel(
                        getLong(getColumnIndexOrThrow(SQLiteHelper.MEASURE_COLUMN_ID)),
                        getString(getColumnIndexOrThrow(SQLiteHelper.MEASURE_COLUMN_DEVICE_ADDRESS)),
                        getString(getColumnIndexOrThrow(SQLiteHelper.MEASURE_COLUMN_DEVICE_NAME)),
                        getLong(getColumnIndexOrThrow(SQLiteHelper.MEASURE_COLUMN_TIMESTAMP)),
                        getInt(getColumnIndexOrThrow(SQLiteHelper.MEASURE_COLUMN_PR)),
                        getInt(getColumnIndexOrThrow(SQLiteHelper.MEASURE_COLUMN_SPO2))
                    ))
                }
            }
            cursor.close()
            db.close()
            Log.i(this.javaClass.name, "get data of ${measures.size} measures successful (page=$page, pageSize=$pageSize, deviceMacAddress=$deviceMacAddress, timestampStart=$timestampStart, timestampEnd=$timestampEnd)")
            return measures
        } catch (e: Exception){
            Log.e(this.javaClass.name, "get measures data from database failed: ${e.message}")
            return ArrayList()
        }
    }

    override fun deleteMeasures() {
        try {
            val db = SQLiteHelper(context).writableDatabase
            db.delete(SQLiteHelper.MEASURE_TABLE_NAME, "true", null)
            db.close()
            Log.i(this.javaClass.name, "delete all measures successful")
        } catch (e: Exception){
            Log.e(this.javaClass.name, "delete all measures failed: ${e.message}")
        }
    }
}