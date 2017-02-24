package com.leo.diary.test;

import java.util.List;

import org.junit.Test;

import com.leo.diary.common.Page;
import com.leo.diary.dao.DiaryDAO;
import com.leo.diary.dao.impl.DiaryDAOImpl;
import com.leo.diary.web.bean.DiarySearchBean;

public class DiaryDaoTest {
	private DiaryDAO diaryDAO = new DiaryDAOImpl();
	
	@Test
	public void testDiaryDao() {
		List<DiarySearchBean> diarySearchBeans = diaryDAO.findInfoByReleaseDate();
		System.out.println(diarySearchBeans);
	}
	
	@Test
	public void testPage() {
		Page page = new Page(1, 3);
		List<DiarySearchBean> info = diaryDAO.findPageRecords(
				page.getStartIndex(), page.getPageSize(), null);
		System.out.println(info);
	}
	
}
