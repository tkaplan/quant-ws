package quant.xml.parser.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by dev on 12/25/14.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Node {
    String name();
    Class type();
}
