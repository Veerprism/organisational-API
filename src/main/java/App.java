
import interfaces.ApiDao;
import com.google.gson.*;
import static spark.Spark.*;
import static spark.Spark.post;
import static spark.Spark.*;
import static spark.Spark.staticFileLocation;


import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static final String url = "jdbc:postgresql://ec2-54-235-92-43.compute-1.amazonaws.com:5432/dem50d28o3djq7";
    public static final String user = "rzxyycrxguyjow";
    public static  final String password = "4532f6e7d77714d198645f21833bca0aed91afeb610505dacef4c793a2a04959";
    public static void main(String args[]){

        port(getHerokuAssignedPort());
        staticFileLocation("/public");
        Gson gson = new Gson();

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "welcome.hbs");
        }, new HandlebarsTemplateEngine());


        get("/newemployee", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Departments.selectAllDept();
            model.put("dept",Departments.selectDepartments);
            System.out.println(Departments.selectDepartments);
            return new ModelAndView(model, "newemployee.hbs");
        }, new HandlebarsTemplateEngine());

        post("/departments/new", "application/json", (req, res) -> {
           Departments dept = gson.fromJson(req.body(), Departments.class);
           System.out.println(Departments.class.getConstructors());
           System.out.println(dept);
           Departments.selectAllDept();
           System.out.println(Departments.selectDepartments);
            System.out.println(dept.getDept_name());
            System.out.println(dept.getDept_description());

            res.status(201);
            res.type("application/json");
            return gson.toJson(dept);
        });
        post("/employee/new", "application/json", (req, res) -> {
            String thename=req.queryParams("thename");
            String position=req.queryParams("position");
            String ty=req.queryParams("department");
            System.out.println(thename+" "+position+" "+ty);
            int deptid=Integer.parseInt(req.queryParams("department"));
            Employees employees=new Employees(thename,position,deptid);
            System.out.println(Employees.class.getConstructors());
            System.out.println(employees.getEmp_name()+":"+employees.getPosition()+":"+employees.getDept_id()+":"+employees.getCreation());

            res.status(201);
            res.type("application/json");
            return gson.toJson(employees);
        });
        post("/employees/new", "application/json", (req, res) -> {

            int deptid=Integer.parseInt(req.queryParams("department"));
            Employees employees=gson.fromJson(req.body(), Employees.class);
            System.out.println(Employees.class.getConstructors());
            System.out.println(employees.getEmp_name()+":"+employees.getPosition()+":"+employees.getDept_id()+":"+employees.getCreation());

            res.status(201);
            res.type("application/json");
            return gson.toJson(employees);
        });
        //View departments using JSON format
        get("/departments", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            Departments.selectAllDept();
            return gson.toJson(Departments.selectDepartments);//send it back to be displayed
        });
        //News posting the JSON way
        post("/news/new", "application/json", (req, res) -> {
            News news=gson.fromJson(req.body(), News.class);
            System.out.println(News.class.getConstructors());
            res.status(201);
            res.type("application/json");
            return gson.toJson(news);
        });

        //News Normal
        get("/postnews", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Departments.selectAllDept();
            model.put("dept",Departments.selectDepartments);
            System.out.println(Departments.selectDepartments);
            return new ModelAndView(model, "news.hbs");
        }, new HandlebarsTemplateEngine());
        //News JSON version
        get("/thenews", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            News.selectAllDept();
            System.out.println(News.selectNews);
            return gson.toJson(News.selectNews);
        });
        //View news normally
        get("/viewnews", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            News.selectAllDept();
            model.put("news",News.selectNews);
            System.out.println(Departments.selectDepartments);
            return new ModelAndView(model, "shownews.hbs");
        }, new HandlebarsTemplateEngine());
        post("/deptnews/new",  (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String thenews=req.queryParams("news");
            int deptid=Integer.parseInt(req.queryParams("department"));
            News news=new News(deptid,thenews);
            System.out.println(News.class.getConstructors());

            return new ModelAndView(model, "news.hbs");
        }, new HandlebarsTemplateEngine());
        //View employees in JSON format
        get("/employees", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            Employees.selectAllEmp();
            System.out.println(Employees.selectEmployees);
            return gson.toJson(Employees.selectEmployees);//send it back to be displayed
        });
        //View employeesin table form
        get("/viewemployees", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Employees.selectAllEmp();
            model.put("emps",Employees.selectEmployees);
            return new ModelAndView(model, "showemp.hbs");
        }, new HandlebarsTemplateEngine());
        get("/specificnews/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int id=Integer.parseInt(req.params("id"));
//            int id=Integer.parseInt(req.params("theId"));
            res.type("application/json");
            System.out.println(News.selectWithId(id));
            return gson.toJson(News.selectWithId(id));//send it back to be displayed
        });
        //Enter new Department normally via form
        get("/newdept", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Departments.selectAllDept();
            model.put("dept",Departments.selectDepartments);
            return new ModelAndView(model, "depts.hbs");
        }, new HandlebarsTemplateEngine());
        post("/deptnew", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String deptname=req.queryParams("deptname");
            String deptdesc=req.queryParams("deptdesc");
            Departments departments=new Departments(deptname,deptdesc);
            return new ModelAndView(model, "depts.hbs");
        }, new HandlebarsTemplateEngine());
        //View departments normally
        get("/viewdepts", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Departments.selectAllDept();
            model.put("dept",Departments.selectDepartments);
            return new ModelAndView(model, "showdept.hbs");
        }, new HandlebarsTemplateEngine());

        //News for company
        get("/newcompanynews", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            return new ModelAndView(model, "companynews.hbs");
        }, new HandlebarsTemplateEngine());

        //Post normally
        post("/newcompanynews", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String news=req.queryParams("news");
            Companynews companynews=new Companynews(news);
            return new ModelAndView(model, "companynews.hbs");
        }, new HandlebarsTemplateEngine());
        //Post via JSON
        post("/news/company", "application/json", (req, res) -> {
            Companynews news=gson.fromJson(req.body(), Companynews.class);
            System.out.println(Companynews.class.getConstructors());
            System.out.println(news.getCompnews());
            res.status(201);
            res.type("application/json");
            return gson.toJson(news);
        });
        //Display in JSON format
        get("/viewcompanyjson", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            News.selectGeneralNews();
            return gson.toJson(News.selectGen);//send it back to be displayed
        });
        //Display news for company in human-readable
        get("/showcompanynews", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            News.selectGeneralNews();
            model.put("news",News.selectGen);
            return new ModelAndView(model, "showcompnews.hbs");
        }, new HandlebarsTemplateEngine());
        get("/companydetails", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Admins.companyDetails();
            System.out.println(Admins.showCompany);
            model.put("company",Admins.showCompany);
            return new ModelAndView(model, "companydetails.hbs");
        }, new HandlebarsTemplateEngine());
    }

}
