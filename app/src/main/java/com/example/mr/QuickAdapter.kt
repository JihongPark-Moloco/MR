package com.example.mr

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter

class QuickAdapter(msgList: ArrayList<Message>) :
    BaseMultiItemQuickAdapter<Message, BaseViewHolder>(msgList) {

    init {
        addItemType(Message.ME, R.layout.rc_item_message_user)
        addItemType(Message.FRIEND, R.layout.rc_item_message_friend)
    }

    override fun convert(helper: BaseViewHolder?, item: Message?) {
        when(helper!!.itemViewType){
            Message.ME -> helper!!.setText(R.id.textContentUser, item!!.text)
            Message.FRIEND -> helper!!.setText(R.id.textContentFriend, item!!.text)
        }
    }


}

