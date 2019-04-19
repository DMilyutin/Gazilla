package com.gazilla.mihail.gazillaj.kotlin.helps.response.callback

import com.gazilla.mihail.gazillaj.kotlin.pojo.UserWithKeys

interface AuthorizCallB {
    fun userWithKeyCallBack(userWithKeys: UserWithKeys)
    fun errorCallBack(error: String)
}