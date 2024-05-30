package com.sknau.choosecheese

data class LoginData(
    var username : String ?= null,
    var password : String ?= null
)

data class LoginBackendResponse(

    val access_token : String,
    val token_type : String
)