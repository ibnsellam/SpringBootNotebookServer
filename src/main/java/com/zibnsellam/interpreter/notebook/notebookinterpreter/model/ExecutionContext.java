package com.zibnsellam.interpreter.notebook.notebookinterpreter.model;

import org.graalvm.polyglot.Context;

public class ExecutionContext {

    private String output;
    private String errors;
	private Context context;
    private boolean timedOut = false;

    public ExecutionContext(String output, String errors, Context context) {
        this.output = output;
        this.context = context;
    }

    public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

	public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean isTimedOut() {
        return timedOut;
    }

    public void setTimedOut(boolean timedOut) {
        this.timedOut = timedOut;
    }
    
    public String getOutput() {
  		return output;
  	}
    
  	public void setOutput(String output) {
  		this.output = output;
  	}


}
