package com.leo.apediary.service;

import java.util.List;

import com.leo.apediary.common.Page;
import com.leo.apediary.domain.Category;
import com.leo.apediary.domain.Diary;
import com.leo.apediary.domain.User;
import com.leo.apediary.web.bean.CategoryInfoBean;
import com.leo.apediary.web.bean.DiarySearchBean;

public interface BusinessService {
	
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return 用户实体
	 */
	User login(String username, String password);
	/**
	 * 更新用户信息
	 * @param user
	 * @return 更新记录数
	 */
	int userUpate(User user);
	/**
	 * 添加分类
	 * @param c
	 * @return 添加记录数
	 */
	int addCategory(Category c);
	/**
	 * 修改分类
	 * @param c
	 * @return 修改的记录数
	 */
	int updateCategory(Category c);
	/**
	 * 查询所有的分类
	 * @return
	 */
	List<Category> findAllCategories();
	/**
	 * 根据id查询分类
	 * @param categoryId
	 * @return 没有找到返回 null
	 */
	Category findCategoryById(String categoryId);
	/**
	 * 根据id删除
	 * @param categoryId
	 * @return 删除的记录数
	 */
	int delCategory(String categoryId);
	/**
	 * 根据分类id查询该分类下的总日记数
	 * @param categoryId
	 * @return 总日记数
	 */
	int diaryCountByCategoryId(String categoryId);
	/**
	 * 根据id查询该分类下是否存在日记
	 * @param categoryId
	 * @return true 表示存在；false 反之
	 */
	boolean existDiary(String categoryId);
	/**
	 * 查询指定类别下的所有日记信息
	 * @return DiarySearchBean list
	 */
	List<CategoryInfoBean> findDiayInfoByCategory();
	
	/**
	 * 添加新日记
	 * @param diary
	 * @return 添加记录数
	 */
	int addDiary(Diary diary);
	/**
	 * 删除日记
	 * @param diaryId
	 * @return 删除记录数
	 */
	int delDiary(String diaryId);
	/**
	 * 更新日记
	 * @param diary
	 * @return 更新记录数
	 */
	int updateDiary(Diary diary);
	/**
	 * 根据id查询日记
	 * @param diaryId
	 * @return 日记实体
	 */
	Diary findDiaryById(String diaryId);
	/**
	 * 查询指定发布日期下的所有日记信息
	 * @return DiarySearchBean list
	 */
	List<DiarySearchBean> findDiaryInfoByReleaseDate();
	
	/**
	 * 根据用户提交的条件，返回封装分页导航信息 Page 对象
	 * @param num 要看的页码，如果为 null 或 ""~默认为 1
	 * @param diarySearchBean 封装了查询条件
	 * @return 封装了分页信息的对象
	 */
	Page findDiaryPageRecords(String num, DiarySearchBean diarySearchBean);
	
}
