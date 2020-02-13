<template>
  <div>
    <v-file-input 
    accept="image/*" 
    label="File input"
    outlined
    v-model="imageData"
    @change="onUpload2"
    ></v-file-input>
    <p>{{ imageData }}</p>
  </div>
</template>

<script>
import firebase from 'firebase/app'

export default {
  name: 'Upload',
  data(){	  
	return{
      imageData: null,  
      picture: null,
      uploadValue: 0
	}
  },
  methods:{
    onUpload2(){
        console.log('img object: ',this.imageData)
        const storageRef=firebase.storage().ref(`${this.imageData.name}`).put(this.imageData);
        storageRef.on(`state_changed`,snapshot=>{
          this.uploadValue = (snapshot.bytesTransferred/snapshot.totalBytes)*100;
        }, error=>{console.log(error.message)},
        ()=>{this.uploadValue=100;
          storageRef.snapshot.ref.getDownloadURL().then((url)=>{
            this.picture =url;
          });
        }      
        );
      }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
img.preview {
    width: 200px;
}
</style>