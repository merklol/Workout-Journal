import extensions.configuration
import extensions.ConfigField
import extensions.implementation

plugins {
    `android-library-convention`
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

configuration(
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner",
    configFields = listOf(
        ConfigField(
            "String",
            "PRIVACY_POLICY_URL",
            "\"https://sites.google.com/view/workoutjournal/privacy_policy\""
        ),
        ConfigField(
            "String",
            "GOOGLE_PLAY_URL",
            "\"market://details?id=com.maximapps.alinchinese\""
        )
    )
)

dependencies {
    implementation(Libraries.room())
    implementation(Libraries.navigation())
    implementation(Libraries.daggerHilt())
    implementation(Libraries.lifecycle)
    implementation(Libraries.viewBindingDelegate)
    implementation(Libraries.sharedPreferences)

    implementation(project(":core"))
    implementation(project(":core-ui"))

    testImplementation(Libraries.jUnit)
    androidTestImplementation(Libraries.androidXJunit)
    androidTestImplementation(Libraries.espresso)
}

kapt {
    correctErrorTypes = true
}