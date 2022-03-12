/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enrollmentsystem;

/**
 *
 * @author Jomardon Refuerzo Gabitanan
 */
public class students {
    private String sId;
    private String sFirstname;
    private String sMiddlename;
    private String sLastname;
    private String extension;
    private String gender;
    private int age;
    private String bdate;
    private String bplace;
    private String address;
    private String yearLevel;
    private String course;
    private String section;
    private String schoolYear;
    private String contact;
    private String email;
    private String gname;
    private String gcontact;
    private String denrooled;
    private String dtime;
    public students(String sId,String sFirstname,String sMiddlename,String sLastname,String extension,String gender,
            int age,String bdate,String bplace,String address,String yearLevel,String course,String section,String schoolYear,
            String contact,String email,String gname,String gcontact,String denrolled,String dtime){
        this.sId=sId;
        this.sFirstname=sFirstname;
        this.sMiddlename=sMiddlename;
        this.sLastname=sLastname;
        this.extension=extension;
        this.gender=gender;
        this.age=age;
        this.bdate=bdate;
        this.bplace=bplace;
        this.address=address;
        this.yearLevel=yearLevel;
        this.course=course;
        this.section=section;
        this.schoolYear=schoolYear;
        this.contact=contact;
        this.email=email;
        this.gname=gname;
        this.gcontact=gcontact;
        this.denrooled=denrolled;
        this.dtime=dtime;
    }
    public String getSId(){
        return this.sId;
    }
    public String getSFirstname(){
        return this.sFirstname;
    }
    public String getSMiddlename(){
        return this.sMiddlename;
    }
    public String getSLastname(){
        return this.sLastname;
    }
    public String getExtension(){
        return this.extension;
    }
    public String getGender(){
        return this.gender;
    }
    public int getAge(){
        return this.age;
    }
    public String getBDate(){
        return this.bdate;
    }
    public String getBPlace(){
        return this.bplace;
    }
    public String getAddress(){
        return this.address;
    }
    public String getYearLevel(){
        return this.yearLevel;
    }
    public String getCourse(){
        return this.course;
    }
    public String getSection(){
        return this.section;
    }
    public String getSchoolYear(){
        return this.schoolYear;
    }
    public String getContact(){
        return this.contact;
    }
    public String getEmail(){
        return this.email;
    }
    public String getGName(){
        return this.gname;
    }
    public String getGContact(){
        return this.gcontact;
    }
    public String getDEnrolled(){
        return this.denrooled;
    }
    public String getTimeEnrolled(){
        return this.dtime;
    }
}