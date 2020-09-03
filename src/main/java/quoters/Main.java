package quoters;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
       //while (true){
        //    Thread.sleep(100);
           // Не извемстно, что будет с классом поэтому ориентируемся на интерфейс
            // context.getBean(TerminatorQuoter.class).sayQuote();
         //   context.getBean(Quoter.class).sayQuote();

        //}


    }
}
