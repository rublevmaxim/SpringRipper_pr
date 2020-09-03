package quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {
    private Map<String,Class> map=new HashMap();
    private ProfilingController controler=new ProfilingController();


    public ProfilingHandlerBeanPostProcessor() throws Exception {
        //В конструкторе производим регистрацию в Mbeanerver
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        //ObjectName: Папка profiling, название controller
        platformMBeanServer.registerMBean(controler,new ObjectName("profiling" , "name", "controller"));
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass=bean.getClass();

        if(beanClass.isAnnotationPresent(Profiling.class)){
            map.put(beanName,beanClass);
        }
        return bean;
    }
    @Override
    //По какой-то причине java 1.8 заставляет сделать завернуть Object bean в final
    //Это связано с работой с анонимными классами.
    //В результате Прокси класс не генерится, а создаётся пустая болванка terminatorQuoter:class com.sun.proxy.$Proxy12
    //без внутренних методов.
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
       Class beanClass=map.get(beanName);

       if(beanClass!=null)//если beanClass!=null значит на этапе before я запоминал его и нужно сгенерить новый класс:

       {
           InvocationHandler invocationHandler = new InvocationHandler() {
               @Override
               public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                   if (controler.isEnabled()) {
                       System.out.println("Профилирую...");
                       long before = System.nanoTime();

                       Object retVal = method.invoke(bean, args);

                       long after = System.nanoTime();
                       System.out.println(after - before);
                       System.out.println("Всё...");


                       return retVal;
                   } else {
                       return method.invoke(bean, args);
                   }
               }
           };

           return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(),invocationHandler);
       }
        return bean;
    }


}
