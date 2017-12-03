import Vue from 'vue'
import Router from 'vue-router'
import CIApplication from '../views/CIApplication/index.vue'
import CIApplicationApply from '../views/CIApplication/views/Apply.vue'
Vue.use(Router)

export function createRouter() {
  return new Router({
    mode: 'history',
    fallback: false,
    routes: [
      {
        path: '*', component: CIApplication,
        children: [
          {
            path: '*', component: CIApplicationApply,
          }
        ]
      },
    ]
  })
}
