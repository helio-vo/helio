import groovyx.net.ws.WSClient

@Grab(group='org.codehaus.groovy.modules', module='groovyws', version='0.5.0-SNAPSHOT')
def getProxy(wsdl, classLoader) {
  new WSClient(wsdl, classLoader)
}
proxy = getProxy("http://localhost:8080/xfire/services/test?wsdl", this.class.classLoader)
proxy.initialize()

result = proxy.convert("AUD", "USD", 10.0)
println "10 AUD are worth ${result} USD"