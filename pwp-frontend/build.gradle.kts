import com.github.gradle.node.yarn.task.YarnTask

plugins {
  alias(libs.plugins.gradleNode)
}

tasks {
  register("yarnInstall", YarnTask::class) {
    args.set(listOf("install"))
  }

  register("yarnBuild", YarnTask::class) {
    dependsOn("yarnInstall")
    args.set(listOf("run", "build"))
  }

  register("copyFrontendToBackend", Copy::class) {
    dependsOn("yarnBuild")

    from("$projectDir/target/dist")
    into("$rootDir/pwp-backend/build/resources/main/static/.")
  }

  register("clean") {
    val distDir = file("$projectDir/target")
    if (distDir.exists()) {
      distDir.deleteRecursively()
    }
  }
}
