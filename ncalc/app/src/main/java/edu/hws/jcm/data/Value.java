package edu.hws.jcm.data;

/**
 * An object of type Value has a real-number value that can be retrieved by
 * calling the getVal() method.
 * This is a central interface, since Value objects are used throughout the
 * JCM system where a real number is needed.
 */
public interface Value extends java.io.Serializable {
    /**
     * Gets the current value of this object.
     */
    double getVal();
}
