import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

abstract class CodegenTask extends DefaultTask {

    @InputFile
    File messageFile

    @Input
    String packageName

    @OutputDirectory
    File outputDirectory

    @TaskAction
    invoke() {
        def messageDecorator = new LoveGradleMessageDecorator()
        def messageProvider =
                new FileDecoratedMessageProvider(messageFile, messageDecorator)
        def simpleFileGenerator = new SimpleMessageClassGenerator(messageProvider)

        simpleFileGenerator.generate(packageName, outputDirectory)
    }
}
