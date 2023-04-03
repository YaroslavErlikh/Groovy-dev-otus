import org.gradle.api.provider.Property

interface SecondPluginExtension {
        Property<String> getMessage()
        Property<String> getBuilder()
}