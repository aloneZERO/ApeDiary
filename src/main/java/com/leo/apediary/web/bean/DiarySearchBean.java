package com.leo.apediary.web.bean;

import com.leo.apediary.domain.Diary;

/**
 * 用于存储首页查询功能提交的条件信息的 bean
 * 
 * @author leo
 */
@SuppressWarnings("serial")
public class DiarySearchBean extends Diary {
	private String releaseDateStr;
	private Integer diaryCount;
	
	public String getReleaseDateStr() {
		return releaseDateStr;
	}
	public void setReleaseDateStr(String releaseDateStr) {
		this.releaseDateStr = releaseDateStr;
	}
	public Integer getDiaryCount() {
		return diaryCount;
	}
	public void setDiaryCount(Integer diaryCount) {
		this.diaryCount = diaryCount;
	}
	@Override
	public String toString() {
		return "DiarySearchBean [id=" + id + ", title=" + title + ", content=" + content + ", releaseDate=" + releaseDate
				+ ", releaseDateStr=" + releaseDateStr + ", diaryCount=" + diaryCount + ", category=" + category + "]";
	}
	
}
