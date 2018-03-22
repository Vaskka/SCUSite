/*
 * ˼·
 * 1 post������ɵ�½
 * 2 �õ�cookies��get���пγ̱����ҳ
 * 3 �Ի�õ�row���ݽ��н�����װΪ�ṹ������
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
 * ����http��ɶԽ�ѧ��վ��Ϣ����ȡ
 * @author czm
 *
 */
public class Spider {
	
	/**
	 * Ĭ�ϵ�½��url
	 */
	private static String loginSite = "http://zhjw.scu.edu.cn/loginAction.do";
	
	/**
	 * �γ̱��url
	 */
	private static String courseTableSite = "http://zhjw.scu.edu.cn/xkAction.do?actionType=6";
	
	/**
	 * �û���
	 */
	private static String username = null;
	
	/**
	 * ����
	 */
	private static String password = null;
	
	/**
	 * ָ������
	 */
	private static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36";
	
	/**
	 * ���ӷ�ʽ
	 */
	private static String connection = "Keep-Alive";
	
	/**
	 * ��������
	 */
	private static String accept = "*/*";
	
	/**
	 * POST����Header
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
	 * �����ʱ��
	 */
	private static int timeOut = 5000;
	
	/**
	 * GET������쳣��Ϣ
	 */
	private static String getExceptionMessage = "GET Exception when get http request. The Exception message is ";
	
	private static String postExceptionMessage = "POST Exception when get http request. The Exception message is ";
	
	/**
	 * Ĭ�ϱ���
	 */
	private static String defaultCharset = "gb2312";
	
	/**
	 * ��ȡ������Ϣ
	 */
	private static String response = null;
	
	/**
	 * �γ̱�
	 */
	private static CourseTable table = null;
	
	/**
	 * ����������ʵ��
	 */
	public Spider() {
		
	}
	
	/**
	 * ����������ʵ��
	 * @param un �γ���վ�û�����ѧ�ţ�
	 * @param password �γ���վ����
	 */
	public Spider(String un, String psw) {
		username = un;
		password = psw;
	}
	
	/**
	 * GET ������url��������
	 * @param url �����url
	 * @return url���ص�response��Ϣ
	 */
	private String get(String url) {
		BufferedReader in = null;
		try {
			URL siteUrl = new URL(url);
			// ������
			URLConnection connection = siteUrl.openConnection();
			// ����Header
			connection.setRequestProperty("accept", Spider.accept);
			connection.setRequestProperty("connection", Spider.connection);
			connection.setRequestProperty("user-agent", Spider.userAgent);
			
			//timeout
			connection.setConnectTimeout(Spider.timeOut);
			connection.setReadTimeout(Spider.timeOut);
			
			this.setData(connection);
			
			// ��������
			connection.connect();
			
			// ʹ��BufferedReader��ȡ������Ӧ
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer resultBuffered = new StringBuffer();
			
			String line = "";
			// ���ж���
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
	 * ���������Ϊָ�����ַ���
	 * @param source ��ת����ַ���
	 * @param charset ָ�����ַ���
	 * @return ����ת�����ַ���
	 * @throws UnsupportedEncodingException ��֧�ֵı����쳣
	 */
	public static String setToEncode(String source, String charset) throws UnsupportedEncodingException {
		if (source != null) {
			byte[] strByte = source.getBytes();
			
			return new String(strByte, charset);
		}
		
		return null;
	}
	
	/**
	 * ��ָ��url����post����
	 * @param url ָ��post��url
	 * @param params ���ݵĲ���
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
			// ��������ʽΪPOST
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
	 * ����Header
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
		conn.setRequestProperty("Cookie", "UM_distinctid=1606dcc2a6c22-07433e1763e643-c303767-e1000-1606dcc2a6d8c; JSESSIONID=dcaDGQ7dbMS7x5pWHOdjw");
	
	}
	
	/**
	 * �����õ�Response����
	 * @throws Exception �����responseΪ��
	 */
	private void dealResponse() throws Exception {
		//responseΪ���˳�
		if (response == null) {
			throw new Exception("�������");
		}
		
	
		table = new CourseTable();
		
		// ��־һ��������flag
		String xpathFlag = "//table[5]/tbody/tr/td/table/tbody/tr[1]/td[1]/text()";
		
		// �õ����е�td
		String xpath = "//table[5]/tbody/tr/td/table/tbody/tr/td/text()";
		
		// �õ���ѧ��
		String xpathAllScore = "//table[6]/tbody/tr/td/div/text()";
		
		// ʵ����xpath��������
		JXDocument jxflag = new JXDocument(response);
		JXDocument jxd = new JXDocument(response);
		JXDocument jxAllScore = new JXDocument(response);
		try {
			String flag = (String) jxflag.sel(xpathFlag).get(0);
			List<Object> result = jxd.sel(xpath);
			String allScore = (String) jxAllScore.sel(xpathAllScore).get(0);
			// ������
			int count = 0;
			Lession newLession = null;
			for (Object o : result) {
				String index = o.toString();
				StringUtils.deleteWhitespace(index);
				// ��⵽������ڲ��ǵ�һ���������dump��table
				if (index.equals(flag)) {
					count = 0;
					if (newLession != null) {
						table.dumpLession(newLession);
					}
					newLession = new Lession();
					continue;
				}
				
				if (newLession != null && (!index.equals(""))) {
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
							newLession.setReadWay(index);
							count++;
							break;
						case 8:
							newLession.setChooseState(index);
							count++;
							break;
						case 9:
							newLession.setWeekNum(index);
							count++;
							break;
						case 10:
							newLession.setWeek(index);
							count++;
							break;
						case 11:
							newLession.setIndex(index);
							count++;
							break;
						case 12:
							newLession.setSchoolArea(index);
							count++;
							break;
						case 13:
							newLession.setPlace(index);
							count++;
							break;
						case 14:
							newLession.setClassRoom(index);
							count++;
							break;
						case 15:
							//����һ���϶�εĿ�
							newLession.addWeekNum(" & " + index);
							count++;
							break;
						case 16:
							newLession.addWeek(" & " + index);
							count++;
							break;
						case 17:
							newLession.addIndex(" & " + index);
							count++;
							break;
					}
				}

			}
			// ������ѧ��
			table.setAllScore(allScore);

		} 
		catch (XpathSyntaxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


	}
	
	/**
	 * ���Response
	 * @throws Exception �û��������벻�Ϸ���null��
	 */
	private void getResponse() throws Exception {
		if (username == null || password == null) {
			throw new Exception("�û��������벻�Ϸ�");
		}
		response = post(loginSite, "zjh=" + username + "&mm=" + password);
	
		response = get(courseTableSite + "&zjh=" + username + "&mm=" + password);
	}
	
	/**
	 * ���ݿγ����Ļ�ÿα�
	 * @return �ṹ���α�
	 */
	public CourseTable getCourseTable() {
		try {
			this.getResponse();
			this.dealResponse();
			// TODO table ��ʽ��rawLessions
			table.dealRawLessions();
			return table;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}
	
	
}
