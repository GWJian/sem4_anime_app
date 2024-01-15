pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        //When you are using the Kotlin DSL for build.gradle.kts
        maven {url = uri("https://jitpack.io")}
    }
}

rootProject.name = "sem4_anime_app"
include(":app")
 