package net.zhuruoling.nai.transformer;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;

public abstract class ClassTransformer implements ClassFileTransformer {

    public void apply(String clazz) throws ClassNotFoundException {
        InstrumentationAccess.inst.addTransformer(this, true);
        Class.forName(clazz);
        InstrumentationAccess.inst.removeTransformer(this);
    }

}
