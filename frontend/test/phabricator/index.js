import getCanduit from '../../src/api/phabricator'
import test from 'ava'

test.cb('canduit can connect',t => {
  t.plan(1)
  getCanduit().exec('user.query',{
    usernames:['timzaak']
  },function(error,users){
    t.is(error,null)
    t.end()
  })
})
