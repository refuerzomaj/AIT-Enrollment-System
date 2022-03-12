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
public class subjects {
    private String code,subjectTitle,schedulle,room;
    private Double lec,lab;
    public subjects(String code,String subjectTitle, double lec,double lab,String schedulle,String room){
        this.code=code;
        this.subjectTitle=subjectTitle;
        this.schedulle=schedulle;
        this.room=room;
        this.lec=lec;
        this.lab=lab;
    }
    public String getCode(){
        return this.code;
    }
    public String getSubjectTitle(){
        return this.subjectTitle;
    }
    public String getSchedulle(){
        return this.schedulle;
    }
    public String getRoom(){
        return this.room;
    }
    public Double getLec(){
        return this.lec;
    }
    public Double getLab(){
        return this.lab;
    }
}
