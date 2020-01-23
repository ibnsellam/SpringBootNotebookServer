package com.zibnsellam.interpreter.notebook.notebookinterpreter.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import com.zibnsellam.interpreter.notebook.notebookinterpreter.model.InterpreterRequest;
import com.zibnsellam.interpreter.notebook.notebookinterpreter.model.RequestBodyContent;
import com.zibnsellam.interpreter.notebook.notebookinterpreter.model.exception.RequestBodyFormatException;
import com.zibnsellam.interpreter.notebook.notebookinterpreter.service.ParsingInterpreterRequestService;

@Service
public class ParsingInterpreterRequestToExecutionRequestServiceImp implements ParsingInterpreterRequestService{

	private static final String REQUEST_PATTERN = "%(\\w+)\\s+(.*)";
    private static final Pattern pattern = Pattern.compile(REQUEST_PATTERN);
    
	@Override
	public InterpreterRequest parsingRequest(RequestBodyContent requestBodyContent) throws RequestBodyFormatException {

		/**
		 * check if a request body respect a such standard format, using a regex pattern
		 */
		Matcher matcher = pattern.matcher(requestBodyContent.getCode());
        if (matcher.matches()) {
        	/**
        	 * extract data
        	 */
            String language = matcher.group(1);
            String code = matcher.group(2);

            /**
             * build an interpreter Request
             */
            InterpreterRequest interpreterRequest = new InterpreterRequest();
            interpreterRequest.setCode(code);
            interpreterRequest.setLanguage(language);

            return interpreterRequest;
        }

        /**
         * return exception if requestBody not as expected
         */
        throw new RequestBodyFormatException();
	}

	

}
