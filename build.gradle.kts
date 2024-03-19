// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        val navVersion = "2.7.7"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
        classpath("com.android.tools.build:gradle:8.3.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.50")
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}
plugins {
    id("com.android.application") version "8.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
}