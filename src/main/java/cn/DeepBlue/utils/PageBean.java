package cn.DeepBlue.utils;

import java.util.List;


/**
 * 分页工具类
 * @author 
 *
 * @param <T>
 */
public class PageBean<T> {
    /**
     * 当前页
     */
	private int currentPageNo;
	/**
	 * 每页显示多少条
	 */
	private int pageSize;
	/**
	 * 总记录数 
	 */
	private int totalCount;
	/**
	 * 总页数 
	 */
	private int totalPage;
	/**
	 * 每页显示的数据集合
	 */
	private List<T> list;
	private int startIndex;
	
	public int getStartIndex() {
		return (this.getCurrentPageNo() - 1) * this.getPageSize();
	}
	
	public int getCurrentPageNo() {
		return currentPageNo;
	}
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	// 实现在设置总记录数的同时计算总页数
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		if(totalCount > 0) {
			this.setTotalPage(this.getTotalCount() % this.getPageSize() == 0 
									? this.getTotalCount() / this.getPageSize()
											: (this.getTotalCount() / this.getPageSize())+1);
		}
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
}
