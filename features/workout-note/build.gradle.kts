import extensions.configuration
import extensions.implementation

plugins {
    `android-library-convention`
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

configuration(testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner")

dependencies {
    implementation(Libraries.room())
    implementation(Libraries.navigation())
    implementation(Libraries.daggerHilt())
    implementation(Libraries.coroutines())
    implementation(Libraries.lifecycle)
    implementation(Libraries.viewBindingDelegate)

    implementation(project(":core"))
    implementation(project(":core-ui"))
    implementation(project(":features:timer"))

    testImplementation(Libraries.jUnit)
    androidTestImplementation(Libraries.androidXJunit)
    androidTestImplementation(Libraries.espresso)
}

kapt {
    correctErrorTypes = true
}