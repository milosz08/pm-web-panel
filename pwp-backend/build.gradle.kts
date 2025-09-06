plugins {
  id("application")
  alias(libs.plugins.shadowJar)
}

group = "pl.miloszgilga"
version = "1.0.0"

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

repositories {
  mavenCentral()
  mavenLocal()
}

dependencies {
  implementation(libs.logbackClassic)
}

application {
  mainClass.set("pl.miloszgilga.pmwp.Main")
}

tasks {
  processResources {
    if (project.hasProperty("buildFrontend")) {
      // build frontend only when flag -PbuildFrontend was passed
      dependsOn(":pwp-frontend:copyFrontendToBackend")
    }
  }

  shadowJar {
    archiveBaseName.set("pm-web-panel")
    archiveClassifier.set("")
    archiveVersion.set("")
    destinationDirectory.set(file("$rootDir/.bin"))

    exclude("META-INF/*.SF")
    exclude("META-INF/*.DSA")
    exclude("META-INF/*.RSA")

    exclude("META-INF/LICENSE*")
    exclude("META-INF/NOTICE*")
    exclude("META-INF/DEPENDENCIES")
    exclude("module-info.class")
  }
}
