package com.example.mr

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter

class BusListAdapter(busList: ArrayList<Bus>) :
    BaseMultiItemQuickAdapter<Bus, BaseViewHolder>(busList) {
    override fun convert(helper: BaseViewHolder?, item: Bus?) {
        when(helper!!.itemViewType){
            0 -> {
                helper.setText(R.id.first_time, item!!.first_time)
                helper.setText(R.id.second_time, item!!.first_time)
                helper.setText(R.id.line_name, item!!.line_num)
            }
        }
    }

    init {
        addItemType(0, R.layout.item_bus)
    }
}

