androidApplication {
    namespace = "com.harmony.player"

    android {
        defaultConfig {
            applicationId = "com.harmony.player"
            minSdk = 30
            targetSdk = 34
            versionCode = 1
            versionName = "1.0.0"
        }
        buildFeatures {
            viewBinding = true
        }
    }

    dependencies {
        // AndroidX core UI
        implementation("androidx.core:core-ktx:1.12.0")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.11.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
        implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
        implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
        implementation("androidx.preference:preference-ktx:1.2.1")
        implementation("androidx.media:media:1.7.0")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

        // Keep utilities and list modules
        implementation(project(":utilities"))
        implementation(project(":list"))
    }
}
