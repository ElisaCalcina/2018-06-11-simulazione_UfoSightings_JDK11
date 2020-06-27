package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.ufo.model.Anni;
import it.polito.tdp.ufo.model.Arco;
import it.polito.tdp.ufo.model.Sighting;

public class SightingsDAO {
	
	public List<Sighting> getSightings() {
		String sql = "SELECT * FROM sighting" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Sighting> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(new Sighting(res.getInt("id"),
						res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), 
						res.getString("state"), 
						res.getString("country"),
						res.getString("shape"),
						res.getInt("duration"),
						res.getString("duration_hm"),
						res.getString("comments"),
						res.getDate("date_posted").toLocalDate(),
						res.getDouble("latitude"), 
						res.getDouble("longitude"))) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Anni> getAnni(){
		String sql="SELECT DISTINCT YEAR(DATETIME) AS d, COUNT(*) AS avvistamenti " + 
				"FROM sighting " + 
				"WHERE country = 'US' " + 
				"GROUP BY d " +
				"ORDER BY d asc ";
		List<Anni> result= new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Anni a = new Anni(Year.of(res.getInt("d")), res.getInt("avvistamenti"));
				result.add(a);
			}
		
			conn.close();
			return result;
		}catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	//vertici--> stati con almeno un avvistamento
	public List<String> getVertici(Year anno){
		String sql="SELECT distinct state as s, COUNT(*) AS avv " + 
				"FROM sighting " + 
				"WHERE country ='US'  AND YEAR(DATETIME)=? " + 
				"GROUP BY state " + 
				"HAVING avv>0 ";
		
		List<String> result= new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno.getValue());
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(res.getString("s"));
			}
			conn.close();
			return result;
	}catch (SQLException e) {
		e.printStackTrace();
		return null ;
	}
	}	
	
	public List<Arco> getArchi(Year anno){
		String sql="SELECT s1.state as st1, s2.state as st2, COUNT(*) as avvistamento " + 
				"FROM sighting AS s1, sighting AS s2 " + 
				"WHERE s1.country ='US' " + 
				"		AND s2.country='US' " + 
				" 		AND YEAR(s1.DATETIME)=? " + 
				" 		AND YEAR(s2.DATETIME)= ? " + 
				"		AND s2.datetime>s1.datetime " + 
				"		AND s1.state<>s2.state " +
				"GROUP BY st1, st2 ";
		
		List<Arco> result= new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno.getValue());
			st.setInt(2, anno.getValue());

			ResultSet res = st.executeQuery();
			while(res.next()) {
				result.add(new Arco(res.getString("st1"), res.getString("st2")));
			}
			conn.close();
			return result;
		
	}catch (SQLException e) {
		e.printStackTrace();
		return null ;
	}
	}
}
