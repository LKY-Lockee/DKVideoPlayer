// Modified by LKY-Lockee on 2026/6/22

plugins {
	id("com.android.application")
}

apply(from = "../constants.gradle.kts")

android {
	namespace = "xyz.doikki.dkplayer"
	compileSdk = project.extra["compileSdkVersion"] as Int

	defaultConfig {
		applicationId = "xyz.doikki.dkplayer"
		minSdk = project.extra["minSdkVersion"] as Int
		targetSdk = project.extra["targetSdkVersion"] as Int
		versionCode = project.extra["releaseVersionCode"] as Int
		versionName = project.extra["releaseVersion"] as String
		multiDexEnabled = true
	}

	buildFeatures {
		buildConfig = true
	}

	lint {
		abortOnError = false
		checkReleaseBuilds = false
	}

	signingConfigs {
		create("release") {
			storeFile = file("../devlin.jks")
			storePassword = findProperty("KEYSTORE_PWD") as String?
			keyAlias = findProperty("KEY_ALIAS") as String?
			keyPassword = findProperty("KEY_PWD") as String?
		}
	}

	buildTypes {
		getByName("debug") {
			signingConfig = signingConfigs.getByName("release")
			isMinifyEnabled = false
			isShrinkResources = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}

		getByName("release") {
			signingConfig = signingConfigs.getByName("release")
			isMinifyEnabled = true
			isShrinkResources = true
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
}

dependencies {
	implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
	implementation("androidx.core:core-ktx:1.19.0")
	implementation("androidx.recyclerview:recyclerview:1.4.0")
	implementation("androidx.constraintlayout:constraintlayout:2.2.1")
	implementation("androidx.viewpager2:viewpager2:1.1.0")
	implementation("com.github.bumptech.glide:glide:5.0.7")
	implementation("com.github.ctiao:DanmakuFlameMaster:0.9.25")
	implementation("com.github.ctiao:ndkbitmap-armv7a:0.9.24")
	implementation("com.google.android.material:material:1.14.0")
	implementation("com.google.code.gson:gson:2.14.0")
	implementation("androidx.activity:activity-ktx:1.13.0")

	implementation(project(":dkplayer-java"))
	implementation(project(":dkplayer-ui"))
	implementation(project(":dkplayer-players:ijk"))
	implementation(project(":dkplayer-localrepo"))
	implementation(project(":dkplayer-players:exo"))
	implementation(project(":dkplayer-videocache"))

	annotationProcessor("com.github.bumptech.glide:compiler:5.0.7")
}