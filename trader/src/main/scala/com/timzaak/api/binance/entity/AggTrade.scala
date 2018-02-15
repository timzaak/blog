package com.timzaak.api.binance.entity

//{
//"e": "aggTrade",  // Event type
//"E": 123456789,   // Event time
//"s": "BNBBTC",    // Symbol
//"a": 12345,       // Aggregate trade ID
//"p": "0.001",     // Price
//"q": "100",       // Quantity
//"f": 100,         // First trade ID
//"l": 105,         // Last trade ID
//"T": 123456785,   // Trade time
//"m": true,        // Is the buyer the market maker?
//"M": true         // Ignore.
//}
case class AggTrade(
  e:S,
  E:L,
  s:S,
  a:L,
  p:S,
  q:S,
  f:L,
  l:L,
  T:L,
  m:B
) {

}
