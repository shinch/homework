import org.gradle.api.tasks.bundling.Jar
import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(rootProject.libs.spring.boot.starter.web)
    implementation(rootProject.libs.bundles.database)
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

