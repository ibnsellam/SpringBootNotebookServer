package com.zibnsellam.interpreter.notebook.notebookinterpreter.service;

import com.zibnsellam.interpreter.notebook.notebookinterpreter.model.InterpreterRequest;
import com.zibnsellam.interpreter.notebook.notebookinterpreter.model.InterpreterResponse;
import com.zibnsellam.interpreter.notebook.notebookinterpreter.model.exception.LanguageNotSupportedException;
import com.zibnsellam.interpreter.notebook.notebookinterpreter.model.exception.TimeOutException;

public interface GraalVMInterpreterService {

	InterpreterResponse execute(InterpreterRequest interpreterRequest) throws TimeOutException, LanguageNotSupportedException;

}
