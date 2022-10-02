# springboot-website

# 스프링 부트 프로젝트 코드리뷰

```java
//HelloResponseDto

import lombok.Getter:
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HelloResponseDto {

	private final String name;
	private final int amount;
}
```

이 파일은 Dto 파일이다. Dto 패키지는 스프링 부트에서 무슨 역할을 할까?

## DTO

DTO는 Data Transfer Object로 계층간 데이터 교환을 위해 Data를 변형하여 사용하는 객체이다.

DTO의 특징이 있다면, 로직을 가지고 있지 않고 순수하게 getter/setter로만 이루어져 있다는것이다.

DTO는 이런 경우에 사용한다.

모든 회원 정보를 불러와야 한다고 가정하자. userTable에는 회원의 이름, 성별, 나이, 핸드폰번호, 아이디, 비밀번호 등 많은 정보들이 존재한다. 하지만, 나는 이름, 성별, 나이만 필요하다.

UserTable에 모든 데이터가 필요하지 않은 지금, Entity로 만들어 둔 class를 사용하기에는 보안상의 문제, 필요없는 값을 가지고 있다는 점에서 좋지 않다. 

이런 경우, 이름, 성별, 나이만 담는 DTO를 만들어서 사용하는 것이다.

이제 코드를 한번 살펴보겠다.

## @Getter

Getter는 왜 쓰는 걸까? 큰 프로젝트에서 엄청 긴 코드를 다룬다고 생각해보자. 다른 사람의 코드 속 모든 변수 값을 가져올 필요도 없을 뿐더러, 가져올 수 있는 것이 마냥 편한 일은 아닐 것이다.

자동차 게임을 개발하는 상황을 가정해보자. 만약 자동차가 충돌 시 튕겨나가는 이벤트 처리를 만드는 일을 담당한다면, 충돌하는 자동차의 색상이나, 브랜드를 알아야할까? 아마 불필요한 정보에 신경이 빼앗길 것이다.

```java
class Application{
    public static void main(String[] args){
        Person jinhwan = new Person();
    }
}

class Person{
    int age;
    int name;
    String hobby;
    int hobby_id;
    String school;
    int school_id;
    String phoneNumber;
    int gender;
    int pw;
}
```

자동차 객체를 가져다 쓸 때 차종, 색, 휠, 차량 넘버, 제조사 등의 잡다한 정보는 자동차를 구현한 사람들의 몫이고, 다른 사람에게 방해만 될 뿐이다.

위 코드에서 age와 name을 제외하곤, 다른 사람에게는 불필요한 정보가 되는것이다.

```java
class Person{
    private int age;
    private int name;
    
    private String hobby;
    private int hobby_id;
    private String school;
    private int school_id;
    private String phoneNumber;
    private int gender;
    private int pw;

    public int getAge() {
        return age;
    }

    public int getName() {
        return name;
    }

    public void setAge(int age) {
        if(age >=0){
            this.age = age;
        }
        else
            this.age = 0;
    }
}
```

이번에는 변수의 접근을 private 처리해서 해당 클래스 안에서만 노출되게 바꾸고, 다른 사람들도 사용할 필요가 있는 주요 변수 age, name만 @getter을 이용해서 드러냈다.

이렇게 변수들의 외부 노출을 제한하고, 노출 범위를 정해주는 것이 Getter고, 그러한 속성이 은닉성이다.

## @RequiredArgsConstuctor

의존성을 주입해주기 위해서 생성자(Constructor), Setter, Field 타입의 방식을 사용해야 했다. 하지만 lombok(롬복)의 @RequiredArgsConstructor 어노테이션을 사용하면 간단한 방법으로 생성자 주입을 해줄 수 있다.

@RequiredArgsConstructor는 final 혹은 @NotNull이 붙은 필드의 생성자를 자동으로 만들어준다. 이를 통해 새로운 필드를 추가할 때 다시 생성자를 만들거나 하는 등의 번거로움을 없앨 수 있다. 하지만 자동적으로 생성자가 만들어지기 때문에 내가 예상하지 못한 결과나 오류가 발생할 수 있기 때문에 그런 점도 염두해둬야 한다.

@RequiredArgsConstructor 어노테이션은 클래스에 선언된 final 변수들, 필드들을 매개변수로 하는 생성자를 자동으로 생성해주는 어노테이션이다.

위의 Dto 에 적용된 롬복이 잘 작동하는지 간단한 테스트 코드를 작성해보겠다.

```java
//HelloResponseDtoTest

package com.example.springbootwebsite.dto;
import com.example.springbootwebsite.web.dto.HelloResponseDto;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트(){
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
```

## @Test

@Test 어노테이션에 대해 알아보려고 한다. 그 전에 테스트에 대해 알아보자.

### 단위 테스트란?

단위 테스트는 개발자가 개발한 모듈들이 정상적으로 동작하고 원하는 결과를 만들어내는지 확인하는 테스트를 말한다.

