import Vue from "vue";
import HhdApp from "./HhdApp";

Vue.config.productionTip = false;
Vue.config.devtools = true;

new Vue({
	el: "#vueApp",
	components: {
		"hhd-app": HhdApp,
	},
});
