plugins {
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.dependency.management) apply false
    alias(libs.plugins.lombok) apply false
}

allprojects {
    repositories {
        mavenCentral()
//        maven { url = uri("https://repo.spring.io/milestone") }
    }
}

subprojects {
    apply {
        plugin("java")
        plugin("java-library")
        plugin("jacoco")
        plugin("groovy")
        plugin("idea")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("io.freefair.lombok")
    }

    group = "com.gmail.shinch"
    version = "0.0.1-SNAPSHOT"

    dependencies {
        val implementation by configurations
        val testImplementation by configurations
        val annotationProcessor by configurations

        implementation(rootProject.libs.bundles.util)
        implementation(rootProject.libs.mapstruct)
        testImplementation(rootProject.libs.spring.boot.starter.test)
        testImplementation(rootProject.libs.bundles.spock)
        annotationProcessor(rootProject.libs.mapstruct.processor)
    }


    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

}
