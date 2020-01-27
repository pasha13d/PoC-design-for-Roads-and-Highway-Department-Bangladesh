package com.secl.eservice.util.constant;

public enum Code {

	C_400("400-env"), 
	C_401("401-env"), 
	C_402("402-env"),
	C_403("403-env"),
	C_404("404-env"), 
	C_500("500-env"), 
	C_501("501-env"), 
	C_200("200");

	private String code;

	Code(String code) {
		this.code = code;
	}

	public String get() {
		return code;
	}

}
