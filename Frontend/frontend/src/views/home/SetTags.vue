<template>
  <div>

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
          <v-btn color="blue darken-1" text >전체선택</v-btn>
          <v-btn color="blue darken-1" text >선택삭제</v-btn>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text >수정완료</v-btn>
     </v-card-actions>
  
    <v-divider class="mx-3"></v-divider>
<!-- 리스트 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->    
<!-- 참고사이트 : https://github.com/David-Desmaisons/draggable-example -->
    <v-card-actions>
       <div class="col-md-3">
      <draggable class="list-group" tag="ul" 
        v-model="list" 
        v-bind="dragOptions" 
        :move="onMove" 
        :options="{animation:300, handle:'.badge'}"
        @start="isDragging=true" 
        @end="isDragging=false"
      >
        <transition-group type="transition" :name="'flip-list'">
          <li class="list-group-item" v-for="element in list" :key="element.order">
            <span class="badge">//{{element.order}}//</span>
            <i :class="element.fixed? 'fa fa-anchor' : 'glyphicon glyphicon-pushpin'" @click=" element.fixed=! element.fixed" aria-hidden="true"></i>
            {{element.name}}
          </li>
        </transition-group>
      </draggable>
    </div>
    </v-card-actions>
    <v-card-actions>
     <div class="list-group col-md-3">
      <pre>{{listString}}</pre>
    </div>
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
import draggable from "vuedraggable";

 export default {
   data: () => ({
      list: message.map((name, index) => {
        return { name, order: index + 1, fixed: false };
      }),
      editable: true,
      isDragging: false,
      delayedDragging: false
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
  "#점심메뉴",
  "#서울3기",
  "#임베디드",
  "#2기3반5조",
  "#흑흑",
  "#어려워",
  "#뷰의신",
  "#집가고싶다"
];
</script>

<style>
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
</style>