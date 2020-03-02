import Vue from 'vue';
import Vuetify from 'vuetify/lib';

// import colors from 'vuetify/lib/util/colors'

Vue.use(Vuetify);

export default new Vuetify({
  theme: {
    themes: {
      light: {
        primary: '#3784d2', // #E53935
        secondary: '#ffd154', // #FFCDD2
        accent: '#88bbee', // #3F51B5
      },
    },
  },
});

