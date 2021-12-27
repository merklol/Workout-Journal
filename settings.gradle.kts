pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "dagger.hilt.android.plugin")
                with("com.google.dagger:hilt-android-gradle-plugin") {
                    useModule("$this:${requested.version}")
                }

            if (requested.id.id == "androidx.navigation.safeargs")
                with("androidx.navigation:navigation-safe-args-gradle-plugin") {
                    useModule("$this:${requested.version}")
                }

        }
    }
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
    }
}

rootProject.name = "Workout Journal"

include(":app")
include(":core")
include(":core-ui")
include(":features:main")
include(":features:timer")
include(":features:workout-note")
