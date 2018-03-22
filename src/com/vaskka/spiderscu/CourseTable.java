package com.vaskka.spiderscu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.*;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * ���ṹ������Ŀα�<br>
 * ʹ��8��List�ֱ𱣴���һ�������Լ�ʵѵ�����п�<br>
 * List�Ѱ��ս�������<br>
 * </p>
 * @author czm
 *
 */
public class CourseTable {
	
	/**
	 * ���еĿε�List
	 */
	private List<Lession> rawLessions = null;
	
	/**
	 * �γ�����
	 */
	private int lessionCount = 0;
	
	/**
	 * ��ѧ��
	 */
	private String allScore = "";
	
	/**
	 * ����һ�����п�
	 */
	private List<Lession> lessionsMonday = null;
	
	/**
	 * ���ڶ������п�
	 */
	private List<Lession> lessionsTuesday = null;
	
	/**
	 * �����������п�
	 */
	private List<Lession> lessionsWednesday = null;
	
	/**
	 * �����ĵ����п�
	 */
	private List<Lession> lessionsThursday = null;
	
	/**
	 * ����������п�
	 */
	private List<Lession> lessionsFriday = null;
	
	/**
	 * �����������п�
	 */
	private List<Lession> lessionsSaturday = null;
	
	/**
	 * �����յ����п�
	 */
	private List<Lession> lessionsSunday = null;
	
	/**
	 * ����ʱ�� �Ŀ�
	 */
	private List<Lession> lessionsOther = null;
	
	/**
	 * �� �ܿγ��� ����γ̱���ʵ��
	 * @param lessionNum �ܵ�lession��Ŀ
	 */
	public CourseTable(int lessionNum) {
		if (lessionNum <= 0) {
			throw new NumberFormatException("����γ��������Ϸ�");
		}
		
		this.lessionCount = lessionNum;
		
		this.rawLessions = new ArrayList<Lession>(this.lessionCount);
		
	}
	
	/**
	 * ����γ̱���ʵ��
	 */
	public CourseTable() {
		this.rawLessions = new ArrayList<Lession>();
	}
	
	/**
	 * ���ܿγ��б���ӿγ�
	 * @param l ��ӵĿγ�
	 */
	public void dumpLession(Lession l) {
		rawLessions.add(l);
	}
	
	/**
	 * ����rawLessions
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
			// ��������add��������
			if (chooseStr.equals("")) {
				this.lessionsOther.add(ls);
			}
			else {
				// ����һ�ܶ��
				if (chooseStr.contains("&")) {
					/* ��һ�ܶ�ڷֿ��洢 */
					Lession oneLession = (Lession) ls.clone();
					Lession anotherLession = (Lession) ls.clone();
					
					String reg = "(^.*?)&(.*$)";
//					String reWithNumber = "(\\d+)&(\\d+)";
//					Pattern pattern = Pattern.compile(reWithNumber);
					
					// �˴�Ϊ���Ч�ʲ�ʹ������
					oneLession.setWeek(String.valueOf(StringUtils.deleteWhitespace(ls.getWeek()).charAt(0)));
					anotherLession.setWeek(String.valueOf(StringUtils.deleteWhitespace(ls.getWeek()).charAt(2)));

					Pattern pattern;
					Matcher match;
					// ���ܴηֿ�
					pattern = Pattern.compile(reg);
					match = pattern.matcher(StringUtils.deleteWhitespace(ls.getWeekNum()));
					match.find();
					oneLession.setWeekNum(match.group(1));
					anotherLession.setWeekNum(match.group(2));
					
					// �������ֿ�
					pattern = Pattern.compile(reg);
					match = pattern.matcher(StringUtils.deleteWhitespace(ls.getIndex()));
					match.find();
					oneLession.setIndex(match.group(1));
					anotherLession.setIndex(match.group(2));
					
					// ���������������ڿηֱ�add����Ӧ��List
					this.selectWithWeekNum(Integer.valueOf(oneLession.getWeek()), oneLession);
					this.selectWithWeekNum(Integer.valueOf(anotherLession.getWeek()), anotherLession);
				}
				else {
					// һ��ֻ��һ��
					this.selectWithWeekNum(Integer.valueOf(chooseStr), ls);
				}
					
			}
		}
		
		// Ϊÿ��List����
		this.sortWithIndexNum();
		
		
	}
	
	/**
	 * �ý����Կν������򣨴��絽��
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
	 * ��������ĳ��List
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
	 * ����������add����Ӧ��List
	 * @param x ������
	 * @param ls add��Lession
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
	 * �����ѧ��
	 * @return
	 */
	public String getAllScore() {
		return allScore;
	}
	
	/**
	 * ������ѧ��
	 * @param allScore
	 */
	public void setAllScore(String allScore) {
		this.allScore = allScore;
	}
	
	/**
	 * ��ȡ����ȫ����һ�ε�List��������
	 * @return ����ȫ����һ�ε�List
	 */
	public List<Lession> getMonday() { 
		return this.lessionsMonday;
	}
	
	/**
	 * ��ȡ����ȫ���ܶ��ε�List��������
	 * @return ����ȫ���ܶ��ε�List
	 */
	public List<Lession> getTuesday() {
		return this.lessionsTuesday;
	}
	
	/**
	 * ��ȡ����ȫ�������ε�List��������
	 * @return ����ȫ�������ε�List
	 */
	public List<Lession> getWednesday() {
		return this.lessionsWednesday;
	}
	
	/**
	 * ��ȡ����ȫ�����Ŀε�List��������
	 * @return ����ȫ�����Ŀε�List
	 */
	public List<Lession> getThursday() {
		return this.lessionsThursday;
	}
	
	/**
	 * ��ȡ����ȫ������ε�List��������
	 * @return ����ȫ������ε�List
	 */
	public List<Lession> getFriday() {
		return this.lessionsFriday;
	}
	
	/**
	 * ��ȡ����ȫ�������ε�List��������
	 * @return ����ȫ�������ε�List
	 */
	public List<Lession> getSaturday() {
		return this.lessionsSaturday;
	}
	
	/**
	 * ��ȡ��������ε�List��������
	 * @return ����ȫ������ε�List
	 */
	public List<Lession> getSunday() {
		return this.lessionsSunday;
	}
	
	/**
	 * ��ȡ����ȫ�������ε�List
	 * @return ����ȫ�������ε�List
	 */
	public List<Lession> getOtherLession() {
		return this.lessionsOther;
	}
}
