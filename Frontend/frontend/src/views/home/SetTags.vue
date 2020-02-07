<template>
  
  <div style="width:100%;">
    

    <!-- v-card속성으로 주면 ..class="mx-auto" -->
    <v-card
    max-width="344"
    class="mx-auto"
    outlined
    style="padding:0px 0px 0px 0px;margin:0px 0px 0px 0px;"
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
          <div class="list-group col-md-3">
            <pre>{{listString}}</pre>
          </div>
          </v-card-actions>
  
<!-- 리스트 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->    
<!-- 참고사이트 : https://github.com/David-Desmaisons/draggable-example -->
    <v-card-actions>
      
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
            :key="element.order">
            <v-chip 
            label=""
            outlined=""
            style="height:45px;width:100%;align:center;"
            >
              <span class="badge"><v-icon class="mr-1">mdi-pound</v-icon></span>
              
              {{element.name}}
              <v-spacer></v-spacer>
              <v-icon right
              @click="list.splice(index,1)"
              style="position:right;"
              >mdi-close-circle-outline</v-icon>
            </v-chip>
            </v-card-actions>
          </transition-group>
        </draggable>
      
    </v-card-actions>

    
<!-- 탭추가 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
    <v-card-actions>
      <v-btn @click="addTag" color="blue darken-1"  style="align:center;">탭추가</v-btn>
    </v-card-actions>
  </v-card>
 

  </div>
</template>

<script>
import draggable from "vuedraggable";

 export default {
   data: () => ({
      list: message.map((name, index) => {
        return { name, order: index + 1 };
      }),
      editable: true,
      isDragging: false,
      delayedDragging: false,
      check:false
   }),
   components: {
    draggable
  },
    methods: {
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
      this.list.push({
        name: (this.list.length+1)+"탭추가",
        order: this.list.length+1
      })
    },
    editDone(){
      this.check = true
    }
  },
  computed: {
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
  }
  }
  const message = [
  "점심메뉴",
  "서울3기",
  "임베디드",
  "2기3반5조",
  "흑흑",
  "어려워",
  "뷰의신",
  "집가고싶다"
];
</script>

<style>
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