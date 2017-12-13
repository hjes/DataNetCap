package com.net.common.asserts;

public class AssertionFailed extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AssertionFailed() {
		printStackTrace();
	}

	public AssertionFailed(String s) {
		super(s);
	}

}