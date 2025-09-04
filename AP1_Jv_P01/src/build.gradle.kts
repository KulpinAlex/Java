plugins {
    id("com.gradleup.shadow") version "8.3.2"
    id("application")
}

group = "rogue.s21"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sourceSets {
   getByName("main") {
      java.srcDir("Rogue")
   }
}

dependencies {
    implementation("com.baulsupp.kolja:jcurses:0.9.5.3")
    implementation("com.google.code.gson:gson:2.13.1")
}

application {
    mainClass.set("Main")
}
