// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.2_01, build R40)
// Generated source version: 1.1.2

package org.egso.comms.log.types;


public class Throwable {
    protected java.lang.String type;
    protected java.lang.String message;
    protected java.lang.String detail;
    
    public Throwable() {
    }
    
    public Throwable(java.lang.String type, java.lang.String message, java.lang.String detail) {
        this.type = type;
        this.message = message;
        this.detail = detail;
    }
    
    public java.lang.String getType() {
        return type;
    }
    
    public void setType(java.lang.String type) {
        this.type = type;
    }
    
    public java.lang.String getMessage() {
        return message;
    }
    
    public void setMessage(java.lang.String message) {
        this.message = message;
    }
    
    public java.lang.String getDetail() {
        return detail;
    }
    
    public void setDetail(java.lang.String detail) {
        this.detail = detail;
    }
}