plugins {
    id("org.jetbrains.intellij") version "1.1.2"
    kotlin("jvm") version "1.7.0"
}

group = "com.github.pppurple"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2021.1")
    updateSinceUntilBuild.set(false)
}

tasks {
    patchPluginXml {
        // https://plugins.jetbrains.com/docs/intellij/build-number-ranges.html
        sinceBuild.set("201")
        changeNotes.set(
            """
            Initial release of the plugin.
            """
        )
    }
}
