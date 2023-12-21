package com.example.redis_write.Entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class TimeStamp{
	
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}