// Modified by LKY-Lockee on 2026/6/22

plugins {
	id("com.android.library")
}

apply(from = "../constants.gradle.kts")

android {
	namespace = "com.danikula.videocache"
	compileSdk = project.extra["compileSdkVersion"] as Int

	defaultConfig {
		minSdk = project.extra["minSdkVersion"] as Int
	}
}

extra.apply {
	set("PUBLISH_ARTIFACT_ID", "videocache")
}

dependencies {
	implementation("androidx.annotation:annotation-jvm:1.10.0")
}

apply(from = "../publish.gradle.kts")