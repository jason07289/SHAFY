<template>
  <div id="setTagas">

    <v-app id="inspire">
    <v-card
    max-width="344"
    class="mx-auto"
    outlined
  >
<!-- 타이틀 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
    <h1>태그 관리</h1>
    <v-divider class="mx-3"></v-divider>
<!-- 버튼들 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
    <v-card-actions>
          <v-btn color="blue darken-1" text ></v-btn>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text >Photo</v-btn>
          <v-btn color="blue darken-1" text >Done</v-btn>
     </v-card-actions>
  
    <v-divider class="mx-3"></v-divider>
<!-- 리스트 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->    
    <v-card-actions>
    <v-list style="width:100%;">
      <v-list-item-group v-model="model" multiple color="indigo" >
        <v-list-item
          v-for="(item, i) in items"
          :key="i"
          
        >
          <v-list-item-icon>
            <v-icon v-text="item.icon"></v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title v-text="item.text"></v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
    </v-card-actions>
<!-- 탭추가 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
    <v-card-actions>
      <v-btn color="blue darken-1"  style="align:center;">탭추가</v-btn>
    </v-card-actions>
  </v-card>
  </v-app>

  </div>
</template>

<script>
 export default {
    data: () => ({
      items: [
        {
          icon: 'mdi-wifi',
          text: 'Wifi',
        },
        {
          icon: 'mdi-bluetooth',
          text: 'Bluetooth',
        },
        {
          icon: 'mdi-chart-donut',
          text: 'Data Usage',
        },
      ],
      model: [1],
    }),
  }
</script>

<style>

</style>