/*
 * 思路
 * 1 post方法完成登陆
 * 2 拿到cookies来get含有课程表的网页
 * 3 对获得的row数据进行解析封装为结构化数据
*/
package com.vaskka.spiderscu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;


/**
 * 基于http完成对教学网站信息的爬取
 * @author czm
 *
 */
public class Spider {
	
	/**
	 * 默认登陆的url
	 */
	private static String loginSite = "http://zhjw.scu.edu.cn/loginAction.do";
	
	/**
	 * 课程表的url
	 */
	private static String courseTableSite = "http://zhjw.scu.edu.cn/xkAction.do?actionType=6";
	
	/**
	 * 用户名
	 */
	private static String username = null;
	
	/**
	 * 密码
	 */
	private static String password = null;
	
	/**
	 * 指定代理
	 */
	private static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36";
	
	/**
	 * 连接方式
	 */
	private static String connection = "Keep-Alive";
	
	/**
	 * 接受类型
	 */
	private static String accept = "*/*";
	
	/**
	 * POST构造Header
	 */
	private static String host = "zhjw.scu.edu.cn";
	private static String contentLength = "18";
	private static String origin = "http://zhjw.scu.edu.cn";
	private static String upgradeInsecureRequests = "1";
	private static String contentType = "application/x-www-form-urlencoded";
	private static String referer = "http://zhjw.scu.edu.cn/login.jsp";
	private static String acceptEncoding = "gzip, deflate";
	private static String acceptLanguage = "zh-CN,zh;q=0.9";
//	private static String cacheControl = "max-age=0";
	
	/**
	 * 请求最长时间
	 */
	private static int timeOut = 5000;
	
	/**
	 * GET请求的异常信息
	 */
	private static String getExceptionMessage = "GET Exception when get http request. The Exception message is ";
	
	/**
	 * POST请求异常信息
	 */
	private static String postExceptionMessage = "POST Exception when get http request. The Exception message is ";
	
	/**
	 * 默认编码
	 */
	private static String defaultCharset = "gb2312";
	
	/**
	 * 爬取到的信息
	 */
	private static String response = null;
	
	/**
	 * 课程表
	 */
	private static CourseTable table = null;
	
	/**
	 * cookies
	 */
	private static String cookies = null;
	
	/**
	 * 指定Spider的字符集
	 */
	private static String CHARSET = null;
	
	/**
	 * 构造爬虫新实例
	 */
	public Spider() {
		
	}
	
	/**
	 * 构造爬虫新实例 , 使用系统默认编码
	 * @param un 课程网站用户名（学号）
	 * @param password 课程网站密码
	 */
	public Spider(String un, String psw) {
		username = un;
		password = psw;
	}
	
	/**
	 * 使用用户名密码和指定的字符集构造Spider新实例
	 * @param un
	 * @param psw
	 * @param charset
	 */
	public Spider(String un, String psw, String charset) {
		username = un;
		password = psw;
		CHARSET = charset;
	}
	
	
	/**
	 * GET 方法向url请求数据
	 * @param url 请求的url
	 * @return url返回的response信息
	 */
	private String get(String url) {
		BufferedReader in = null;
		try {
			URL siteUrl = new URL(url);
			// 打开链接
			URLConnection connection = siteUrl.openConnection();
			// 设置Header
			connection.setRequestProperty("accept", Spider.accept);
			connection.setRequestProperty("connection", Spider.connection);
			connection.setRequestProperty("user-agent", Spider.userAgent);
			
			//timeout
			connection.setConnectTimeout(Spider.timeOut);
			connection.setReadTimeout(Spider.timeOut);
			
			this.setData(connection);
			
			// 建立链接
			connection.connect();
			
			// 使用BufferedReader读取数据响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GBK"));
			StringBuffer resultBuffered = new StringBuffer();
			
			String line = "";
			// 逐行读入
			while ((line = in.readLine()) != null) {
				resultBuffered.append(line);
			}
			
			return resultBuffered.toString();
			
		}
		catch (Exception e) {
			return  Spider.getExceptionMessage + e.getMessage();
		}
		finally {
			try {
				if (in != null) {
					in.close();
				}
			}
			catch (IOException e) {
			}
		}
		
	}

	
	/**
	 * 将结果编码为指定的字符集
	 * @param source 待转码的字符串
	 * @param sourceCharset 本地的编码
	 * @param destCharset 指定的字符集
	 * @return 经过转化的字符串
	 * @throws UnsupportedEncodingException 不支持的编码异常
	 */
	private static String setToEncode(String source, String sourceCharset, String destCharset) throws UnsupportedEncodingException {
		if (source != null) {
			byte[] strByte = source.getBytes(sourceCharset);
			
			return new String(strByte, destCharset);
		}
		
		return null;
	}
	
	/**
	 * 向指定url发送post请求
	 * @param url 指定post的url
	 * @param params 传递的参数
	 * @return
	 */
	private String post(String url, String params) {
		String result = "";
		PrintWriter out = null;
		InputStream in = null;
		try {
			URL siteUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) siteUrl.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			// 设置请求方式为POST
			connection.setRequestMethod("POST");
			this.setData(connection);
			connection.connect();
			
			out = new PrintWriter(connection.getOutputStream());
			out.print(params);
			out.flush();
			
			in = connection.getInputStream();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(in, Spider.defaultCharset));
			StringBuffer resultBuffer = new StringBuffer();
			String line = "";
			while ((line = buffer.readLine()) != null) {
				resultBuffer.append(line);
			}
			Spider.cookies = connection.getHeaderField("Set-Cookie");

