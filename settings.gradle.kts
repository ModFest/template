val slug: String by settings
rootProject.name = slug

pluginManagement {
	repositories {
		maven("https://maven.fabricmc.net/")
		gradlePluginPortal()
	}
}
