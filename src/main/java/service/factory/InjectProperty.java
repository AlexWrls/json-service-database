package service.factory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * InjectProperty устанавливает значения properties для подключения к БД
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectProperty {
    String value() default "";
}
