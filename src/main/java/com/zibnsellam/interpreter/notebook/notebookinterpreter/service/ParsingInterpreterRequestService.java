package com.zibnsellam.interpreter.notebook.notebookinterpreter.service;

import com.zibnsellam.interpreter.notebook.notebookinterpreter.model.InterpreterRequest;
import com.zibnsellam.interpreter.notebook.notebookinterpreter.model.RequestBodyContent;
import com.zibnsellam.interpreter.notebook.notebookinterpreter.model.exception.RequestBodyFormatException;

public interface ParsingInterpreterRequestService {

	public InterpreterRequest parsingRequest(RequestBodyContent requestBodyContent) throws RequestBodyFormatException;
}
