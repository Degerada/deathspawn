import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

group = "de.degra"
version = "0.1-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.3.41"
}

repositories {
    mavenCentral()
    maven {
        name = "destroystokyo-repo"
        url = URI.create("https://repo.destroystokyo.com/repository/maven-public/")
    }
    maven { url = URI.create("https://maven.sk89q.com/repo/") }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.destroystokyo.paper", "paper-api", "1.14.3-R0.1-SNAPSHOT")
    compileOnly("com.sk89q.worldguard", "worldguard-bukkit", "7.0.0")
}

tasks.create<Jar>("fatJar") {
    appendix = "fat"
    setDuplicatesStrategy(DuplicatesStrategy.EXCLUDE)
    manifest {
        //attributes(mapOf("Main-Class" to "Main")) // replace it with your own
    }
    val sourceMain = sourceSets["main"]
    from(sourceMain.output)

    configurations
        .runtimeClasspath
        .filter { it.name.endsWith(".jar") }
        .filter { it.name.contains("kotlin") || it.name.contains("annotations") }
        .forEach { jar ->
            from(zipTree(jar))
        }

    into("/") {
        from(file("./plugin.yml"))
    }
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}