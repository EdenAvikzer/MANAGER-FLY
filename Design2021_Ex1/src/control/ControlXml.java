package control;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import util.Consts;

public class ControlXml {
	
public static ControlXml instance;
	
	public static ControlXml getInstance() {
		if (instance == null)
			instance = new ControlXml();
		return instance;
	}

	public HashMap<String, String> importStatusFromXML() {
		HashMap<String, String> updatedStatus = new HashMap<>();
		try {
			Document doc = DocumentBuilderFactory.newInstance()
								.newDocumentBuilder().parse(new File("resources/flights.xml"));
			doc.getDocumentElement().normalize();
			NodeList nl = doc.getElementsByTagName("flight");
			for (int i = 0; i < nl.getLength(); i++) {
				if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element el = (Element) nl.item(i);
					String flightID = el.getElementsByTagName("flightNum").item(0).getTextContent();
					String status = el.getElementsByTagName("status").item(0).getTextContent();
					if(!isFlightExist(flightID)) {
						System.out.println("ERROR");
					}
					else {
						updatedStatus.put(flightID, status);
						updateStatus(flightID, status);
					}
				}
			}
			
			Alert alert = new Alert(AlertType.INFORMATION, "Data imported to data bases");
			alert.setHeaderText("Success");
			alert.setTitle("imported");
			alert.showAndWait();
			
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		return updatedStatus;
		
	}

	private boolean updateStatus(String flightID, String status) {
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_STATUS)) {
				int i = 1;
				stmt.setString(i++, status); // can't be null
				stmt.setString(i++, flightID); // can't be null
				stmt.executeUpdate();
				return true;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;	
	}

	private boolean isFlightExist(String flightID) {
		String flightNum = "";
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement callst = conn.prepareCall(Consts.SQL_FLIGHT_EXIST))
					{
					int k=1;
					callst.setString(k++, flightID);
					
					ResultSet rs = callst.executeQuery();
					while (rs.next()) 
						flightNum = rs.getString(1);
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if(flightNum.isEmpty())
			return false;
		return true;
	}
	
	
}
