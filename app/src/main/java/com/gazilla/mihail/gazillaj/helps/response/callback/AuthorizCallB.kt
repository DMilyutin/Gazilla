package com.gazilla.mihail.gazillaj.helps.response.callback

import com.gazilla.mihail.gazillaj.pojo.UserWithKeys

interface AuthorizCallB {
    fun userWithKeyCallBack(userWithKeys: UserWithKeys)
    fun errorCallBack(error: String)
}