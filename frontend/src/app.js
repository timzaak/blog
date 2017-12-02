import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import { createStore } from './store'
import { sync } from 'vuex-router-sync'
import { createRouter } from './router'

import App from './App.vue'

Vue.use(ElementUI)

export function createApp() {
  // create store and router instances
  const store = createStore()
  const router = createRouter()

  // sync the router with the vuex store.
  // this registers `store.state.route`
  sync(store, router)

  const app = new Vue({
    router,
    store,
    render: h => h(App)
  })

  return { app, router, store }
}


// new Vue({
//   router
// }).$mount('#app')
