package com.example.scm.Validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


// can include in java doc
@Documented

// where to use
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})

// action at runtime
@Retention(RetentionPolicy.RUNTIME)

// rules given in this class
@Constraint(validatedBy = FileValidator.class)
public @interface ValidFile {
    String message() default "Invalid file";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {}; 

}
