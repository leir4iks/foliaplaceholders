plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.example"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://jitpack.io") // Для FoliaLib
}

dependencies {
    implementation("com.github.TechnicallyCoded:FoliaLib:main-SNAPSHOT") // FoliaLib с JitPack
    compileOnly("me.clip:placeholderapi:2.11.6")
}

tasks {
    jar {
        enabled = false // Используем shadowJar
    }

    shadowJar {
        archiveBaseName.set("foliaplaceholders")
        archiveVersion.set("1.0.0")
        archiveClassifier.set("")
        archiveExtension.set("jar")

        // Релокация FoliaLib (обязательно!)
        relocate("com.tcoded.folialib", "com.example.foliaplaceholders.shaded.folialib")

        mergeServiceFiles()

        dependencies {
            exclude(dependency("me.clip:placeholderapi"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}
