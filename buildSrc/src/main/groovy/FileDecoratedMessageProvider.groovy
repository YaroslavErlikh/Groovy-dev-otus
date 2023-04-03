class FileDecoratedMessageProvider implements MessageProvider {

    private File messageFile
    private LoveGradleMessageDecorator loveGradleMessageDecorator

    @Override
    String provide() {
        fileMessage = messageFile.text
        return loveGradleMessageDecorator.decorate(fileMessage)
    }
}
