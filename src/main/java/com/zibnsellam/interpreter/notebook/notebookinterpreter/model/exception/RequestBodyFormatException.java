package com.zibnsellam.interpreter.notebook.notebookinterpreter.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad request body format")
public class RequestBodyFormatException extends RuntimeException{


}
