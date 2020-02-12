<template>
    <v-app-bar
      color="white"
      flat
      elevate-on-scroll
      fixed
      dense
      style="background-color:var(--main-y);"
    >

      <v-toolbar-title style="color:#ffffff; padding-left:4%;width:40%">S<v-icon size="20" style="padding:0px 0px 5px 0px;color:#ffffff;">mdi-pound</v-icon>ARFY</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon>
        <v-icon color="#ffffff">mdi-magnify</v-icon>
      </v-btn>


    </v-app-bar>

</template>

<script>
  export default {
    data () {
      return {
        tab: null,
        items: [
          'web', 'shopping', 'videos', 'images', 'news',
        ],
        text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
      }
    },
  }
</script>

<style scoped>
v-app-bar{
  background-color: white;
}
</style>