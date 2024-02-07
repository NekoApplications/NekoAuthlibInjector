package icu.takeneko.nai.transformer;

import java.lang.instrument.ClassFileTransformer;

public abstract class ClassTransformer implements ClassFileTransformer {

    public void apply(String clazz) throws ClassNotFoundException {
        InstrumentationAccess.inst.addTransformer(this, true);
        Class.forName(clazz);
        InstrumentationAccess.inst.removeTransformer(this);
    }

}
