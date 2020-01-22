# url 구조 

[TOC]

- request 

  ```json
  {
  	header : {}, 
      params : {},// URL의 인자로 보내짐 ex) URI?[key]=[value] 
      body : {},
      Authorization : {},
  }
  ```

  

- reponse (json)

  ```json
  {
      header : {},
      body : {}, // data or payload
  }
  ```

- 데이터를 list 단위로 받아올 때, filtering 이나 sorting은 params를 이용합니다.

  ```
  filter 
  
  
  
  sorted_by
  
  
  ```

- 아래 request는 body만 기입했습니다.

  

## USER 

```
		URI 				
GET 	user/list
		user/{int}
POST	user/login
		user/signup
PUT		user/{int}
DELETE	user/{int}
```





### GET: /user/list

- request 

  ```json
  {
  	header:{},
  	params:{
          sort_by: [all...]
      },
  	Authorization:{},
  }
  ```

-  response

  - deleted가 되지 않은 사용자의 리스트

  ```json
  [
  	{
          no 
          id 
          name 
          password 
          phone 
          birth 
          nickname 
          location 
          grade
          class1
          class2
          utype
          img
          state
          banned
          auth
          deleted
  	}
  	{}
  	...
]
  ```
  
  

### GET: /user/{int}

- request

  ```json
  {
  	header:{},
  	params:{},
  	Authorization:{},
  }
  ```

- reponse

  ```json
  {
  	header:{},
  	body:{
  		no 
          id 
          name 
          password 
          phone 
          birth 
          nickname 
          location 
          grade
          class1
          class2
          utype
          img
          state
          banned
          auth
          deleted
  	},
  }
  ```

  

### POST : /user/login

- request 

  ```json
  {
  	header :{},
  	body :{
  		id : [required],
  		password : [required]
  	},
  }
  ```

- response

  ```json
  {
  	state : [ok, fail]
  }
  ```

  

### POST: /user/signup

- request

  ```json
  {
  	header :{},
  	body :{
              id : [required],
              name : [required],
              password : [required],
              phone : [required],
              birth : [required],
              nickname : 
              location : [required],
              grade :
              class1 :
              class2 :
              utype :
              img :
              state :
  	},
  } 
  ```

- reponse 

  ```json
  {
  	state : [ok, fail]
  }
  ```

  

### PUT: /user

- request 

  ```json
  {
  	body : {
          id : [required],
          name : [required],
          password : [required],
          phone : [required],
          birth : [required],
          nickname : 
          location : [required],
          grade :
          class1 :
          class2 :
          utype :
          img :
          state :
  	}
  }
  ```

- response

  ```json
  {
  	state : [ok, fail]
  }
  ```

  

### DELETE: /user/{int}

- request 

  ```json
  {
  	header : {},
  	params : {},	
  }
  ```

- response

  ```json
  {
  	state : [ok, fail]
  }
  ```



## POST : 게시글 

```json
		URI 				
GET 	post/list
		post/{int}
POST	post
PUT		post/{int}
DELETE	post/{int}
```



### GET: /post/list

- request 

  ```json
  {
  	header : {},
  	params : {
  		sort_by: [all, like ...]
  	},	
  }
  ```

- response

  ```json
  [
  	{
  		pno 
  		title 
  		content
  		hashtag
  		id
  		datetime
  		views
  		likes
  		attachments
  		deleted
  	}
  	{}
  	...
  ]
  ```

  

### GET: /post/{int}

- request 

  ```json
  {
  	header : {},
  	params : {},	
  }
  ```
  
- response

  ```json
  {
      pno 
      title 
      content
      hashtag
      id
      datetime
      views
      likes
      attachments
      deleted
  }
  
  ```



### POST: /post

- request 

  ```json
  {
  	header : {},
  	body : {
  		pno :[required]
  		title : [required] 
  		content : [required]
  		hashtag
  		id
  		views
  		likes
  		attachments
  		deleted
  	},	
  }
  ```
  
