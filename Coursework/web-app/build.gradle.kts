plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
}

var swaggerVersion = "2.3.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$swaggerVersion")

    implementation(project(":Coursework:domain"))
}
