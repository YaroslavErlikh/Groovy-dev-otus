import groovy.transform.ToString

@ToString(fieldSeparator = ";", nameValueSeparator = "=", includeNames = true)
class ServerConfig extends ServerConfigurable {
    String name
    String description

    Protocol http
    Protocol https

    List<Mapping> mappings

    def http(Closure closure) {
        http = new Protocol()
        closure.setDelegate(http)
        closure.setResolveStrategy(Closure.DELEGATE_FIRST)
        closure.call()
    }

    def https(Closure closure) {
        https = new Protocol()
        closure.setDelegate(https)
        closure.setResolveStrategy(Closure.DELEGATE_FIRST)
        closure.call()
    }
}