- response

  ```json
  {
    	state : [ok, fail]
  }
  ```



### PUT: /post/{int}

- request 

  ```json
  {
  	header : {},
  	body : {
  		pno :[required]
  		title : [required] 
  		content : [required]
  		hashtag
  		id
  		datetime : [required]
  		views
  		likes
  		attachments
  		deleted
  	},	
  }
  ```

- response ​​

  ```json
  {
    	state : [ok, fail]
  }
  ```



### DELETE: /post/{int}

- request 

  ```json
  {
  	header : {},
  	body : {},	
  }
  ```
  
- response

  ```json
  {
      state : [ok, fail]
  }
  ```



## Notice

```
		URI 				
GET 	notice/list
		notice/{int}
POST	notice/
PUT		notice/{int}
DELETE	notice/{int}
```



### GET: /notice/list

- request

  ```json
  {
  	header : {},
  	body : {},	
  }
  ```

- reponse

  ```json
  [
      {
          body:
          {
          no
          title
          content
          id
          datetime
          deleted
      	}
      }
  ]
  ```

  

### GET: /notice/{int}

- request 

  ```json
  {
  	header : {},
  	body : {},	
  }
  ```

- response

  ```json
      {
          body:
          {
              no 
              title 
              content 
              id 
              datetime 
              deleted
      	}
      }
  ```

  

### POST: /notice
- request 

  ```json
  {
  	header : {},
  	body : {
              title : [required],
              content : [required],
              id : [required],
      },	
  }
  ```
  
- response

  ```json
  {
      state : [ok, fail]
  }
  ```



### PUT : /notice/{int}

- request 

  ```json
  {
  	header : {},
  	body : {
          	no :[required],
              title : [required],
              content : [required],
              id : [required],
              datetime : [required],
      },	
  }
  ```

- reponse

  ```json
  {
      state : [ok, fail]
  }
  ```

  

### DELETE : /notice/{int}

- request 

  ```json
  {
      header : {},
      params : {},
      body : {},
  }
  ```

- reponse

  ```json
  {
      state : [ok, fail]
  }
  ```

  

## FAQ

```
		URI 				
GET 	faq/list
		faq/{int}
POST	faq/
PUT		faq/{int}
DELETE	faq/{int}
```



### GET : /faq/list

- request 

  ```
  {
      header : {},
      params : {
      	sort_by: [all...]
      },
      body : {},
  }
  ```

- reponse

  ```
  [
  	...
      {
          no 
        title
          content
          id
          datetime
          deleted
      }
      ...
  ]
  
  ```
  
  

### GET : /faq/{int}

- request

  ```json
  {
      header : {},
      params : {},
      body : {},
  }
  ```

- response

  ```json
  {
      no 
      title
      content
      id
      datetime
      deleted
  }
  ```



### POST : /faq

- request 

  ```json
  {
  	body: {
          title : [required],
          content : [required],
          id : [required],
  		}
  }
  ```


- reponse

  ```json
  {
      state : [ok, fail]
  }
  ```

  

### PUT : /faq/{int}

- request 

  ```json
  {
  	body: {
          title : [required],
          content : [required],
          id : [required],
          datetime: [required]
  		}
  }
  ```

- response

  ```json
  {
      state : [ok, fail]
}
  ```
  
  

### DELETE : /faq/{int}
- request 

  ```json
  {
      header : {},
      params : {},
      body : {},
  }
  ```

- response

  ```json
  {
      state : [ok, fail]
  }
  ```



## TabTag

```
		URI 				
GET 	tabtag/list
		tabtag/{string}
POST	tabtag
PUT		tabtag/{int}
DELETE	tabtag/{int}
```



### GET : /tabtag/list

- request 

  ```json
  {
      header : {},
      params : {
      	filtered_by : [all...]
      },
      body : {},
  }
  ```

- reponse 

  ```json
  [
      ...
      {
          no 
          hashtag
          id
          deleted
      }    
      ...
  ]
  ```



### GET : /tabtag/{int}

