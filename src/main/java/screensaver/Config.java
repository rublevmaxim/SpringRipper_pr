package screensaver;

import org.springframework.context.annotation.*;

import java.awt.*;
import java.util.Random;

@Configuration
@ComponentScan(basePackages="screensaver")
public class Config {
    @Bean
    @Scope("prototype")
    // @Scope("prototype", proxyMode=ScopedProxyMode.TARGET_CLASS) Не очень хорошее решение: proxyMode=ScopedProxyMode.TARGET_CLASS
    // Каждый раз при обращении к color получаем новый бин
    public Color color(){
      Random random=new Random();
      return new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
    }

    @Bean
    public ColorFrame frame(){
        return new ColorFrame(){
            @Override
            protected Color getColor(){
                return color();// color() в данной ситуации не метод, а сам бин( public Color color())
            }
        };
    }


    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        while(true){
            context.getBean(ColorFrame.class).showOnRandomPlace();
            Thread.sleep(100);
        }
    }
}
