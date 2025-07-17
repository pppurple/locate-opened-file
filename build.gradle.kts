import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.intellij.platform.gradle.models.ProductRelease
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.intellij.platform") version "2.6.0"
    kotlin("jvm") version "2.2.0"
}

group = "com.github.pppurple"
version = "1.1.0"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    intellijPlatform {
        intellijIdeaCommunity("2025.1.2")
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)
    }
}

intellijPlatform {
    pluginConfiguration {
        id = "com.github.pppurple.locate-opened-file"
        name = "Locate Opened File"
        vendor {
            name = "pppurple"
            url = "https://github.com/pppurple"
        }
        description =
            """
            <h1>Locate Opened File</h1>
            <p>This plugin provides scrolling to opened file without focusing on the Project view.</p>
            <h2>Usage</h2>
            <ol>
              <li>Install the plugin</li>
              <li>Default keymap is <code>Shift + Ctrl + S</code></li>
            </ol>
            """.trimIndent()

        ideaVersion {
            // https://plugins.jetbrains.com/docs/intellij/build-number-ranges.html
            // https://plugins.jetbrains.com/docs/intellij/build-number-ranges.html#earlier-versions
            // https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-extension.html#intellijPlatform-pluginConfiguration-ideaVersion
            sinceBuild = "201"
        }

        changeNotes =
            """
            Adapt to 2025.1.2
            """.trimIndent()
    }

    pluginVerification {
        ides {
            ide(IntelliJPlatformType.IntellijIdeaCommunity, "2025.1.2")
            recommended()
            select {
                types = listOf(IntelliJPlatformType.PhpStorm)
                channels = listOf(ProductRelease.Channel.RELEASE)
                sinceBuild = "201"
            }
        }
    }

    publishing {
        token = providers.gradleProperty("intellijPlatformPublishingToken")
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}
