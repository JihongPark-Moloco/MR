package com.example.mr

import com.chad.library.adapter.base.entity.MultiItemEntity

class Bus : MultiItemEntity {
    var first_time: String? = ""
    var second_time: String? = ""
    var line_num: String? = ""

    constructor(first_time: String, second_time: String, line_num:String){
        this.first_time = first_time
        this.second_time = second_time
        this.line_num = line_num
    }

    override fun getItemType(): Int {
        return 0
    }

}