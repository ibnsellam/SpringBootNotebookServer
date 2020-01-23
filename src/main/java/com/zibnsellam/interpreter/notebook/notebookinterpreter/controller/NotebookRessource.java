package com.zibnsellam.interpreter.notebook.notebookinterpreter.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.zibnsellam.interpreter.notebook.notebookinterpreter.model.InterpreterRequest;
import com.zibnsellam.interpreter.notebook.notebookinterpreter.model.RequestBodyContent;
import com.zibnsellam.interpreter.notebook.notebookinterpreter.model.InterpreterResponse;
import com.zibnsellam.interpreter.notebook.notebookinterpreter.service.GraalVMInterpreterService;
import com.zibnsellam.interpreter.notebook.notebookinterpreter.service.ParsingInterpreterRequestService;

@RestController
public class NotebookRessource {
	
	/**
	 * inject a parsingInterpreterRequest service
	 */
	 @Autowired
	  private ParsingInterpreterRequestService parsingInterpreterRequestService;
	 
	 /**
		 * inject a graalVMInterpreter service
		 */
	 @Autowired
	  private GraalVMInterpreterService graalVMInterpreterService;
	
	 @PostMapping("/execute")
	  public ResponseEntity<InterpreterResponse> execute(@RequestBody RequestBodyContent requestBodyContent,  HttpSession httpSession) throws Exception {
	 
	 		/**
	 		 * invoke parsing interpreter request service
	 		 */
	 		final String user_sessionId = httpSession.getId();
	    	InterpreterRequest interpreterRequest = parsingInterpreterRequestService.parsingRequest(requestBodyContent);

	    	/**
	    	 * get the user sessionId from the httpSession interface
	    	 */
	    	interpreterRequest.setSessionId(user_sessionId);

	        /**
	         * invoke PythonGraal Interpreter Service 
	         */
	    	InterpreterResponse interpreterResponse = graalVMInterpreterService.execute(interpreterRequest);
	        
	    	/**
	    	 * return responseEntity that represents the whole HTTP response: status code, headers, and body
	    	 */
	        return ResponseEntity.ok(interpreterResponse);
	  }
}
