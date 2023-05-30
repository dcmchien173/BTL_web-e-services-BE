
package model;


public class Teacher {
    private string Teacher_id;
    private String Teacher_password;
    private String Teacher_email;
    private String Teacher_avatar;
    private String Teacher_about_me;
    private String Teacher_connect;
    private double Teacher_ruby;      
    private int countQuizCreated;
   
    

    
    public Teacher() {
    }

    public Teacher( String Teacher_id, String Teacher_password, String Teacher_email, String Teacher_avatar, String Teacher_about_me, String Teacher_connect) {
       
        this.Teacher_id = Teacher_id;
        this.Teacher_password = Teacher_password;
        this.Teacher_email = Teacher_email;
        this.Teacher_avatar = Teacher_avatar;
        this.Teacher_about_me = Teacher_about_me;
        this.Teacher_connect = Teacher_connect;

    }

    
    

    public void setTeacher_id(String Teacher_id) {
        this.Teacher_id = Teacher_id;
    }

    public String getTeacher_Password() {
        return Teacher_password;
    }

    public void setTeacher_Password(String Teacher_password) {
        this.Teacher_password = Teacher_password;
    }

    public String getTeacher_Email() {
        return Teacher_email;
    }

    public void setTeacherEmail(String Teacher_email) {
        this.Teacher_email = Teacher_email;
    }

    public String getTeacher_Avatar() {
        return Teacher_avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getAbout_me() {
        return about_me;
    }

    public void setTeacher_About_me(String about_me) {
        this.Teacher_about_me = Teacher_about_me;
    }

    public String getTeacher_Connect() {
        return Teacher_connect;
    }

    public void setTeacher_Connect(String Teacher_connect) {
        this.Teacher_connect = Teacher_connect;
    }
    
    public int getCountQuizCreated() {
        return countQuizCreated;
    }

    public void setCountQuizCreated(int countQuizCreated) {
        this.countQuizCreated = countQuizCreated;
    }

    @Override
    public String toString() {
        return "Teacher{" + "Teacher_id=" + Teacher_id + ", Teacher_password=" + Teacher_password + ", Teacher_email=" + Teacher_email + ", Teacher_avatar=" + Teacher_avatar + ", Teacher_about_me=" + Teacher_about_me + ", Teacher_connect=" + Teacher_connect + '}';
    }

    
}
