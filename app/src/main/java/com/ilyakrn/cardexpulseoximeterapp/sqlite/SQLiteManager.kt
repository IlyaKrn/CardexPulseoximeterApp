package com.ilyakrn.cardexpulseoximeterapp.sqlite

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.ilyakrn.cardexpulseoximeterapp.models.MeasureModel

class SQLiteManager(private val context: Context) {

    fun createMeasure(measure: MeasureModel): Long{
        try {
            val db = SQLiteHelper(context).writableDatabase
            val id =  db.insert(SQLiteHelper.MEASURE_TABLE_NAME, null, ContentValues().apply {
                put(SQLiteHelper.MEASURE_COLUMN_DEVICE_ADDRESS, measure.deviceAddress)
                put(SQLiteHelper.MEASURE_COLUMN_DEVICE_NAME, measure.deviceName)
                put(SQLiteHelper.MEASURE_COLUMN_TIMESTAMP, measure.timestamp)
                put(SQLiteHelper.MEASURE_COLUMN_PR, measure.PR)
                put(SQLiteHelper.MEASURE_COLUMN_SPO2, measure.SpO2)
            })
            db.close()
            Log.i(this.javaClass.name, "save measure to database successful (id=$id)")
            return id
        } catch (e: Exception){
            Log.e(this.javaClass.name, "save measure to database failed: ${e.message}")
            return -1
        }
    }

    fun getMeasures(): ArrayList<MeasureModel> {
        try {
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
            Log.i(this.javaClass.name, "get data of ${measures.size} measures successful")
            return measures
        } catch (e: Exception){
            Log.e(this.javaClass.name, "get measures data from database failed: ${e.message}")
            return ArrayList()
        }
    }

    fun deleteMeasure(id: Int) {
        try{
            SQLiteHelper(context).writableDatabase.apply {
                delete(SQLiteHelper.MEASURE_TABLE_NAME, "WHERE ${SQLiteHelper.MEASURE_COLUMN_ID} = $id", null)
                close()
            }
            Log.i(this.javaClass.name, "delete measure with id $id successful")
        } catch (e: Exception){
            Log.e(this.javaClass.name, "delete measure with id $id failed: ${e.message}")
        }
    }

    fun deleteAllMeasures() {
        try {
            SQLiteHelper(context).writableDatabase.apply {
                delete(SQLiteHelper.MEASURE_TABLE_NAME, "true", null)
                close()
            }
            Log.i(this.javaClass.name, "delete all measures successful")
        } catch (e: Exception){
            Log.e(this.javaClass.name, "delete all measures failed: ${e.message}")
        }
    }
}