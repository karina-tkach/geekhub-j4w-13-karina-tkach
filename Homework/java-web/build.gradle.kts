val assertjVersion = "3.24.2"
val springVersion = "6.1.3"

dependencies {
    implementation("org.springframework:spring-context:$springVersion")
    testImplementation("org.springframework:spring-test:$springVersion")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation(platform("org.mockito:mockito-bom:5.7.0"))

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.assertj:assertj-core:$assertjVersion")
}
