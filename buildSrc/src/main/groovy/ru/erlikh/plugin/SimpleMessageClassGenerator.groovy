package ru.erlikh.plugin

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

class SimpleMessageClassGenerator {

    private MessageProvider messageProvider

    void generate(String packageName, File outputDir) {

        def className = "SimpleClass"
        def message = messageProvider.provide()

        def kotlinFileSpecBuilder = FileSpec.builder(packageName, className)

        def classBuilder = TypeSpec.classBuilder(className)
        def property = PropertySpec.builder("message", String::class)
                .initializer("\"$message\"")
                .build()

        def clazz = classBuilder.addProperty(property).build()

        def kotlinFileSpec = kotlinFileSpecBuilder.addType(clazz).build()
        kotlinFileSpec.writeTo(outputDir)

//        def fileName = "SimpleFile.txt"
//        def message = messageProvider.provide()
//
//        def path = packageName.replace(".","\\") + "\\" + outputDir
//        def folders = new File(path)
//        def directory = folders.toPath()
//
//        if (Files.notExists(directory) || Files.isDirectory(directory)) {
//            throw new IOException("path $directory exists but is not a directory.")
//        }
//        var outputDirectory = directory
//
//        def directories = Files.createDirectories(outputDirectory)
//
//        def filePath = directories.toString() + "\\" + fileName
//
//        Files.write(new File(filePath).toPath(), message.bytes)
    }
}
