subprojects {
    val assertjVersion = "3.24.2"
    val postgresqlVersion = "42.7.1"
    val flywayVersion = "10.6.0"
    val lombokVersion = "1.18.30"
    val springTestVersion = "3.2.4"


    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
        implementation("org.springframework.boot:spring-boot-starter-security")

        implementation("org.postgresql:postgresql:$postgresqlVersion")
        implementation("org.flywaydb:flyway-core:$flywayVersion")
        runtimeOnly("org.flywaydb:flyway-database-postgresql:$flywayVersion")

        compileOnly("org.projectlombok:lombok:$lombokVersion")
        annotationProcessor("org.projectlombok:lombok:$lombokVersion")

        testCompileOnly("org.projectlombok:lombok:$lombokVersion")
        testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

        testImplementation("org.springframework.boot:spring-boot-starter-test:$springTestVersion")
        testImplementation(platform("org.junit:junit-bom:5.9.1"))
        testImplementation(platform("org.mockito:mockito-bom:5.7.0"))

        testImplementation("org.junit.jupiter:junit-jupiter")
        testImplementation("org.mockito:mockito-core")
        testImplementation("org.mockito:mockito-junit-jupiter")
        testImplementation("org.assertj:assertj-core:$assertjVersion")
    }
}
