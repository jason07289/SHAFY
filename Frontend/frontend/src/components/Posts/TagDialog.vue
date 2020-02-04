<template>

      <v-card style="align:center;">
        <v-card-actions style="align:center;">
            <v-card-title  class="headline"  style="text-align:center;align:center;width:100%;" >#{{hashtagName}}</v-card-title>
        </v-card-actions>
        <v-card-actions>

          <v-btn
            style="width:100%;"
            color="green darken-1"
            text
            @click="dialog = false"
          >
            메인 탭에 팔로우(or삭제로합시다)
          </v-btn>

        </v-card-actions>
          <v-divider class="mx-0"></v-divider>
<v-card-actions>
          <v-btn
            style="width:100%;"
            color="green darken-1"
            text
            @click="dialog = false"
          >
            새로운 탭에 추가
          </v-btn>
        </v-card-actions>
      </v-card>

</template>

<script>
  export default {
    data () {
      return {
        dialog: false,
      }
    },
    props:['hashtagName'],
  }
</script>

<style>

</style>