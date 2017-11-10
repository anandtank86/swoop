
package com.swoop.demo.core.exceptions;

import com.swoop.demo.utils.MessageProperties;
import java.text.MessageFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author 
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MandatoryFieldException extends InvalidParameterException {

    /**
     *
     * @param fieldName
     */
    public MandatoryFieldException(String fieldName) {
        super(MessageFormat.format(MessageProperties.getInstance().getProperty(MessageConstants.MANDATORY_PARAMETER_ERROR), fieldName));
    }
}
