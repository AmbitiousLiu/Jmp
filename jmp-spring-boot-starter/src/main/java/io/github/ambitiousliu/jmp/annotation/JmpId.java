package io.github.ambitiousliu.jmp.annotation;

import java.lang.annotation.*;
/**
 * @author ambitious liu
 * @since 2022-06-18
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface JmpId {
    String value() default "";
}
