plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.shv.canifly"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.shv.canifly"
        minSdk = 27
        targetSdk = 31
        multiDexEnabled = true
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        packaging {
            resources {
                excludes.add("lib/arm64-v8a/libc++_shared.so")
            }
        }

        ndk {
            abiFilters.add("arm64-v8a") // MSDK only supports arm64-v8a architecture
        }

        packaging {
            resources {
                pickFirsts.add("lib/arm64-v8a/libc++_shared.so")
                pickFirsts.add("lib/armeabi-v7a/libc++_shared.so")
                pickFirsts.add("lib/x86/libc++_shared.so")
                pickFirsts.add("lib/x86_64/libjsc.so")
                pickFirsts.add("lib/arm64-v8a/libjsc.so")
                pickFirsts.add("lib/x86_64/libc++_shared.so")
            }
        }

        sourceSets.getByName("main") {
            jniLibs.setSrcDirs(listOf("src/main/cpp/libs"))
        }

        packaging {
            jniLibs.keepDebugSymbols.add("*/*/libconstants.so")
            jniLibs.keepDebugSymbols.add("*/*/libdji_innertools.so")
            jniLibs.keepDebugSymbols.add("*/*/libdjibase.so")
            jniLibs.keepDebugSymbols.add("*/*/libDJICSDKCommon.so")
            jniLibs.keepDebugSymbols.add("*/*/libDJIFlySafeCore-CSDK.so")
            jniLibs.keepDebugSymbols.add("*/*/libdjifs_jni-CSDK.so")
            jniLibs.keepDebugSymbols.add("*/*/libDJIRegister.so")
            jniLibs.keepDebugSymbols.add("*/*/libdjisdk_jni.so")
            jniLibs.keepDebugSymbols.add("*/*/libDJIUpgradeCore.so")
            jniLibs.keepDebugSymbols.add("*/*/libDJIUpgradeJNI.so")
            jniLibs.keepDebugSymbols.add("*/*/libDJIWaypointV2Core-CSDK.so")
            jniLibs.keepDebugSymbols.add("*/*/libdjiwpv2-CSDK.so")
            jniLibs.keepDebugSymbols.add("*/*/libFlightRecordEngine.so")
            jniLibs.keepDebugSymbols.add("*/*/libvideo-framing.so")
            jniLibs.keepDebugSymbols.add("*/*/libwaes.so")
            jniLibs.keepDebugSymbols.add("*/*/libagora-rtsa-sdk.so")
            jniLibs.keepDebugSymbols.add("*/*/libc++.so")
            jniLibs.keepDebugSymbols.add("*/*/libc++_shared.so")
            jniLibs.keepDebugSymbols.add("*/*/libmrtc_28181.so")
            jniLibs.keepDebugSymbols.add("*/*/libmrtc_agora.so")
            jniLibs.keepDebugSymbols.add("*/*/libmrtc_core.so")
            jniLibs.keepDebugSymbols.add("*/*/libmrtc_core_jni.so")
            jniLibs.keepDebugSymbols.add("*/*/libmrtc_data.so")
            jniLibs.keepDebugSymbols.add("*/*/libmrtc_log.so")
            jniLibs.keepDebugSymbols.add("*/*/libmrtc_onvif.so")
            jniLibs.keepDebugSymbols.add("*/*/libmrtc_rtmp.so")
            jniLibs.keepDebugSymbols.add("*/*/libmrtc_rtsp.so")
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
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.preference:preference-ktx:1.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    val lifecycle_version = "2.7.0"

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    val roomVersion = "2.6.1"

    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    implementation("io.coil-kt:coil:2.3.0")

    implementation("androidx.work:work-runtime-ktx:2.9.0")

    implementation("com.google.dagger:hilt-android:2.50")
    ksp("com.google.dagger:hilt-android-compiler:2.50")

    implementation("com.google.android.gms:play-services-location:21.1.0")

    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")

    val navVersion = "2.7.7"
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    implementation("androidx.multidex:multidex:2.0.1")
    //mapbox
    implementation("com.mapbox.maps:android:11.1.0")
    //dji
    implementation("com.dji:dji-sdk-v5-aircraft:5.8.0")
    compileOnly("com.dji:dji-sdk-v5-aircraft-provided:5.8.0")
    implementation("com.dji:dji-sdk-v5-networkImp:5.8.0")
}