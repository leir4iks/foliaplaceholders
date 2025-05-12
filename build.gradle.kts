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
    maven("https://jitpack.io")
}

dependencies {
    // Paper API (Bukkit API) для доступа к org.bukkit.*
    compileOnly("io.papermc.paper:paper-api:1.20.2-R0.1-SNAPSHOT")

    // PlaceholderAPI - compileOnly, т.к. на сервере должна быть установлена
    compileOnly("me.clip:placeholderapi:2.11.6")

    // FoliaLib из jitpack, включаем в shadowJar
    implementation("com.github.TechnicallyCoded:FoliaLib:main-SNAPSHOT")
}

tasks {
    jar {
        enabled = false
    }

    shadowJar {
        archiveBaseName.set("foliaplaceholders")
        archiveVersion.set("1.0.0")
        archiveClassifier.set("")
        archiveExtension.set("jar")

        // Релокация FoliaLib, чтобы избежать конфликтов
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
