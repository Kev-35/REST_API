import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("java")
    id("io.qameta.allure") version "2.11.2"
    id("io.freefair.lombok") version "6.0.0-m2"
}

group = "ru.kev35"
version = "1.0-SNAPSHOT"


allure {
    report {
        version.set("2.24.0") //версия Allure Report (https://github.com/allure-framework/allure2)
    }
    adapter {
        aspectjWeaver.set(true) // обработка аннотации @Step
        frameworks {
            junit5 {
                adapterVersion.set("2.24.0") //версия Allure JUnit5 (https://github.com/allure-framework/allure-java)
            }
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {

    testImplementation(platform("org.junit:junit-bom:5.10.0")) //Junit5
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("com.github.javafaker:javafaker:1.0.2") //Fake data for tests

    testImplementation("com.codeborne:selenide:7.9.4")  //Code borne for Selenide
    testImplementation("com.codeborne:pdf-test:1.5.0") // Parse files pdf csv
    testImplementation("com.codeborne:xls-test:1.4.3") // Parse files xls, xlsx
    testImplementation("com.opencsv:opencsv:5.12.0") // Parse files csv
    testImplementation("com.google.code.gson:gson:2.13.2") // Parse files json

    testImplementation("com.fasterxml.jackson.core:jackson-core:2.16.0") // Parse files json
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")
    testImplementation("com.fasterxml.jackson.core:jackson-annotations:2.16.0")

    testImplementation("io.qameta.allure:allure-selenide:2.17.3") // Allure for Selenide
    testRuntimeOnly("org.aspectj:aspectjweaver:1.9.25") //aspectjWeaver для обработки аннотации step for Allure

    testImplementation("io.rest-assured:rest-assured:5.5.6") // REST-assured
    testImplementation("io.rest-assured:json-schema-validator:5.5.6")
    testImplementation("io.qameta.allure:allure-rest-assured:2.24.0")

    implementation("org.slf4j:slf4j-simple:2.0.7") // используется в Java-приложениях для абстракции процесса логирования или "org.slf4j:slf4j-api:2.0.7"
    implementation("org.assertj:assertj-core:3.27.6")
}

tasks.withType<Test> {
    useJUnitPlatform()

    testLogging {
        lifecycle {
            events("started", "skipped", "failed", "standard_error", "standard_out")
            // Замените "short" на TestExceptionFormat.SHORT
            exceptionFormat = TestExceptionFormat.SHORT
        }
    }
}

tasks.register("apiTest", Test::class) {
    useJUnitPlatform {
        includeTags("API-test") // запуск тестов по Тегу запуск
//     (или в терминале, или в дженкинсе -> gradle demoqa
//            excludeTags("Tag")
//    исключает тесты по Тегу
    }
}