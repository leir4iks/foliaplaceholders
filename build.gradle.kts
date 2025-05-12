plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.papermc.paperweight.userdev") version "1.7.7"
}

group = "com.example"
version = "1.0.0"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")

    compileOnly("dev.folia:folia-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.6")
}

tasks {
    // Настройка shadowJar
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveBaseName.set("foliaplaceholders")
        archiveVersion.set(version.toString())
        archiveClassifier.set("")
        archiveExtension.set("jar")
        mergeServiceFiles()
    }

    // Регистрация задачи реобфускации shadowJar
    val reobfShadowJar by registering(io.papermc.paperweight.tasks.ReobfJar::class) {
        input.set(named("shadowJar").flatMap { it.archiveFile })
        output.set(layout.buildDirectory.file("libs/foliaplaceholders-reobf.jar"))
    }

    named("build") {
        dependsOn(reobfShadowJar)
    }
}
