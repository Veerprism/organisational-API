import abstracts.DBconnect;
import interfaces.ApiDao;
import org.joda.time.DateTime;

import java.sql.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

public class Departments implements ApiDao {
private String dept_name;
private String dept_description;
private java.sql.Date creation;
private String insert="INSERT INTO departments(name,description,totalemployees,totalnews,creation) VALUES (?,?,?,?,CURRENT_TIMESTAMP)";
private static String updateCompanyDept="UPDATE thecompany SET totaldepartments = totaldepartments + 1";
private String update="UPDATE departments WHERE id=?";
private String delete="DELETE FROM departments WHERE id=?";
private static String selectAll="SELECT * FROM departments";
private static String selectOne="SELECT * FROM departments WHERE id=?";
private static String specifics="SELECT a.name, b.name, c.news FROM departments a, employees b , deptnews c WHERE id=?";
public static HashMap<Integer,HashMap<String,Object>> selectDepartments=new HashMap<>();

Departments(String dept_name,String dept_description){
    this.dept_name=dept_name;
    this.dept_description=dept_description;

    this.insert();

}

    public void insert(){
        try{
//            conn = DriverManager.getConnection(url, user, password);
            Connection conn=DBconnect.getConn();
            PreparedStatement stmt =conn.prepareStatement(insert);
            PreparedStatement stm =conn.prepareStatement(updateCompanyDept);
            stmt.setString(1,this.dept_name);
            stmt.setString(2,this.dept_description);
            stmt.setInt(3,0);
            stmt.setInt(4,0);
            stmt.executeUpdate();
            stm.executeUpdate();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    };

    public void update(){

    };

    public void remove(int theID){
        try{
//            conn = DriverManager.getConnection(url, user, password);
            Connection conn=DBconnect.getConn();
            PreparedStatement stmt =conn.prepareStatement(delete);
            stmt.setInt(1,theID);
            stmt.executeUpdate();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    };
    public void selectAll(){

    }
    public static void selectAllDept(){
        try {
            Connection conn=DBconnect.getConn();
            PreparedStatement stmt=conn.prepareStatement(selectAll);
            ResultSet rs=stmt.executeQuery();
            ResultSetMetaData metaData=rs.getMetaData();
            int count=1;
            while(rs.next()){
                HashMap<String,Object> finn=new HashMap<>();
                int id=rs.getInt("id");
                String dept_name=rs.getString("name");
                String desc=rs.getString("description");
                int emp_total=rs.getInt("totalemployees");
                int news_total=rs.getInt("totalnews");
                java.sql.Date creation=rs.getDate("creation");

                finn.put("id",id);finn.put("dept_name",dept_name);finn.put("desc",desc);
                finn.put("emp_total",emp_total);finn.put("news_total",news_total);finn.put("creation",creation);
                selectDepartments.put(count,finn);
                count++;
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static Object selectWithId(int theID){
        HashMap<String,Object> one=new HashMap<>();
        try {
            Connection conn=DBconnect.getConn();
            PreparedStatement stmt=conn.prepareStatement(selectOne);
            stmt.setInt(1,theID);
            ResultSet rs=stmt.executeQuery();
            ResultSetMetaData metaData=rs.getMetaData();

            while(rs.next()){
                int id=rs.getInt("id");
                String dept_name=rs.getString("name");
                String desc=rs.getString("description");
                int emp_total=rs.getInt("totalemployees");
                int news_total=rs.getInt("totalnews");
                java.sql.Date creation=rs.getDate("creation");

                one.put("id",id);one.put("dept_name",dept_name);one.put("desc",desc);
                one.put("emp_total",emp_total);one.put("news_total",news_total);one.put("creation",creation);
            }


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    return one;
    };
    public static Object getSpecifics(int theID){
        HashMap<String,Object> one=new HashMap<>();
        try {
            Connection conn=DBconnect.getConn();
            PreparedStatement stmt=conn.prepareStatement(specifics);
            stmt.setInt(1,theID);
            ResultSet rs=stmt.executeQuery();
            ResultSetMetaData metaData=rs.getMetaData();

            while(rs.next()){
                String name=rs.getString("name");
                String name1=rs.getString("name");
                String news=rs.getString("news");

                one.put("name",name);one.put("name1",name1);one.put("news",news);

            }


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return one;

    }

public String getDept_name(){
    return dept_name;

}
    public String getDept_description(){
        return dept_description;

    }

}
