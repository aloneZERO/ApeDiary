package com.leo.diary.dao;

import java.util.List;

import com.leo.diary.domain.Category;
import com.leo.diary.web.bean.CategoryInfoBean;

public interface CategoryDAO {

	int save(Category c);

	List<Category> findAll();

	Category findById(String categoryId);

	int del(String categoryId);

	int diaryCount(String categoryId);

	int update(Category c);
	
	int getTotalRecordsNum();

	List<CategoryInfoBean> findDiaryInfo();

}
