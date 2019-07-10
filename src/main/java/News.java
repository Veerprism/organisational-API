import abstracts.DBconnect;
import interfaces.ApiDao;

import java.sql.*;
import java.util.HashMap;

public class News implements ApiDao {
    private String insert="INSERT INTO deptnews(departmentid,news,creation) VALUES (?,?,CURRENT_TIMESTAMP)";
    private static String selectDeptNews="SELECT departments.name,deptnews.departmentid,deptnews.news,deptnews.creation FROM deptnews JOIN departments ON deptnews.departmentid=departments.id WHERE departments.id=?";
    private static String selectGenNews="SELECT * FROM companynews";
    private static String updateDeptNews="UPDATE departments SET totalnews = totalnews + 1 WHERE id=?";


    private static String selectAllDeptNews="SELECT departments.name,deptnews.departmentid,deptnews.news,deptnews.creation FROM deptnews JOIN departments ON deptnews.departmentid=departments.id";

    public static HashMap<Integer,HashMap<String,Object>> selectNews=new HashMap<>();
    public static HashMap<Integer,HashMap<String,Object>> selectGen=new HashMap<>();



    private String update="UPDATE departments WHERE id=?";

    public int getDeptid() {
        return deptid;
    }

    public String getDeptnews() {
        return deptnews;
    }




    private int deptid;
    private String deptnews;

    News(int deptid,String deptnews){
        this.deptid=deptid;
        this.deptnews=deptnews;
        this.insert();
    }


    public void insert(){
        try{
//            conn = DriverManager.getConnection(url, user, password);
            Connection conn= DBconnect.getConn();
            PreparedStatement stmt =conn.prepareStatement(insert);
            PreparedStatement stm =conn.prepareStatement(updateDeptNews);
            stm.setInt(1,this.deptid);
            stmt.setInt(1,this.deptid);
            stmt.setString(2,this.deptnews);

            stmt.executeUpdate();
            stm.executeUpdate();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    };

    public static Object selectWithId(int theID){
        HashMap<Integer,HashMap<String,Object>> finn=new HashMap<>();
        try {
            Connection conn=DBconnect.getConn();
            PreparedStatement stmt=conn.prepareStatement(selectDeptNews);
            stmt.setInt(1,theID);
            ResultSet rs=stmt.executeQuery();
            ResultSetMetaData metaData=rs.getMetaData();
            int count=1;
            while(rs.next()){
                HashMap<String,Object> one=new HashMap<>();
                String dept_name=rs.getString("name");
                int dept_id=rs.getInt("departmentid");
                String dept_news=rs.getString("news");
                java.sql.Date creation=rs.getDate("creation");

                one.put("dept_name",dept_name);one.put("dept_id",dept_id);one.put("dept_news",dept_news);
                one.put("creation",creation);
                finn.put(count,one);
                count++;
            }


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return finn;
    };


    public void update(){};

    public void remove(int i){};

    public void  selectAll(){

    };
    public static void  selectAllDept(){

        try {
            Connection conn=DBconnect.getConn();
            PreparedStatement stmt=conn.prepareStatement(selectAllDeptNews);

            ResultSet rs=stmt.executeQuery();
            ResultSetMetaData metaData=rs.getMetaData();
            int count =1;
            while(rs.next()){
                HashMap<String,Object> one=new HashMap<>();
                String dept_name=rs.getString("name");
                int dept_id=rs.getInt("departmentid");
                String dept_news=rs.getString("news");
                java.sql.Date creation=rs.getDate("creation");

                one.put("dept_name",dept_name);one.put("dept_id",dept_id);one.put("dept_news",dept_news);
                one.put("creation",creation);
                selectNews.put(count,one);
                count++;

            }


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    };
    public static  void selectGeneralNews(){


        try {
            Connection conn=DBconnect.getConn();
            PreparedStatement stmt=conn.prepareStatement(selectGenNews);

            ResultSet rs=stmt.executeQuery();
            ResultSetMetaData metaData=rs.getMetaData();
            int count =1;
            while(rs.next()){
                HashMap<String,Object> one=new HashMap<>();
                int id=rs.getInt("newsid");
                String actual_news=rs.getString("actualnews");

                java.sql.Date creation=rs.getDate("creation");

                one.put("id",id);one.put("actual_news",actual_news);
                one.put("creation",creation);
                selectGen.put(count,one);

            }


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }


}
