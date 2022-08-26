package com.example.springbootwebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing 삭제
@SpringBootApplication
public class SpringbootWebsiteApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringbootWebsiteApplication.class, args);
    }
}

//-----------8/19---------------
// port:8080 준비되었다는 오류 -> sudo 명령어로 킬 하고 다시
// indexControllerTest 에서 자꾸 오류 -> )) 추가해서 해결
// 테스트 확인 후 Application 돌려서 웹으로 접속하니 ??? 로 텍스트 깨짐 -> 검색해보니 springboot 버전때문에 -> 그래서 2.7.0 으로 버전 다운그레이드 -> 그래도 문제 -> application.properties 에 코드 추가(server.servlet.encoding.force-response: true) -> 해결

//-----------8/20----------------
//Whitelabel Error Page status=500 오류 -> posts-update.mustache 에서 label="content" 부분 id를 ind로 오타 -> 해결
//또 port:8080 already
// lsof -i tcp:8080
// sudo kill -9 PID 번호




