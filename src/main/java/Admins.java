import abstracts.DBconnect;
import interfaces.ApiDao;

import java.sql.*;
import java.util.HashMap;

public class Admins {





    private String username;
    private String password;
    public static HashMap<Integer,HashMap<String,Object>> selectAdmins=new HashMap<>();
    public static HashMap<Integer,HashMap<String,Object>>  showCompany=new HashMap<>();
    Admins(String username,String password){
        this.username=username;
        this.password=password;
    }
  public void addDepartment(String dept_name,String dept_description){
      String insertDept="INSERT INTO departments(name,description,totalemployees,totalnews,creation) VALUES (?,?,?,?,CURRENT_TIMESTAMP)";
      try{

          Connection conn= DBconnect.getConn();
          PreparedStatement stmt =conn.prepareStatement(insertDept);
          stmt.setString(1,dept_name);
          stmt.setString(2,dept_description);
          stmt.setInt(3,0);
          stmt.setInt(4,0);
          stmt.executeUpdate();

      }catch (SQLException e){
          System.out.println(e.getMessage());
      }
  }
    public void delDepartment(int theID){
        String deleteDept="DELETE FROM departments WHERE id=?";
      try{

            Connection conn= DBconnect.getConn();
            PreparedStatement stmt =conn.prepareStatement(deleteDept);
            stmt.setInt(1,theID);
            stmt.executeUpdate();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
   public static void addEmployeee(String emp_name,String position,int dept_id){
       String insertEmp="INSERT INTO employees(name,position,departmentid,creation) VALUES (?,?,?,CURRENT_TIMESTAMP)";
       try{
           Connection conn= DBconnect.getConn();
           PreparedStatement stmt=conn.prepareStatement(insertEmp);
           stmt.setString(1,emp_name);
           stmt.setString(2,position);
           stmt.setInt(3,dept_id);
           stmt.executeUpdate();

       }catch (SQLException e){
           System.out.println(e.getMessage());
       }

    }
    public void delEmployeee(int theID){
        String deleteEmp="DELETE FROM employees WHERE id=?";
        try{

            Connection conn= DBconnect.getConn();
            PreparedStatement stmt =conn.prepareStatement(deleteEmp);
            stmt.setInt(1,theID);
            stmt.executeUpdate();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public static void selectAdmins(){
        String selectAll="SELECT * FROM admins";

        try{
            Connection conn= DBconnect.getConn();
            PreparedStatement stmt=conn.prepareStatement(selectAll);
            ResultSet rs=stmt.executeQuery();
            ResultSetMetaData metaData=rs.getMetaData();
            int count=1;
            while(rs.next()){
                HashMap<String,Object> finn=new HashMap<>();
                int id=rs.getInt("id");
                String username=rs.getString("username");
                java.sql.Date creation=rs.getDate("creation");
                finn.put("id",id);finn.put("username",username);finn.put("creation",creation);

                selectAdmins.put(count,finn);
                count++;
            }


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
    public static Object selectWithIdEmp(int theID){
        String selectOne="SELECT * FROM employees WHERE id=?";
        HashMap<String,Object> one=new HashMap<>();
        try {
            Connection conn=DBconnect.getConn();
            PreparedStatement stmt=conn.prepareStatement(selectOne);
            stmt.setInt(1,theID);
            ResultSet rs=stmt.executeQuery();
            ResultSetMetaData metaData=rs.getMetaData();

            while(rs.next()){
                int id=rs.getInt("id");
                String name=rs.getString("name");
                String position=rs.getString("position");
                String desc=rs.getString("description");
                java.sql.Date creation=rs.getDate("creation");

                one.put("id",id);one.put("emp_name",name);one.put("position",position);
                one.put("desc",desc);one.put("creation",creation);
            }


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return one;
    };
    public static Object selectWithId(int theID){
        String selectOne="SELECT * FROM departments WHERE id=?";
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
    public static void removeDept(int theID){
        String delete="DELETE FROM departments WHERE id=?";
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
    public static void removeEmp(int theID){
        String deleteOne="DELETE FROM employees WHERE id=?";
        try{
            Connection conn= DBconnect.getConn();
            PreparedStatement stmt=conn.prepareStatement(deleteOne);
            stmt.setInt(1,theID);
            stmt.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public static void companyDetails(){
         String selectAll="SELECT * FROM thecompany";
        try{
            Connection conn= DBconnect.getConn();
            PreparedStatement stmt=conn.prepareStatement(selectAll);


            ResultSet rs=stmt.executeQuery();
            ResultSetMetaData metaData=rs.getMetaData();
            int count=1;
            while(rs.next()){
                HashMap<String,Object> finn=new HashMap<>();

                int id=rs.getInt("id");
                String name=rs.getString("name");
                String desc=rs.getString("description");
                int total_dept=rs.getInt("totaldepartments");
                int total_emp=rs.getInt("totalemployees");
                java.sql.Date creation=rs.getDate("creation");

                finn.put("id",id);finn.put("companyname",name);finn.put("desc",desc);
                finn.put("total_dept",total_dept);finn.put("total_emp",total_emp);finn.put("creation",creation);
                showCompany.put(count,finn);
                count++;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
