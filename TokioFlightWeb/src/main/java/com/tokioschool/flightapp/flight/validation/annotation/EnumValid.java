package com.tokioschool.flightapp.flight.validation.annotation;

import com.tokioschool.flightapp.flight.validation.annotation.impl.EnumValidImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.lang.NonNull;

import java.lang.annotation.*;

@Documented
@Target(value={ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidImpl.class)
public @interface EnumValid {
    // required params
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    // business drools

    String message() default "Invalid status flight";
    @NonNull Class<? extends Enum<?>> target(); // enumerations to validate
    boolean required() default false;

}
