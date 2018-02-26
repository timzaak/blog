package test

import com.timzaak.assets.SimpleAssetsProvider
import com.timzaak.data.dsl.{BinanceAggTradeDetailData, HuobiTradeDetailData}
import com.timzaak.trigger.FollowFairMarket
import monix.execution.Scheduler
import monix.reactive.Observable

//sbt "runMain test.FollowFairRunner"
object FollowFairRunner extends App{di=>
  implicit val scheduler =  monix.execution.Scheduler.global

  val assets = new SimpleAssetsProvider(500)

  def run(symbol:String) = {
    val huobiData = new HuobiTradeDetailData(symbol).collect {
      case a if a.data.exists(_.direction == "buy") =>
        (a.ts, a.data.filter(_.direction == "buy").map(_.amount).max, a.data.filter(_.direction == "buy").map(_.price).max)
    }
    val binanceData = new BinanceAggTradeDetailData(symbol)
    val tri = new FollowFairMarket {
      override implicit def scheduler: Scheduler = di.scheduler

      override def slowMarketBuyTrade: Observable[(L, D, D)] = huobiData

      override def fairMarketBuyTrade: Observable[(L, D, D)] = binanceData.collect {
        case a if a.m =>
          (a.T, a.q.toDouble, a.p.toDouble)
      }
    }

    tri.calculate(20, 10*1000).foldLeftL(0d){ case (mount,(buyPrice,sellPrice,profile))=>
      val money = assets.withdrawAssets
      if(money >= 495D){//止损价格
        money/buyPrice
      }else{
        mount
      }

    }.runAsync
  }


 def trigger(symbol:S) = new FollowFairMarket {
   override implicit def scheduler: Scheduler =  di.scheduler

   override def slowMarketBuyTrade: Observable[(L, D, D)] = new HuobiTradeDetailData(symbol).collect {
     case a if a.data.exists(_.direction == "buy") =>
       (a.ts,a.data.filter(_.direction == "buy").map(_.amount).max, a.data.filter(_.direction == "buy").map(_.price).max)
   }

   override def fairMarketBuyTrade: Observable[(L, D, D)] =new BinanceAggTradeDetailData(symbol).collect {
     case a if a.m  =>
        (a.T , a.q.toDouble, a.p.toDouble)
   }

 }

  trigger("btcusdt").calculate(20, 20*1000).foldLeftL(assets){ (assets,v)=>
    assets
  }.runAsync
  trigger("ethusdt").calculate(20, 20*1000).foldLeftL(assets){ (assets,v)=>
    assets
  }.runAsync
  trigger("neousdt").calculate(20, 20*1000).foldLeftL(assets){ (assets,v)=>
    assets
  }.runAsync
  trigger("ltcusdt").calculate(20, 20*1000).foldLeftL(assets){ (assets,v)=>
    assets
  }.runAsync
}
