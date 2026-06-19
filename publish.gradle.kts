// Modified by LKY-Lockee on 2026/6/22

import java.io.FileInputStream
import java.util.Properties

apply(plugin = "maven-publish")
apply(plugin = "signing")

tasks.register<Jar>("androidSourcesJar") {
	archiveClassifier.set("sources")
	from(project.fileTree("src/main/java"))
	exclude("**/R.class")
	exclude("**/BuildConfig.class")
}

extra.apply {
	set("PUBLISH_GROUP_ID", "xyz.doikki.android.dkplayer")
	set("PUBLISH_VERSION", project.extra["releaseVersion"])
}

extra["signing.keyId"] = ""
extra["signing.password"] = ""
extra["signing.secretKeyRingFile"] = ""
extra["ossrhUsername"] = ""
extra["ossrhPassword"] = ""

val secretPropsFile = project.rootProject.file("local.properties")
if (secretPropsFile.exists())
{
	println("Found secret props file, loading props")
	val p = Properties()
	p.load(FileInputStream(secretPropsFile))
	p.forEach { (name, value) ->
		extra[name.toString()] = value
	}
} else
{
	println("No props file, loading env vars")
}

configure<org.gradle.api.publish.PublishingExtension> {
	publications {
		create<MavenPublication>("release") {
			groupId = extra["PUBLISH_GROUP_ID"] as String
			artifactId = extra["PUBLISH_ARTIFACT_ID"] as String
			version = extra["PUBLISH_VERSION"] as String

			artifact(layout.buildDirectory.file("outputs/aar/${project.name}-release.aar"))
			artifact(tasks.named("androidSourcesJar"))

			pom {
				name.set(extra["PUBLISH_ARTIFACT_ID"] as String)
				description.set("A video player for Android.")
				url.set("https://github.com/Doikki/DKVideoPlayer")
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
				}
				scm {
					connection.set("scm:git:github.com/Doikki/DKVideoPlayer.git")
					developerConnection.set("scm:git:ssh://github.com/Doikki/DKVideoPlayer.git")
					url.set("https://github.com/Doikki/DKVideoPlayer/tree/master")
				}
				withXml {
					val dependenciesNode = asNode().appendNode("dependencies")
					project.configurations.getByName("implementation").allDependencies.forEach {
						if (it.group != null && it.group != "unspecified"
							&& it.name != "unspecified"
							&& it.version != "unspecified"
						)
						{
							val dependencyNode = dependenciesNode.appendNode("dependency")
							dependencyNode.appendNode("groupId", it.group)
							dependencyNode.appendNode("artifactId", it.name)
							dependencyNode.appendNode("version", it.version)
						}
					}
				}
			}
		}
	}
	repositories {
		maven {
			name = "mavenCentral"
			val releasesRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
			val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
			url = uri(if (project.version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
			credentials {
				username = extra["ossrhUsername"] as String?
				password = extra["ossrhPassword"] as String?
			}
		}
	}
}

configure<org.gradle.plugins.signing.SigningExtension> {
	sign(project.extensions.getByType(org.gradle.api.publish.PublishingExtension::class.java).publications)
}