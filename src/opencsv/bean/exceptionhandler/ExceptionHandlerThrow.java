package opencsv.bean.exceptionhandler;

import opencsv.exceptions.CsvException;

/**
 * An exception handler that always throws exceptions raised.
 *
 * @author Andrew Rucker Jones
 * @since 5.2
 */
final public class ExceptionHandlerThrow implements CsvExceptionHandler {

    /**
     * Default Constructor.
     */
    public ExceptionHandlerThrow() {
    }

    @Override
    public CsvException handleException(CsvException e) throws CsvException {
        throw e;
    }
}
