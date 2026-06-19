// Modified by LKY-Lockee on 2026/6/22

plugins {
	id("com.android.library")
}

apply(from = "../../constants.gradle.kts")

android {
	namespace = "xyz.doikki.videoplayer.exo"
	compileSdk = project.extra["compileSdkVersion"] as Int

	defaultConfig {
		minSdk = project.extra["minSdkVersion"] as Int
	}
}

extra.apply {
	set("PUBLISH_ARTIFACT_ID", "player-exo")
}

dependencies {
	implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
	implementation(project(":dkplayer-java"))
	implementation("androidx.media3:media3-datasource-rtmp:1.10.1") {
		exclude(group = "io.antmedia", module = "rtmp-client")
	}
	implementation("com.github.mcxinyu:LibRtmp-Client-for-Android:16647b19d5")
	implementation("androidx.annotation:annotation-jvm:1.10.0")
	api("androidx.media3:media3-exoplayer:1.10.1")
	api("androidx.media3:media3-exoplayer-dash:1.10.1")
	api("androidx.media3:media3-exoplayer-hls:1.10.1")
	api("androidx.media3:media3-exoplayer-rtsp:1.10.1")
}

apply(from = "../../publish.gradle.kts")