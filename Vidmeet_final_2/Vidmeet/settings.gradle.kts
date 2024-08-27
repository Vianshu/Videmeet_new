pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
//        maven { url =uri("https://maven.google.com") }

        mavenCentral()
        maven { url =uri("https://github.com/jitsi/jitsi-maven-repository/raw/master/releases") }
        maven { url =uri("https://jitpack.io") }

    }
}

rootProject.name = "Vidmeet"
include(":app")
 