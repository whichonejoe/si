/**
 * 
 */
package com.sidc.dao.ra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sidc.blackcore.api.ra.response.RoomInfoEnity;
import com.sidc.dao.ra.response.DeviceCatalogue;
import com.sidc.dao.ra.response.DeviceEnity;
import com.sidc.dao.ra.response.Language;
import com.sidc.dao.ra.response.RoomRcuEnity;

/**
 * @author Nandin
 *
 */
public class RoomRcuDao {

	private RoomRcuDao() {
	}

	private static class LazyHolder {
		public static final RoomRcuDao INSTANCE = new RoomRcuDao();
	}

	public static RoomRcuDao getInstance() {
		return LazyHolder.INSTANCE;
	}

	private final static String LIST_ROOM_RCU = "select A.roomno, B.name roomType, E.name catalogue, F.name catalogue_name, D.device keycode, D1.name keycode_name, D1.lang"
			+ " from room_rcu A left join rcu_group B ON (A.rcu_group_id = B.id)"
			+ " left join rcu_group_device C ON (C.rcu_group_id = B.id) "
			+ " left join rcu_device D ON (C.rcu_device_id = D.id) "
			+ " left join rcu_device_lang D1 ON (D.id = D1.rcu_deviceid) "
			+ " left join rcu_device_group E ON (D.rcu_device_group_id = E.id)"
			+ " left join rcu_device_group_lang F ON (E.id = F.rcu_device_group_id and F.lang = D1.lang)"
			+ " order by A.roomno, E.name, D.device";

	public List<RoomRcuEnity> listRoomRcuDevice(final Connection conn) throws SQLException {

		List<RoomRcuEnity> list = new ArrayList<RoomRcuEnity>();

		PreparedStatement psmt = null;
		try {
			psmt = conn.prepareStatement(LIST_ROOM_RCU);
			ResultSet rs = psmt.executeQuery();

			String roomNo = null;
			String roomType = null;
			String catalogue = null;
			String catalogue_name = null;
			String keyCode = null;
			String keyCode_name = null;
			String lang = null;

			List<DeviceCatalogue> catas = new ArrayList<DeviceCatalogue>();
			List<Language> device_langs = new ArrayList<Language>();
			List<Language> catalogue_langs = new ArrayList<Language>();
			List<DeviceEnity> devices = new ArrayList<DeviceEnity>();

			while (rs.next()) {

				String temp_roomNo = rs.getString("roomno");
				String temp_roomType = rs.getString("roomType");
				String temp_catalogue = rs.getString("catalogue");
				String temp_catalogue_name = rs.getString("catalogue_name");
				String temp_keyCode = rs.getString("keycode");
				String temp_keyCode_name = rs.getString("keycode_name");
				String temp_lang = rs.getString("lang");

				if (roomNo == null && roomType == null) {
					roomNo = temp_roomNo;
					roomType = temp_roomType;
					catalogue = temp_catalogue;
					catalogue_name = temp_catalogue_name;
					keyCode = temp_keyCode;
					keyCode_name = temp_keyCode_name;
					lang = temp_lang;

				}

				if (temp_keyCode_name != null && !keyCode_name.equals(temp_keyCode_name)) {
					if (temp_lang != null && temp_keyCode_name != null) {
						device_langs.add(new Language(keyCode_name, temp_lang));
					}

					if (temp_keyCode_name != null) {
						keyCode_name = temp_keyCode_name;
					}
				}

				if (!keyCode.equals(temp_keyCode)) {
					devices.add(new DeviceEnity(keyCode, device_langs));
					device_langs = new ArrayList<Language>();
					keyCode = temp_keyCode;
				}

				if (temp_catalogue_name != null && !catalogue_name.equals(temp_catalogue_name)) {
					Language _temp = new Language(catalogue_name, lang);
					if (!catalogue_langs.contains(_temp)) {
						catalogue_langs.add(_temp);
					}

					lang = temp_lang;
					catalogue_name = temp_catalogue_name;
				}

				if (!catalogue.equals(temp_catalogue)) {
					catas.add(new DeviceCatalogue(catalogue, devices, catalogue_langs));
					devices = new ArrayList<DeviceEnity>();
					catalogue_langs = new ArrayList<Language>();
					catalogue = temp_catalogue;
				}

				if (!roomNo.equals(temp_roomNo)) {
					list.add(new RoomRcuEnity(roomNo, roomType, catas));
					catas = new ArrayList<DeviceCatalogue>();
					roomType = temp_roomType;
					roomNo = temp_roomNo;
				}

			}

			device_langs.add(new Language(keyCode_name));
			devices.add(new DeviceEnity(keyCode, device_langs));
			catalogue_langs.add(new Language(catalogue_name, lang));
			catas.add(new DeviceCatalogue(catalogue, devices, catalogue_langs));
			list.add(new RoomRcuEnity(roomNo, roomType, catas));

		} finally {
			if (psmt != null && !psmt.isClosed()) {
				psmt.close();
			}
		}

		return list;
	}

	private final static String LIST_RCU_MODULE_BY_LANG = "SELECT A.no,A.floor, C.name, (select count(bill_no) as total "
			+ " from bill where chko_time is null and chki_time < now() " + " and bill_no = A.bill_no) as ischeckin "
			+ " FROM room A LEFT JOIN room_rcu B ON (A.no =B.roomno) "
			+ " LEFT JOIN rcu_group C ON(B.rcu_group_id = C.id) " + " order by A.no * 1 ";

	public List<RoomInfoEnity> listRoomRcuInfo(final Connection conn) throws SQLException {

		List<RoomInfoEnity> list = new ArrayList<RoomInfoEnity>();

		PreparedStatement psmt = null;
		try {
			psmt = conn.prepareStatement(LIST_RCU_MODULE_BY_LANG);

			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				final boolean isCheckin = (rs.getInt("ischeckin") > 0) ? true : false;
				list.add(new RoomInfoEnity(rs.getString("no"), rs.getString("floor"), isCheckin, rs.getString("name")));
			}

			conn.commit();
		} finally {
			if (psmt != null && !psmt.isClosed()) {
				psmt.close();
			}
		}

		return list;
	}
}
