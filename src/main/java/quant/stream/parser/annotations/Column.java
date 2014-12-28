package quant.stream.parser.annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by dev on 12/13/14.
 */
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RepeatableColumns.class)
public @interface Column {
    short index();
    String field();
    Class type();
}
