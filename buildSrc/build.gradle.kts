plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    mavenCentral()
    google()
    maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
}

dependencies {
    implementation("com.android.tools.build:gradle:7.0.4")
    implementation(kotlin("gradle-plugin", version = "1.6.10"))
}