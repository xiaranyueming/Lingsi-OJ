package com.demo.lingsiojbackend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(value = {ElementType.METHOD})
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Auth {

}
