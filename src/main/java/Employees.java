import abstracts.DBconnect;
import interfaces.ApiDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Employees implements ApiDao {
    private String emp_name;
    private String position;
    private java.sql.Date creation;
    private int dept_id;
    private static String insert="INSERT INTO employees(empname,position,departmentid,creation) VALUES (?,?,?,CURRENT_TIMESTAMP)";
    private static String selectAll="SELECT employees.id,employees.empname,employees.position,employees.creation,employees.departmentid,departments.name FROM employees JOIN departments ON departments.id=employees.departmentid";
    public static String selectOne="SELECT * FROM employees WHERE id=?";
    private static String deleteOne="DELETE FROM employees WHERE id=?";
    private static String updateDeptEmp="UPDATE departments SET totalemployees = totalemployees + 1 WHERE id=?";
    private static String updateCompanyEmp="UPDATE thecompany SET totalemployees = totalemployees + 1";
    public static HashMap<Integer,HashMap<String,Object>> selectEmployees=new HashMap<>();
//    public ArrayList<Object> roles=new ArrayList<>();

Employees(String emp_name,String position,int dept_id){
this.emp_name=emp_name;
this.position=position;
this.dept_id=dept_id;
this.insert();
}
    public void insert(){
try{
    Connection conn= DBconnect.getConn();
    PreparedStatement stmt=conn.prepareStatement(insert);
    PreparedStatement stm =conn.prepareStatement(updateDeptEmp);
    PreparedStatement stms=conn.prepareStatement(updateCompanyEmp);
    stm.setInt(1,this.dept_id);
    stmt.setString(1,this.emp_name);
    stmt.setString(2,this.position);
    stmt.setInt(3,this.dept_id);
    stmt.executeUpdate();
    stm.executeUpdate();
    stms.executeUpdate();

}catch (SQLException e){
    System.out.println(e.getMessage());
}

    };

    public void update(){

    };

    public void remove(int theID){
        try{
            Connection conn= DBconnect.getConn();
            PreparedStatement stmt=conn.prepareStatement(deleteOne);
            stmt.setInt(1,theID);
            stmt.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    };
    public void selectAll(){

    }

    public static void selectAllEmp(){
        try{
            Connection conn= DBconnect.getConn();
            PreparedStatement stmt=conn.prepareStatement(selectAll);
            ResultSet rs=stmt.executeQuery();
            ResultSetMetaData metaData=rs.getMetaData();
            int count=1;
            while(rs.next()){
                HashMap<String,Object> finn=new HashMap<>();

                int id=rs.getInt("id");
                String name=rs.getString("empname");
                String position=rs.getString("position");
                String dept_name=rs.getString("name");
                int dept_id=rs.getInt("departmentid");
                java.sql.Date creation=rs.getDate("creation");

                finn.put("id",id);finn.put("emp_name",name);finn.put("position",position);
                finn.put("department_id",dept_id);finn.put("dept_name",dept_name);finn.put("creation",creation);
                selectEmployees.put(count,finn);
                count++;
            }


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    };

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
                String name=rs.getString("name");
                String position=rs.getString("position");
                int dept=rs.getInt("departmentid");
                java.sql.Date creation=rs.getDate("creation");

                one.put("id",id);one.put("emp_name",name);one.put("position",position);
                one.put("desc",dept);one.put("creation",creation);
            }


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return one;
    };

    public int getDept_id() {
        return dept_id;
    }

    public String getPosition() {
        return position;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public Date getCreation() {
        return creation;
    }

    //insert roles to an employee
    public static void addRoles(int theID,String...theroles){
        String insertRoles="INSERT INTO roles(employeeid,roles) values(?,?)";
        try{
            Connection conn= DBconnect.getConn();
            PreparedStatement stmt=conn.prepareStatement(insertRoles);

            for(int i=0;i<theroles.length;i++) {
                stmt.setInt(1, theID);
                stmt.setString(2, theroles[i]);
            }

            stmt.executeUpdate();



        }catch (SQLException e){
            System.out.println(e.getMessage());
        }


    }

}
