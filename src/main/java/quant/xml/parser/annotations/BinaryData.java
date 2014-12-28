package quant.xml.parser.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by dev on 12/27/14.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BinaryData {
    String name();
    FieldData[] data();
}
