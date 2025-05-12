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
    maven("https://jitpack.io") // Добавляем jitpack для FoliaLib
}

dependencies {
    // FoliaLib из jitpack, используем implementation, чтобы включить в shadowJar
    implementation("com.github.technicallycoded:FoliaLib:main-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.6")
}

tasks {
    jar {
        enabled = false // Отключаем обычный jar, используем только shadowJar
    }

    shadowJar {
        archiveBaseName.set("foliaplaceholders")
        archiveVersion.set("1.0.0")
        archiveClassifier.set("")
        archiveExtension.set("jar")

        // Релокация пакетов FoliaLib, чтобы избежать конфликтов
        relocate("com.tcoded.folialib", "com.example.foliaplaceholders.lib.folialib")

        // Если нужно, можно раскомментировать для PlaceholderAPI (но обычно не обязательно)
        // relocate("me.clip.placeholderapi", "com.example.foliaplaceholders.lib.placeholderapi")

        mergeServiceFiles()

        // Исключаем зависимости, которые должны быть на сервере (PlaceholderAPI и Folia)
        dependencies {
            exclude(dependency("me.clip:placeholderapi"))
            exclude(dependency("com.github.technicallycoded:FoliaLib"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}
