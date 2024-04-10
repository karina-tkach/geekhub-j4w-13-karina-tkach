plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
}

var swaggerVersion = "2.3.0"
val thymeleafExtrasVersion = "3.1.2.RELEASE"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6:$thymeleafExtrasVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$swaggerVersion")

    implementation(project(":Coursework:domain"))

    testImplementation("org.springframework.security:spring-security-test")
}