			result = resultBuffer.toString();
			
		}
		catch (Exception e) {
			return Spider.postExceptionMessage + e.getMessage();
		}
		finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}

			}
			catch (IOException e) {
				return e.getMessage();
			}
		}
		
		return result;
	}
	
	/**
	 * 构造Header
	 * @param conn
	 */
	private void setData(URLConnection conn) {
		conn.setRequestProperty("Host", Spider.host);
		conn.setRequestProperty("Connection", Spider.connection);
		conn.setRequestProperty("Content-Length", Spider.contentLength);
//		conn.setRequestProperty("Cache-Control", Spider.cacheControl);
		conn.setRequestProperty("Origin", Spider.origin);
		conn.setRequestProperty("Upgrade-Insecure-Requests", Spider.upgradeInsecureRequests);
		conn.setRequestProperty("Content-Type", Spider.contentType);
		conn.setRequestProperty("User-Agent", Spider.userAgent);
		conn.setRequestProperty("Accept", Spider.accept);
		conn.setRequestProperty("Referer", Spider.referer);
		conn.setRequestProperty("Accept-Encoding", Spider.acceptEncoding);
		conn.setRequestProperty("Accept-Language", Spider.acceptLanguage);
		
		if (cookies != null) {
			conn.setRequestProperty("Cookie", cookies);
		}
		
		
	}
	
	/**
	 * 处理获得的Response数据
	 * @throws Exception 请求的response为空
	 */
	private void dealResponse() throws Exception {
		//response为空退出
		if (response == null) {
			throw new Exception("请求错误");
		}
		
	
		table = new CourseTable();
		
		// 标志一个结束的flag
		String xpathFlag = "//table[5]/tbody/tr/td/table/tbody/tr[1]/td[1]/text()";
		
		// 拿到所有的td
		String xpath = "//table[5]/tbody/tr/td/table/tbody/tr/td/text()";
		
		// 拿到总学分
		String xpathAllScore = "//table[6]/tbody/tr/td/div/text()";
		
		// 实例化xpath处理引擎
		JXDocument jxflag = new JXDocument(response);
		JXDocument jxd = new JXDocument(response);
		JXDocument jxAllScore = new JXDocument(response);
		try {
			String flag = (String) jxflag.sel(xpathFlag).get(0);
			List<Object> result = jxd.sel(xpath);
			String allScore = (String) jxAllScore.sel(xpathAllScore).get(0);
			// 计数器
			int count = 0;
			Lession newLession = null;
			for (Object o : result) {
				String index = o.toString();
				index = StringUtils.deleteWhitespace(index);
				
				// 检测到标记且在不是第一个的情况下dump进table
				if (index.equals(flag)) {

					count = 0;
					if (newLession != null) {
						table.dumpLession(newLession);
					}
					newLession = new Lession();
					continue;
				}
				
				if (newLession != null) {
					switch (count) {
						case 0:
							newLession.setLessionNum(index);
							count++;
							break;
						case 1:
							newLession.setName(index);
							count++;
							break;
						case 2:
							newLession.setLessionIndexNum(index);
							count++;
							break;
						case 3:
							newLession.setScore(index);
							count++;
							break;
						case 4:
							newLession.setAttr(index);
							count++;
							break;
						case 5:
							newLession.setExam(index);
							count++;
							break;
						case 6:
							newLession.setTeacher(index);
							count++;
							break;
						case 7:
							
							count++;
							break;
						case 8:
							newLession.setReadWay(index);
							
							count++;
							break;
						case 9:
							newLession.setChooseState(index);
							
							count++;
							break;
						case 10:
							newLession.setWeekNum(index);
							
							count++;
							break;
						case 11:
							newLession.setWeek(index);
							
							count++;
							break;
						case 12:
							newLession.setIndex(index);
							
							count++;
							break;
						case 13:
							newLession.setSchoolArea(index);
							
							count++;
							break;
						case 14:
							newLession.setPlace(index);
							
							count++;
							break;
						case 15:
							newLession.setClassRoom(index);
	
							count++;
							break;
						case 16:
							//调整一周上多次的课
							newLession.addWeekNum(" & " + index);
							
							count++;
							break;
						case 17:
							newLession.addWeek(" & " + index);
							count++;
							break;
						case 18:
							newLession.addIndex(" & " + index);
							count++;
							break;
						case 19:
							newLession.addSchoolArea(" & " + index);
							count++;
							break;
						case 20:
							newLession.addPlace(" & " + index);
							count++;
							break;
						case 21:
							newLession.addClassRoom(" & " + index);
							count++;
							break;
					}
				}

			}
			// 将最后一个也dump进table
			table.dumpLession(newLession);
			// 设置总学分
			table.setAllScore(allScore);
		} 
		catch (XpathSyntaxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


	}
	
	/**
	 * 获得Response
	 * @throws Exception 用户名或密码不合法（null）
	 */
	private void getResponse() throws Exception {
		if (username == null || password == null) {
			throw new Exception("用户名或密码不合法");
		}
		response = post(loginSite, "zjh=" + username + "&mm=" + password);

		response = get(courseTableSite + "&zjh=" + username + "&mm=" + password);
	}
	
	/**
	 * 根据课程中心获得课表
	 * @return 结构化课表
	 */
	public CourseTable getCourseTable() {
		try {
			this.getResponse();
			
			// 如果指定字符集就转换编码
			if (CHARSET != null) {
				setToEncode(response, "GBK", CHARSET);
				
			}
			
			this.dealResponse();
			// TODO table 格式化rawLessions
			
			table.dealRawLessions();
			return table;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
}
