package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {

		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));

				result.add(artObj);
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Exhibition> listExhibitions() {

		String sql = "SELECT * from exhibitions";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Exhibition exObj = new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"), res.getString("exhibition_title"), 
						res.getInt("begin"), res.getInt("end"));

				result.add(exObj);
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * IDMAP DI ARTISTI
	public Map<Integer, Artist> listArtist() {

		String sql = "SELECT * FROM artists";
		Map<Integer, Artist> result = new HashMap<Integer, Artist>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Artist a = new Artist(res.getInt("artist_id"), res.getString("name"));

				if(!result.containsKey(a.getArtist_id()))
					result.put(a.getArtist_id(), a);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	 */

	public List<String> listRole() {

		String sql = "SELECT DISTINCT a.role " + 
				"FROM authorship AS a " + 
				"ORDER BY a.role ASC";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				result.add(res.getString("a.role"));

			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * in questo caso, trovo solo i vertici
	 */
	public List<Integer> listArtistByRole(String role) {

		String sql = "SELECT * from authorship WHERE role = ?";
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, role);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				result.add(res.getInt("artist_id"));

			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


	/*
	 * In questo invece trovo archi e vertici INSIEME
	 */
	public List<Adiacenza> getAdiacenze(String ruolo) {

		String sql = "SELECT au1.artist_id AS id1, au2.artist_id AS id2, COUNT(DISTINCT eo1.exhibition_id) AS peso " + 
				"FROM authorship AS au1, authorship AS au2, exhibition_objects AS eo1, exhibition_objects AS eo2, artists AS a1, artists AS a2 " + 
				"WHERE au1.artist_id < au2.artist_id AND au1.object_id = eo1.object_id " + 
				"AND au2.object_id = eo2.object_id AND au1.object_id <> au2.object_id AND eo1.exhibition_id = eo2.exhibition_id and au1.role = ? AND au1.role = au2.role " + 
				"AND a1.artist_id = au1.artist_id AND a2.artist_id = au2.artist_id " + 
				"GROUP BY au1.artist_id, au2.artist_id";

		List<Adiacenza> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, ruolo);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Adiacenza a = new Adiacenza(res.getInt("id1"), res.getInt("id2"), res.getInt("peso"));
				result.add(a);
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
