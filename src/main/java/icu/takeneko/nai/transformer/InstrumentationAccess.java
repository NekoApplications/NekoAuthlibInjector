package icu.takeneko.nai.transformer;

import net.bytebuddy.agent.ByteBuddyAgent;

import java.lang.instrument.Instrumentation;

public class InstrumentationAccess {

    public static Instrumentation inst;

    public static void initAccess(){
        inst = ByteBuddyAgent.install();
    }
}
