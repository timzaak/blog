import createCanduit from 'canduit'
import config from '../../config'

let _conduit =  null
function _create(){
  _conduit = createCanduit({
    user: ['timzaak'],
    api: 'https://test-ufab5xzeitt5.phacility.com/api/',
    token: 'api-h5xaygxkizrrdv7y4jthq7aanajo',
  },(err,canduit)=>{

  })
  return _conduit
}

export default function getConduit(){
  if(_conduit){
    console.log(_conduit)
    return _conduit
  }else{
    return _create()
  }
};
