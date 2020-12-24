package mypkg.common;

import javax.servlet.http.HttpServletRequest;

public interface Validator {
	//유효성 검사
	public boolean validate(HttpServletRequest request);
}
