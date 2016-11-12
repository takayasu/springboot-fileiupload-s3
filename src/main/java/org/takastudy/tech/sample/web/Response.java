package org.takastudy.tech.sample.web;

import java.util.ArrayList;
import java.util.List;

public class Response {
	
	public String status;
	public List<String> fileList;
	public String errorStatus;
	
	public Response(){
		fileList = new ArrayList<>();
	}

}
