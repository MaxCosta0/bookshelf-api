package com.maxley.bookshelf.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ExceptionProperty {

	private String title;
	private Integer status;
	private LocalDateTime dataTime;
	private List<ExceptionPropertyField> fields;
	
	public ExceptionProperty() {
		
	}

	public ExceptionProperty(String title, Integer status, LocalDateTime dataTime,
			List<ExceptionPropertyField> fields) {
		super();
		this.title = title;
		this.status = status;
		this.dataTime = dataTime;
		this.fields = fields;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public LocalDateTime getDataTime() {
		return dataTime;
	}

	public void setDataTime(LocalDateTime dataTime) {
		this.dataTime = dataTime;
	}

	public List<ExceptionPropertyField> getFields() {
		return fields;
	}

	public void setFields(List<ExceptionPropertyField> fields) {
		this.fields = fields;
	}
	
}
