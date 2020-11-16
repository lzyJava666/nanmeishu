package com.nanmeishu.user.web;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import sun.awt.SunHints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SuppressWarnings("deprecation")
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD,ElementType.TYPE})
public @interface TokenVerifyAnnotation {
    //定义在注解类中
    int value() default 0;
}
