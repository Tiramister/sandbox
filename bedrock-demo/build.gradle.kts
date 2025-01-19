plugins {
    id("java")
    id("com.diffplug.spotless") version "7.0.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("software.amazon.awssdk:bom:2.30.2"))
    implementation("software.amazon.awssdk:bedrockruntime")
}

spotless {
    java {
        googleJavaFormat()
    }
}