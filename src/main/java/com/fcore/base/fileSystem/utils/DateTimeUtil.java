package com.fcore.base.fileSystem.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

/**
 * 日期时间工具类
 *
 */
public class DateTimeUtil {

	private static final Logger logger = Logger.getLogger(DateTimeUtil.class);

	private final static String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	/**
	 * yyyy-MM-dd
	 */
	public static int yyyy_MM_dd = 0;
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static int yyyy_MM_dd_HH_mm_ss = 1;
	/**
	 * HH:mm:ss
	 */
	public static int HH_mm_ss = 2;
	/**
	 * yyyyMMddHHmmss
	 */
	public static int yyyyMMddHHmmss = 3;
	/**
	 * yyyyMMddHHmmssSS
	 */
	public static int yyyyMMddHHmmssSSS = 4;
	/**
	 * yyyy年MM月dd日
	 */
	public static int c_yyyy_MM_dd_ = 5;
	/**
	 * yyyy年MM月dd日HH时mm分ss秒
	 */
	public static int c_yyyy_MM_dd_HH_mm_ss_ = 6;
	/**
	 * yyyy
	 */
	public static int yyyy = 7;
	/**
	 * MM
	 */
	public static int MM = 8;
	/**
	 * dd
	 */
	public static int dd = 9;
	/**
	 * yyyyMMdd
	 */
	public static int yyyyMMdd = 10;
	/**
	 * yyyy-MM
	 */
	public static int yyyy_MM = 11;
	/**
	 * yyyy年MM月dd日 EE
	 */
	public static int c_yyyy_MM_dd_EE = 12;
	/**
	 * yyyy/MM/dd HH:mm:ss
	 */
	public static int x_yyyy_MM_dd_HH_mm_ss = 13;

	/**
	 * HHmmss
	 */
	public static int HHmmss = 14;

	/**
	 * HHmmssSS
	 */
	public static int HHmmssSSS = 15;

	/**
	 * 格式化日期样式
	 * @param caseNum
	 * @return
	 */
	public static SimpleDateFormat getDateFormat(int caseNum) {
		SimpleDateFormat dateFormat = null;
		switch (caseNum) {
		case 0:
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			break;
		case 1:
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		case 2:
			dateFormat = new SimpleDateFormat("HH:mm:ss");
			break;
		case 3:
			dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			break;
		case 4:
			dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			break;
		case 10:
			dateFormat = new SimpleDateFormat("yyyyMMdd");
			break;
		case 5:
			dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
			break;
		case 6:
			dateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
			break;
		case 7:
			dateFormat = new SimpleDateFormat("yyyy");
			break;
		case 8:
			dateFormat = new SimpleDateFormat("MM");
			break;
		case 9:
			dateFormat = new SimpleDateFormat("dd");
			break;
		case 11:
			dateFormat = new SimpleDateFormat("yyyy-MM");
			break;
		case 12:
			dateFormat = new SimpleDateFormat("yyyy年MM月dd日 EE");
			break;
		case 13:
			dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			break;
		case 14:
			dateFormat = new SimpleDateFormat("HHmmss");
			break;
		case 15:
			dateFormat = new SimpleDateFormat("HHmmssSSS");
			break;
		default:
			break;
		}
		return dateFormat;
	}

	/**
	 * 把日期转换为字符串
	 * @param date
	 * @param caseNum
	 * @return
	 */
	public static String formatDate(Date date, int caseNum) {
		String dateStr = null;
		SimpleDateFormat dateFormat = getDateFormat(caseNum);
		dateStr = dateFormat.format(date);
		return dateStr;
	}

	/**
	 * 把字符串转换为日期
	 * @param dateStr
	 * @param caseNum
	 * @return
	 */
	public static Date parseDate(String dateStr, int caseNum) {
		Date date = null;
		try {
			SimpleDateFormat dateFormat = getDateFormat(caseNum);
			date = dateFormat.parse(dateStr);
		} catch (Exception e) {
			logger.error("parseDate error:", e);
		}
		return date;
	}

	/**
	 * 获取当前时间的字符串形式
	 * @param caseNum
	 * @return
	 * @throws Exception
	 */
	public static String getNowDateStr(int caseNum) {
		Date date = new Date();
		SimpleDateFormat dateFormat = getDateFormat(caseNum);
		String dateStr = dateFormat.format(date);
		return dateStr;
	}

	/**
	 * 获取昨天时间的日期格式
	 * @param caseNum
	 * @return
	 */
	public static String getYesDateStr(int caseNum){
		Date date=new Date();//取时间
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
		 date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
		 SimpleDateFormat formatter = getDateFormat(caseNum);
		 String dateString = formatter.format(date);
		 return dateString;
	}
	
