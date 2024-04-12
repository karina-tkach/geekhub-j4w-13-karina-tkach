plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    id("java")
}
tasks {
    "bootJar" {
        enabled = false
    }
    "jar" {
        enabled = true
    }
}
