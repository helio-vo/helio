// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.2_01, build R40)
// Generated source version: 1.1.2

package org.egso.comms.nds.types;


public class Application {
    protected java.lang.String id;
    protected java.lang.String parentPISId;
    protected java.lang.String name;
    protected java.net.URI type;
    protected java.net.URI endpoint;
    protected boolean alive;
    
    public Application() {
    }
    
    public Application(java.lang.String id, java.lang.String parentPISId, java.lang.String name, java.net.URI type, java.net.URI endpoint, boolean alive) {
        this.id = id;
        this.parentPISId = parentPISId;
        this.name = name;
        this.type = type;
        this.endpoint = endpoint;
        this.alive = alive;
    }
    
    public java.lang.String getId() {
        return id;
    }
    
    public void setId(java.lang.String id) {
        this.id = id;
    }
    
    public java.lang.String getParentPISId() {
        return parentPISId;
    }
    
    public void setParentPISId(java.lang.String parentPISId) {
        this.parentPISId = parentPISId;
    }
    
    public java.lang.String getName() {
        return name;
    }
    
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    public java.net.URI getType() {
        return type;
    }
    
    public void setType(java.net.URI type) {
        this.type = type;
    }
    
    public java.net.URI getEndpoint() {
        return endpoint;
    }
    
    public void setEndpoint(java.net.URI endpoint) {
        this.endpoint = endpoint;
    }
    
    public boolean isAlive() {
        return alive;
    }
    
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}