plugins {
    id("org.springframework.boot") version "2.5.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_14
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security:2.5.0")
    implementation("org.springframework.boot:spring-boot-starter-web:2.5.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.5.0")
    compileOnly("org.projectlombok:lombok:1.18.26")
    runtimeOnly("com.h2database:h2:2.1.214")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.0")
    compileOnly("com.warrenstrange:googleauth:1.5.0")
    compileOnly("com.google.zxing:javase:3.5.2")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:2.5.0")
    implementation("org.jboss.aerogear:aerogear-otp-java:1.0.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

