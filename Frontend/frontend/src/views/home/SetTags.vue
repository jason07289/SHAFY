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
          <v-btn color="blue darken-1" text v-if="editmode===false" @click="makelist">수정하기</v-btn>
          <v-btn color="blue darken-1" text v-if="editmode===true" @click="editDone">수정완료</v-btn>
     </v-card-actions>
          
  
<!-- 리스트 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->    
    <!-- 참고사이트 : https://github.com/David-Desmaisons/draggable-example -->

      
        <draggable
          v-if="editmode"
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

        <v-card-actions
          v-else
            v-for="name in (fixedList.length==0?message:fixedList)" 
            :key="name"
            style="padding:0px 16px 5px 16px;"
            >
            <v-chip 
            label=""
            outlined=""
            style="height:45px;width:100%;"
            >
              <span class="badge"><v-icon color="grey" class="mr-1">mdi-pound</v-icon></span>
              
              {{name}}
              <v-spacer></v-spacer>
            </v-chip>
            </v-card-actions>
      


    
<!-- 탭추가 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
    <v-card-actions>
      <v-chip 
        v-if="list.length<10&&editmode"
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
            @keyup.enter="addTag"
          ></v-text-field>
          <v-btn 
            class="custom_active white--text"
            depressed 
            small 
            @click="addTag"
            >추가</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>





<script>
import draggable from "vuedraggable";
import { mapActions, mapState } from 'vuex';

 export default {
   data: () => ({
      list:[],
      editmode: false,
      editable: true,
      isDragging: false,
      delayedDragging: false,
      check:false,
      addTagDialog:false,
      addText:'',
      fixedList:[],
   }),
   components: {
    draggable
  },
    methods: {
    ...mapActions({
      getAllTab: 'tags/getAllTab',
      updateTab: 'tags/updateTab',
    }),
    makelist(){
      this.editmode = true
      if(this.fixedList.length==0){
        this.list = this.message.map((name, index)=>{
          return {name, order: index + 1 }
        })
      }else{
        this.list = this.fixedList.map((name, index)=>{
          return {name, order: index + 1 }
        })
      }
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

      //형시 껌사
      if(!regExp.test(this.addText)){
        this.addText = '';
        return;
      }

      //중복 검사
      for(var i=0; i<this.list.length; i++){
        if(this.addText==this.list[i].name){
          return;
        }
      }

      this.list.push({
        name: this.addText,
        order: this.list.length+1
      })
      this.addText = '';
    },

    editDone(){
      this.fixedList=[];
      for(var i=0; i<this.list.length; i++){
        this.fixedList.push(this.list[i].name)
      }
      console.log(this.returnStr)
      this.updateTab({hashtag:this.returnStr})
      this.check = true
      this.editmode = false
      
    },
    unedu(){
      if(! this.userInfo.approval){
        alert('승인된 회원만 사용 가능합니다. 관리자에게 문의해주세요')
        this.$router.push({name:'Home'})
      }
      
    }
  },
  computed: {
    ...mapState({
    message : state => state.tags.tabtags,
    userInfo: state=> state.user.userInfo,
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
    }
  },
  created(){
    this.getAllTab()
    this.unedu()
    }
  }
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
