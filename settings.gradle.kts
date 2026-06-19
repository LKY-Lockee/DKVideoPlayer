// Modified by LKY-Lockee on 2026/6/22

pluginManagement {
	repositories {
		gradlePluginPortal()
		google()
		mavenCentral()
		maven { url = uri("https://artifactory.appodeal.com/appodeal") }
		maven { url = uri("https://jitpack.io/") }
	}
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		google()
		mavenCentral()
		maven { url = uri("https://artifactory.appodeal.com/appodeal") }
		maven { url = uri("https://jitpack.io/") }
	}
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":dkplayer-sample")
include(":dkplayer-videocache")
include(":dkplayer-java")
include(":dkplayer-localrepo")
include(":dkplayer-ui")
include("dkplayer-players:ijk")
include("dkplayer-players:exo")