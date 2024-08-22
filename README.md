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
$ git clone [https://github.com/ko-itbuddy/coffee-shop.git](https://github.com/ko-itbuddy/coffee-shop.git)
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
