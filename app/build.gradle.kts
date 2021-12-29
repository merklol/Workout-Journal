import extensions.configuration
import extensions.implementation

plugins {
    `android-app-convention`
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

configuration(
    applicationId = "com.maximapps.workoutjournal",
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
)

dependencies {
    implementation(Libraries.room())
    implementation(Libraries.navigation())
    implementation(Libraries.daggerHilt())
    implementation(Libraries.preferences)

    implementation(project(":core"))
    implementation(project(":core-ui"))
    implementation(project(":features:main"))
    implementation(project(":features:timer"))
    implementation(project(":features:workout-note"))
}

kapt {
    correctErrorTypes = true
}