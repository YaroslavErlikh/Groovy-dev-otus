class LoveGradleMessageDecorator implements MessageDecorator {

    @Override
    String decorate(String message) {
        return "$message Love Gradle"
    }
}
