package com.dazon.agent;


import com.dazon.common.Test1;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;

/**
 * @description:
 * @author: Dazon Wu
 * @date: 2021/11/3  11:43
 */
public class MyAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("agentArgs : " + agentArgs);
        inst.addTransformer(new DefineTransformer(), true);
    }
    public static void agentmain(String agentArgs, Instrumentation inst) throws Exception {
        System.out.println("agentArgs : " + agentArgs);
        inst.addTransformer(new DefineTransformer(), true);
        inst.retransformClasses(Test1.class);

    }

    /*static class DefineTransformer implements ClassFileTransformer{

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
            System.out.println("premain load Class:" + className);
            return classfileBuffer;
        }
    }*/


    static class DefineTransformer implements ClassFileTransformer {

        @Override
        public byte[] transform(ClassLoader loader, String className,
                                Class<?> classBeingRedefined,
                                ProtectionDomain protectionDomain,
                                byte[] classfileBuffer) {
            //不修改
            /*System.out.println("premain load Class:" + className);
            return classfileBuffer;*/
            System.out.println("premain load Class:" + className);
            // 修改
            if ("com/dazon/common/Test1".equals(className)) {
                try {
                    // 从ClassPool获得CtClass对象
                    final ClassPool classPool = ClassPool.getDefault();
                    final CtClass clazz1 = classPool.get("com.dazon.common.Test1");
                    CtMethod getNum1 = clazz1.getDeclaredMethod("getNum");
                    //这里对 java.util.Date.convertToAbbr() 方法进行了改写，在 return之前增加了一个 打印操作
                    //String methodBody = "System.out.println(\"不执行while true 了\");return true;";
                    final CtClass clazz2 = classPool.get("com.dazon.common.Test2");
                    CtMethod getNum2 = clazz2.getDeclaredMethod("getNum");
                    clazz2.setName("com.dazon.common.Test1");



                    //getNum1.setBody("return 2;");

                    // 返回字节码，并且detachCtClass对象
                    byte[] byteCode = clazz2.toBytecode();
                    //detach的意思是将内存中曾经被javassist加载过的对象移除，如果下次有需要在内存中找不到会重新走javassist加载
                    clazz1.detach();
                    return byteCode;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            // 如果返回null则字节码不会被修改
            return null;
        }

    }
}
