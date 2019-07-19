package core.check;

import java.lang.annotation.*;

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface IndentityCheck {
    String objectJson() default "";
    String typeName = "身份校验";
}
