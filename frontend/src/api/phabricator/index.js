import createCanduit from 'canduit'
import config from '../../config'

let _canduit =  null
function _create(){
  _canduit = createCanduit({
    user: ['timzaak'],
    api: 'https://test-ufab5xzeitt5.phacility.com/api/',
    token: 'api-ekicvzzxzp6vzuotl7m5iuuiqggk',
  },(err,canduit)=>{

  })
  return _canduit
}

export default function getConduit(){
  if(_canduit){
    return _canduit
  }else{
    return _create()
  }
};
