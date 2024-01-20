val httpVersion = "5.3"
val gsonVersion = "2.10.1"
val mockitoVersion = "5.7.0"
val jupiterVersion = "2.17.0"
val assertjVersion = "3.8.0"

dependencies {
    implementation("org.apache.httpcomponents.client5:httpclient5:$httpVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("org.mockito:mockito-junit-jupiter:$jupiterVersion")
    testImplementation("org.assertj:assertj-core:$assertjVersion")
}
