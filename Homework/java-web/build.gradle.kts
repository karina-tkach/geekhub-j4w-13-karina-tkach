val assertjVersion = "3.24.2"
val springVersion = "6.1.3"
val postgresqlVersion = "42.7.1"
val hikariVersion = "5.1.0"
val jdbcVersion = "6.1.3"
val flywayVersion = "10.6.0"

dependencies {
    implementation("org.springframework:spring-context:$springVersion")

    implementation("org.springframework:spring-jdbc:$jdbcVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("com.zaxxer:HikariCP:$hikariVersion")

    implementation("org.flywaydb:flyway-core:$flywayVersion")
    runtimeOnly("org.flywaydb:flyway-database-postgresql:$flywayVersion")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation(platform("org.mockito:mockito-bom:5.7.0"))

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.assertj:assertj-core:$assertjVersion")
}
