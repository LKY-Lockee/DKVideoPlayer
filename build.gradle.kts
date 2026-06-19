// Modified by LKY-Lockee on 2026/6/22

plugins {
	id("com.android.application") version "9.1.1" apply false
	id("com.android.library") version "9.1.1" apply false
	id("org.jetbrains.dokka") version "2.2.0" apply false
	id("org.jetbrains.kotlin.android") version "2.2.10" apply false
}

tasks.register<Delete>("clean") {
	delete(layout.buildDirectory)
}

tasks.register<Javadoc>("javadoc") {
	options.encoding = "utf-8"
}