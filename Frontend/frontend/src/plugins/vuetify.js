import Vue from 'vue';
import Vuetify from 'vuetify/lib';


Vue.use(Vuetify);

export default new Vuetify({
    theme: {
        themes: {
          light: {
            primary: '#87CeeB',//'#1867C0',
            secondary: '#5CBBF6',
            tertiary: '#E57373',
            accent: '#005CAF',
          },
        },
      },
});
