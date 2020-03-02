### JavaScript의 함수



- 함수 선언식(Function Declarations)

  브라우저가 코드를 해석할 때 코드의 위치와 관계없이 가장 먼저 실행된다. 

  호이스팅에 영향을 받는다.

  ```javascript
  function [name]() {
      // write your logic
  }
  ```

- 함수 표현식(Function Expressions)

  호이스팅에 영향을 받지 않는다.

  클로져, 콜백으로 사용한다.

  ```javascript
  var [name] () {
      // write your logic
  }
  ```

*호이스팅* 이란 ?

함수 안에 있는 선언들을 모두 끌어올려서 해당 함수 유효 범위의 최상단에 선언하는 것

var 변수 선언(할당에는 적용 x) 과 함수 선언문에서 발생

- 화살표 함수 

  ES6에 새롭게 추가된 문법.

  항상 익명이다. 

  ```javascript
  // 매개변수가 없는 경우
  var [name] = () => { }
  
  // 매개변수가 한 개인 경우
  var [name] = x => x
  
  // 매개변수가 여러 개인 경우
  var [name] = (a, b) => {}
  ```