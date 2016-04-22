package me.shizh.db.derby;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DerbyDao {

	public static List<String[]> query(String sql, String... args) {
		
		List<String[]> list = new ArrayList<>();
		try {
			PreparedStatement ps = Derby.getConn().prepareStatement("select * "+sql);
			for (int i = 0; i < args.length; i++) {
				ps.setString(i+1, args[i]);
			}
			ResultSet rs = ps.executeQuery();
			int cc = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String[] rec = new String[cc];
				for (int i = 1; i <= cc; i++) {
					rec[i - 1] = rs.getString(i);
				}
				list.add(rec);
			}
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return list;
	}

	public static void createTable(String createSql) {
		try {
			Derby.getConn().createStatement().executeUpdate(createSql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * String insertSQL = "insert into Employee values (?,?)";
	 * 
	 * @param insertSql
	 * @param args
	 */
	public static void saft_insert(String insertSql, String... args) {
		try {
			PreparedStatement psInsert = Derby.getConn().prepareStatement(
					insertSql);
			for (int i = 1; i <= args.length; i++) {
				psInsert.setString(i, args[i-1]);
			}
			psInsert.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public static void insert(String insertSql) {
		try {
			Derby.getConn().createStatement().execute(insertSql);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
//		 createTable("create table firsttable(id varchar(20) primary key,name varchar(20))");
//		 createTable("create table word_dict(	word varchar(100) primary key,	lang varchar(20), 	trans_result varchar(500),	dict_simple varchar(500),	dict_detail varchar(1000),	dict_pinyin varchar(500),	baike_link varchar(1000),	baike_image varchar(1000),	baike_content varchar(3000))");
//		System.exit(0);
		// insert("insert into firsttable values('id1', 'name1')");
	}
}