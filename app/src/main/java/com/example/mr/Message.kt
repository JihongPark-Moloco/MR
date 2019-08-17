package com.example.mr

import android.util.Log
import com.chad.library.adapter.base.entity.MultiItemEntity
import org.json.JSONObject

class Message : MultiItemEntity {
    private var itemType: Int = 0
    var send_time: String? = ""
    var sender: String? = ""
    var msg: String? = ""

    constructor(send_time: String, sender: String, msg: String, selfID: String, senderID: String) {
        this.send_time = send_time
        this.sender = sender
        this.msg = msg

        if (senderID == selfID) {
            itemType = 1
        } else {
            itemType = 2
        }
    }

    constructor(item: JSONObject, selfID: String) {
        this.send_time = item["send_timed"].toString()
        this.sender = item["sender"].toString()
        this.msg = item["msg"].toString()
        var senderID = item["sender"].toString()

        if (senderID == selfID) {
            itemType = 1
        } else {
            itemType = 2
        }
    }


    override fun getItemType(): Int {
        return itemType
    }

    companion object {
        val ME = 1
        val FRIEND = 2
    }
}