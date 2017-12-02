import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export function createRouter() {
  return new Router({
    mode: 'history',
    fallback: false,
    routes: [
      {
        path: '*', component: {template: "<p>Hello World</p>"}
      }
    ]
  })
}
