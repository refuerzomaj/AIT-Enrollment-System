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
public class section {
    private String course;
    private String yearlevel;
    private String sectionName;
    public section(String course,String yearlevel,String sectionName){
        this.course=course;
        this.yearlevel=yearlevel;
        this.sectionName=sectionName;
    }
    public String getCourse(){
        return this.course;
    }
    public String getYearLevel(){
        return this.yearlevel;
    }
    public String getSectioName(){
        return this.sectionName;
    }
}
