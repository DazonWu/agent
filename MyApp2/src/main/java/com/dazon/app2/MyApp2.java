package com.dazon.app2;


import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;


import java.util.List;

/**
 * @description:
 * @author: Dazon Wu
 * @date: 2021/11/3  11:47
 */
public class MyApp2 {


    public static void main(String[] args) throws Exception {



        //获取当前系统中所有 运行中的 虚拟机
        System.out.println("running JVM start ");
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            //如果虚拟机的名称为 xxx 则 该虚拟机为目标虚拟机，获取该虚拟机的 pid
            //然后加载 agent.jar 发送给该虚拟机
            System.out.println(vmd.displayName());
            if (vmd.displayName().endsWith("com.dazon.app.MyApp1")) {
                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                virtualMachine.loadAgent("/Users/wudacheng/Project/agent/myagent/target/myagent-1.0-SNAPSHOT.jar");
                virtualMachine.detach();
            }
        }

    }


}
