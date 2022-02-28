package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.User;
import util.Consts;

public class LoginControl {
	
	private User loginCustumer;
	
	public static LoginControl instance;
	public static LoginControl getInstance() {
		if (instance == null)
			instance = new LoginControl();
		return instance;
	}
	
	public User getLoginUser() {
		return loginCustumer;
	}

	public void setLoginUser(User loginCustumer) {
		this.loginCustumer = loginCustumer;
	}
	
	public ArrayList<User> getAllUsers() {
		ArrayList<User> myCutstumers = new ArrayList<>();
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_ALL_USERS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					myCutstumers.add(new User(rs.getString(i++), rs.getString(i++), rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return myCutstumers;	
		
	}
}
