package com.timzaak.diff

import scala.collection.JavaConverters._
import com.github.difflib.DiffUtils
import org.jsoup.Jsoup

object HtmlDiff extends App {
 new HtmlDiff {}.d
}

trait HtmlDiff {
  def d: Unit = {
    val text1 = s"""
       |<li><a href="#play" onclick="ck_yk('v_19rr4v9gcs',this,'qytw/')" title="《我身后的陶斯》 第1集">1-2集上 繁中</a></li>
       |<li><a href="#play" onclick="ck_yk('v_19rr4v96ic',this,'qytw/')" title="《我身后的陶斯》 第2集">1-2集下 繁中</a></li>
       |<li><a href="#play" onclick="ck_yk('v_19rr4vdu6k',this,'qytw/')" title="《我身后的陶斯》 第3集">3-4集上 繁中</a></li>
       |<li><a href="#play" onclick="ck_yk('v_19rr4vdl3w',this,'qytw/')" title="《我身后的陶斯》 第4集">3-4集下 繁中</a></li>
       |<li><a href="#play" onclick="ck_yk('v_19rr5e5f30',this,'qytw/')" title="《我身后的陶斯》 第5集">5-6集上 繁中</a></li>
       |<li><a href="#play" onclick="ck_yk('v_19rr5e5gxw',this,'qytw/')" title="《我身后的陶斯》 第6集">5-6集下 繁中</a></li>
       |<li><a href="#play" onclick="ck_m3u8('https://www3.yuboyun.com/hls/2018/09/28/enKeatRy/playlist.m3u8',this);" title="《我身后的陶斯》第1-2集">第1-2集</a></li>
       |<li><a href="#play" onclick="ck_m3u8('https://www3.yuboyun.com/hls/2018/09/28/siIfxMky/playlist.m3u8',this);" title="《我身后的陶斯》第3-4集">第3-4集</a></li>
       |<li><a href="#play" onclick="ck_m3u8('https://www3.yuboyun.com/hls/2018/10/04/sPnBvtB3/playlist.m3u8',this);" title="《我身后的陶斯》第5-6集">第5-6集</a></li>
       |<li><a href="#play" onclick="ck_m3u8('https://www3.yuboyun.com/hls/2018/10/05/xZDa4IOX/playlist.m3u8',this);" title="《我身后的陶斯》第7-8集">第7-8集</a></li>
       |<li><a href="#play" onclick="ck_m3u8('https://www3.yuboyun.com/hls/2018/10/12/KNhFPyP4/playlist.m3u8',this);" title="《我身后的陶斯》第9-10集">第9-10集</a></li>
       |<li><a href="#play" onclick="ck_m3u8('https://www3.yuboyun.com/hls/2018/10/12/y3tuEdBu/playlist.m3u8',this);" title="《我身后的陶斯》第11-12集">第11-12集</a></li>
       |<li><a href="#play" onclick="ck_m3u8('https://zkgn.wb699.com/2018/09/28/viAKkvh6Oocho3qY/playlist.m3u8',this);" title="《我身后的陶斯》第1-2集">第1-2集</a></li>
       |<li><a href="#play" onclick="ck_m3u8('https://zkgn.wb699.com/2018/09/28/4lD7PxDvxFlZlJuK/playlist.m3u8',this);" title="《我身后的陶斯》第3-4集">第3-4集</a></li>
       |<li><a href="#play" onclick="ck_m3u8('https://zkcdn.wb699.com/2018/10/04/1h3JV8C24cIW6eVu/playlist.m3u8',this);" title="《我身后的陶斯》第5-6集">第5-6集</a></li>
       |<li><a href="#play" onclick="ck_m3u8('https://zkcdn.wb699.com/2018/10/05/8BLz4jlT6xtttEEZ/playlist.m3u8',this);" title="《我身后的陶斯》第7-8集">第7-8集</a></li>
       |<li><a href="#play" onclick="ck_m3u8('https://zkgn.wb699.com/2018/10/11/IsoYZcdxVNFw1F9N/playlist.m3u8',this);" title="《我身后的陶斯》第9-10集">第9-10集</a></li>
     """.stripMargin
    val text2 =
      s"""
         |<li><a href="#play" onclick="ck_yk('v_19rr4v9gcs',this,'qytw/')" title="《我身后的陶斯》 第1集">1-2集上 繁中</a></li>
         |<li><a href="#play" onclick="ck_yk('v_19rr4v96ic',this,'qytw/')" title="《我身后的陶斯》 第2集">1-2集下 繁中</a></li>
         |<li><a href="#play" onclick="ck_yk('v_19rr4vdu6k',this,'qytw/')" title="《我身后的陶斯》 第3集">3-4集上 繁中</a></li>
         |<li><a href="#play" onclick="ck_yk('v_19rr4vdl3w',this,'qytw/')" title="《我身后的陶斯》 第4集">3-4集下 繁中</a></li>
         |<li><a href="#play" onclick="ck_yk('v_19rr5e5f30',this,'qytw/')" title="《我身后的陶斯》 第5集">5-6集上 繁中</a></li>
         |<li><a href="#play" onclick="ck_yk('v_19rr5e5gxw',this,'qytw/')" title="《我身后的陶斯》 第6集">5-6集下 繁中</a></li>
         |<li><a href="#play" onclick="ck_m3u8('https://www3.yuboyun.com/hls/2018/09/28/enKeatRy/playlist.m3u8',this);" title="《我身后的陶斯》第1-2集">第1-2集</a></li>
         |<li><a href="#play" onclick="ck_m3u8('https://www3.yuboyun.com/hls/2018/09/28/siIfxMky/playlist.m3u8',this);" title="《我身后的陶斯》第3-4集">第3-4集</a></li>
         |<li><a href="#play" onclick="ck_m3u8('https://www3.yuboyun.com/hls/2018/10/04/sPnBvtB3/playlist.m3u8',this);" title="《我身后的陶斯》第5-6集">第5-6集</a></li>
         |<li><a href="#play" onclick="ck_m3u8('https://www3.yuboyun.com/hls/2018/10/05/xZDa4IOX/playlist.m3u8',this);" title="《我身后的陶斯》第7-8集">第7-8集</a></li>
         |<li><a href="#play" onclick="ck_m3u8('https://www3.yuboyun.com/hls/2018/10/12/KNhFPyP4/playlist.m3u8',this);" title="《我身后的陶斯》第9-10集">第9-10集</a></li>
         |<li><a href="#play" onclick="ck_m3u8('https://www3.yuboyun.com/hls/2018/10/12/y3tuEdBu/playlist.m3u8',this);" title="《我身后的陶斯》第11-12集">第11-12集</a></li>
         |<li><a href="#play" onclick="ck_m3u8('https://zkgn.wb699.com/2018/09/28/viAKkvh6Oocho3qY/playlist.m3u8',this);" title="《我身后的陶斯》第1-2集">第1-2集</a></li>
         |<li><a href="#play" onclick="ck_m3u8('https://zkgn.wb699.com/2018/09/28/4lD7PxDvxFlZlJuK/playlist.m3u8',this);" title="《我身后的陶斯》第3-4集">第3-4集</a></li>
         |<li><a href="#play" onclick="ck_m3u8('https://zkcdn.wb699.com/2018/10/04/1h3JV8C24cIW6eVu/playlist.m3u8',this);" title="《我身后的陶斯》第5-6集">第5-6集</a></li>
         |<li><a href="#play" onclick="ck_m3u8('https://zkcdn.wb699.com/2018/10/05/8BLz4jlT6xtttEEZ/playlist.m3u8',this);" title="《我身后的陶斯》第7-8集">第7-8集</a></li>
         |<li><a href="#play" onclick="ck_m3u8('https://zkgn.wb699.com/2018/10/11/IsoYZcdxVNFw1F9N/playlist.m3u8',this);" title="《我身后的陶斯》第9-10集">第9-10集</a></li>
         |<li><a href="#play" onclick="ck_m3u8('https://zkcdn.wb699.com/2018/10/12/uT7llMME9JL7NJ2q/playlist.m3u8',this);" title="《我身后的陶斯》第11-12集">第11-12集</a></li>
       """.stripMargin

    val patch = DiffUtils.diff(text2,text1)

    patch.getDeltas.asScala.foreach{ delta =>
      delta.getOriginal.getLines.asScala.foreach{line =>
        println(Jsoup.parse(line).select("a").text())
      }
    }
  }
}
