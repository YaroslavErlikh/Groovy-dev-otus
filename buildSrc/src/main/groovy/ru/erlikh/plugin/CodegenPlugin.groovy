package ru.erlikh.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer

class CodegenPlugin implements Plugin<Project> {

    void apply(Project project) {
        def extension = project.extensions.create('simpleCodegen', CodegenPluginExtension)

        CodegenTask codegenTask = project.tasks.register("codegenSimpleClass", CodegenTask.class) {
            group = "Code generation"
            description = "Generates simple Groovy class with single message property"

            it.packageName = extension.packageNameProperty
            it.messageFile = extension.messageFileProperty.get()
            it.outputDirectory = extension.outputDirProperty.get()
        }.get()

        project.tasks.withType().configureEach {
            dependsOn(codegenTask)
        }

        project.afterEvaluate {
            (project.extensions["sourceSets"] as SourceSetContainer)["main"]
                    .java
                    .srcDir(extension.outputDirProperty)
        }
    }
}
