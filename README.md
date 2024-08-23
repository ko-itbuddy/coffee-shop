## RE: COFFEE-SHOP


## 목차
- [개발 환경](#개발-환경)
- [빌드 및 실행하기](#빌드-및-실행하기)

---

## 개발 환경
- 기본 환경
    - IDE: IntelliJ IDEA Ultimate
    - OS: Mac OS X
    - GIT
    - Docker
- Server
    - Java17
    - Spring Boot 3.3.1
    - JPA
    - Gradle
    - Junit5
    - TestContainer

---

## 빌드 및 실행
### 터미널 환경
- Git, Java, Docker

```
$ git clone https://github.com/ko-itbuddy/coffee-shop.git
$ cd kakaopay-task3
$ docker-compose -f local/redis-mysql/docker-compose.yml up -d
$ ./gradlew clean build
$ java -jar build/libs/coffee-shop.jar
```

## SWAGGER 접속
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## 종료
```
$ ^C
$ docker-compose -f local/redis-mysql/docker-compose.yml down
```


## Velog.io
[카카오페이 서버 개발과제 (5) 완료 후기, 다시 시작](https://velog.io/@itbuddy/%EC%B9%B4%EC%B9%B4%EC%98%A4%ED%8E%98%EC%9D%B4-%EC%84%9C%EB%B2%84-%EA%B0%9C%EB%B0%9C%EA%B3%BC%EC%A0%9C-5-%EC%99%84%EB%A3%8C-%ED%9B%84%EA%B8%B0-%EB%8B%A4%EC%8B%9C-%EC%8B%9C%EC%9E%91)
