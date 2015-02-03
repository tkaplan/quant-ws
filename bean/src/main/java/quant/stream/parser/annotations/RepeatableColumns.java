package quant.stream.parser.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by dev on 12/13/14.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatableColumns {
    Column[] value();
}
