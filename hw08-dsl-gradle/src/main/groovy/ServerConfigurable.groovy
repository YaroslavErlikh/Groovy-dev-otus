import org.codehaus.groovy.control.CompilerConfiguration
import java.nio.file.Paths

class ServerConfigurable extends GroovyObjectSupport {

    URI scriptPath

    def include(String path) {
        URI uri = Paths.get(scriptPath).getParent().resolve(path).toUri()
        runFrom(uri)
    }

    def runFrom(URI uri) {
        this.scriptPath = uri
        CompilerConfiguration cc = new CompilerConfiguration()
        cc.setScriptBaseClass(DelegatingScript.class.getName())
        GroovyShell sh = new GroovyShell(Main.class.getClassLoader(), new Binding(), cc)
        DelegatingScript script = (DelegatingScript)sh.parse(uri)
        script.setDelegate(this)
        script.run()
    }

    def methodMissing(String name, def args) {
        MetaProperty metaProperty = getMetaClass().getMetaProperty(name)
        if (metaProperty != null) {
            Closure closure = (Closure) ((Object[]) args)[0]
            Object value = getProperty(name) ?:
                    metaProperty.getType().getConstructor().newInstance()
            if (value instanceof ServerConfigurable) {
                ((ServerConfigurable) value).scriptPath = scriptPath
            }
            closure.setDelegate(value)
            closure.setResolveStrategy(Closure.DELEGATE_FIRST)
            closure.call()
            setProperty(name, value)
        } else {
            throw new IllegalArgumentException("No such field: " + name)
        }
    }
}
