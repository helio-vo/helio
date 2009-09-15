/**
import groovyx.net.ws.WSClient

def proxy = new
WSClient("http://localhost:8080/Helio/services/test?wsdl",
this.class.classLoader)
println "Proxy: ${proxy.dump()}"
proxy.initialize()
def result = proxy.convert('USD', 'AUD', 100.00D)

pritln "Res: ${result}"
**/