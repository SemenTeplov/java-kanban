package opencsv.exceptions;

import opencsv.validators.LineValidator;
import opencsv.validators.LineValidatorAggregator;

/**
 * Exception thrown by a {@link LineValidator} or
 * {@link LineValidatorAggregator} when a single line is invalid.
 *
 * @author Scott Conway
 * @since 5.0
 */
public class CsvValidationException extends CsvFieldAssignmentException {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public CsvValidationException() {
        super();
    }

    /**
     * Constructor that allows for a human readable message.
     *
     * @param message Error text.
     */
    public CsvValidationException(String message) {
        super(message);
    }
}
