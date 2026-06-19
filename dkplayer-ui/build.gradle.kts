// Modified by LKY-Lockee on 2026/6/22

plugins {
	id("com.android.library")
}

apply(from = "../constants.gradle.kts")

android {
	namespace = "xyz.doikki.videocontroller"
	compileSdk = project.extra["compileSdkVersion"] as Int

	defaultConfig {
		minSdk = project.extra["minSdkVersion"] as Int
	}
}

extra.apply {
	set("PUBLISH_ARTIFACT_ID", "dkplayer-ui")
}

dependencies {
	implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
	implementation("androidx.annotation:annotation-jvm:1.10.0")
	api(project(":dkplayer-java"))
}

apply(from = "../publish.gradle.kts")