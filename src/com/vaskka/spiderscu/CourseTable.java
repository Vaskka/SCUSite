package com.vaskka.spiderscu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.*;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 经结构化处理的课表<br>
 * 使用8个List分别保存周一至周日以及实训等所有课<br>
 * List已按照节数排序<br>
 * </p>
 * @author czm
 *
 */
public class CourseTable {
	
	/**
	 * 所有的课的List
	 */
	private List<Lession> rawLessions = null;
	
	/**
	 * 课程总数
	 */
	private int lessionCount = 0;
	
	/**
	 * 总学分
	 */
	private String allScore = "";
	
	/**
	 * 星期一的所有课
	 */
	private List<Lession> lessionsMonday = null;
	
	/**
	 * 星期二的所有课
	 */
	private List<Lession> lessionsTuesday = null;
	
	/**
	 * 星期三的所有课
	 */
	private List<Lession> lessionsWednesday = null;
	
	/**
	 * 星期四的所有课
	 */
	private List<Lession> lessionsThursday = null;
	
	/**
	 * 星期五的所有课
	 */
	private List<Lession> lessionsFriday = null;
	
	/**
	 * 星期六的所有课
	 */
	private List<Lession> lessionsSaturday = null;
	
	/**
	 * 星期日的所有课
	 */
	private List<Lession> lessionsSunday = null;
	
	/**
	 * 其他时间 的课
	 */
	private List<Lession> lessionsOther = null;
	
	/**
	 * 以 总课程数 构造课程表新实例
	 * @param lessionNum 总的lession数目
	 */
	public CourseTable(int lessionNum) {
		if (lessionNum <= 0) {
			throw new NumberFormatException("输入课程总数不合法");
		}
		
		this.lessionCount = lessionNum;
		
		this.rawLessions = new ArrayList<Lession>(this.lessionCount);
		
	}
	
	/**
	 * 构造课程表新实例
	 */
	public CourseTable() {
		this.rawLessions = new ArrayList<Lession>();
	}
	
	/**
	 * 向总课程列表添加课程
	 * @param l 添加的课程
	 */
	public void dumpLession(Lession l) {
		rawLessions.add(l);
	}
	
	/**
	 * 处理rawLessions
	 */
	public void dealRawLessions() {
		this.lessionCount = this.rawLessions.size();
		this.lessionsFriday = new ArrayList<Lession>();
		this.lessionsMonday = new ArrayList<Lession>();
		this.lessionsOther = new ArrayList<Lession>();
		this.lessionsSaturday = new ArrayList<Lession>();
		this.lessionsSunday = new ArrayList<Lession>();
		this.lessionsThursday = new ArrayList<Lession>();
		this.lessionsTuesday = new ArrayList<Lession>();
		this.lessionsWednesday = new ArrayList<Lession>();
		
		for (Lession ls : this.rawLessions) {
			String chooseStr = StringUtils.deleteWhitespace(ls.getWeek());
			// 无星期数add进其他课
			if (chooseStr.equals("")) {
				this.lessionsOther.add(ls);
			}
			else {
				// 处理一周多节
				if (chooseStr.contains("&")) {
					/* 讲一周多节分开存储 */
					Lession oneLession = (Lession) ls.clone();
					Lession anotherLession = (Lession) ls.clone();
					
					String reg = "(^.*?)&(.*$)";
//					String reWithNumber = "(\\d+)&(\\d+)";
//					Pattern pattern = Pattern.compile(reWithNumber);
					
					// 此处为提高效率不使用正则
					oneLession.setWeek(String.valueOf(StringUtils.deleteWhitespace(ls.getWeek()).charAt(0)));
					anotherLession.setWeek(String.valueOf(StringUtils.deleteWhitespace(ls.getWeek()).charAt(2)));

					Pattern pattern;
					Matcher match;
					// 将周次分开
					pattern = Pattern.compile(reg);
					match = pattern.matcher(StringUtils.deleteWhitespace(ls.getWeekNum()));
					match.find();
					oneLession.setWeekNum(match.group(1));
					anotherLession.setWeekNum(match.group(2));
					
					// 将节数分开
					pattern = Pattern.compile(reg);
					match = pattern.matcher(StringUtils.deleteWhitespace(ls.getIndex()));
					match.find();
					oneLession.setIndex(match.group(1));
					anotherLession.setIndex(match.group(2));
					
					// 利用星期数将两节课分别add进相应的List
					this.selectWithWeekNum(Integer.valueOf(oneLession.getWeek()), oneLession);
					this.selectWithWeekNum(Integer.valueOf(anotherLession.getWeek()), anotherLession);
				}
				else {
					// 一周只上一次
					this.selectWithWeekNum(Integer.valueOf(chooseStr), ls);
				}
					
			}
		}
		
		// 为每个List排序
		this.sortWithIndexNum();
		
		
	}
	
