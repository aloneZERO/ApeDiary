package com.leo.apediary.test;


import com.leo.apediary.common.Page;
import com.leo.apediary.dao.DiaryDAO;
import com.leo.apediary.dao.impl.DiaryDAOImpl;
import com.leo.apediary.web.bean.DiarySearchBean;
import org.junit.Test;

import java.util.List;

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
