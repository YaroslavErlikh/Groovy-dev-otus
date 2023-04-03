import org.gradle.api.Plugin
import org.gradle.api.Project

class SecondPlugin implements Plugin<Project> {

    void apply(Project project) {
        // Add the 'greeting' extension object
        def extension = project.extensions.create('secondPlugin', SecondPluginExtension)
        extension.message.convention('Hello from GreetingPlugin')
        // Add a task that uses configuration from the extension object
        project.task('helloSecond') {
            doLast {
                println "${extension.message.get()} from ${extension.builder.get()}"
            }
        }
    }
}