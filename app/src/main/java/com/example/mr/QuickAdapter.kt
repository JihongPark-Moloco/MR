package com.example.mr

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class QuickAdapter(layoutResId: Int, msgList: ArrayList<Message>) : BaseQuickAdapter<Message, BaseViewHolder>(layoutResId, msgList) {
    override fun convert(helper: BaseViewHolder?, item: Message?) {
        helper!!.setText(R.id.textContentUser, item!!.text)
    }
}