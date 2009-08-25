import groovyx.net.ws.WSClient

println "entet 1";
@Grab(group='org.codehaus.groovy.modules', module='groovyws', version='0.5.0-SNAPSHOT')
def getProxy(wsdl, classLoader) {
  new WSClient(wsdl, classLoader)
}
println "entet 2";
proxy = getProxy("http://localhost:8080/Helio/services/test?wsdl", this.class.classLoader)
println "entet 3";
proxy.initialize()
println "entet 4";
result = proxy.convert("AUD", "USD", 10.0)
println "10 AUD are worth ${result} USD"