대표적으로 스프링에는 JUnit Test가 존재하고 클래스를 생성하는 것처럼 JUnit Test Case를 생성하여 단위 테스트를 수행할 수 있다.

### 사용 목적

단위 테스트를 사용하는 목적은 내가 작성한 코드가 내가 의도한 대로 동작하는가? 에 대한 답변을 확인하기 위해서다.

물론 코드를 테스트하는 방법은 메인함수를 작성한다거나 Swagger를 이용한다거나 클라이언트 단의 테스트 코드를 작성하는 등의 다양한 방법들이 존재한다.

그럼에도 불구하고 단위 테스트를 사용하는 이유는 단위 테스트만이 가지고 있는 특징들이 존재하기 때문이다.

### 특징

1. 작성된 코드를 작은 단위들로 나눈 뒤 테스트가 필요한 단위들만 묶어 한 번에 테스트가 가능
2. 묶여진 단위들이 문제가 없을 시 더 큰 단위로 묶으며 테스트하는 상향식 테스트가 가능
3. 작성해둔 단위 테스트를 통해 리팩토링 된 코드의 신뢰도를 높임
4. 개발 코드와 테스트 코드가 분리되어 있어 코드 관리가 용이
5. 트랜잭션 처리가 정상적으로 이루어져도 롤백되어 데이터베이스에 반영되지 않도록 설정 가능

## assertThat

assertThat을 알기전에 assertj라는 테스트 검증 라이브러리를 한번 알아보자.

### assertJ란?

assertJ는 유연한 테스트 인터페이스를 제공해주는 자바 라이브러리다. 테스트 코드 작성에 있어서 높은 가독성과 쉬운 유지 보수성을 지향한다.

이제 다시 assertThat에 대해 알아보자.

```java
@Test
void a_few_simple_assertions(){
								assertThat("The Lord of the Rings").isNotNull()
								.startsWith("The")
								.contains("Lord")
								.endsWith("Rings");
}
```

왼쪽에서 오른쪽으로 읽으면서 자연스럽게 “The Lord of the Rings”에 대해서 not null이고 The로 시작하며 Load를 포함하고 Rings로 끝나는지 검증하는 구문임을 알 수 있다.

### isEqualTo

isEqualTo는 대상의 내용자체를 비교한다. assertThat에 있는 값과 isEqualTo의 값을 비교해서 같을 때만 성공이다.

예제 코드를 통해 알아보겠다.

```java
String a = "apple";
String b = "apple";
```

이 경우 아래와 같이 표현된다.

```java
assertThat(a).isEqualTo(b)
```

---

## HelloController.java

```java
import com.example.springbootwebsite.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloResponseDto(@RequestParam("name") String name, @RequestParam("amount") int amount){
        return new HelloResponseDto(name, amount);
    }
}
```

### @RestController

RestController는 ‘REST’를 위한 전용 컨트롤러다.

<aside>
💡 여기서  잠깐, REST란?

REST는 REpresentative State Transfer의 약자로 분산 시스템을 위한 아키텍쳐다.
네트워크를 경유해서 외부 서버에 접속하거나 필요한 정보를 불러오기 위한 구조라고 생각할 수 있다.
그리고 이 REST 개념을 바탕으로 설계된 시스템을 ‘RESTFul’ 이라고 표현한다.

REST의 경우, 클라이언트가 특정 URL에 접속하면 웹페이지를 그리는 것이 아니라 특정 정보 또는 특정 처리 결과를 텍스트 형태로 반환한다.

</aside>

이런 RESTFul한 웹 서비스를 구축하기 위해 사용하는 것이 RestController이다.

RestController는 웹 어플리케이션의 일반적인 페이지에 비해 구조가 간단하다.

RestController 어노테이션은 서버의 URL과 특정 처리를 연동(매핑)시키는 구조이다. 매서드마다 ‘이것은 OO 이라는 주소용 처리’라고 연동해 놓고, 서버로 요청이 온 경우 해당 주소에 할당되어 있는 메소드가 자동으로 실행되는 것이다.

### @GetMapping

GetMapping 어노테이션을 이해하려면 사용자의 요청 처리 과정을 알아야 한다. 

Client가 웹 서비스에 요청을 보내면 Dispatcher Servlet이 매핑되는 Handler를 찾는다.

Controller가 요청을 처리한 후에 View를 Dispatcher Servlet 에게 전달해주고 다시 사용자에게 응답이 되는 흐름이다.

Controller가 View를 반환하기 위해서는 ViewResolver가 사용되며, ViewResolver 설정에 맞게 View를 찾아 랜더링한다.

여기서 GetMapping 어노테이션의 역할은 View를 찾아서 반환을 하는것이다. 

위의 코드에서 @GetMapping(”/hello”) 을 적어주었다. 이는 localhost:8080/hello 요청이 들어오면 아래의 함수를 실행하라고 해석하면 된다.

근데 return 값이 String 자료형의 hello으로 되어있는것을 볼 수 있다. 그러면 Spring이 자동으로 src/main/resource의 templates에서 hello라는 이름의 View를 찾아서 반환해준다.

