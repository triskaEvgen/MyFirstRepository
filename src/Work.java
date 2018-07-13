import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Work {
	static Scanner in  = new Scanner(System.in);
	static Connection conn;
	public static void main(String[] args) throws SQLException {
		String bdUrl = "jdbc:mysql://localhost:3306/country3";
		String username = "root";
		String password = "wesfdboklq11";
		conn = DriverManager.getConnection(bdUrl, username, password);
		CreateTablePerson();
		CreateTableCity();
		CreateTableCountry();
		Communication();
		AddCountry();
		AddCity();
		AddHuman();
		SelectHumans();
		SelectHumanID(1);
		SelectCityID(1);
		SelectCountryID(1);
		SelectHumanCity();
		SelectCityCountry();
		SelectHumanCityCountry();
		conn.close();
	}
	public static void CreateTablePerson() throws SQLException { 
		String dropQuery = "drop table if exists human";
		String CreateQuery = "create table human("
				+ "id int primary key auto_increment,"
				+ "first_name varchar(15) not null , "
				+ "last_name varchar(15) not null,"
				+ "age int not null,"
				+ "hobby varchar(255) not null,"
				+ "city_id int not null,"
				+ "country_id int not null"
				+ ");"; 
		Statement stmt = conn.createStatement();
		stmt.execute(dropQuery);
		stmt.execute(CreateQuery);
		System.out.println("table parson create");
	}
	public static void CreateTableCity() throws SQLException { 
		String dropQuery = "drop table if exists city";
		String createQuery = "create table city("
				+ "id int primary key auto_increment,"
				+ "name varchar(15) not null unique,  "
				+ "country_id int not null"
				+ ");"; 
		Statement stmt = conn.createStatement();
		stmt.execute(dropQuery);
		stmt.execute(createQuery);
		System.out.println("table city create");
	}
	public static void CreateTableCountry() throws SQLException { 
		String dropQuery = "drop table if exists country";
		String createQuery = "create table country("
				+ "id int primary key auto_increment,"
				+ "name_country varchar(15) not null unique"
				+ ");"; 
		Statement stmt = conn.createStatement();
		stmt.execute(dropQuery);
		stmt.execute(createQuery);
		System.out.println("table country create");
	}
	public static void Communication() throws SQLException {
		String Query = "alter table city add foreign key (country_id) references country(id); ";
		String Query1 = "alter table human add foreign key (city_id) references city(id); ";
		String Query2 = "alter table human add foreign key (country_id) references country(id); ";
		Statement stmt = conn.createStatement();
		stmt.execute(Query);
		stmt.execute(Query1);
		stmt.execute(Query2);
	}
	public static void AddCountry() throws SQLException {
		String query = "insert into country(name_country) values (?)";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, "Ukraine");
		pstmt.executeUpdate();
		pstmt.close();
	}
	public static void AddCity() throws SQLException {
		String query = "insert into city(name , country_id) values (? , ?)";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, "Lviv");
		pstmt.setInt(2, 1);
		pstmt.executeUpdate();
		pstmt.close();
	}
	public static void AddHuman() throws SQLException {
		String query = "insert into human(first_name ,last_name ,age ,hobby ,city_id , country_id) values (? ,? ,? ,? ,? ,?)";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, "Ivan");
		pstmt.setString(2, "Lev");
		pstmt.setInt(3, 29);
		pstmt.setString(4, "chess" );
		pstmt.setInt(5, 1);
		pstmt.setInt(6, 1);
		
		pstmt.executeUpdate();
		pstmt.close();
	}
	public static void SelectHumans() throws SQLException {
		String query = "select * from human;";
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			System.out.println(rs.getString("first_name"));
		}
	}
	public static void SelectHumanID(int i) throws SQLException {
		String query = "Select * from human where id = ?;";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setInt(1 , i);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
				System.out.println("ID:" + rs.getInt("id") + "\t |"
						+ "first_name:" + rs.getString("first_name") + "\t |"
						+ "last_name:" + rs.getString("last_name") + "\t |"
						+ "age:" + rs.getInt("age") + "\t |"
						+ "hobby:" + rs.getString("hobby") + "\t |");					
		}
		
	}
	public static void SelectCityID(int i) throws SQLException {
		String query = "Select * from city where id = ?;";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, i);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			System.out.println("ID:" + rs.getInt("id") + "\t |"
					 + "name:" + rs.getString("name" ) + "\t |"
					 + "country_id:" + rs.getInt("country_id" ) + "\t |");
		}
		rs.close();
		pstmt.close();
	}
	public static void SelectCountryID(int i) throws SQLException {
		String query = "Select * from country where id = ?;";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, i);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			System.out.println("ID:" + rs.getInt("id") + "\t |"
					 + "name:" + rs.getString("name_country" ) + "\t |");
		}
		rs.close();
		pstmt.close();
	}
	public static void SelectHumanCity() throws SQLException {
		String query = "Select h.* , c.* from human h join city c on h.city_id = c.id;";
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
			
		while (rs.next()){
			System.out.println( "Name_City:" + rs.getString("name" ) + "\t |"
					 + "First_Name:" + rs.getString("first_name") + "\t |"
					 + "Last_name:" + rs.getString("last_name") + "\t |");
		}
		rs.close();
		pstmt.close();
	}
	public static void SelectCityCountry() throws SQLException {
		String query = "Select c.* , co.* from city c join country co on c.country_id = co.id;";
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
			
		while (rs.next()){
			System.out.println("Name_City:" + rs.getString("name" ) + "\t |"
					 + "Name_Country:" + rs.getString("name_country") + "\t |");
		}
		rs.close();
		pstmt.close();
	}
	public static void SelectHumanCityCountry() throws SQLException {
		String query = "Select h.* , co.*, c.* from human h join country co on h.country_id = co.id "
				+ "join city c on h.city_id = c.id;";
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
			
		while (rs.next()){
			System.out.println("Name:" + rs.getString("first_name") + "\t |"
					 + "Name_City:" + rs.getString("name" ) + "\t |"
					 + "Name_Country:" + rs.getString("name_country") + "\t |");
		}
		rs.close();
		pstmt.close();
	
	}
}

	
	
	
	
	
	