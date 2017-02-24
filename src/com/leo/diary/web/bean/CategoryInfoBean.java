package com.leo.diary.web.bean;

import com.leo.diary.domain.Category;

@SuppressWarnings("serial")
public class CategoryInfoBean extends Category {
	private Integer diaryCount;

	public Integer getDiaryCount() {
		return diaryCount;
	}
	public void setDiaryCount(Integer diaryCount) {
		this.diaryCount = diaryCount;
	}
	@Override
	public String toString() {
		return "CategoryInfoBean [diaryCount=" + diaryCount + ", id=" + id + ", name=" + name + "]";
	}
	
}
