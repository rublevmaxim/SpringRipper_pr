package screensaver;


import javafx.util.Pair;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static java.time.LocalTime.now;


//Дполним анатацию scope.
//Например если хотим,чтобы новый бин выдавался периодически
//Данное Scope расширение необходимо зарегистрировать(CustomScopeRegistyiBeanFactoryPostProcessore)
public class PeriodicalScopeConfigurer implements Scope {
    Map<String, Pair<LocalTime,Object>> map=new HashMap<>();//<Имя бина> против пары <врямя, объект бина>

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
       if (map.containsKey(name)){//Если бин существует в мапе
           Pair<LocalTime,Object> pair=map.get(name);

           int secondsSinceLastRequest=now().getSecond()-pair.getKey().getSecond();
           if (secondsSinceLastRequest>3){//Проверяем что он существует больше 3 сек
               map.put(name,new Pair(now(),objectFactory.getObject()));//Кладём новый
           }
       }else{
           map.put(name,new Pair(now(),objectFactory.getObject()));//если не существует то кладём новый
       }
        return map.get(name).getValue();
    }

    @Override
    public Object remove(String s) {
        return null;
    }

    @Override
    public void registerDestructionCallback(String s, Runnable runnable) {

    }

    @Override
    public Object resolveContextualObject(String s) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}
