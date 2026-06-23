// Modified by LKY-Lockee on 2026/6/22

plugins {
	id("com.android.library")
	id("com.vanniktech.maven.publish")
	id("com.kezong.fat-aar")
}

val mCompileSdkVersion = rootProject.extra["mCompileSdkVersion"] as Int
val mMinSdkVersion = rootProject.extra["mMinSdkVersion"] as Int

android {
	namespace = "xyz.doikki.videoplayer.ijk"
	compileSdk = mCompileSdkVersion

	defaultConfig {
		minSdk = mMinSdkVersion
	}
}

extra.apply {
	set("PUBLISH_ARTIFACT_ID", "player-ijk")
}

dependencies {
	embed(project(":dkplayer-locallibs:ijkplayer"))

	compileOnly(project(":dkplayer-java"))
}