plugins {
    id("org.openapi.generator") version "7.2.0"
    id("java")
}

val swaggerVersion = "1.6.8"
val findBugsVersion = "3.0.2"
val okhttpVersion = "4.10.0"
val gsonVersion = "2.9.1"
val gsonFireVersion = "1.9.0"
val javaxVersion = "1.1.1"
val javaxxVersion = "2.1.1"
val jacksonVersion = "0.2.6"
val apacheVersion = "3.12.0"
val jakartaVersion = "1.3.5"
val jupiterVersion = "5.9.1"
val mockitoVersion = "3.12.4"

dependencies {
    implementation("io.swagger:swagger-annotations:$swaggerVersion")
    implementation("com.google.code.findbugs:jsr305:$findBugsVersion")
    implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")
    implementation("io.gsonfire:gson-fire:$gsonFireVersion")
    implementation("javax.ws.rs:jsr311-api:$javaxVersion")
    implementation("javax.ws.rs:javax.ws.rs-api:$javaxxVersion")
    implementation("org.openapitools:jackson-databind-nullable:$jacksonVersion")
    implementation(group = "org.apache.commons", name = "commons-lang3", version = apacheVersion)
    implementation("jakarta.annotation:jakarta.annotation-api:$jakartaVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
}

openApiGenerate {
    generatorName = "java"
    inputSpec = "$rootDir/Homework/java-core/12-Gradle/open-api-generation/src/main/resources/openapi.yaml"
    outputDir = "$rootDir/Homework/java-core/12-Gradle/open-api-generation"
    modelPackage = "org.geekhub.hw12.ciphers.model"
    generateModelTests = false
    generateModelDocumentation = false
    generateApiTests = false
    generateApiDocumentation = false
    globalProperties.set(mapOf("models" to ""))
    supportingFilesConstrainedTo.set(listOf("JSON.java"))
}
