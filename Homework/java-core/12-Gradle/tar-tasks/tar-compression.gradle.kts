tasks.register<Tar>("compressFiles") {
    description = "Archives all '*.text' resources files into a tar archive using `Gzip` algorithm"
    group = "custom-archive"

    doFirst {
        println("Archiving files to build/archive...")
    }

    archiveBaseName.set("compressedFiles")
    destinationDirectory.set(layout.projectDirectory.dir("build/archive"))
    compression = Compression.GZIP
    from(layout.projectDirectory.dir("src/main/resources")) {
        includeEmptyDirs = false
        include("**/*.text")
        rename("(.*).text$", "$1.txt")
    }
}

tasks.register("triggerCompression") {
    description = "Triggers files compression"
    group = "custom-archive"
    dependsOn("compressFiles")
}

tasks.register<Copy>("decompressTarArchive") {
    description = "Decompresses the tar archive into the 'src/test/resources' directory"
    group = "custom-archive"

    from(project.tarTree("build/archive/compressedFiles.tgz"))
    into("src/test/resources")

    doLast {
        println("Unarchiving files to src/test/resources...")
    }
}

tasks.register<Delete>("cleanup") {
    description = "Deletes all files from the `build/archive` and `src/test/resources` dirs"
    group = "custom-archive"

    val firstDir = "src/test/resources/"
    file(firstDir).list()?.forEach { f ->
        delete("${firstDir}${f}")
    }

    val secondDir = "build/archive/"
    file(secondDir).list()?.forEach { f ->
        delete("${secondDir}${f}")
    }

    //delete("build/archive/, "src/test/resources/")
}
