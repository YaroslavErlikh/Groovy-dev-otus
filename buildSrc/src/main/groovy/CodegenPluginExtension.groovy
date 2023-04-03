import org.gradle.api.provider.Property

interface CodegenPluginExtension {

    Property<File> getMessageFileProperty()
    Property<File> getOutputDirProperty()
    Property<String> getPackageNameProperty()

}