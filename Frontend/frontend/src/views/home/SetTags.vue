/*
1. computed에 fixed 데이터라는 drag 되지않는 v-chip 그룹을 만든다
2. 수정하기 버튼을 눌렀을 때, data에 있는 this.list에 값을 깊은 복사 한다.
3. 수정 완료를 눌렀을 때, this.list에 값을 store에 보낸다. 
 */
<template>
  
  <div id="settags" style="width:100%;">
    
    <!-- v-card속성으로 주면 ..class="mx-auto" -->
    <v-card
    mt="400"
    max-width="344"
    class="mx-auto"
    outlined
    style="padding:0px 0px 0px 0px;margin:0px 0px 0px 0px;width:344px;"
    >
<!-- 타이틀 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
    <v-card-title>탭 관리</v-card-title>
    <v-divider class="mx-0"></v-divider>
<!-- 버튼들 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
    <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="editDone">수정완료</v-btn>
     </v-card-actions>
          <!-- 배열 확인용 -->
          <v-card-actions v-if="check">
            <div style="width:100%;">{{returnStr}}</div>
            <div style="width:100%;">{{listString}}</div>
          </v-card-actions>
  
<!-- 리스트 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->    
    <!-- 참고사이트 : https://github.com/David-Desmaisons/draggable-example -->

      
        <draggable
          v-model="list" 
          v-bind="dragOptions" 
          :move="onMove" 
          :options="{animation:300, handle:'.badge'}"
          @start="isDragging=true" 
          @end="isDragging=false"
          style="width:100%;"
        >
        
          <transition-group type="transition" :name="'flip-list'">
            <v-card-actions
            v-for="(element,index) in list" 
            :key="element.order"
            style="padding:0px 16px 5px 16px;"
            >
            <v-chip 
            label=""
            outlined=""
            style="height:45px;width:100%;"
            >
              <span class="badge"><v-icon class="mr-1">mdi-pound</v-icon></span>
              
              {{element.name}}
              <v-spacer></v-spacer>
              <v-icon right
              @click="list.splice(index,1)"
              style="position:right;"
              >mdi-close</v-icon>
            </v-chip>
            </v-card-actions>
          </transition-group>
        </draggable>
      


    
<!-- 탭추가 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
    <v-card-actions>
      <v-chip 
        v-if="list.length<10"
        label=""
        style="height:45px;width:100%;margin:0px 8px 10px 8px;"
        @click="addTagDialog=true"
      >
        <v-spacer></v-spacer>
        <v-icon class="mr-1">mdi-plus</v-icon>
        <v-spacer></v-spacer>
      </v-chip>
    </v-card-actions>
  </v-card>

 <v-dialog v-model="addTagDialog" max-width="400px">
      <v-card
        outlined
        style="padding: 10px 20px 10px 20px;"
      >
        <v-card-actions>
            <v-icon color="grey">mdi-pound</v-icon>
            <v-text-field
            v-model="addText"
            label="등록할 태그이름"
            single-line
            style="margin:12px 9px 0px 9px;"
          ></v-text-field>
          <v-btn 
            class="custom_active white--text"
            depressed 
            small 
            @click="addTag">추가</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    {{ tabtags }}
  </div>
</template>





<script>
 /* eslint-disable no-unused-vars */
import draggable from "vuedraggable";
import { mapActions, mapState } from 'vuex';
import store from '../../store'

var message = [
    "점심메뉴",
    "서울3기",
]
 export default {
   data: () => ({
//       message : [
//   "점심메뉴",
//   "서울3기",
//   "임베디드",
//   "2기3반5조",
//   "흑흑",
//   "어려워",
//   "뷰의신",
//   "집가고싶다"
// ], 
      // message: this.tabtags,
      // list: message.map((name, index) => {
      // return { name, order: index + 1 };
      // }),
      list:[],
      editable: true,
      isDragging: false,
      delayedDragging: false,
      check:false,
      addTagDialog:false,
      addText:'',
   }),
   components: {
    draggable
  },
    methods: {
    ...mapActions({
      getAllTab: 'tags/getAllTab',
      updateTab: 'tags/updateTab',
    }),
      taginit(){
      this.list = message.map((name, index) => {
      return { name, order: index + 1 };
      })
      console.log(this.list)
    },
    orderList() {
      this.list = this.list.sort((one, two) => {
        return one.order - two.order;
      });
    },
    onMove({ relatedContext, draggedContext }) {
      const relatedElement = relatedContext.element;
      const draggedElement = draggedContext.element;
      return (
        (!relatedElement || !relatedElement.fixed) && !draggedElement.fixed
      );
    },
    addTag(){
      this.addTagDialog = false;
      var regExp = /([\w|ㄱ-힣])*[^#\s]/g
      if(!regExp.test(this.addText)){
        this.addText = '';
        return;
      }
      this.list.push({
        name: this.addText,
        order: this.list.length+1
      })
      this.addText = '';
    },
    editDone(){
      console.log(this.returnStr)
      this.updateTab({hashtag:this.returnStr})
      this.check = true
    },
  },
  updated:{
      
  },
  computed: {
    ...mapState({
      tabtags : state => state.tags.tabtags
    }),
    dragOptions() {
      return {
        animation: 0,
        group: "description",
        disabled: !this.editable,
        ghostClass: "ghost"
      };
    },
    listString() {
      return JSON.stringify(this.list, null, 2);
    },
    returnStr(){
      var str=''
      for(var i=0; i<this.list.length; i++){
        str += '#'+ this.list[i].name
      }
      return str;
    }
  },
  watch: {
    isDragging(newValue) {
      if (newValue) {
        this.delayedDragging = true;
        return;
      }
      this.$nextTick(() => {
        this.delayedDragging = false;
      });
    },
  taginit2(){
      this.$nextTick(()=>{
        this.taginit()
        console.log('yyyy',this.list)
      })
    }
  },
  created(){
    this.getAllTab()
    this.$nextTick(this.taginit())
    console.log('언제..찍혀..',this.list)
    }
  }
//   const message = [
//   "점심메뉴",
//   "서울3기",
//   "임베디드",
//   "2기3반5조",
//   "흑흑",
//   "어려워",
//   "뷰의신",
//   "집가고싶다"
// ];

</script>

<style>
#settags{
  background-color: #111111;
}

/*드래그전용 건들노노*/
.flip-list-move {
  transition: transform 0.5s;
}
.no-move {
  transition: transform 0s;
}
.ghost {
  opacity: 0.5;
  background: #c8ebfb;
}
.list-group {
  min-height: 20px;
}
.list-group-item {
  cursor: move;
}
.list-group-item i {
  cursor: pointer;
}

.v-chip__content{
  width:100%;
}
</style>