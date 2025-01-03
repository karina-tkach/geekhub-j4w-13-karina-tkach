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
        create<MavenPublication>("ciphers-library") {
        groupId = "org.geekhub"
        artifactId = "ciphers"
        version = "1.0.1"

        from(components["java"])
        }
    }

    repositories {
        maven {
            url = uri("https://repo.repsy.io/mvn/karinatkach/ciphers-library")
            credentials {
                username = System.getenv("REPSY_USER")
                password = System.getenv("REPSY_PASSWORD")
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
