// Modified by LKY-Lockee on 2026/6/22

plugins {
	id("com.android.application") version "9.1.1" apply false
	id("com.android.library") version "9.1.1" apply false
	id("org.jetbrains.dokka") version "2.2.0" apply false
	id("org.jetbrains.kotlin.android") version "2.2.10" apply false
	id("com.vanniktech.maven.publish") version "0.37.0" apply false
	id("com.kezong.fat-aar") version "1.4.5" apply false
}

extra.apply {
	set("mCompileSdkVersion", 37)
	set("mMinSdkVersion", 28)
	set("mTargetSdkVersion", 37)
	set("mReleaseVersion", "4.0.1")
	set("mReleaseVersionCode", 60)
}

tasks.register<Delete>("clean") {
	delete(layout.buildDirectory)
}

tasks.register<Javadoc>("javadoc") {
	options.encoding = "utf-8"
}

subprojects {
	plugins.withType<com.vanniktech.maven.publish.MavenPublishPlugin> {
		configure<com.vanniktech.maven.publish.MavenPublishBaseExtension> {
			project.afterEvaluate {
				val publishGroupId = "io.github.lky-lockee.dkplayer"
				val publishArtifactId = project.findProperty("PUBLISH_ARTIFACT_ID") as String?
				val publishVersion = rootProject.findProperty("mReleaseVersion") as String?

				coordinates(publishGroupId, publishArtifactId, publishVersion)

				pom {
					name.set(publishArtifactId)
					description.set("A video player for Android.")
					url.set("https://github.com/LKY-Lockee/DKVideoPlayer")

					licenses {
						license {
							name.set("The Apache License, Version 2.0")
							url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
						}
					}

					developers {
						developer {
							id.set("Doikki")
							name.set("Doikki")
							email.set("xinyunjian1995@gmail.com")
						}
						developer {
							id.set("LKY-Lockee")
							name.set("LKY-Lockee")
							email.set("dncmtwu@gmail.com")
						}
					}

					scm {
						connection.set("scm:git:github.com/LKY-Lockee/DKVideoPlayer.git")
						developerConnection.set("scm:git:ssh://github.com/LKY-Lockee/DKVideoPlayer.git")
						url.set("https://github.com/LKY-Lockee/DKVideoPlayer/tree/master")
					}
				}

				publishToMavenCentral()
				signAllPublications()
			}
		}
	}
}