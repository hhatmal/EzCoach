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
        mavenCentral()
    }
}

rootProject.name = "EzCoach"
include(":app")
include(":core:data")
include(":core:presentation")
include(":core:presentation:designsystem")
include(":core:domain")
include(":workout:presentation")
include(":workout:domain")
include(":workout:data")
include(":auth:data")
include(":auth:domain")
include(":auth:presentation")
include(":core:presentation:ui")
