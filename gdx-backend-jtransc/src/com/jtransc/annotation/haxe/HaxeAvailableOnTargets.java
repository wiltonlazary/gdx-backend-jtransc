package com.jtransc.annotation.haxe;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// @TODO: Temporal until jtransc >= 0.2.9
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface HaxeAvailableOnTargets {
	String[] value();
}