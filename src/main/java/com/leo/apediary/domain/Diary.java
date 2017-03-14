package com.leo.apediary.domain;

import java.io.Serializable;
import java.util.Date;

public class Diary implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String id;
	protected String title;
	protected String content;
	protected Date releaseDate;
	protected Category category;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "Diary [id=" + id + ", title=" + title + ", content=" + content + ", releaseDate=" + releaseDate
				+ ", category=" + category + "]";
	}
	
}
