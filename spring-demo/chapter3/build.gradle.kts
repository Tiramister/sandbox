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
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-jdbc")
    // BasicDataSource
    implementation("org.apache.commons:commons-dbcp2:2.13.0")
    // JDBC drivers
    implementation("org.postgresql:postgresql:42.7.5")
    implementation("com.mysql:mysql-connector-j:9.2.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(platform("org.springframework:spring-framework-bom:6.2.1"))
    testImplementation("org.springframework:spring-test")
}

tasks.test {
    useJUnitPlatform()
}

spotless {
    java {
        googleJavaFormat()
    }
}
