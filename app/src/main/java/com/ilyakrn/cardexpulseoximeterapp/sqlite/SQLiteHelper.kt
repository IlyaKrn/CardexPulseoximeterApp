package com.ilyakrn.cardexpulseoximeterapp.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(MEASURE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(MEASURE_DROP)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "cardexpulseoximeterapp.db"

        const val MEASURE_TABLE_NAME = "measures"
        const val MEASURE_COLUMN_ID = "id"
        const val MEASURE_COLUMN_DEVICE_ADDRESS = "deviceAddress"
        const val MEASURE_COLUMN_DEVICE_NAME = "deviceName"
        const val MEASURE_COLUMN_TIMESTAMP = "timestamp"
        const val MEASURE_COLUMN_PR = "pr"
        const val MEASURE_COLUMN_SPO2 = "spo2"
        const val MEASURE_CREATE = "CREATE TABLE IF NOT EXISTS $MEASURE_TABLE_NAME (" +
                "$MEASURE_COLUMN_ID INTEGER PRIMARY KEY," +
                "$MEASURE_COLUMN_DEVICE_ADDRESS TEXT," +
                "$MEASURE_COLUMN_DEVICE_NAME TEXT," +
                "$MEASURE_COLUMN_TIMESTAMP TEXT," +
                "$MEASURE_COLUMN_PR INTEGER," +
                "$MEASURE_COLUMN_SPO2 INTEGER" +
                ")"
        const val MEASURE_DROP = "DROP TABLE IF EXISTS $MEASURE_TABLE_NAME"
    }
}