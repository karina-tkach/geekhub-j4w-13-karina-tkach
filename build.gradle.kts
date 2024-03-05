import name.remal.gradle_plugins.sonarlint.SonarLintExtension
import org.gradle.plugins.ide.idea.model.IdeaModel

plugins {
    id("name.remal.sonarlint") version "3.3.11"
}

allprojects {
    group = "org.geekhub"

    apply(plugin = "java")
    apply(plugin = "idea")
    apply(plugin = "checkstyle")
    apply(plugin = "jacoco")
    apply(plugin = "name.remal.sonarlint")

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        "implementation"("com.google.code.findbugs:jsr305:3.0.2")
    }

    tasks.named("build") {
        dependsOn("checkstyleMain", "sonarlintMain")
    }

    configure<IdeaModel> {
        module {
            sourceDirs.plusAssign(file("src/main/java"))
        }
    }

    tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
        dependsOn("jacocoTestReport")
        violationRules {
            isFailOnViolation = true
            rule {
                limit {
                    minimum = "0.85".toBigDecimal()
                }
            }
        }
    }

    configure<SonarLintExtension> {
        languages {
            include("java")
        }
        rules {
            disable(
                    "java:S1192", // Allow string literals to be duplicated
                    "java:S1197", // Allow constants to be defined in interfaces
                    "java:S1118", // Allow utility classes to have a private constructor
                    "java:S106", // Allow system out and err to be used
                    "java:S107", // Allow constructors with more than 7 parameters
                    "java:S3776", // Allow methods with more than 15 lines
                    "java:S1135", // Allow TODO comments
                    "java:S2094" // Allow empty classes for homeworks
            )
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

tasks.wrapper {
    gradleVersion = "8.6"
    distributionType = Wrapper.DistributionType.ALL
}
