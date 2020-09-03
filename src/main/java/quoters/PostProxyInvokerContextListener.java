package quoters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;

public class PostProxyInvokerContextListener implements ApplicationListener<ContextRefreshedEvent> {
   @Autowired
   private ConfigurableListableBeanFactory factory;//Инжектим главную фабрику, т.к. только она умеет делать
                                                    //getDefinition
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext context = contextRefreshedEvent.getApplicationContext();
        //На данном этапе здесь уже будет Proxybean, поэтому напрямую по имени вытаскивать бин нельзя.
        //Поэтому необходимо сюда проинжектировать главную фабрику spring. Это корректно, т.к.
        // взаимодействие происходит внутри Spring
        // (если бы Quoter  проинжектировал ConfigurableListableBeanFactory это было бы неверно )


        String[] names=context.getBeanDefinitionNames();
        for (String name : names) {

            System.out.println(name+":"+context.getBean(name).getClass());
            for (Method method : context.getBean(name).getClass().getMethods()) {
                method.getName();
            }

            BeanDefinition beanDefinition = factory.getBeanDefinition(name);
            String originalClassName = beanDefinition.getBeanClassName();//Вытаскием оригинальное название класса, которое было прописано ещё в XML
            try {
                Class<?> originalClass = Class.forName(originalClassName);//Вытаскиваем класс по названию
                Method[] methods=originalClass.getMethods();//Вытаскиваем методы класса
                for (Method method : methods) {
                    if (method.isAnnotationPresent(PostProxy.class)){
                        //method.invoke()->Работать не будет, т.к. метод мы ищем в оригинальном классе,
                        //а bean создан из Proxy_class. Это два разных класса.
                        //Вместо этого необходимо вытощить данный метод у текущего Прокси класса
                        Object bean = context.getBean(name);
                        Method currentmethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                        currentmethod.invoke(bean);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
