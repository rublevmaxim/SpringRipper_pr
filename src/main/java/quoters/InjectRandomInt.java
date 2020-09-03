package quoters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
// RUNTIME-Будет компилироваться в байт коде. Видны в Runtime.
// Class-(по умолчанию) в байткод попадает. Но через Rflection считать не получится.
// Source-данная анотация видна исключительно в source(не будет в байткод. #Override).
public @interface InjectRandomInt {
    int min();
    int max();
}
