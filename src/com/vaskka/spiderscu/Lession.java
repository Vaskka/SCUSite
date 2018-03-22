package com.vaskka.spiderscu;

public class Lession {
	
	/**
	 * 所在的星期数（e.g.星期一）
	 */
	private String week = "";
	
	/**
	 * 该科的节数（e.g.第三节）
	 */
	private String index = "";
	
	/**
	 * 课程名
	 */
	private String name = "";
	
	/**
	 * 上课地点
	 */
	private String place = "";
	
	/**
	 * 课序号
	 */
	private String lessionIndexNum = "";
	
	/**
	 * 课程号
	 */
	private String lessionNum = "";
	
	/**
	 * 学分
	 */
	private String score = "";
	
	/**
	 * 课程属性
	 */
	private String attr = "";
	
	/**
	 * 考察类型
	 */
	private String exam = "";
	
	/**
	 * 教师
	 */
	private String teacher = "";
	
	/**
	 * 修读方式
	 */
	private String readWay = "";
	
	/**
	 * 选课状态
	 */
	private String chooseState = "";
	
	/**
	 * 周次
	 */
	private String weekNum = "";

	/**
	 * 校区
	 */
	private String schoolArea = "";
	
	/**
	 * 教室
	 */
	private String classRoom = "";

	/**
	 * 获取星期数
	 * @return the week
	 */
	public String getWeek() {
		return week;
	}

	/**
	 * 设置星期数
	 * @param week the week to set
	 */
	public void setWeek(String week) {
		this.week = week;
	}
	
	/**
	 * 追加星期数信息
	 * @param week 追加的信息数
	 */
	public void addWeek(String week) {
		this.week += week;
	}

	/**
	 * 获取节数
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * 设置节数
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}
	
	/**
	 * 追加节数信息
	 * @param index 追加的节数
	 */
	public void addIndex(String index) {
		this.index += index;
	}
	
	/**
	 * 获取课程名
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置课程名
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取上课地点
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * 设置上课地点
	 * @param place the place to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * 获取课序号
	 * @return the lessionIndexNum
	 */
	public String getLessionIndexNum() {
		return lessionIndexNum;
	}

	/**
	 * 设置课序号
	 * @param lessionIndexNum the lessionIndexNum to set
	 */
	public void setLessionIndexNum(String lessionIndexNum) {
		this.lessionIndexNum = lessionIndexNum;
	}

	/**
	 * 获取课程号
	 * @return the lessionNum
	 */
	public String getLessionNum() {
		return lessionNum;
	}

	/**
	 * 设置课程号
	 * @param lessionNum the lessionNum to set
	 */
	public void setLessionNum(String lessionNum) {
		this.lessionNum = lessionNum;
	}

	/**
	 * 获取学分数
	 * @return the score
	 */
	public String getScore() {
		return score;
	}

	/**
	 * 设置学分数
	 * @param score the score to set
	 */
	public void setScore(String score) {
		this.score = score;
	}

	/**
	 * 获取课程属性（任选 选修 必修）
	 * @return the attr
	 */
	public String getAttr() {
		return attr;
	}

	/**
	 * 设置课程属性
	 * @param attr the attr to set
	 */
	public void setAttr(String attr) {
		this.attr = attr;
	}

	/**
	 * 获取考试方式
	 * @return the exam
	 */
	public String getExam() {
		return exam;
	}

	/**
	 * 设置考试方式
	 * @param exam the exam to set
	 */
	public void setExam(String exam) {
		this.exam = exam;
	}

	/**
	 * 获取教师姓名
	 * @return the teacher
	 */
	public String getTeacher() {
		return teacher;
	}

	/**
	 * 设置教师姓名
	 * @param teacher the teacher to set
	 */
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	/**
	 * 获取修读方式
	 * @return the readWay
	 */
	public String getReadWay() {
		return readWay;
	}

	/**
	 * 设置修读方式
	 * @param readWay the readWay to set
	 */
	public void setReadWay(String readWay) {
		this.readWay = readWay;
	}

	/**
	 * 获取选课状态
	 * @return the chooseState
	 */
	public String getChooseState() {
		return chooseState;
	}

	/**
	 * 设置选课状态
	 * @param chooseState the chooseState to set
	 */
	public void setChooseState(String chooseState) {
		this.chooseState = chooseState;
	}

	/**
	 * 获取周次
	 * @return the weekNum
	 */
	public String getWeekNum() {
		return weekNum;
	}

	/**
	 * 设置周次
	 * @param weekNum the weekNum to set
	 */
	public void setWeekNum(String weekNum) {
		this.weekNum = weekNum;
	}
	
	/**
	 * 追加周次信息
	 * @param weekNum 追加的周次
	 */
	public void addWeekNum(String weekNum) {
		this.weekNum += weekNum;
	}
	
	/**
	 * 获取校区
	 * @return the schoolArea
	 */
	public String getSchoolArea() {
		return schoolArea;
	}

	/**
	 * 设置校区
	 * @param schoolArea the schoolArea to set
	 */
	public void setSchoolArea(String schoolArea) {
		this.schoolArea = schoolArea;
	}
	
	/**
	 * 获取教室
	 * @return 上课教室
	 */
	public String getClassRoom() {
		return classRoom;
	}
	
	/**
	 * 获取教室
	 * @param classRoom 上课的教室
	 */
	public void setClassRoom(String classRoom) {
		this.classRoom = classRoom;
	}
	
	/**
	 * deepcopy 得到Lession新实例
	 */
	@Override
	public Object clone() {

		Lession newLession = new Lession();
		newLession.attr = new String(this.attr);
		newLession.chooseState = new String(this.chooseState);
		newLession.classRoom = new String(this.classRoom);
		newLession.lessionIndexNum = new String(this.lessionIndexNum);
		newLession.index = new String(this.index);
		newLession.exam = new String(this.exam);
		newLession.lessionNum = new String(this.lessionNum);
		newLession.name = new String(this.name);
		newLession.place = new String(this.place);
		newLession.readWay = new String(this.readWay);
		newLession.schoolArea = new String(this.schoolArea);
		newLession.score = new String(this.score);
		newLession.teacher = new String(this.teacher);
		newLession.week = new String(this.week);
		newLession.weekNum = new String(this.weekNum);
		return newLession;
	}
	
}
