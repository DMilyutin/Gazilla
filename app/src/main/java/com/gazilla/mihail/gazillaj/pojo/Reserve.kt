package com.gazilla.mihail.gazillaj.pojo

class Reserve(val id: Int, val userId: Int, val tableId: Int, val qty: Int, val hours: Int, val  date: String,
              val name: String, val phone:String, val  commentL: String, val startDate: String) {

    constructor(name: String, phone: String, qty: Int, date: String, hours: Int,
                comment: String ) : this(0, 0, 0, qty, hours, date ,name, phone, comment,date)


}