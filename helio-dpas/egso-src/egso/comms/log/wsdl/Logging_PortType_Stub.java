// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.2_01, build R40)
// Generated source version: 1.1.2

package org.egso.comms.log.wsdl;

import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.JAXRPCException;
import javax.xml.rpc.handler.HandlerChain;

import com.sun.xml.rpc.client.SenderException;
import com.sun.xml.rpc.client.StreamingSenderState;
import com.sun.xml.rpc.client.http.HttpClientTransport;
import com.sun.xml.rpc.encoding.CombinedSerializer;
import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
import com.sun.xml.rpc.encoding.SOAPDeserializationState;
import com.sun.xml.rpc.soap.message.InternalSOAPMessage;
import com.sun.xml.rpc.soap.message.SOAPBlockInfo;
import com.sun.xml.rpc.soap.streaming.SOAPNamespaceConstants;
import com.sun.xml.rpc.streaming.XMLReader;

public class Logging_PortType_Stub
    extends com.sun.xml.rpc.client.StubBase
    implements org.egso.comms.log.wsdl.Logging_PortType {
    
    
    
    /*
     *  public constructor
     */
    public Logging_PortType_Stub(HandlerChain handlerChain) {
        super(handlerChain);
        _setProperty(ENDPOINT_ADDRESS_PROPERTY, "REPLACE_WITH_ACTUAL_URL");
    }
    
    
    /*
     *  implementation of appendEvents
     */
    public void appendEvents(org.egso.comms.log.types.EventList eventList)
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(appendEvents_OPCODE);
            
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1_appendEvents_appendEvents_QNAME);
            _bodyBlock.setValue(eventList);
            _bodyBlock.setSerializer(ns1_myEventList_LiteralSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _sendOneWay((String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            } catch (RemoteException e) {
                // let this one through unchanged
                throw e;
            } catch (JAXRPCException e) {
                throw new RemoteException(e.getMessage(), e);
            } catch (Exception e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException)e;
                } else {
                    throw new RemoteException(e.getMessage(), e);
                }
            }
        }
        
        /*
         *  implementation of createLog
         */
        public org.egso.comms.log.types.Log createLog(org.egso.comms.log.types.Log log)
            throws java.rmi.RemoteException {
            
            try {
                
                StreamingSenderState _state = _start(_handlerChain);
                
                InternalSOAPMessage _request = _state.getRequest();
                _request.setOperationCode(createLog_OPCODE);
                
                
                SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1_createLog_createLog_QNAME);
                _bodyBlock.setValue(log);
                _bodyBlock.setSerializer(ns1_myLog_LiteralSerializer);
                _request.setBody(_bodyBlock);
                
                _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
                
                _send((String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
                
                org.egso.comms.log.types.Log _result = null;
                Object _responseObj = _state.getResponse().getBody().getValue();
                if (_responseObj instanceof SOAPDeserializationState) {
                    _result = (org.egso.comms.log.types.Log)((SOAPDeserializationState) _responseObj).getInstance();
                } else {
                    _result = (org.egso.comms.log.types.Log)_responseObj;
                }
                
                return _result;
                
            } catch (RemoteException e) {
                // let this one through unchanged
                throw e;
            } catch (JAXRPCException e) {
                throw new RemoteException(e.getMessage(), e);
            } catch (Exception e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException)e;
                } else {
                    throw new RemoteException(e.getMessage(), e);
                }
            }
        }
        
        
        /*
         *  this method deserializes the request/response structure in the body
         */
        protected void _readFirstBodyElement(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState  state) throws Exception {
            int opcode = state.getRequest().getOperationCode();
            switch (opcode) {
                case createLog_OPCODE:
                    _deserialize_createLog(bodyReader, deserializationContext, state);
                    break;
                default:
                    throw new SenderException("sender.response.unrecognizedOperation", Integer.toString(opcode));
            }
        }
        
        
        
        
        /*
         * This method deserializes the body of the createLog operation.
         */
        private void _deserialize_createLog(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
            Object myLogObj =
                ns1_myLog_LiteralSerializer.deserialize(ns1_createLog_log_QNAME,
                    bodyReader, deserializationContext);
            
            SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_createLog_log_QNAME);
            bodyBlock.setValue(myLogObj);
            state.getResponse().setBody(bodyBlock);
        }
        
        
        
        protected String _getDefaultEnvelopeEncodingStyle() {
            return null;
        }
        
        public String _getImplicitEnvelopeEncodingStyle() {
            return "";
        }
        
        public String _getEncodingStyle() {
            return SOAPNamespaceConstants.ENCODING;
        }
        
        public void _setEncodingStyle(String encodingStyle) {
            throw new UnsupportedOperationException("cannot set encoding style");
        }
        
        
        
        
        
        /*
         * This method returns an array containing (prefix, nsURI) pairs.
         */
        protected String[] _getNamespaceDeclarations() {
            return myNamespace_declarations;
        }
        
        /*
         * This method returns an array containing the names of the headers we understand.
         */
        public QName[] _getUnderstoodHeaders() {
            return understoodHeaderNames;
        }
        
        public void _initialize(InternalTypeMappingRegistry registry) throws Exception {
            super._initialize(registry);
            ns1_myLog_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", org.egso.comms.log.types.Log.class, ns1_Log_TYPE_QNAME);
            ns1_myEventList_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", org.egso.comms.log.types.EventList.class, ns1_EventList_TYPE_QNAME);
        }
        
        private static final QName _portName = new QName("urn:org.egso.comms/log/wsdl/Logging", "LoggingPort");
        private static final int appendEvents_OPCODE = 0;
        private static final int createLog_OPCODE = 1;
        private static final QName ns1_appendEvents_appendEvents_QNAME = new QName("urn:org.egso.comms/log/types/", "appendEvents");
        private static final QName ns1_EventList_TYPE_QNAME = new QName("urn:org.egso.comms/log/types/", "EventList");
        private CombinedSerializer ns1_myEventList_LiteralSerializer;
        private static final QName ns1_createLog_createLog_QNAME = new QName("urn:org.egso.comms/log/types/", "createLog");
        private static final QName ns1_Log_TYPE_QNAME = new QName("urn:org.egso.comms/log/types/", "Log");
        private CombinedSerializer ns1_myLog_LiteralSerializer;
        private static final QName ns1_createLog_log_QNAME = new QName("urn:org.egso.comms/log/types/", "log");
        private static final String[] myNamespace_declarations =
                                            new String[] {
                                                "ns0", "urn:org.egso.comms/log/types/"
                                            };
        
        private static final QName[] understoodHeaderNames = new QName[] {  };
    }