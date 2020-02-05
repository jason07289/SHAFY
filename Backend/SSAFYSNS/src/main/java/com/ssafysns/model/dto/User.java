 package com.ssafysns.model.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@Data 어노테이션으로 게터 세터를 알아서 처리 -> 롬복
/* @Data는 @ToString, @EqualsAndHashCode, @Getter, @Setter, 
 * @RequiredArgsConstructor을 한번에 사용하는 강력한 어노테이션. 
 * 강력한 어노테이션인 만큼 그에 따른 부작용도 많다.
 * 
 * 우선 Setter 같은 경우...
 * 간단하게 정리하면 Setter는 그 의도가 분명하지 않고 객체를 언제든지 변경할 수 있는 상태가 되어서 
 * 객체의 안전성이 보장받기 힘들다.
 * 위 코드에서 email의 변경 기능이 제공 되지 않는다고 가정한다면 email 관련된 setter도 제공되지 않아야 안전. 
 * 단순 안전함을 넘어서 해당 객체가 자기 자신을 가장 잘 표현하는 구조 즉 email의 변경 포인트를 제공하지 않음으로써
 * email 변경 기능이 없다는 것을 표현할 수 있다.
 * 
 */
//@NoArgsConstructor(access=AccessLevel.PROTECTED)
/*
 * 기본 생성자(No Args Constructor: 파라미터 없는 생성자)의 접근권한을 결정
 * AccessLevel을 PUBLIC,PACKAGE 등 다른것으로도 할 수 있다.
 * protected를 통해 외부에서 해당 생성자에 접근하는 것을 막을 수 있다.
 * 이 부분을 protected로 했기 때문에 생성자를 만들어줘야함....
 * 
 * 
 * @AllArgsConstructor는 모든 필드를 인자값(모든 파라미터가 있는)으로 하는 생성자를 만들어준다.
 * 
 * @RequiredArgsConstructor는 기본 값이 없고 
 * @NonNull (not null처리된 애들) 빼고 다 파라미터로 필요
 * 
 */

/*
 * RequiredArgsContructor(staticName=“of") : static factory 메서드를 생성함 private
 * User(){ // 이렇게 생성자를 은닉하고 } public static User of(){ return new User(); //
 * 스테틱으로 생성하게 끔 유도 가능 }
 * 
 */


@ToString // (exclude = "banned") 열외가능
/*
 * toString method 생성 ->롬복 exclude를 통해 만들기 toString 메서드에서 뺄 수 있음.
 * 
 * @Data를 안쓰는 이유 중 하나
 * 
 * >>>>>>> branch 'develop' of
 * https://lab.ssafy.com/webmobile2-sub2/s02p12a305.git
 * 
 * 부가적으로 @EqualsAndHashCode(exclude = "id") 이런식으로 Equal메소드랑 해시코드 메소드도 생성 가능
 */
@Setter
@Getter
@Entity // db의 테이블과 일대일로 매칭되는 객체 단위 @Id 어노테이션으로 pk 지정
/*
 * application.properties의 "spring.jpa.hibernate.ddl-auto=update" 때문에
 * entityManager가 자동으로 DDL을 수행해준다. 콘솔에서 확인가능.
 *
 * @Entity(name="user")로도 table명 지정가능 CamelCase가 java에서 쓰이고 db는 under_score기 때문에
 * 명시해주는게 좋음
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "user") // name을 통해 테이블 명을 지정가능, 이 어노테이션이 없다면 class명을 default로 table생성
public class User {
	/*
	 * 이거 헷갈림 GenerationType.?? 1. AUTO : 특정 DB에 맞게 자동 선택 jpa에게 생선전략 위임 (개꿀) 2.
	 * INDENTITY : DB의 identity 컬럼을 이용 -> 숫자값에서만 가능 3. SEQUENCE : DB의 seqeunce 컬럼을
	 * 이용 4. TABLE : 유일성이 보장된 데이터베이스 테이블을 이용
	 * 
	 * AUTO 이외의 생성전략을 사용할 때 DB에 대한 지식이 좀 더 요구된다고함 그래서 아직 이해가 안되는듯..
	 * "https://www.popit.kr/%ED%95%98%EC%9D%B4%EB%B2%84%EB%84%A4%EC%9D%B4%ED%8A%B8%EB%8A%94-%EC%96%B4%EB%96%BB%EA%B2%8C-%EC%9E%90%EB%8F%99-%ED%82%A4-%EC%83%9D%EC%84%B1-%EC%A0%84%EB%9E%B5%EC%9D%84-%EA%B2%B0%EC%A0%95%ED%95%98/"
	 * 더 알고싶다면 링크 ctrl+클릭
	 */
	@Id // 기본키 (pk)
	@Column(length = 50, nullable = false, unique = true)
	private String id;

//	@NotNull 이걸로도 낫 널 지정 가능 이렇게 하면 자바상에서도 에러를 잡아줘서 더 좋긴함
	@Column(length = 20, unique = true)
	private String token;

//@ToString.Exclude tostring메서드 제외도 가능 @EqualsAndHashCode.Exclude 마찬가지
	@Column(length = 20, nullable = false)
	private String name;


	@Column(length = 200)
	private String password;

//	@Getter
//	@Setter(AccessLevel.PROTECTED) 이런식으로 속성별 제한을 걸수도 있는듯...
	@Column(length = 20, nullable = false)
	private String phone;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE) // DB 타입에 맞춰서 매핑
	/*
	 * TemporalType.??? date(년월일), time(시분초), timestamp(년월일 시분초) 세가지 중 고르자 default는
	 * timestamp
	 */
	private Date birth;// Temporal 어노테이션 쓸때는 꼭 java.util.date를 쓰도록 하자

	@Column(length = 20, unique = true, nullable=false)
	private String nickname;

	@Column(length = 20, nullable = false)
	private String loacation;

	@Column(length = 10)
	private String grade;

	@Column(length = 10)
	private String class1;

	@Column(length = 10) 
	private String class2;

	@Column(length = 20)
	private String utype;

	@Column(length = 200)
	private String img;

	@Column(length = 20)
	private String state;

	@Column() // 이렇게 특별한 옵션없을 땐 생략 ㄱ
	private Integer banned;

	@Column(length = 20)
	private String auth;
	
	@Column()
	private Integer deleted;
	
	@Column(length=10)
	private Integer approval;
	
	@Column(nullable = false, columnDefinition = "int default 0")
	// 알람 발생 여부 0: false, 1: true
	private int alarm;

	@Builder
	public User(String id) {
		this.id=id;
	}
	
	
	// 외래키 설정
//	@OneToMany(mappedBy = "user")
//	private List<TabHashtag> tabHashtags = new ArrayList<TabHashtag>();
//	@OneToMany(mappedBy = "user")
//	private List<FollowHashtag> followHashtags = new ArrayList<FollowHashtag>();
//	@OneToMany(mappedBy = "user")
//	private List<Post> posts = new ArrayList<Post>();
//	@OneToMany(mappedBy = "userByNickname")
//	private List<Comments> commentss = new ArrayList<Comments>();

}
