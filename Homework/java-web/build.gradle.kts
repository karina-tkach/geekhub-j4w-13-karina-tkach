plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    id("java")
}

val assertjVersion = "3.24.2"
val springVersion = "6.1.3"
val postgresqlVersion = "42.7.1"
val hikariVersion = "5.1.0"
val jdbcVersion = "6.1.3"
val flywayVersion = "10.6.0"
val ciphersVersion = "1.0.1"

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.repsy.io/mvn/karinatkach/ciphers-library")
        credentials {
            username = System.getenv("REPSY_USER") ?: providers.gradleProperty("username").get()
            password = System.getenv("REPSY_PASSWORD") ?: providers.gradleProperty("password").get()
        }
        authentication {
            create<BasicAuthentication>("basic")
        }
    }
}

dependencies {
    implementation("org.springframework:spring-context:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")

    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("org.geekhub:ciphers:$ciphersVersion")

    implementation("org.flywaydb:flyway-core:$flywayVersion")
    runtimeOnly("org.flywaydb:flyway-database-postgresql:$flywayVersion")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation(platform("org.mockito:mockito-bom:5.7.0"))

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.assertj:assertj-core:$assertjVersion")
}