	/**
	 * 用节数对课进行排序（从早到晚）
	 */
	private void sortWithIndexNum() {
		this._sortEachList(lessionsThursday);
		this._sortEachList(lessionsTuesday);
		this._sortEachList(lessionsFriday);
		this._sortEachList(lessionsMonday);
		this._sortEachList(lessionsSaturday);
		this._sortEachList(lessionsSunday);
		this._sortEachList(lessionsWednesday);
		
	}
	
	/**
	 * 具体排序某个List
	 */
	private void _sortEachList(List<Lession> lst) {
		Collections.sort(lst, new Comparator<Lession>() {
			
			@Override
			public int compare(Lession a, Lession b) {
				String strA = a.getIndex();
				String strB = b.getIndex();
				
				String reg = "(^\\d+?)~\\d+$";
				Pattern pattern = Pattern.compile(reg);
				Matcher match = pattern.matcher(strA);
				match.find();
				int aNum = Integer.valueOf(match.group(1));
				
				match = pattern.matcher(strB);
				match.find();
				int bNum = Integer.valueOf(match.group(1));
				
				return aNum - bNum;
			}
		});
	}
	
	/**
	 * 根据星期数add进对应的List
	 * @param x 星期数
	 * @param ls add的Lession
	 */
	private void selectWithWeekNum(int x, Lession ls) {
		switch (x) {
		case 1:
			this.lessionsMonday.add(ls);
			break;
		case 2:
			this.lessionsTuesday.add(ls);
			break;
		case 3:
			this.lessionsWednesday.add(ls);
			break;
		case 4:
			this.lessionsThursday.add(ls);
			break;
		case 5:
			this.lessionsFriday.add(ls);
			break;
		case 6:
			this.lessionsSaturday.add(ls);
			break;
		case 7:
			this.lessionsSunday.add(ls);
			break;
		
		}
	}
	
	/**
	 * 获得总学分
	 * @return
	 */
	public String getAllScore() {
		return allScore;
	}
	
	/**
	 * 设置总学分
	 * @param allScore
	 */
	public void setAllScore(String allScore) {
		this.allScore = allScore;
	}
	
	/**
	 * 获取包含全部周一课的List（已排序）
	 * @return 包含全部周一课的List
	 */
	public List<Lession> getMonday() { 
		return this.lessionsMonday;
	}
	
	/**
	 * 获取包含全部周二课的List（已排序）
	 * @return 包含全部周二课的List
	 */
	public List<Lession> getTuesday() {
		return this.lessionsTuesday;
	}
	
	/**
	 * 获取包含全部周三课的List（已排序）
	 * @return 包含全部周三课的List
	 */
	public List<Lession> getWednesday() {
		return this.lessionsWednesday;
	}
	
	/**
	 * 获取包含全部周四课的List（已排序）
	 * @return 包含全部周四课的List
	 */
	public List<Lession> getThursday() {
		return this.lessionsThursday;
	}
	
	/**
	 * 获取包含全部周五课的List（已排序）
	 * @return 包含全部周五课的List
	 */
	public List<Lession> getFriday() {
		return this.lessionsFriday;
	}
	
	/**
	 * 获取包含全部周六课的List（已排序）
	 * @return 包含全部周六课的List
	 */
	public List<Lession> getSaturday() {
		return this.lessionsSaturday;
	}
	
	/**
	 * 获取储存周天课的List（已排序）
	 * @return 包含全部周天课的List
	 */
	public List<Lession> getSunday() {
		return this.lessionsSunday;
	}
	
	/**
	 * 获取包含全部其他课的List
	 * @return 包含全部其他课的List
	 */
	public List<Lession> getOtherLession() {
		return this.lessionsOther;
	}
}
