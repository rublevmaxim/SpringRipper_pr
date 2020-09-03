package quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class DeplicationHandlerBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    //Данный Listner работает на этапе пока бины ещё не созданы.
    //На данном этапе существует только BeeanDefinition
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] names = beanFactory.getBeanDefinitionNames();
        for (String name : names) {
            BeanDefinition beanDefinition=beanFactory.getBeanDefinition(name);
            String beanClassName = beanDefinition.getBeanClassName();
            try {
                Class<?> beanClass=Class.forName(beanClassName);//Вытаскиваем Class
                DeprecatedClass annotation= beanClass.getAnnotation(DeprecatedClass.class);//Проверяем наличие анотации
                //Ессли класс помечен анотацией DeprecatedClass.class, то подкидываем новый класс annotation.newImpl().getName()
                if (annotation!=null){
                    beanDefinition.setBeanClassName(annotation.newImpl().getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
