import org.codehaus.groovy.control.CompilerConfiguration

CompilerConfiguration configuration = new CompilerConfiguration()
configuration.setScriptBaseClass(DelegatingScript.class.getName())
GroovyShell sh = new GroovyShell(this.class.getClassLoader(), new Binding(), configuration)
File scriptFile = new File('srvConfigTest.dsl')
DelegatingScript script = sh.parse(scriptFile) as DelegatingScript
def srvConfig = new ServerConfig()
srvConfig.scriptPath = scriptFile.toURI()
script.setDelegate(srvConfig)
script.run()
println(srvConfig.toString())
