import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.apache.tools.ant.filters.FixCrLfFilter
import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    kotlin("jvm") version "1.8.21"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("com.github.gmazzo.buildconfig") version "3.0.0"
}

group = "ru.remsoftware"
version = "1.0-SNAPSHOT"

repositories {
    maven(url = "https://papermc.io/repo/repository/maven-public/")
}

val minecraft_version: String by project

dependencies {
    // PaperMC Dependency
    compileOnly("com.destroystokyo.paper", "paper-api", "$minecraft_version-R0.1-SNAPSHOT")

}

buildConfig {
    className("BuildConfig")
    packageName("SkillsPlugin")
}


tasks {
    processResources {
        filter(FixCrLfFilter::class)
        filter(ReplaceTokens::class, "tokens" to mapOf("version" to project.version))
        filteringCharset = "UTF-8"
    }
    jar {
        // Disabled, because we use the shadowJar task for building our jar
        enabled = false
    }
    build {
        dependsOn(shadowJar)
    }
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}