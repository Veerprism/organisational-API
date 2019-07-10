import abstracts.DBconnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Companynews {
    private String companynews;
    private static String insertGenNews="INSERT INTO companynews(actualnews,creation) VALUES (?,CURRENT_TIMESTAMP)";
    private static String updateCompany="UPDATE thecompany SET totalnews = totalnews + 1";

    public String getCompnews() {
        return companynews;
    }



    Companynews(String companynews){

        this.companynews=companynews;
        this.insertComp();
    }
    public void insertComp(){
        try{
//            conn = DriverManager.getConnection(url, user, password);
            Connection conn= DBconnect.getConn();
            PreparedStatement stmt =conn.prepareStatement(insertGenNews);
            PreparedStatement stm =conn.prepareStatement(updateCompany);
            stmt.setString(1,this.companynews);
            stmt.executeUpdate();
            stm.executeUpdate();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    };

}
