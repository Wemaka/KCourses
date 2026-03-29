pluginManagement {
    includeBuild("build-logic")

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
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "KCourses"

include(":app")

include(":core:common")
include(":core:storage")
include(":core:navigation")

include(":data")

include(":domain")

include(":feature:common")

include(":feature:login:api")
include(":feature:login:impl")

include(":feature:courses:api")
include(":feature:courses:impl")

include(":feature:favoriteCourses:api")
include(":feature:favoriteCourses:impl")

include(":uikit")
