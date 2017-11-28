package annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Comparator;

@Retention(RetentionPolicy.RUNTIME)
public @interface Attribute
{
	String value();
}
