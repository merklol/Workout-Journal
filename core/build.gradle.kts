import extensions.configuration
import extensions.implementation

plugins {
    `android-library-convention`
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

configuration(testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner")

dependencies {
    implementation(Libraries.room())
    implementation(Libraries.coroutines())
    implementation(Libraries.navigation())
    implementation(Libraries.daggerHilt())
    implementation(Libraries.lifecycle)
    implementation(Libraries.recyclerview)

    implementation(project(":core-ui"))
}

kapt {
    correctErrorTypes = true
}
