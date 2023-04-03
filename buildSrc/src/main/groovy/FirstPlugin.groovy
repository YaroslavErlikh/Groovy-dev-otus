import org.gradle.api.Plugin
import org.gradle.api.Project

class FirstPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.task('helloFirst') {
            doLast {
                println 'Hello, this is FirstPlugin'
            }
        }
    }
}
