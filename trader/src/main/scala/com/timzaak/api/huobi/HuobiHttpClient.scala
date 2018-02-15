package com.timzaak.api.huobi

import com.timzaak.api.huobi.api.http.HttpHuobiApi

class HuobiHttpClient(
    val accessKey:S,
    val secretKey:S
) extends HttpHuobiApi{

}
