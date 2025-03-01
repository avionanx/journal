import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform

plugins {
  id("java")
  id("java-library")
  id("com.github.johnrengelman.shadow") version "7.1.2"
  id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "avi-journal"
version = "1.0-SNAPSHOT"

java {
  sourceCompatibility = JavaVersion.VERSION_21
  targetCompatibility = JavaVersion.VERSION_21
}

javafx {
  modules("javafx.controls", "javafx.fxml")
}

// This is so it picks up new builds on jitpack
configurations.all {
  resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

repositories {
  mavenCentral()
  mavenLocal() // Uncomment to use mavenLocal version of LoD engine
//  maven { url = uri("https://jitpack.io") }
}

dependencies {
  implementation("legend:lod:snapshot") // Uncomment to use mavenLocal version of LoD engine (also comment out next line)
//  implementation("com.github.Legend-of-Dragoon-Modding:Legend-of-Dragoon-Java:main-SNAPSHOT")
  implementation("com.opencsv:opencsv:5.7.1")
  implementation ("io.socket:socket.io-client:2.1.1")
  implementation ("com.google.protobuf:protobuf-java:4.27.2")
  api("org.legendofdragoon:mod-loader:4.2.0")
  api("org.legendofdragoon:script-recompiler:0.5.6")
  api("com.badlogicgames.jamepad:jamepad:2.30.0.0")
}

sourceSets {
  main {
    java {
      srcDirs("src/main/java")
      exclude(".gradle", "build", "files")
    }
  }
}

buildscript {
  repositories {
    gradlePluginPortal()
  }
  /*dependencies {
    implementation("com.github.johnrengelman.shadow:7.1.2")
  }*/
}

apply(plugin = "com.github.johnrengelman.shadow")
apply(plugin = "java")
apply(plugin = "org.openjfx.javafxplugin")

tasks.jar {
  exclude("*.jar");

  manifest {
    attributes["Main-Class"] = "lod.journal.Main"
  }
  val dependencies = configurations
          .runtimeClasspath
          .get()
          .map(::zipTree) // OR .map { zipTree(it) }
  from(dependencies)
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
tasks.register<Jar>("customJar") {
  //todo
  manifest {
    attributes["Main-Class"] = "lod.journal.Main" // Replace with your main class
  }
}