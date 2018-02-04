## Auth-Service

project example: https://github.com/Driox/oauth-provider-seed

教程：http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html


在此基础上加入 X-Sign 和 X-Timestamp 参数，放到报头里。


X-Sign: 'XXkskdfkewkffe'
X-Timestamp: '213992130444'
X-Sign-Algo: SHA-256


App客户端加密过程： 加密函数(有序请求参数 + &timestamp=${X-Timestamp})

App服务器端加密过程： 加密函数(有序请求参数 + &secret=${clientSecret}&timestamp=${X-Timestamp}) 

  
不准备支持： client credentials







 