	/**
	 * 得到年份
	 * @param c
	 * @return
	 */
	public static int getYear(Calendar c) {
		return c.get(Calendar.YEAR);
	}

	/**
	 * 得到当前年份
	 * @return
	 */
	public static int getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 得到月份
	 * @param c
	 * @return
	 */
	public static int getMonth(Calendar c) {
		return c.get(Calendar.MONTH);
	}

	/**
	 * 得到当前月份
	 * @return
	 */
	public static int getMonth() {
		return Calendar.getInstance().get(Calendar.MONTH);
	}

	/**
	 * 得到日
	 * @param c
	 * @return
	 */
	public static int getDate(Calendar c) {
		return c.get(Calendar.DATE);
	}

	/**
	 * 得到当前日
	 * @return
	 */
	public static int getDate() {
		return Calendar.getInstance().get(Calendar.DATE);
	}

	/**
	 * 得到星期
	 * @param c
	 * @return
	 */
	public static int getDay(Calendar c) {
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 得到当前星期
	 * @return
	 */
	public static int getDay() {
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 得到星期
	 * @param c
	 * @return
	 */
	public static String getChineseDay(Calendar c) {
		return dayNames[getDay(c) - 1];
	}

	/**
	 * 得到当前星期（中文）
	 * @return
	 */
	public static String getChineseDay() {
		return dayNames[getDay() - 1];
	}

	/**
	 * 得到小时
	 * @param c
	 * @return
	 */
	public static int getHour(Calendar c) {
		return c.get(Calendar.HOUR);
	}

	/**
	 * 得到当前小时
	 * @return
	 */
	public static int getHour() {
		return Calendar.getInstance().get(Calendar.HOUR);
	}

	/**
	 * 昨天
	 * @param c
	 * @return
	 */
	public static Calendar yesterday(Calendar c) {
		long offset = 1 * 24 * 60 * 60 * 1000;
		c.setTimeInMillis(c.getTimeInMillis() - offset);
		return c;
	}

	/**
	 * 当前时间的昨天
	 * @return
	 */
	public static Calendar yesterday() {
		long offset = 1 * 24 * 60 * 60 * 1000;
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(c.getTimeInMillis() - offset);
		return c;
	}

	/**
	 * 明天
	 * @param c
	 * @return
	 */
	public static Calendar tomorrow(Calendar c) {
		long offset = 1 * 24 * 60 * 60 * 1000;
		c.setTimeInMillis(c.getTimeInMillis() + offset);
		return c;
	}

	/**
	 * 当前时间的明天
	 * @return
	 */
	public static Calendar tomorrow() {
		long offset = 1 * 24 * 60 * 60 * 1000;
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(c.getTimeInMillis() + offset);
		return c;
	}

	/**
	 * Date类型转换到Calendar类型
	 * @param d
	 * @return
	 */
	public static Calendar Date2Calendar(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c;
	}

	/**
	 * Calendar类型转换到Date类型
	 * @param c
	 * @return
	 */
	public static Date Calendar2Date(Calendar c) {
		return c.getTime();
	}

	/**
	 * 计算两个日期之间相差的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int daysBetweenDate(Date startDate, Date endDate) {
		Long interval = endDate.getTime() - startDate.getTime();
		interval = interval / (24 * 60 * 60 * 1000);
		return interval.intValue();
	}

	/**
	 * 日期加减
	 * @param date
	 * @param formatCaseNum
	 * @param calendarType
	 * @param num
	 * @return
	 * @throws Exception
	 */
	static public String addDate(String date, int formatCaseNum, int calendarType, int num) {
		SimpleDateFormat format = null;
		Calendar calendar = Calendar.getInstance();
		try {
			format = getDateFormat(formatCaseNum);
			calendar.setTime(format.parse(date));
			calendar.add(calendarType, num);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return format.format(calendar.getTime());
	}

	/**
	 * 判断是否是周末
	 * @param date
	 * @return
	 */
	public static boolean isWeekend(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_WEEK);
		if (day == 1 || day == 7) {
			return true;
		}
		return false;
	}

	/**
	 * 给定日期是第几季度
	 * @param date
	 * @return
	 */
	public static int getQuarter(Date date) {
		int quarter = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = getMonth(c);
		if (month >= 0 && month <= 2) {
			quarter = 1;
		} else if (month >= 3 && month <= 5) {
			quarter = 2;
		} else if (month >= 6 && month <= 8) {
			quarter = 3;
		} else if (month >= 9 && month <= 11) {
			quarter = 4;
		}
		return quarter;
	}

	/**
	 * 判断某个日期是否在某个日期范围 
	 * @param beginDate
	 * @param endDate
	 * @param src
	 * @return
	 */
	public static boolean isBetween(Date beginDate, Date endDate, Date src) {
		return beginDate.before(src) && endDate.after(src);
	}
}
