dependencies {
    implementation(project(":domain", "runtimeElements"))
    implementation(rootProject.libs.bundles.springboot)
    implementation(rootProject.libs.bundles.springdoc)
}

val jar: Jar by tasks
jar.enabled = false

tasks.bootJar {
    dependsOn(copyDependency)
    exclude("dd-java-agent-*.jar")
}

val copyDependency by tasks.registering(Copy::class) {
    from(configurations.compileClasspath)
    into("$buildDir/libs")
    include("dd-java-agent-*.jar")
}
