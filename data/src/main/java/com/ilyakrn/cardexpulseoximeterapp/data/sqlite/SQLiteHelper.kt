package com.ilyakrn.cardexpulseoximeterapp.data.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(MEASURE_CREATE)
        Log.i(this.javaClass.name, "database create: $MEASURE_CREATE")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(MEASURE_DROP)
        Log.i(this.javaClass.name, "database drop: $MEASURE_DROP")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "cardexpulseoximeterapp.db"

        const val MEASURE_TABLE_NAME = "measures"
        const val MEASURE_COLUMN_ID = "measure_id"
        const val MEASURE_COLUMN_DEVICE_ADDRESS = "device_mac_ddress"
        const val MEASURE_COLUMN_DEVICE_NAME = "device_name"
        const val MEASURE_COLUMN_TIMESTAMP = "timestamp"
        const val MEASURE_COLUMN_PR = "pr"
        const val MEASURE_COLUMN_SPO2 = "spo2"
        const val MEASURE_CREATE = "CREATE TABLE IF NOT EXISTS $MEASURE_TABLE_NAME (" +
                "$MEASURE_COLUMN_ID INTEGER PRIMARY KEY," +
                "$MEASURE_COLUMN_DEVICE_ADDRESS TEXT," +
                "$MEASURE_COLUMN_DEVICE_NAME TEXT," +
                "$MEASURE_COLUMN_TIMESTAMP INTEGER," +
                "$MEASURE_COLUMN_PR INTEGER," +
                "$MEASURE_COLUMN_SPO2 INTEGER" +
                ")"
        const val MEASURE_DROP = "DROP TABLE IF EXISTS $MEASURE_TABLE_NAME"
    }
}