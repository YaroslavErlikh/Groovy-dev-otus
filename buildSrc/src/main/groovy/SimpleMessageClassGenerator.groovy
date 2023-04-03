import java.nio.file.Files

class SimpleMessageClassGenerator {

    private MessageProvider messageProvider

    void generate(String packageName, File outputDir) {

        def fileName = "SimpleFile.txt"
        def message = messageProvider.provide()

        def path = packageName.replace(".","\\") + "\\" + outputDir
        def folders = new File(path)
        def directory = folders.toPath()

        if (Files.notExists(directory) || Files.isDirectory(directory)) {
            throw new IOException("path $directory exists but is not a directory.")
        }
        var outputDirectory = directory

        def directories = Files.createDirectories(outputDirectory)

        def filePath = directories.toString() + "\\" + fileName

        Files.write(new File(filePath).toPath(), message.bytes)
    }
}