참고로 static에 들어가는 정적 contents들은 따로 controller가 없어도 알아서 찾아준다. 정확히는 스프링 컨테이너에 해당 이름을 사용하는 컨트롤러가 없다면 static에서 찾도록 설계되어 있다.

### @RequestParam

RequestParam 어노테이션은 HttpServletRequest 객체와 같은 역할을 한다. HttpServletRequest 에서는 getParameter() 메소드를 이용했지만, @RequestParam을 이용해서 받아오는 방법도 있다.

@RequestParam 어노테이션은 아래와 같은 형식을 따른다.

```java
@RequestParam("가져올 데이터의 이름")[데이터 타입][가져온 데이터를 담을 변수]
```

그리고 HelloResponseDto 객체를 이용해서 View로 값을 넘겨준다.

## HelloControllerTest.java

```java
import com.example.springbootwebsite.config.auth.SecurityConfig;
import com.example.springbootwebsite.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @WithMockUser(roles = "USER")
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @WithMockUser(roles = "USER")
    @Test
    public void holloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                        get("/hello/dto")
                                .param("name", name)
                                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));

    }
```

### @RunWith(SpringRunner.class)

RunWith 어노테이션은 JUnit의 테스트 러너를 확장하는 방법 중 하나로 커스텀 러너를 설정해주는 방법이다. 테스트를 진행할 때 Junit에 내장된 실행자 외에 다른 실행자를 실행시킨다. 위 코드에서는 SpringRunner.class를 설정했기 때문에 SpringRunner라는 스프링 실행자를 사용하면 이처럼 @RunWith는 스프링 부트 테스트와 JUnit 사이에 연결자 역할을 한다.

하지만 JUnit4에서 지원하는 @RunWith(SpringRunner.class)를 사용한다면, @Autowired, @MockBean에 해당되는 것들에만 application context를 로딩하게 되므로 JUnit4에서는 필요한 조건에 맞춰서 어노테이션을 사용해야 한다.

<aside>
💡 JUnit5 부터는 @RunWith 을 사용하지 않는다고 한다. 대신 @ExtendWith 로 변경되었다.

</aside>

### @WebMvcTest

WebMvcTest 어노테이션은 Controller를 테스트하기 위한 어노테이션이다. WebMvcTest 어노테이션은 Controller가 정상적으로 작동하는지 테스트하는 것이기 때문에 Web과 관련된 의존성만을 가지고 있다.

WebMvcTest 에서 가져오는 의존성들 즉, 다음 어노테이션들만 ComponentScan 해서 가져온다.

- @Controller
- @ControllerAdvice
- @JsonComponent
- Converter
- GenericConverter
- Filter
- HandlerInterceptor

그렇기 때문에 Service, Repository 등의 어노테이션을 컨트롤러에서 사용하고 있어도 가져와서 사용하지 않는다.

여기서 WebMvcTest 는 단위테스트라는 것을 기억하자.

### @ComponentScan.Filter

Application Context 설정 클래스에 @ComponentScan(”패키지 경로”)를 적어두면 해당 패키지에 있는 @Controller, @Sevice, @Repository, @Component 객체들이 DI 컨테이너에 등록된다.

그 중 FilterType.ASSIGNABLE_TYPE은 클래스를 기준으로 객체를 가져온다. classes에 할당할 수 있는 클래스, 즉 상속이나 구현한 클래스까지 포함한다.

위의 코드를 기준으로 FilterType.ASSIGNABLE_TYPE 필터를 사용하여 SecurityConfig 객체를 가져온다.

### @WithMockUser

WithMockUser 어노테이션은 Spring Security 에서 제공되는 어노테이션으로, Spring MVC 를 테스트할 때 사용되는 어노테이션이다. 결과적으로, 이 어노테이션을 부여하면 인증이 된 상태로 테스트를 진행하도록 도와준다.

### perform()

MockMvc가 제공하는 메서드로, 브라우저에서 서버에 URL 요청을 하듯 컨트롤러를 실행시킬 수 있다.

### .andExpect 메소드

.perform() 메소드를 이용하여 요청을 전송하면, 그 결과로 ResultActions 객체를 리턴하는데 이 객체는 응답 결과를 검증할 수 있는 andExcept() 메소드를 제공한다.

- 상태 코드(status())
    - 메소드 이름: 상태코드
    - isOk(): 200
    - isNotFound(): 404
    - isMethodNotAllowed(): 405
    - isInternalServerError(): 500
    - is(int status): status 상태 코드
- 뷰(view())
    - 리턴하는 뷰 이름을 검증한다
    - ex) view().name(”blog”): 리턴하는 뷰 이름이 blog인가?
- 리다이렉트(redirect())
    - 리다이렉트 응답을 검증한다
    - ex) redirectUrl(”/blog”): ‘/blog’로 리다이렉트 되었는가?
- 모델 정보(model())
    - 컨트롤러에서 저장한 모델들의 정보 검증
- 응답 정보 검증(content())
    - 응답에 대한 정보를 검증해준다.
