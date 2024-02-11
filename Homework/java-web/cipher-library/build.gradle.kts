plugins {
    id("java-library")
    id("maven-publish")
}

val assertjVersion = "3.24.2"

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:$assertjVersion")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
        groupId = "org.geekhub"
        artifactId = "ciphers"
        version = "1.0.0"

        from(components["java"])
        }
    }

    repositories {
        maven {
            url = uri("https://repsy.io/mvn/vrudas/ktkach-j4w-s13-repo")
            credentials {
                username = System.getenv("REPSY_USERNAME")
                password = System.getenv("REPSY_PASSWORD")
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
