plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}


android {
    namespace = "com.jyotish.template"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jyotish.template"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }


    externalNativeBuild {
        externalNativeBuild {
            cmake {
                path = file("CMakeLists.txt")
            }
        }
    }


    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    //Binding
    implementation ("com.wada811.viewbindingktx:viewbindingktx:3.0.0")
    implementation ("com.wada811.databindingktx:databindingktx:7.0.0")

    //chuker
    debugImplementation ("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation ("com.github.chuckerteam.chucker:library-no-op:4.0.0")

    //Coroutines

//    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
//    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
//    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")

    //Datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    //OKHttp
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    //OKHttp Logging
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.0.1")

    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

}