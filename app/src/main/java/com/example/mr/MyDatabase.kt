package com.example.mr

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.util.Log
import com.naver.maps.geometry.LatLng
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper
import java.lang.reflect.Constructor


class MyDatabase(context: Context) :
    SQLiteAssetHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    init {
        setForcedUpgrade()
    }

    fun BusTime(season: Int, cam_mr: Boolean): ArrayList<Bus> {
        val db = readableDatabase
        var cur: Cursor? = null

        when (season) {
            NORMAL_ON -> {
                if (cam_mr) {
                    Log.d("cur", "#1")
                    cur = db.rawQuery("select * from LID where LID >= 1 AND LID <= 40", null)
                } else {
                    Log.d("cur", "#2")
                    cur = db.rawQuery("select * from LID where LID >= 41 AND LID <= 80", null)
                }
            }
            NORMAL_CAM_VAC -> {
                if (cam_mr) {
                    Log.d("cur", "#3")
                    cur = db.rawQuery("select * from LID where LID >= 81 AND LID <= 97", null)
                } else {
                    Log.d("cur", "#4")
                    cur = db.rawQuery("select * from LID where LID >= 98 AND LID <= 114", null)
                }
            }
            NORMAL_ALL_VAC, HOLI_ALL -> {
                if (cam_mr) {
                    Log.d("cur", "#5")
                    cur = db.rawQuery("select * from LID where LID >= 115 AND LID <= 131", null)
                } else {
                    Log.d("cur", "#6")
                    cur = db.rawQuery("select * from LID where LID >= 132 AND LID <= 148", null)
                }
            }
        }

        cur!!.moveToFirst()

        var BusList = ArrayList<Bus>()

        do {
            var FirstTime = cur!!.getString(1)
            var SecondTime = cur!!.getString(2)
            var Line = cur!!.getString(3)

            BusList.add(Bus(FirstTime, SecondTime, Line))
        } while (cur!!.moveToNext())

        return BusList
    }

    fun BusLine(first_time: String, second_time: String, line: String): ArrayList<LatLng> {
        var db = readableDatabase

        var cur = db.rawQuery(
            "select LID from LID where FirstTime= \"$first_time\" AND SecondTime = \"$second_time\" AND Line = \"$line\"",
            null
        )

        cur.moveToFirst()

        var LID = cur!!.getString(0)


        cur = db.rawQuery(
            "select Latitude,Longitude from Line where LID=$LID",
            null
        )
        cur.moveToFirst()

        var a = cur!!.getString(0)
        var b =cur!!.getString(1)
        Log.d("cur","1 is $a, 2 is $b")

        var LineList = ArrayList<LatLng>()

        do {
            LineList.add(LatLng(cur!!.getDouble(0), cur!!.getDouble(1)))
        } while (cur!!.moveToNext())

        return LineList
    }

    companion object {
        private val DATABASE_NAME = "storage.db"
        private val DATABASE_VERSION = 3
        var NORMAL_ON = 0
        var NORMAL_CAM_VAC = 1
        var NORMAL_ALL_VAC = 2
        var HOLI_ALL = 3
    }

}