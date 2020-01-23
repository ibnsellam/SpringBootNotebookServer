package com.zibnsellam.interpreter.notebook.notebookinterpreter.model;

public class InterpreterResponse {
    
	private String result;
    private String error;

    public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public InterpreterResponse(String result, String error) {
		super();
		this.result = result;
		this.error = error;
	}
	
	
	


}
