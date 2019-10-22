import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.1.8.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.2.71"
    kotlin("plugin.spring") version "1.2.71"
}

group = "io.jgoerner"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    jcenter()
    mavenCentral()
    maven(url="https://jitpack.io")
}

dependencies {
    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    // spring boot web for general app organization
    implementation("org.springframework.boot:spring-boot-starter-web")

    // spring boot config processor for config mgm
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // dev libs
    runtimeOnly("org.springframework.boot:spring-boot-devtools")

    // bundled dependency for RDF4J
    implementation("com.ontotext.graphdb:graphdb-free-runtime:8.11.0")
    
    // HTTP communication
    implementation("com.github.jkcclemens:khttp:-SNAPSHOT")

    // RML Engines
    implementation("com.taxonic.carml:carml-engine:0.2.3")
    implementation("com.taxonic.carml:carml-logical-source-resolver-jsonpath:0.2.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
