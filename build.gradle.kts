plugins {
	alias(libs.plugins.loom)
	alias(libs.plugins.minotaur)
	`maven-publish`
}

val modVersion: String by project
val branchName: String by project
val slug: String by project

version = "$modVersion+$branchName"

repositories {
	// Modrinth Maven - see: https://support.modrinth.com/en/articles/8801191-modrinth-maven
	// To use in a dependency, use: maven.modrinth:mod-id
	exclusiveContent {
		forRepositories(maven("https://api.modrinth.com/maven")).filter {
			includeGroup("maven.modrinth")
		}
	}
}

dependencies {
	minecraft(libs.mc)

	implementation(libs.fl)
	implementation(libs.fapi)
}

tasks.processResources {
	val user: String by project
	val authors: String by project
	val contributors: String by project

	val meta: Map<String, Any> = mapOf(
		"version" to project.version,
		"modId" to providers.gradleProperty("modId"),
		"modName" to providers.gradleProperty("modName"),
		"modDescription" to providers.gradleProperty("modDescription"),
		"homepage" to "https://modrinth.com/mod/$slug",
		"issues" to "https://github.com/$user/$slug/issues",
		"sources" to "https://github.com/$user/$slug",
		"license" to providers.gradleProperty("license"),
		"authors" to authors.split(", ").joinToString("\",\n    \""),
		"contributors" to contributors.split(", ").joinToString("\",\n    \""),
		"members" to "$authors${if (contributors.isEmpty()) "" else ". Contributions by $contributors."}",
		"mc" to providers.gradleProperty("compatibleVersions").map { it.split(", ")[0] },
		"fl" to libs.versions.fl,
		"fapi" to libs.versions.fapi
	)

	inputs.properties(meta)

	filesMatching(listOf("*.mod.json", "META-INF/*mods.toml")) {
		expand(meta)
	}
}

tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"
	options.release = 25
}

java {
	withSourcesJar()
	sourceCompatibility = JavaVersion.VERSION_25
	targetCompatibility = JavaVersion.VERSION_25
}

publishing {
	publications {
		register<MavenPublication>("mavenJava") {
			from(components["java"])
		}
	}
}

modrinth {
	val compatibleVersions: String by project
	val compatibleLoaders: String by project
	val readme: RegularFile = rootProject.layout.projectDirectory.file("README.md")

	projectId = slug
	token = providers.environmentVariable("MODRINTH_TOKEN")

	versionNumber = project.version.toString()
	uploadFile.set(tasks.jar)
	gameVersions = compatibleVersions.split(", ")
	loaders = compatibleLoaders.split(", ")
	changelog = providers.environmentVariable("CHANGELOG")

	syncBodyFrom = providers.fileContents(readme).asText.map {
		"<!--DO NOT EDIT MANUALLY: synced from gh readme-->\n$it"
	}

	dependencies {
		required.version("fabric-api", libs.versions.fapi.get())
	}
}
