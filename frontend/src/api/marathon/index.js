import axios from 'axios'
import config from '../../config'

function prefix(uri){
  return `${config.marathon_host}/v2/${uri}`
}

export function getApps(){
  return axios.get(prefix('apps'))
}

export function createApps(params){
  return axios.post(prefix('apps',params))
}
