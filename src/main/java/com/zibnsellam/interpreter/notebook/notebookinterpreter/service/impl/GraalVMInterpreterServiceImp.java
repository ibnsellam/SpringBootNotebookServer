package com.zibnsellam.interpreter.notebook.notebookinterpreter.service.impl;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Value;
import org.springframework.stereotype.Service;
import com.zibnsellam.interpreter.notebook.notebookinterpreter.model.ExecutionContext;
import com.zibnsellam.interpreter.notebook.notebookinterpreter.model.InterpreterRequest;
import com.zibnsellam.interpreter.notebook.notebookinterpreter.model.InterpreterResponse;
import com.zibnsellam.interpreter.notebook.notebookinterpreter.model.exception.LanguageNotSupportedException;
import com.zibnsellam.interpreter.notebook.notebookinterpreter.model.exception.TimeOutException;
import com.zibnsellam.interpreter.notebook.notebookinterpreter.service.GraalVMInterpreterService;

@Service
public class GraalVMInterpreterServiceImp implements GraalVMInterpreterService{
	
		/**
		 * Hashmap implementation where we gonna store all executions context with a thread-safe mode,
		 * to support concurrent access 
		 */
	    private Map<String, ExecutionContext> sessionContexts = new ConcurrentHashMap<>();
    

	    @Override
	    public InterpreterResponse execute(InterpreterRequest interpreterRequest) {
	
		 	/**
		 	 * check if a language is not supported
		 	 */
	        if (!unsupportedLanguage(interpreterRequest)) {
	            throw new LanguageNotSupportedException();
	        }

	    	
	    	
	        /**
	         * preparing an execution context for a user
	         */
	        ExecutionContext executionContext = getContext(interpreterRequest.getSessionId(),interpreterRequest);
	        
	        /**
	         * release resources, that happen when the interpreter take a long time to finish 
	         *
	         */
	        
	        Timer timer = new Timer(true);
	        timer.schedule(new TimerTask() {
	            @Override
	            public void run() {
	                executionContext.getContext().close(true);
					executionContext.setTimedOut(true);
	            }
	        }, 5000);

	        try {
	        	/** 
	        	 *interpret the request code by the GraalPython, and return the result
	        	 */

	            Value result = executionContext.getContext().eval(interpreterRequest.getLanguage(), interpreterRequest.getCode());
	           
	            
	            /** 
	             * build the response
	             * 
	             */
	         
	            return new InterpreterResponse(result.toString(), executionContext.getErrors());
	            
	        } catch(PolyglotException e) {
	        
	        	/**
		         * release resources if an PolyglotException is catched
		         *
		         */
	            timer.cancel();
	            timer.purge();
	            if (e.isCancelled()) {
	                
	                sessionContexts.remove(interpreterRequest.getSessionId());
	                throw new TimeOutException();
	            }

	            return new InterpreterResponse("" , e.getMessage());
	        }

	    }

	    /**
	     * unsupported language 
	     */
	    private boolean unsupportedLanguage(InterpreterRequest interpreterRequest) {
	    	/**
    		 * Returns true if this context contains a mapping for the specified language
    		 */
	    		try(Context tmpContext = Context.create()) {
		            return tmpContext.getEngine().getLanguages().containsKey(interpreterRequest.getLanguage());

	    		}
	    		

	    }

    	
    	/** 
    	 * return an execution context for a sessionId from the sessionContexts map if exist,
    	 * if not, build a new execution context
    	 */
	    private ExecutionContext getContext(String sessionId,InterpreterRequest interpreterRequest) {
	    	
	        return sessionContexts.computeIfAbsent(sessionId, key -> buildContext(interpreterRequest));
	        
	    }


		/** 
    	 * Build an execution context for a new sessionId
    	 */
	    private ExecutionContext buildContext(InterpreterRequest interpreterRequest) {


	       String output = "",errors = "";
	        Context context = Context.newBuilder(interpreterRequest.getLanguage())
	                .build();

	        return new ExecutionContext(output,errors,context);
	    }

	
}
