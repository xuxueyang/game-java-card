package core.check;

import java.lang.annotation.*;

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PermissionCheck {
    String objectJson() default "";
    String typeName = "权限校验";
}
