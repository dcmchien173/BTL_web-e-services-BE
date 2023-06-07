
package Project.QuizPractice.src.java.model;


public class Student {
    private String Student_id;
    private String Student_name;
    private String Student_password;
    private String Student_email;
    private String Student_avatar;
    private int Student_role_id;
    private String Student_about_me;
    private String Student_connect;  
    

    
    public Student() {
    }

    public Student(String Student_id, String Student_username, String Student_password, String Student_email, String Student_avatar, int Student_role_id, String Student_about_me, String Student_connect) {
        this.Student_id = Student_id;
        this.Student_name = Student_name;
        this.Student_password = Student_password;
        this.Student_email = Student_email;
        this.Student_avatar = Student_avatar;
        this.Student_role_id = Student_role_id;
        this.Student_about_me = Student_about_me;
        this.Student_connect = Student_connect;
    }

    public String getStudent_id() {
        return Student_id;
    }

    public void setStudent_id(String Student_id) {
        this.Student_id = Student_id;
    }

    public String getStudent_name() {
        return Student_name;
    }
    

    public void setStudent_name(String Student_name) {
        this.Student_name = Student_name;
    }

    public String getStudent_password() {
        return Student_password;
    }

    public void setStudent_password(String Student_password) {
        this.Student_password = Student_password;
    }

    public String getStudent_email() {
        return Student_email;
    }

    public void setStudent_email(String Student_email) {
        this.Student_email = Student_email;
    }

    public String getStudent_avatar() {
        return Student_avatar;
    }

    public void setStudent_avatar(String Student_avatar) {
        this.Student_avatar = Student_avatar;
    }

    public int getStudent_role_id() {
        return Student_role_id;
    }

    public void setStudent_role_id(int Student_role_id) {
        this.Student_role_id = Student_role_id;
    }

    public String getStudent_about_me() {
        return Student_about_me;
    }

    public void setStudent_about_me(String Student_about_me) {
        this.Student_about_me = Student_about_me;
    }

    public String getStudent_connect() {
        return Student_connect;
    }

    public void setStudent_connect(String Student_connect) {
        this.Student_connect = Student_connect;
    }

    @Override
    public String toString() {
        return "Student{" + "Student_id=" + Student_id + ", Student_name=" + Student_name + ", Student_password=" + Student_password + ", Student_email=" + Student_email + ", Student_avatar=" + Student_avatar + ", Student_role_id=" + Student_role_id + ", Student_about_me=" + Student_about_me + ", Student_connect=" + Student_connect + '}';
    }

    
}
