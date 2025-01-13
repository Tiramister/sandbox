plugins {
    id("java")
    id("com.diffplug.spotless") version "7.0.1"
}

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.springframework/spring-framework-bom
    implementation(platform("org.springframework:spring-framework-bom:6.2.1"))
    implementation("org.springframework:spring-core")
    // DI コンテナを提供するライブラリ
    implementation("org.springframework:spring-context")
    // AOP を提供するライブラリ
    implementation("org.springframework:spring-aop")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

spotless {
    java {
        googleJavaFormat()
    }
}
