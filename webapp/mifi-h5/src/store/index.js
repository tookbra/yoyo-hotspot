import Vue from 'vue'
import Vuex from 'vuex'
import vuexI18n from 'vuex-i18n'
import app from './modules/app'
import getters from './getters'
import objectAssign from 'object-assign'
import { LocalePlugin } from 'vux'
import vuxLocales from '../locales/all.yml'
import componentsLocales from '../locales/components.yml'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {},
  mutations: {},
  modules: {
    app,
    i18n: vuexI18n.store
  },
  getters
})

Vue.use(vuexI18n.plugin, store)
const finalLocales = {
  'en': objectAssign(vuxLocales['en'], componentsLocales['en']),
  'zh-CN': objectAssign(vuxLocales['zh-CN'], componentsLocales['zh-CN'])
}

for (let i in finalLocales) {
  Vue.i18n.add(i, finalLocales[i])
}

Vue.use(LocalePlugin)
const nowLocale = Vue.locale.get()
if (/zh/.test(nowLocale)) {
  Vue.i18n.set('zh-CN')
} else {
  Vue.i18n.set('en')
}

export default store
