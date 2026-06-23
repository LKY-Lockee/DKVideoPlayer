// Modified by LKY-Lockee on 2026/6/22

plugins {
	id("com.android.library")
	id("com.vanniktech.maven.publish")
}

val mCompileSdkVersion = rootProject.extra["mCompileSdkVersion"] as Int
val mMinSdkVersion = rootProject.extra["mMinSdkVersion"] as Int

android {
	namespace = "xyz.doikki.videoplayer"
	compileSdk = mCompileSdkVersion

	defaultConfig {
		minSdk = mMinSdkVersion
	}

	buildFeatures {
		buildConfig = true
	}
}

extra.apply {
	set("PUBLISH_ARTIFACT_ID", "dkplayer-java")
}

dependencies {
	implementation("androidx.annotation:annotation-jvm:1.10.0")
}