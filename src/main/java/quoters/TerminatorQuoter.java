package quoters;

import javax.annotation.PostConstruct;

@Profiling
//@DeprecatedClass(newImpl=T1000.class)//<-Class устарел подкидываем вместо него новый класс
public class TerminatorQuoter implements Quoter {
    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    @InjectRandomInt(min=2,max=7)
    private int repeat;
    private String message;

    @Override
    public void sayQuote() {
        for (int i=0;i<repeat;i++) {
            System.out.println("message="+message);
        }
    }

    @PostConstruct//не будет работать без записис в context.xml :
    //    <!bean class="org.springframework.context.annotation.CommonAnnotationBeanPostProcessor"/>
    //   Чтобы не запоминать название конкретного PostProcessora подключают name_space
    //  <context:annotation-config></context:annotation-config> или <context:component-scan>
    @PostProxy//Созданная анатация, которая с помощью contextListenr(там есть более поздние методы)
    // инициализирует 3-ю фазу Конструктора. Метод должен запустится, после
    // @PostConstruct, init
    public void init(){
        System.out.println("Phase 2");
        System.out.println(repeat);
    }

    public TerminatorQuoter() {
        //System.out.println(repeat);
        //Распечатается 0, т.к. repeat на данном этапе не проинициализирован
        //Что бы этого избежать создадим init метод

        System.out.println("Phase 1");
    }



    public void setMessage(String message) {
        this.message = message;
    }
}