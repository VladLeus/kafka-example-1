plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencies {
    implementation("org.apache.kafka:kafka-clients:4.0.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.guava:guava:31.1-jre")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}