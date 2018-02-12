package com.timzaak.coin.huobi

import com.timzaak.coin.huobi.api.http.HttpHuobiApi

class HuobiHttpClient(
    val accessKey:S,
    val secretKey:S
) extends HttpHuobiApi{

}
