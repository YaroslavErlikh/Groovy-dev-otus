import groovy.transform.ToString

@ToString(fieldSeparator = ";", nameValueSeparator = "=", includeNames = true)
class Protocol extends ServerConfigurable {
    String host
    int port
    boolean secure
}
