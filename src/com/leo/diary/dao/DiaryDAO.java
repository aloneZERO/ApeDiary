package com.leo.diary.dao;

import java.util.List;

import com.leo.diary.domain.Diary;
import com.leo.diary.web.bean.DiarySearchBean;

public interface DiaryDAO {

	int save(Diary diary);

	int del(String diaryId);

	int update(Diary diary);

	Diary findById(String diaryId);

	int getTotalRecordsNum(DiarySearchBean diarySearchBean);
	
	List<DiarySearchBean> findPageRecords(int startIndex, int pageSize, DiarySearchBean diarySearchBean);
	
	List<DiarySearchBean> findInfoByReleaseDate();

}
