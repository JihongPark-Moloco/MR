package com.example.mr

import com.chad.library.adapter.base.entity.MultiItemEntity

class Message : MultiItemEntity {
    private var itemType: Int = 0
    var senderID: String? = ""
    var text: String? = ""
    var sendTime: String? = ""

    constructor(senderID: String, text: String, sendTime: String, selfID: String) {
        this.senderID = senderID
        this.text = text
        this.sendTime = sendTime

        if(senderID == selfID){
            itemType = 1
        }else{
            itemType = 2
        }
    }

    constructor(msgData: MessageData, selfID: String){
        this.senderID = msgData.senderID
        this.text = msgData.text
        this.sendTime = msgData.sendTime

        if(senderID == selfID){
            itemType = 1
        }else{
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

    fun makeMessageData(): MessageData{
        return MessageData(senderID = senderID!!,text =  text!!,sendTime =  sendTime!!)
    }
}