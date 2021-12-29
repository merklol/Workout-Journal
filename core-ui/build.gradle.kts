import extensions.configuration

plugins {
    `android-library-convention`
}

configuration(testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner")

dependencies {
    implementation(Libraries.preferences)
}