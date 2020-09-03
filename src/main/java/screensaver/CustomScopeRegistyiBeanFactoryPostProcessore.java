package screensaver;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

//СОЗДАЁМ CUSTOMNII SCOPE
//Регистрируем новый вид Scope("Periodical") на этапе BeanFactoryPostProcessor
@Component
public class CustomScopeRegistyiBeanFactoryPostProcessore implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        beanFactory.registerScope("periodical",new PeriodicalScopeConfigurer());
    }
}
