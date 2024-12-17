package org.mql.java.reflection.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) //1er raccourci si il y a un seul element on fait pas {} si il y a un seul att et qui est value donc on l ecrit pas
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
	String value();
}
