//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.08.01 at 05:51:55 PM CST 
//


package org.ckr.msdemo.policy.ws;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.ckr.msdemo.policy.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.ckr.msdemo.policy.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetPolicyResponse }
     * 
     */
    public GetPolicyResponse createGetPolicyResponse() {
        return new GetPolicyResponse();
    }

    /**
     * Create an instance of {@link Policy }
     * 
     */
    public Policy createPolicy() {
        return new Policy();
    }

    /**
     * Create an instance of {@link GetPolicyRequest }
     * 
     */
    public GetPolicyRequest createGetPolicyRequest() {
        return new GetPolicyRequest();
    }

}
