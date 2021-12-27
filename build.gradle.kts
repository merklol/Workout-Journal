plugins {
    id("dagger.hilt.android.plugin") version "2.40.1" apply false
    id("androidx.navigation.safeargs") version "2.4.0-rc01" apply false
    id("io.gitlab.arturbosch.detekt") version "1.19.0"
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
    }
}

tasks.register<io.gitlab.arturbosch.detekt.Detekt>("detektAll") {
    description = "Runs a custom detekt build."
    setSource(projectDir)
    config.setFrom(project.file("config/detekt/detekt.yml"))
    parallel = true
    reports {
        html {
            required.set(true)
            outputLocation.set(file("$rootDir/reports/detekt/detekt.html"))
        }
    }
    include("**/*.kt")
    exclude("**/resources/**", "**/build/**")

}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = "11"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}