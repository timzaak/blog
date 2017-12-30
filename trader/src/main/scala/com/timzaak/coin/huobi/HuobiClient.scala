package com.timzaak.coin.huobi

import com.timzaak.coin.huobi.api.HttpHuobiApi

class HuobiClient(
    val accessKey:S,
    val secretKey:S
) extends HttpHuobiApi{

}
