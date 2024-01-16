package net.zhuruoling.nai.transformer;

import net.zhuruoling.nai.Mod;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ListIterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class YggdrasilEnvironmentTransformer extends ClassTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!Objects.equals(className, "com.mojang.authlib.yggdrasil.YggdrasilEnvironment".replace(".", "/")))
            return null;
        ClassNode node = new ClassNode();
        ClassReader reader = new ClassReader(classfileBuffer);
        reader.accept(node, 0);
        AtomicBoolean dirty = new AtomicBoolean(true);
        node.methods.stream()
                .filter(methodNode -> "<clinit>".equals(methodNode.name) && "()V".equals(methodNode.desc))
                .findFirst()
                .ifPresent(methodNode -> dirty.set(patchLDC(methodNode)));
        if (dirty.get()) {
            final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            node.accept(writer);
            return writer.toByteArray();
        }
        return null;
    }

    private boolean patchLDC(MethodNode classNode){
        InsnList list = classNode.instructions;
        ListIterator<AbstractInsnNode> iter = list.iterator();
        while (iter.hasNext()){
            AbstractInsnNode insnNode = iter.next();
            if (insnNode instanceof LdcInsnNode){
                if (((LdcInsnNode)insnNode).cst.equals(Mod.originalUrl)) {
//                    ((LdcInsnNode) insnNode).cst = Mod.authUrl;
                    iter.remove();
                    iter.add(new LdcInsnNode(Mod.authUrl));
                    return true;
                }
            }
        }
        return false;
    }
}
