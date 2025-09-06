tasks {
  register("clean") {
    val binDir = file("$projectDir/.bin")
    if (binDir.exists()) {
      binDir.deleteRecursively()
    }
    dependsOn(":pwp-backend:clean")
    dependsOn(":pwp-frontend:clean")
  }
}
