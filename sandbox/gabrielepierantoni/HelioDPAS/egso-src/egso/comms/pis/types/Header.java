// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.2_01, build R40)
// Generated source version: 1.1.2

package org.egso.comms.pis.types;


public class Header {
    protected java.lang.String deliveryId;
    protected java.lang.String[] deliveryNodes;
    protected int statusCode;
    protected java.lang.String senderId;
    protected java.lang.String recipientId;
    
    public Header() {
    }
    
    public Header(java.lang.String deliveryId, java.lang.String[] deliveryNodes, int statusCode, java.lang.String senderId, java.lang.String recipientId) {
        this.deliveryId = deliveryId;
        this.deliveryNodes = deliveryNodes;
        this.statusCode = statusCode;
        this.senderId = senderId;
        this.recipientId = recipientId;
    }
    
    public java.lang.String getDeliveryId() {
        return deliveryId;
    }
    
    public void setDeliveryId(java.lang.String deliveryId) {
        this.deliveryId = deliveryId;
    }
    
    public java.lang.String[] getDeliveryNodes() {
        return deliveryNodes;
    }
    
    public void setDeliveryNodes(java.lang.String[] deliveryNodes) {
        this.deliveryNodes = deliveryNodes;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
    
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    
    public java.lang.String getSenderId() {
        return senderId;
    }
    
    public void setSenderId(java.lang.String senderId) {
        this.senderId = senderId;
    }
    
    public java.lang.String getRecipientId() {
        return recipientId;
    }
    
    public void setRecipientId(java.lang.String recipientId) {
        this.recipientId = recipientId;
    }
}