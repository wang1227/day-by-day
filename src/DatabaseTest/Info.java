package DatabaseTest;

import org.litepal.crud.DataSupport;

public class Info extends DataSupport{
	private int id;
	private String title;
	private String content;
	private String date;
	private String alarm;
	private String youxianji;
	private int textcolor;
	private String openalarm;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAlarm() {
		return alarm;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}

	public String getYouxianji() {
		return youxianji;
	}

	public void setYouxianji(String youxianji) {
		this.youxianji = youxianji;
	}

	public int getTextcolor() {
		return textcolor;
	}

	public void setTextcolor(int textcolor) {
		this.textcolor = textcolor;
	}

	public String getOpenalarm() {
		return openalarm;
	}

	public void setOpenalarm(String openalarm) {
		this.openalarm = openalarm;
	}

	public int getid() {
		return id;
	}

	public void set_id(int id) {
		this.id = id;
	}

}
