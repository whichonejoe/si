/**
 * 
 */
package com.sidc.blackcore.api.agent.request;

import java.io.Serializable;
import java.util.List;

/**
 * @author Joe
 *
 */
public class CheckInRequest implements Serializable {

	private static final long serialVersionUID = 7830794728149833025L;

	private String roomno;
	private String checkindate;
	private List<GuestRequest> guests;
	private String langcode;
	private String billno;
	private String tvright;

	public CheckInRequest(String roomno, String checkindate, List<GuestRequest> guests, String langcode,
			String billno, String tvright) {
		super();
		this.roomno = roomno;
		this.checkindate = checkindate;
		this.guests = guests;
		this.langcode = langcode;
		this.billno = billno;
		this.tvright = tvright;
	}

	public String getRoomno() {
		return roomno;
	}

	public String getCheckindate() {
		return checkindate;
	}

	public String getLangcode() {
		return langcode;
	}

	public String getBillno() {
		return billno;
	}

	public String getTvright() {
		return tvright;
	}

	public List<GuestRequest> getGuests() {
		return guests;
	}

	public void setGuests(List<GuestRequest> guests) {
		this.guests = guests;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CheckInRequest [roomno=\n");
		builder.append(roomno);
		builder.append(", checkindate=\n");
		builder.append(checkindate);
		builder.append(", guests=\n");
		builder.append(guests);
		builder.append(", langcode=\n");
		builder.append(langcode);
		builder.append(", billno=\n");
		builder.append(billno);
		builder.append(", tvright=\n");
		builder.append(tvright);
		builder.append("]");
		return builder.toString();
	}

}
