import groovy.transform.ToString

@ToString(fieldSeparator = ";", nameValueSeparator = "=", includeNames = true)
class Mapping extends ServerConfigurable {
    String endpoint
    String method
}
