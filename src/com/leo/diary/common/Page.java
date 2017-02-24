package com.leo.diary.common;

import java.util.List;

import com.leo.diary.util.InitUtils;
import com.leo.diary.web.bean.DiarySearchBean;
import com.mysql.jdbc.StringUtils;

/**
 * 分页实体
 * @author leo
 */
public class Page {
	private List<DiarySearchBean> records; // 记录集合
	private int pageNum; // 当前页码
	private int totalPageNum; // 总页码
	private int prePageNum; // 上一页
	private int nextPageNum; // 下一页
	
	private int pageSize = 6; // 每页显示的记录条数(默认值 6)
	private int totalRecordsNum; // 总记录条数
	
	private int startIndex; // 每页开始记录的索引
	
	private String url; // 查询分页的请求的地址

	public Page(int pageNum, int totalRecordsNum) {
		String val = InitUtils.getProperty("pageSize");
		if (StringUtils.isStrictlyNumeric(val)) {
			setPageSize(Integer.parseInt(val));
		}
		setPageNum(pageNum);
		setTotalRecordsNum(totalRecordsNum);
		
		// 计算总页数
		this.totalPageNum = totalRecordsNum % pageSize == 0 
				? totalRecordsNum / pageSize
				: (totalRecordsNum / pageSize + 1);
		// 计算开始记录的索引
		setStartIndex( (pageNum - 1) * pageSize );
	}
	
	
	public List<DiarySearchBean> getRecords() {
		return records;
	}

	public void setRecords(List<DiarySearchBean> records) {
		this.records = records;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public int getPrePageNum() {
		prePageNum = pageNum - 1;
		if (prePageNum < 1) {
			prePageNum = 1;
		}
		return prePageNum;
	}

	public void setPrePageNum(int prePageNum) {
		this.prePageNum = prePageNum;
	}

	public int getNextPageNum() {
		nextPageNum = pageNum + 1;
		if (nextPageNum > totalPageNum) {
			nextPageNum = totalPageNum;
		}
		return nextPageNum;
	}

	public void setNextPageNum(int nextPageNum) {
		this.nextPageNum = nextPageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecordsNum() {
		return totalRecordsNum;
	}

	public void setTotalRecordsNum(int totalRecordsNum) {
		this.totalRecordsNum = totalRecordsNum;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Page [records=" + records + ", pageNum=" + pageNum + ", totalPageNum=" + totalPageNum + ", prePageNum="
				+ prePageNum + ", nextPageNum=" + nextPageNum + ", pageSize=" + pageSize + ", totalRecordsNum="
				+ totalRecordsNum + ", startIndex=" + startIndex + ", url=" + url + "]";
	}
	
}
