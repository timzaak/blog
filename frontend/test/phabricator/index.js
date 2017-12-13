import getConduit from '../../src/api/phabricator'
import test from 'ava'

// 跨域问题： 艹....
// test.cb('canduit can connect',t => {
//    t.plan(1)
//   getConduit().exec('user.query',{
//     usernames:['timzaak']
//   },function(error,users){
//     t.is(error,null)
//     t.end()
//   })
// })