- request

  ```json
  {
      header : {},
      params : {},
      body : {},
  }
  ```

- reponse

  ```json
  {
      no 
      hashtag
      id
      deleted
  }
  ```



### POST : /tabtag

- request 

  ```json
  {
      header : {},
      params : {},
      body : {
          {
          	no : [auto_increment],
              hashtag : [required],  
              id : [required]
  			deleted 
  			
          }
      },
  }
  ```

- reponse

  ```json
  {
      state : [ok, fail]
  }
  ```



### PUT : /tabtag/{int}

- request

  ```json
  {
      header : {},
      params : {},
      body : {
          {
          	no : [required],
              hashtag : [required],  
              id : [required]
  			deleted 
          }
      },
  }
  ```

- response

  ```json
  {
      state : [ok, fail]
  }
  ```



### DELETE : /tabtag/{int}

- request 

  ```json
  {
      header : {},
      params : {},
      body : {},
  }
  ```

- reponse

  ```json
  {
      state : [ok, fail]
  }
  ```

  

## FollowTag

```
		URI 				
GET 	followtag/list
		followtag/{string}
POST	followtag/
PUT		followtag/{int}
DELETE	followtag/{int}
```



### GET : /followtag/list
- request
  ```
  {
      header : {},
      params : {
      	filtered_by: [id,...]
      },
      body : {},
  }
  ```
- response
  ```
  [
      ...
      {
          no 
          hashtag
          id
          deleted
      }    
      ...
  ]
  ```
### GET : /followtag/{int}
- request
  ```json
  {
      header : {},
      params : {},
      body : {},
  }
  ```
- response
  ```json
  {
     no
     hashtag
     id
     deleted
  }
  ```
### POST : /followtag
- request
  ```json
  {
  	body: {
             hashtag : [required],
             id : [required],
  		}
  }
  ```
- reponse
  ```json
  {
      state : [ok, fail]
  }
  ```
### PUT : /followtag/{int}
- request
  ```json
  {
  	body: {
         	   no : [required]
             hashtag : [required]
             id : [required]
  		}
  }
  ```
- response
  ```json
  {
      state : [ok, fail]
  }
  ```
### DELETE : /followtag/{int}
- request
  ```json
  {
      header : {},
      params : {},
      body : {},
  }
  ```
- response
  ```json
  {
      state : [ok, fail]
  }
  ```



## BookMarkTag

```
		URI 				
GET 	bookmarktag/list
		bookmarktag/{string}
POST	bookmarktag/
PUT		bookmarktag/{int}
DELETE	bookmarktag/{int}
```



### GET : /bookmarktag/list

- request
  ```
  {
      header : {},
      params : {
      	filtered_by: [id,...]
      },
      body : {},
  }
  ```
- response
  ```
  [
      ...
      {
          no 
          hashtag
          id
          deleted
      }    
      ...
  ]
  ```
### GET : /bookmarktag/{int}
- request
  ```json
  {
      header : {},
      params : {},
      body : {},
  }
  ```
- response
  ```json
  {
     no
     hashtag
     id
     deleted
  }
  ```
### POST : /bookmarktag
- request
  ```json
  {
  	body: {
             hashtag : [required],
             id : [required],
  		}
  }
  ```
- reponse
  ```json
  {
      state : [ok, fail]
  }
  ```
### PUT : /bookmarktag/{int}
- request
  ```json
  {
  	body: {
         	   no : [required]
             hashtag : [required]
             id : [required]
  		}
  }
  ```
- response
  ```json
  {
      state : [ok, fail]
  }
  ```
### DELETE : /bookmarktag/{int}
- request
  ```json
  {
      header : {},
      params : {},
      body : {},
  }
  ```
- response
  ```json
  {
      state : [ok, fail]
  }
  ```





----

참고 

[REST-API-Design-Filtering-Sorting-and-Pagination](https://www.moesif.com/blog/technical/api-design/REST-API-Design-Filtering-Sorting-and-Pagination/)

---

작성자 : 황란아, 이수진 