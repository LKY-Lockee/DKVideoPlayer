// Modified by LKY-Lockee on 2026/6/22

plugins {
	id("com.android.library")
	id("com.vanniktech.maven.publish")
}

val mCompileSdkVersion = rootProject.extra["mCompileSdkVersion"] as Int
val mMinSdkVersion = rootProject.extra["mMinSdkVersion"] as Int

android {
	namespace = "xyz.doikki.videoplayer.exo"
	compileSdk = mCompileSdkVersion

	defaultConfig {
		minSdk = mMinSdkVersion
	}
}

extra.apply {
	set("PUBLISH_ARTIFACT_ID", "player-exo")
}

dependencies {
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