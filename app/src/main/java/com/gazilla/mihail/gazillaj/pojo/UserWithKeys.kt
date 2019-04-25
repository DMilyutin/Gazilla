package com.gazilla.mihail.gazillaj.pojo

class UserWithKeys(var id: Int, var name: String, var phone: String, val email: String, var sum: Int, var score: Int,
                   var level: Int, var publickey: String, var privatekey: String, var refererLink: String, var favorites: IntArray){

    constructor() : this( 0, "", "", "", 0, 0, 0, "", "", "", IntArray(0))
}