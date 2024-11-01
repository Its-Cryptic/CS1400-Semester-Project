plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

val jomlVersion = "1.10.5"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // Log4j
    implementation("org.apache.logging.log4j:log4j-api:2.23.1")
    implementation("org.apache.logging.log4j:log4j-core:2.23.1")

    implementation("org.joml", "joml", jomlVersion)

    implementation("org.jspecify:jspecify:1.0.0")

    implementation("org.json:json:20240303")

    implementation("org.openjdk.nashorn:nashorn-core:15.4")

}

tasks.test {
    useJUnitPlatform()
}