package control;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import util.Consts;

public class ControlReport {
	
	public static ControlReport instance;
	
	public static ControlReport getInstance() {
		if (instance == null)
			instance = new ControlReport();
		return instance;
	}
	
	public JFrame produceBigFlightReport(Integer seatNum, Date start, Date end) 
	{
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR))
			{
				HashMap<String, Object> params = new HashMap<>();

				params.put("tourist-seats", seatNum);
				params.put("from", start);
				params.put("to", end);
				JasperPrint print = JasperFillManager.fillReport(
						getClass().getResourceAsStream("/boundary/BigFlightRepo.jasper"),
						params, conn);
				JFrame frame = new JFrame("Big Flight Report");
				frame.getContentPane().add(new JRViewer(print));
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.pack();
				return frame;
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	public JFrame produceDestCountryReport(String country) {
		 try {
	            Class.forName(Consts.JDBC_STR);
	            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR)) {
	            	HashMap<String, Object> params = new HashMap<>();
	            	params.put("1", country);
	            	JasperPrint print = JasperFillManager.fillReport(
	            			getClass().getResourceAsStream("/boundary/TargetReport.jasper"),
	                        params, conn);
	                JFrame frame = new JFrame("Product " + country + "'s Orders By Country");
	                frame.getContentPane().add(new JRViewer(print));
	                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	                frame.pack();
	                return frame;
	            } catch (SQLException | JRException | NullPointerException e) {
	                e.printStackTrace();
	            }
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        
	        return null;
	}

	public List<String> getAllDestCountries() {
		List<String> destCountriesList = new ArrayList<String>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_DEST_COUNTRIES);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					destCountriesList.add(rs.getString(i++));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return destCountriesList;	
	}

}
