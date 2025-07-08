package com.ilyakrn.cardexpulseoximeterapp.sqlite

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.ilyakrn.cardexpulseoximeterapp.models.MeasureModel

class SQLiteManager(private val context: Context) {

    fun createMeasure(measure: MeasureModel): Long{
        val db = SQLiteHelper(context).writableDatabase
        val id =  db.insert(SQLiteHelper.MEASURE_TABLE_NAME, null, ContentValues().apply {
            put(SQLiteHelper.MEASURE_COLUMN_DEVICE_ADDRESS, measure.deviceAddress)
            put(SQLiteHelper.MEASURE_COLUMN_DEVICE_NAME, measure.deviceName)
            put(SQLiteHelper.MEASURE_COLUMN_TIMESTAMP, measure.timestamp)
            put(SQLiteHelper.MEASURE_COLUMN_PR, measure.PR)
            put(SQLiteHelper.MEASURE_COLUMN_SPO2, measure.SpO2)
        })
        db.close()
        return id
    }

    fun getMeasures(): ArrayList<MeasureModel> {
        val db = SQLiteHelper(context).readableDatabase
        val cursor = db.query(
            SQLiteHelper.MEASURE_TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            "${SQLiteHelper.MEASURE_COLUMN_TIMESTAMP} DESC"
        )
        val measures = ArrayList<MeasureModel>()
        with(cursor) {
            while (moveToNext()) {
                measures.add(MeasureModel(
                    getLong(getColumnIndexOrThrow(SQLiteHelper.MEASURE_COLUMN_ID)),
                    getString(getColumnIndexOrThrow(SQLiteHelper.MEASURE_COLUMN_DEVICE_ADDRESS)),
                    getString(getColumnIndexOrThrow(SQLiteHelper.MEASURE_COLUMN_DEVICE_NAME)),
                    getString(getColumnIndexOrThrow(SQLiteHelper.MEASURE_COLUMN_TIMESTAMP)),
                    getInt(getColumnIndexOrThrow(SQLiteHelper.MEASURE_COLUMN_PR)),
                    getInt(getColumnIndexOrThrow(SQLiteHelper.MEASURE_COLUMN_SPO2))
                ))
            }
        }
        cursor.close()
        db.close()
        return measures
    }

    fun deleteMeasure(id: Int) {
        SQLiteHelper(context).writableDatabase.apply {
            delete(SQLiteHelper.MEASURE_TABLE_NAME, "WHERE ${SQLiteHelper.MEASURE_COLUMN_ID} = $id", null)
            close()
        }
    }

    fun deleteAllMeasures() {
        SQLiteHelper(context).writableDatabase.apply {
            delete(SQLiteHelper.MEASURE_TABLE_NAME, "true", null)
            close()
        }
    }
}