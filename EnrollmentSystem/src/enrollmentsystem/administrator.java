/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enrollmentsystem;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Jomardon Refuerzo Gabitanan
 */
public final class administrator extends javax.swing.JPanel{

    /**
     * Creates new form studentList
     */
    private final String dbURL = "jdbc:mysql://localhost/aviation_enrollment_system" ;
    private final String USER = "root";
    private final String PASS = "";
    private String eid,name,secName;
    private String sqlWhere="";
    private DefaultTableModel model,model2;
    private controller controll,controll2;
    private Border redline = BorderFactory.createLineBorder(new Color(255,51,51),2);
    public administrator() {
        initComponents();
        defaultPanel();
        setAvailableYear();
        ShowTable();
        ShowTable2();
        defaultImg();
    }
    public void setUserProfileName(String name){
        this.name=name;
    }
    public String getUserProfileName(){
        return name;
    }
    public void defaultImg(){
        passX.setVisible(false);
        passC.setVisible(false);
        cpassX.setVisible(false);
        cpassC.setVisible(false);
    }
    public void signupPlaceholder(){
        if(lname.getText().equals("")){
            lname.setText("Enter lastname..");
            lname.setForeground(new Color (153,153,153));
        }
        if(fname.getText().equals("")){
            fname.setText("Enter firtname..");
            fname.setForeground(new Color (153,153,153));
        }
        if(mname.getText().equals("")){
            mname.setText("Enter middlename..");
            mname.setForeground(new Color (153,153,153));
        }
        if(contact.getText().equals("")){
            contact.setText("Enter contact..");
            contact.setForeground(new Color (153,153,153));
        }
        if(position.getText().equals("")){
            position.setText("Enter position..");
            position.setForeground(new Color (153,153,153));
        }
        if(address.getText().equals("")){
            address.setText("Enter address..");
            address.setForeground(new Color (153,153,153));
        }
        if(email.getText().equals("")){
            email.setText("Enter email..");
            email.setForeground(new Color (153,153,153));
        }
        if(username.getText().equals("")){
            username.setText("Enter username..");
            username.setForeground(new Color (153,153,153));
        }
        if(password.getText().equals("")){
            password.setText("Enter password..");
            password.setForeground(new Color (153,153,153));
        }
        if(cpassword.getText().equals("")){
            cpassword.setText("Enter confirm password..");
            cpassword.setForeground(new Color (153,153,153));
        }
    }
    public void panelPicker(){
        if(!controll.getPanel().equals(userInfoPanel)&&
            controll.getButton().equals(userInfoBtn)){
            controll.setUnshowPanel();
            controll.setPanel(userInfoPanel);
            controll.setShowPanel();
        }else if(!controll.getPanel().equals(schedPanel)&&
                controll.getButton().equals(scheduleBtn)){
            controll.setUnshowPanel();
            controll.setPanel(schedPanel);
            controll.setShowPanel();
        }else if(!controll.getPanel().equals(sectionPanel)&&
                controll.getButton().equals(sectionNameBtn)){
            controll.setUnshowPanel();
            controll.setPanel(sectionPanel);
            controll.setShowPanel();
        }else if(!controll.getPanel().equals(signUpPanel)&&
                controll.getButton().equals(signUpBtn)){
            controll.setUnshowPanel();
            controll.setPanel(signUpPanel);
            controll.setShowPanel();
        }
    }
    public void searchUserId(String uid){
        try{
            Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
                conn.setAutoCommit(false);
                String sql = "SELECT userId FROM user_info WHERE userId='"+uid+"';";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet result = ps.executeQuery();
                while(result.next()){
                    conn.commit();
                    return;
                }
                JOptionPane.showMessageDialog(this,"Please enter correct id");
                searchField.setText("");
        }catch(SQLException e){
            System.out.println(e);
        
        }
    }
    public void setAvailableYear(){
        try{
                jComboBox2.removeAllItems();
                jComboBox2.addItem("Select");
                Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
                conn.setAutoCommit(false);
                String sql = "SELECT DISTINCT(yearlevel) FROM section;";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet result = ps.executeQuery();
                while(result.next()){
                    jComboBox2.addItem(result.getString(1));
                }
                conn.commit();
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public void showEmployeeId(){
        try{
                Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
                String sql = "SELECT COUNT(userType) FROM user_info WHERE usertype=\"Admin\";";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet result = ps.executeQuery();
                while(result.next()){
                    eid="ADMIN-000"+Integer.toString(result.getInt(1)+1);
                    labelE.setText("Employee ID: "+eid);
                }
        }catch(SQLException e){
            System.out.println(e);
        }
                
    }
    public void showUserId(){
        try{
                Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
                String sql = "SELECT COUNT(userType) FROM user_info WHERE usertype=\"User\";";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet result = ps.executeQuery();
                while(result.next()){
                    eid="USER-000"+Integer.toString(result.getInt(1)+1);
                    labelE.setText("Employee ID: "+eid);
                }
        }catch(SQLException e){
            System.out.println(e);
        }
                
    }
    public void getSectionName(){
        try{
            sec.removeAllItems();
            sec.addItem("Select");
                Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
                String sql = "SELECT name FROM section WHERE course='"+nos.getSelectedItem().toString()
                        +"' AND yearlevel='"+jComboBox2.getSelectedItem().toString()+"' ;";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet result = ps.executeQuery();
                while(result.next()){
                    int length = result.getString(1).length();
                    char lastChar = result.getString(1).charAt(length-1);
                    sec.addItem(Character.toString(lastChar));
                }
        }catch(SQLException e){
            System.out.println(e);
        }
                
    }
    public int getSectionId(String sec){
        int sectionId =0;
        try{
                Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
                conn.setAutoCommit(false);
                String sql = "SELECT sectionId FROM section WHERE name='"+sec+"' ;";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet result = ps.executeQuery();
                while(result.next()){
                    sectionId = result.getInt(1);
                    conn.commit();
                    return sectionId;
                }
        }catch(SQLException e){
            System.out.println(e);
        }
        return sectionId;        
    }
    public void setDefaultSubject(){
                                code1.setText("CODE");title1.setText("TITLE");lec1.setText("0.0");lab1.setText("0.0");
                                fhour1.setVisible(true);fdot1.setVisible(true);fminute1.setVisible(true);sdot1.setVisible(true);
                                f1.setVisible(true);dash1.setVisible(true);
                                shour1.setVisible(true);sminute1.setVisible(true);s1.setVisible(true);
                                day1.setVisible(true);room1.setVisible(true);tdot1.setVisible(true);ffdot1.setVisible(true);
                                
                                code2.setText("CODE");title2.setText("TITLE");lec2.setText("0.0");lab2.setText("0.0");
                                fhour2.setVisible(true);fdot2.setVisible(true);fminute2.setVisible(true);sdot2.setVisible(true);
                                f2.setVisible(true);dash2.setVisible(true);
                                shour2.setVisible(true);sminute2.setVisible(true);s2.setVisible(true);
                                day2.setVisible(true);room2.setVisible(true);tdot2.setVisible(true);ffdot2.setVisible(true);
                                
                                code3.setText("CODE");title3.setText("TITLE");lec3.setText("0.0");lab3.setText("0.0");
                                fhour3.setVisible(true);fdot3.setVisible(true);fminute3.setVisible(true);sdot3.setVisible(true);
                                f3.setVisible(true);dash3.setVisible(true);
                                shour3.setVisible(true);sminute3.setVisible(true);s3.setVisible(true);
                                day3.setVisible(true);room3.setVisible(true);tdot3.setVisible(true);ffdot3.setVisible(true);
                                
                                code4.setText("CODE");title4.setText("TITLE");lec4.setText("0.0");lab4.setText("0.0");
                                fhour4.setVisible(true);fdot4.setVisible(true);fminute4.setVisible(true);sdot4.setVisible(true);
                                f4.setVisible(true);dash4.setVisible(true);
                                shour4.setVisible(true);sminute4.setVisible(true);s4.setVisible(true);
                                day4.setVisible(true);room4.setVisible(true);tdot4.setVisible(true);ffdot4.setVisible(true);
                                
                                code5.setText("CODE");title5.setText("TITLE");lec5.setText("0.0");lab5.setText("0.0");
                                fhour5.setVisible(true);fdot5.setVisible(true);fminute5.setVisible(true);sdot5.setVisible(true);
                                f5.setVisible(true);dash5.setVisible(true);
                                shour5.setVisible(true);sminute5.setVisible(true);s5.setVisible(true);
                                day5.setVisible(true);room5.setVisible(true);tdot5.setVisible(true);ffdot5.setVisible(true);
                                
                                code6.setText("CODE");title6.setText("TITLE");lec6.setText("0.0");lab6.setText("0.0");
                                fhour6.setVisible(true);fdot6.setVisible(true);fminute6.setVisible(true);sdot6.setVisible(true);
                                f6.setVisible(true);dash6.setVisible(true);
                                shour6.setVisible(true);sminute6.setVisible(true);s6.setVisible(true);
                                day6.setVisible(true);room6.setVisible(true);tdot6.setVisible(true);ffdot6.setVisible(true);
                                
                                code7.setText("CODE");title7.setText("TITLE");lec7.setText("0.0");lab7.setText("0.0");
                                fhour7.setVisible(true);fdot7.setVisible(true);fminute7.setVisible(true);sdot7.setVisible(true);
                                f7.setVisible(true);dash7.setVisible(true);
                                shour7.setVisible(true);sminute7.setVisible(true);s7.setVisible(true);
                                day7.setVisible(true);room7.setVisible(true);tdot7.setVisible(true);ffdot7.setVisible(true);
                                
                                code8.setText("CODE");title8.setText("TITLE");lec8.setText("0.0");lab8.setText("0.0");
                                fhour8.setVisible(true);fdot8.setVisible(true);fminute8.setVisible(true);sdot8.setVisible(true);
                                f8.setVisible(true);dash8.setVisible(true);
                                shour8.setVisible(true);sminute8.setVisible(true);s8.setVisible(true);
                                day8.setVisible(true);room8.setVisible(true);tdot8.setVisible(true);ffdot8.setVisible(true);
                                
                                code9.setText("CODE");title9.setText("TITLE");lec9.setText("0.0");lab9.setText("0.0");
                                fhour9.setVisible(true);fdot9.setVisible(true);fminute9.setVisible(true);sdot9.setVisible(true);
                                f9.setVisible(true);dash9.setVisible(true);
                                shour9.setVisible(true);sminute9.setVisible(true);s9.setVisible(true);
                                day9.setVisible(true);room9.setVisible(true);tdot9.setVisible(true);ffdot9.setVisible(true);
                                
                                code10.setText("CODE");title10.setText("TITLE");lec10.setText("0.0");lab10.setText("0.0");
                                fhour10.setVisible(true);fdot10.setVisible(true);fminute10.setVisible(true);sdot10.setVisible(true);
                                f10.setVisible(true);dash10.setVisible(true);
                                shour10.setVisible(true);sminute10.setVisible(true);s10.setVisible(true);
                                day10.setVisible(true);room10.setVisible(true);tdot10.setVisible(true);ffdot10.setVisible(true);
    }
    public void validateSchedFields(){
        if(nos.getSelectedItem().toString().equals("Select")&&
                 jComboBox2.getSelectedItem().toString().equals("Select")&&
                                    sem.getSelectedItem().toString().equals("Select")){
                                setDefaultSubject();
                            }
                            if(nos.getSelectedItem().toString().equals("BSAIT")&&jComboBox2.getSelectedItem().toString().equals("1st Year")&&
                                    sem.getSelectedItem().toString().equals("Select")){
                                 setDefaultSubject();
                            }
                            if(nos.getSelectedItem().toString().equals("BSAIT")&&
                                    jComboBox2.getSelectedItem().toString().equals("1st Year")&&
                                    sem.getSelectedItem().toString().equals("1st Semester")){
                                setDefaultSubject();
                                code1.setText("GEC 1");title1.setText("Art Appreciation");lec1.setText("3.0");lab1.setText("-");
                                code2.setText("GEC 2");title2.setText("Reading in the Philippine History");lec2.setText("3.0");lab2.setText("-");
                                code3.setText("GEC 5");title3.setText("Understanding the Self");lec3.setText("3.0");lab3.setText("3.0");
                                code4.setText("IT 111");title4.setText("Introduction to Computing");lec4.setText("2.0");lab4.setText("3.0");
                                code5.setText("IT 112");title5.setText("Computer Programming 1(Java)");lec5.setText("2.0");lab5.setText("3.0");
                                code6.setText("AIT 111");title6.setText("Aviation Fundamentals");lec6.setText("2.0");lab6.setText("-");
                                code7.setText("IT 113");title7.setText("Web Development(HTML5 & CSS3 )");lec7.setText("2.0");lab7.setText("3.0");
                                code8.setText("PE 1");title8.setText("Physical Education 1");lec8.setText("2.0");lab8.setText("-");
                                code9.setText("NSTP 1");title9.setText("CWTS/ROTC1");lec9.setText("3.0");lab9.setText("-");
                                code10.setText("");code10.setText("");title10.setText("");lec10.setText("");lab10.setText("");
                                fhour10.setVisible(false);fdot10.setVisible(false);fminute10.setVisible(false);sdot10.setVisible(false);
                                f10.setVisible(false);dash10.setVisible(false);tdot10.setVisible(false);ffdot10.setVisible(false);
                                shour10.setVisible(false);sminute10.setVisible(false);s10.setVisible(false);
                                day10.setVisible(false);room10.setVisible(false);
                            }
                            else if(nos.getSelectedItem().toString().equals("BSAIT")&&
                                    jComboBox2.getSelectedItem().toString().equals("1st Year")&&
                                    sem.getSelectedItem().toString().equals("2nd Semester")){
                                setDefaultSubject();
                                code1.setText("GEC 6");title1.setText("Mathematics in the Modern World");lec1.setText("3.0");lab1.setText("-");
                                code2.setText("GEC 8");title2.setText("Ethics");lec2.setText("3.0");lab2.setText("-");
                                code3.setText("PE 2");title3.setText("Physical Ecuation 2");lec3.setText("2.0");lab3.setText("-");
                                code4.setText("IT 121");title4.setText("Computer Networking 1");lec4.setText("2.0");lab4.setText("3.0");
                                code5.setText("IT 122");title5.setText("Computer Programming 2(Advance Java)");lec5.setText("2.0");lab5.setText("3.0");
                                code6.setText("IT 123");title6.setText("Intro to Human Computer Interaction");lec6.setText("3.0");lab6.setText("-");
                                code7.setText("IT 124");title7.setText("Discrete Mathematics");lec7.setText("3.0");lab7.setText("-");
                                code8.setText("AIT Elec 1");title8.setText("Professional Elective 1");lec8.setText("3.0");lab8.setText("-");
                                code9.setText("NSTP 2");title9.setText("CWTS 2/ROTC 2");lec9.setText("3.0");lab9.setText("-");
                                code10.setText("");title10.setText("");lec10.setText("");lab10.setText("");
                                fhour10.setVisible(false);fdot10.setVisible(false);fminute10.setVisible(false);sdot10.setVisible(false);
                                f10.setVisible(false);dash10.setVisible(false);
                                shour10.setVisible(false);sminute10.setVisible(false);s10.setVisible(false);
                                day10.setVisible(false);room10.setVisible(false);tdot10.setVisible(false);ffdot10.setVisible(false);
                            }
                            else if(nos.getSelectedItem().toString().equals("BSAIT")&&
                                    jComboBox2.getSelectedItem().toString().equals("2nd Year")&&
                                    sem.getSelectedItem().toString().equals("1st Semester")){
                                setDefaultSubject();
                                code1.setText("GEC 4");title1.setText("Purposive Communication");lec1.setText("3.0");lab1.setText("-");
                                code2.setText("IT 211");title2.setText("Computer Networking 2");lec2.setText("2.0");lab2.setText("3.0");
                                code3.setText("IT 212");title3.setText("Object Oriented Programming 1 (Java)");lec3.setText("2.0");lab3.setText("3.0");
                                code4.setText("IT 213");title4.setText("Data Structure & Algorithms (Java)");lec4.setText("2.0");lab4.setText("3.0");
                                code5.setText("IT 214");title5.setText("Advance Web Programming (PHP,MYSQL&XI)");lec5.setText("2.0");lab5.setText("3.0");
                                code6.setText("IT 215");title6.setText("Quantitative Method (Modeling and Simulator)");lec6.setText("3.0");lab6.setText("-");
                                code7.setText("PE 3");title7.setText("Physical Education 3");lec7.setText("2.0");lab7.setText("");
                                code8.setText("AIT Elec 2");title8.setText("Profesional Elective 3");lec8.setText("2.0");lab8.setText("-");
                                code9.setText("");title9.setText("");lec9.setText("");lab9.setText("");
                                fhour9.setVisible(false);fdot9.setVisible(false);fminute9.setVisible(false);sdot9.setVisible(false);
                                f9.setVisible(false);dash9.setVisible(false);
                                shour9.setVisible(false);sminute9.setVisible(false);s9.setVisible(false);
                                day9.setVisible(false);room9.setVisible(false);tdot9.setVisible(false);ffdot9.setVisible(false);
                                code10.setText("");title10.setText("");lec10.setText("");lab10.setText("");
                                fhour10.setVisible(false);fdot10.setVisible(false);fminute10.setVisible(false);sdot10.setVisible(false);
                                f10.setVisible(false);dash10.setVisible(false);
                                shour10.setVisible(false);sminute10.setVisible(false);s10.setVisible(false);
                                day10.setVisible(false);room10.setVisible(false);tdot10.setVisible(false);ffdot10.setVisible(false);
                            }
                            else if(nos.getSelectedItem().toString().equals("BSAIT")&&
                                    jComboBox2.getSelectedItem().toString().equals("2nd Year")&&
                                    sem.getSelectedItem().toString().equals("2nd Semester")){
                                setDefaultSubject();
                                code1.setText("GEC 1");title1.setText("Character Building");lec1.setText("3.0");lab1.setText("-");
                                code2.setText("GEC 11");title2.setText("Introduksyong ng Pamamahayag");lec2.setText("3.0");lab2.setText("-");
                                code3.setText("IT 221");title3.setText("Aviation Information Management I");lec3.setText("2.0");lab3.setText("3.0");
                                code4.setText("IT 222");title4.setText("Computer Networking 3");lec4.setText("2.0");lab4.setText("3.0");
                                code5.setText("IT 223");title5.setText("Aviation Secure Web Development");lec5.setText("2.0");lab5.setText("3.0");
                                code6.setText("IT 224");title6.setText("Aviation System Requirement Analysis & Design");lec6.setText("3.0");lab6.setText("-");
                                code7.setText("AIT Elec 3");title7.setText("Professional Elective 3");lec7.setText("3.0");lab7.setText("-");
                                code8.setText("PE 4");title8.setText("Physcal Education 4");lec8.setText("2.0");lab8.setText("-");
                                code9.setText("");title9.setText("");lec9.setText("");lab9.setText("");
                                fhour9.setVisible(false);fdot9.setVisible(false);fminute9.setVisible(false);sdot9.setVisible(false);
                                f9.setVisible(false);dash9.setVisible(false);
                                shour9.setVisible(false);sminute9.setVisible(false);s9.setVisible(false);
                                day9.setVisible(false);room9.setVisible(false);tdot9.setVisible(false);ffdot9.setVisible(false);
                                code10.setText("");title10.setText("");lec10.setText("");lab10.setText("");
                                fhour10.setVisible(false);fdot10.setVisible(false);fminute10.setVisible(false);sdot10.setVisible(false);
                                f10.setVisible(false);dash10.setVisible(false);
                                shour10.setVisible(false);sminute10.setVisible(false);s10.setVisible(false);
                                day10.setVisible(false);room10.setVisible(false);tdot10.setVisible(false);ffdot10.setVisible(false);
                            }
                            else if(nos.getSelectedItem().toString().equals("BSAIT")&&
                                    jComboBox2.getSelectedItem().toString().equals("3rd Year")&&
                                    sem.getSelectedItem().toString().equals("1st Semester")){
                                setDefaultSubject();
                                code1.setText("GEC 9");title1.setText("Life & Works of Rizal");lec1.setText("3.0");lab1.setText("-");
                                code2.setText("GEC 3");title2.setText("Science Technology and Society");lec2.setText("3.0");lab2.setText("-");
                                code3.setText("GEC 10");title3.setText("Panitikan ng Pilipinas");lec3.setText("3.0");lab3.setText("-");
                                code4.setText("IT 311");title4.setText("Aviation Database Systems");lec4.setText("2.0");lab4.setText("3.0");
                                code5.setText("IT 312");title5.setText("System Integration and Architecture 1");lec5.setText("3.0");lab5.setText("-");
                                code6.setText("IT 313");title6.setText("Aviation Information Assurance & Security");lec6.setText("3.0");lab6.setText("-");
                                code7.setText("IT 314");title7.setText("Object Oriented Programming 2");lec7.setText("2.0");lab7.setText("3.0");
                                code8.setText("IT 315");title8.setText("Social & Professional Issues");lec8.setText("3.0");lab8.setText("-");
                                code9.setText("AIT Elec 4");title9.setText("Professional Elective 4");lec9.setText("3.0");lab9.setText("-");
                                code10.setText(""); title10.setText("");lec10.setText("");lab10.setText("");
                                fhour10.setVisible(false);fdot10.setVisible(false);fminute10.setVisible(false);sdot10.setVisible(false);
                                f10.setVisible(false);dash10.setVisible(false);
                                shour10.setVisible(false);sminute10.setVisible(false);s10.setVisible(false);
                                day10.setVisible(false);room10.setVisible(false);tdot10.setVisible(false);ffdot10.setVisible(false);
                            }
                            else if(nos.getSelectedItem().toString().equals("BSAIT")&&
                                    jComboBox2.getSelectedItem().toString().equals("3rd Year")&&
                                    sem.getSelectedItem().toString().equals("2nd Semester")){
                                setDefaultSubject();
                                code1.setText("AIT Elec 5");title1.setText("");lec1.setText("");lab1.setText("");
                                code2.setText("IT 321");title2.setText("");lec2.setText("");lab2.setText("");
                                code3.setText("IT 322");title3.setText("");lec3.setText("");lab3.setText("");
                                code4.setText("IT 323");title4.setText("");lec4.setText("");lab4.setText("");
                                code5.setText("IT 324");title5.setText("");lec5.setText("");lab5.setText("");
                                code6.setText("IT 325");title6.setText("");lec6.setText("");lab6.setText("");
                                code7.setText("IT 326");title7.setText("");lec7.setText("");lab7.setText("");
                                code8.setText("IT 327");title8.setText("");lec8.setText("");lab8.setText("");
                                code9.setText("");title9.setText("");lec9.setText("");lab9.setText("");
                                fhour10.setVisible(false);fdot10.setVisible(false);fminute10.setVisible(false);sdot10.setVisible(false);
                                f9.setVisible(false);dash9.setVisible(false);
                                shour9.setVisible(false);sminute9.setVisible(false);s9.setVisible(false);
                                day9.setVisible(false);room9.setVisible(false);tdot9.setVisible(false);ffdot9.setVisible(false);
                                code10.setText("");title10.setText("");lec10.setText("");lab10.setText("");
                                fhour10.setVisible(false);fdot10.setVisible(false);fminute10.setVisible(false);sdot10.setVisible(false);
                                f10.setVisible(false);dash10.setVisible(false);
                                shour10.setVisible(false);sminute10.setVisible(false);s10.setVisible(false);
                                day10.setVisible(false);room10.setVisible(false);tdot10.setVisible(false);ffdot10.setVisible(false);
                            }
                            else if(nos.getSelectedItem().toString().equals("BSAIT")&&
                                    jComboBox2.getSelectedItem().toString().equals("4th Year")&&
                                    sem.getSelectedItem().toString().equals("1st Semester")){
                                setDefaultSubject();
                                code1.setText("GEC 12");title1.setText("Environmental Science");lec1.setText("3.0");lab1.setText("-");
                                code2.setText("GEC 7");title2.setText("Contemporary World");lec2.setText("3.0");lab2.setText("-");
                                code3.setText("IT 411");title3.setText("Capstone Project 2");lec3.setText("3.0");lab3.setText("-");
                                code4.setText("IT 412");title4.setText("Aviation System Administration & Maintenance");lec4.setText("2.0");lab4.setText("3.0");
                                code5.setText("IT 413");title5.setText("Project Quality Management System");lec5.setText("3.0");lab5.setText("-");
                                code6.setText("IT 414");title6.setText("Aviation Database Administration");lec6.setText("2.0");lab6.setText("3.0");
                                code7.setText("IT 415");title7.setText("Virtualization & Cloud Platforms");lec7.setText("3.0");lab7.setText("-");
                                code8.setText("AIT Elec 6");title8.setText("Professional Elective 6");lec8.setText("3.0");lab8.setText("-");
                                code9.setText("");title9.setText("");lec9.setText("");lab9.setText("");
                                fhour9.setVisible(false);fdot9.setVisible(false);fminute9.setVisible(false);sdot9.setVisible(false);
                                f9.setVisible(false);dash9.setVisible(false);
                                shour9.setVisible(false);sminute9.setVisible(false);s9.setVisible(false);
                                day9.setVisible(false);room9.setVisible(false);tdot9.setVisible(false);ffdot9.setVisible(false);
                                code10.setText("");title10.setText("");lec10.setText("");lab10.setText("");
                                fhour10.setVisible(false);fdot10.setVisible(false);fminute10.setVisible(false);sdot10.setVisible(false);
                                f10.setVisible(false);dash10.setVisible(false);
                                shour10.setVisible(false);sminute10.setVisible(false);s10.setVisible(false);
                                day10.setVisible(false);room10.setVisible(false);tdot10.setVisible(false);ffdot10.setVisible(false);
                            }
                            else if(nos.getSelectedItem().toString().equals("BSAIT")&&
                                    jComboBox2.getSelectedItem().toString().equals("4th Year")&&
                                    sem.getSelectedItem().toString().equals("2nd Semester")){
                                setDefaultSubject();
                                code1.setText("IT 421");title1.setText("Internship/On The Job Training");lec1.setText("-");lab1.setText("18.0");
                                
                                code2.setText("");title2.setText("");lec2.setText("");lab2.setText("");
                                fhour2.setVisible(false);fdot2.setVisible(false);fminute2.setVisible(false);sdot2.setVisible(false);
                                f2.setVisible(false);dash2.setVisible(false);
                                shour2.setVisible(false);sminute2.setVisible(false);s2.setVisible(false);
                                day2.setVisible(false);room2.setVisible(false);tdot2.setVisible(false);ffdot2.setVisible(false);
                                
                                code3.setText("");title3.setText("");lec3.setText("");lab3.setText("");
                                fhour3.setVisible(false);fdot3.setVisible(false);fminute3.setVisible(false);sdot3.setVisible(false);
                                f3.setVisible(false);dash3.setVisible(false);
                                shour3.setVisible(false);sminute3.setVisible(false);s3.setVisible(false);
                                day3.setVisible(false);room3.setVisible(false);tdot3.setVisible(false);ffdot3.setVisible(false);
                                
                                code4.setText("");title4.setText("");lec4.setText("");lab4.setText("");
                                fhour4.setVisible(false);fdot4.setVisible(false);fminute4.setVisible(false);sdot4.setVisible(false);
                                f4.setVisible(false);dash4.setVisible(false);
                                shour4.setVisible(false);sminute4.setVisible(false);s4.setVisible(false);
                                day4.setVisible(false);room4.setVisible(false);tdot4.setVisible(false);ffdot4.setVisible(false);
                                
                                code5.setText("");title5.setText("");lec5.setText("");lab5.setText("");
                                fhour5.setVisible(false);fdot5.setVisible(false);fminute5.setVisible(false);sdot5.setVisible(false);
                                f5.setVisible(false);dash5.setVisible(false);
                                shour5.setVisible(false);sminute5.setVisible(false);s5.setVisible(false);
                                day5.setVisible(false);room5.setVisible(false);tdot5.setVisible(false);ffdot5.setVisible(false);
                                
                                code6.setText("");title6.setText("");lec6.setText("");lab6.setText("");
                                fhour6.setVisible(false);fdot6.setVisible(false);fminute6.setVisible(false);sdot6.setVisible(false);
                                f6.setVisible(false);dash6.setVisible(false);
                                shour6.setVisible(false);sminute6.setVisible(false);s6.setVisible(false);
                                day6.setVisible(false);room6.setVisible(false);tdot6.setVisible(false);ffdot6.setVisible(false);
                                
                                code7.setText("");title7.setText("");lec7.setText("");lab7.setText("");
                                fhour7.setVisible(false);fdot7.setVisible(false);fminute7.setVisible(false);sdot7.setVisible(false);
                                f7.setVisible(false);dash7.setVisible(false);
                                shour7.setVisible(false);sminute7.setVisible(false);s7.setVisible(false);
                                day7.setVisible(false);room7.setVisible(false);tdot7.setVisible(false);ffdot7.setVisible(false);
                                
                                code8.setText("");title8.setText("");lec8.setText("");lab8.setText("");
                                fhour8.setVisible(false);fdot8.setVisible(false);fminute8.setVisible(false);sdot8.setVisible(false);
                                f8.setVisible(false);dash8.setVisible(false);
                                shour8.setVisible(false);sminute8.setVisible(false);s8.setVisible(false);
                                day8.setVisible(false);room8.setVisible(false);tdot8.setVisible(false);ffdot8.setVisible(false);
                                
                                code9.setText("");title9.setText("");lec9.setText("");lab9.setText("");
                                fhour9.setVisible(false);fdot9.setVisible(false);fminute9.setVisible(false);sdot9.setVisible(false);
                                f9.setVisible(false);dash9.setVisible(false);
                                shour9.setVisible(false);sminute9.setVisible(false);s9.setVisible(false);
                                day9.setVisible(false);room9.setVisible(false);tdot9.setVisible(false);ffdot9.setVisible(false);
                                
                                code10.setText("");title10.setText("");lec10.setText("");lab10.setText("");
                                fhour10.setVisible(false);fdot10.setVisible(false);fminute10.setVisible(false);sdot10.setVisible(false);
                                f10.setVisible(false);dash10.setVisible(false);
                                shour10.setVisible(false);sminute10.setVisible(false);s10.setVisible(false);
                                day10.setVisible(false);room10.setVisible(false);tdot10.setVisible(false);ffdot10.setVisible(false);
                            }
    }
    public void defaultPanel(){
        red.setVisible(false);
        defaultSchedPanel.setPreferredSize(new Dimension(1448,503));
        controll = new controller();
        controll2 = new controller();
        controll.setPanel(schedPanel);
        controll.setUnshowPanel();
        controll2.setPanel(jPanel1);
        controll2.setUnshowPanel();
        controll2.setPanel(updatePanel);
        controll2.setUnshowPanel();
        controll.setPanel(sectionPanel);
        controll.setUnshowPanel();
        controll.setPanel(signUpPanel);
        controll.setUnshowPanel();
        controll.setPanel(userInfoPanel);
        controll.setButton(userInfoBtn);
        controll.setColorOfButton(new Color(255,0,51));//red
        controll.setJLabel(label1);
        controll.setColorLabel(new Color(255,255,255));//white
    }
    private  ArrayList<user> getUser(){
        ArrayList<user> details = new ArrayList <user>();
        
        
        try{
            Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement("SELECT ui.userId,ui.firstname,ui.middlename,"
                    + "ui.lastname,ui.extension,ui.position,ui.gender,ui.age,ui.status,ui.address,ui.userType,"
                    + "uc.contact,uc.email,ua.username FROM user_info AS ui,user_contact AS uc,user_account AS"
                    + " ua WHERE uc.userId = ui.userId AND ua.userId = ui.userId;");
            ResultSet result = stmt.executeQuery();
            
            user d;
            
            while(result.next()){
                d = new user(
                        result.getString(1),
                        result.getString(14),
                        result.getString(4),
                        result.getString(2),
                        result.getString(3),
                        result.getString(7),
                        result.getInt(8),
                        result.getString(9),
                        result.getString(13),
                        result.getString(12),
                        result.getString(10),
                        result.getString(6),
                        result.getString(11)
                );      
                
                details.add(d);
                
                }
        }catch(SQLException e){
            System.out.println(e);
        }
        return details;
    }
      private void ShowTable(){
           ArrayList<user> show = getUser();
           model = (DefaultTableModel)userTable.getModel();
           
           //String[] columnName = {"Recieve No", "Recieving Date", "Item No", "Item Name", "Description", "Expiry Date", "Quantity", "Buying Price", "Selling Price", "Total Price"};
        Object[] row = new Object[13];
        
           
           for(int j = 0; j < getUser().size(); j++){
            
            row[0] = getUser().get(j).getEmployeeId();
             row[1] = getUser().get(j).getUsername();
              row[2] = getUser().get(j).getLastname();
              row[3] = getUser().get(j).getFirstname();
              row[4] = getUser().get(j).getMiddlename();
               row[5] = getUser().get(j).getGender();
                 row[6] = getUser().get(j).getAge();
                   row[7] = getUser().get(j).getStatus();
                    row[8] = getUser().get(j).getEmail();
                      row[9] = getUser().get(j).getContact();
                        row[10] = getUser().get(j).getAddress();
                          row[11] = getUser().get(j).getPosition();
                             row[12] = getUser().get(j).getUserType();
                      
                model.addRow(row);
                userTable.setRowHeight(30);
               
            }
            TableColumnModel tcmodel = userTable.getColumnModel();
            tcmodel.getColumn(0).setPreferredWidth(100);
            tcmodel.getColumn(1).setPreferredWidth(180);
            tcmodel.getColumn(2).setPreferredWidth(180);
            tcmodel.getColumn(3).setPreferredWidth(180);
            tcmodel.getColumn(4).setPreferredWidth(180);
            tcmodel.getColumn(5).setPreferredWidth(100);
            tcmodel.getColumn(6).setPreferredWidth(100);
            tcmodel.getColumn(7).setPreferredWidth(120);
            tcmodel.getColumn(8).setPreferredWidth(180);
            tcmodel.getColumn(9).setPreferredWidth(250);
            tcmodel.getColumn(10).setPreferredWidth(120);
            tcmodel.getColumn(11).setPreferredWidth(100);
       }
      
      private  ArrayList<section> getSection(){
        ArrayList<section> details = new ArrayList <section>();
        
        
        try{
            Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement("SELECT course,yearlevel,name FROM section "+sqlWhere+";");
            ResultSet result = stmt.executeQuery();
            
            section d;
            
            while(result.next()){
                d = new section(
                result.getString(1),
                result.getString(2),
                result.getString(3)
                );      
                
                details.add(d);
                
                }
        }catch(SQLException e){
            System.out.println(e);
        }
        return details;
    }
      private void ShowTable2(){
           ArrayList<section> show = getSection();
           model2 = (DefaultTableModel)jTable1.getModel();
           
           //String[] columnName = {"Recieve No", "Recieving Date", "Item No", "Item Name", "Description", "Expiry Date", "Quantity", "Buying Price", "Selling Price", "Total Price"};
        Object[] row = new Object[3];
        
           
           for(int j = 0; j < getSection().size(); j++){
            
            row[0] = getSection().get(j).getCourse();
            row[1] = getSection().get(j).getYearLevel();
            row[2] = getSection().get(j).getSectioName();
                model2.addRow(row);
                jTable1.setRowHeight(30);
               
            }
            TableColumnModel tcmodel = userTable.getColumnModel();
            tcmodel.getColumn(0).setPreferredWidth(100);
       }
      
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        signUpBtn = new javax.swing.JPanel();
        label4 = new javax.swing.JLabel();
        board = new javax.swing.JPanel();
        userInfoPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        searchLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        searchBtn1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        eidlabel = new javax.swing.JLabel();
        contactlabel = new javax.swing.JLabel();
        emaillabel = new javax.swing.JLabel();
        positionlabel = new javax.swing.JLabel();
        usertypelabel = new javax.swing.JLabel();
        statuslabel = new javax.swing.JLabel();
        addresslabel = new javax.swing.JLabel();
        agelabel = new javax.swing.JLabel();
        usernamelabel = new javax.swing.JLabel();
        genderlabel = new javax.swing.JLabel();
        fullnamelabel = new javax.swing.JLabel();
        doneBtn = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        updateBtn = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        updatePanel = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        fname1 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        mname1 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        position2 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        gender1 = new javax.swing.JComboBox<>();
        age2 = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        userType1 = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        address1 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        email1 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        status1 = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        lname1 = new javax.swing.JTextField();
        contact1 = new javax.swing.JTextField();
        extension1 = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        username1 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        password1 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        cpassword1 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        saveBtn = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        cancelBtn = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        schedPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        nos = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        defaultSchedPanel = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        code1 = new javax.swing.JLabel();
        code6 = new javax.swing.JLabel();
        code2 = new javax.swing.JLabel();
        code3 = new javax.swing.JLabel();
        code8 = new javax.swing.JLabel();
        code7 = new javax.swing.JLabel();
        code5 = new javax.swing.JLabel();
        code4 = new javax.swing.JLabel();
        code10 = new javax.swing.JLabel();
        code9 = new javax.swing.JLabel();
        title1 = new javax.swing.JLabel();
        title4 = new javax.swing.JLabel();
        title3 = new javax.swing.JLabel();
        title2 = new javax.swing.JLabel();
        title5 = new javax.swing.JLabel();
        title6 = new javax.swing.JLabel();
        title7 = new javax.swing.JLabel();
        title8 = new javax.swing.JLabel();
        title9 = new javax.swing.JLabel();
        title10 = new javax.swing.JLabel();
        lab1 = new javax.swing.JLabel();
        lab2 = new javax.swing.JLabel();
        lab3 = new javax.swing.JLabel();
        lab4 = new javax.swing.JLabel();
        lab5 = new javax.swing.JLabel();
        lab6 = new javax.swing.JLabel();
        lab7 = new javax.swing.JLabel();
        lab8 = new javax.swing.JLabel();
        lab9 = new javax.swing.JLabel();
        lab10 = new javax.swing.JLabel();
        jLabel174 = new javax.swing.JLabel();
        jLabel175 = new javax.swing.JLabel();
        jLabel176 = new javax.swing.JLabel();
        ffdot2 = new javax.swing.JLabel();
        day1 = new javax.swing.JComboBox<>();
        day2 = new javax.swing.JComboBox<>();
        day3 = new javax.swing.JComboBox<>();
        day4 = new javax.swing.JComboBox<>();
        day10 = new javax.swing.JComboBox<>();
        day5 = new javax.swing.JComboBox<>();
        day7 = new javax.swing.JComboBox<>();
        day8 = new javax.swing.JComboBox<>();
        day9 = new javax.swing.JComboBox<>();
        day6 = new javax.swing.JComboBox<>();
        room1 = new javax.swing.JTextField();
        room2 = new javax.swing.JTextField();
        room4 = new javax.swing.JTextField();
        room3 = new javax.swing.JTextField();
        room8 = new javax.swing.JTextField();
        room7 = new javax.swing.JTextField();
        room6 = new javax.swing.JTextField();
        room5 = new javax.swing.JTextField();
        room9 = new javax.swing.JTextField();
        room10 = new javax.swing.JTextField();
        shour1 = new javax.swing.JComboBox<>();
        sminute1 = new javax.swing.JComboBox<>();
        s1 = new javax.swing.JComboBox<>();
        shour2 = new javax.swing.JComboBox<>();
        sminute2 = new javax.swing.JComboBox<>();
        s2 = new javax.swing.JComboBox<>();
        shour3 = new javax.swing.JComboBox<>();
        sminute3 = new javax.swing.JComboBox<>();
        s3 = new javax.swing.JComboBox<>();
        shour4 = new javax.swing.JComboBox<>();
        sminute4 = new javax.swing.JComboBox<>();
        s4 = new javax.swing.JComboBox<>();
        shour5 = new javax.swing.JComboBox<>();
        sminute5 = new javax.swing.JComboBox<>();
        s5 = new javax.swing.JComboBox<>();
        shour6 = new javax.swing.JComboBox<>();
        sminute6 = new javax.swing.JComboBox<>();
        s6 = new javax.swing.JComboBox<>();
        shour7 = new javax.swing.JComboBox<>();
        sminute7 = new javax.swing.JComboBox<>();
        s7 = new javax.swing.JComboBox<>();
        shour8 = new javax.swing.JComboBox<>();
        sminute8 = new javax.swing.JComboBox<>();
        s8 = new javax.swing.JComboBox<>();
        shour9 = new javax.swing.JComboBox<>();
        sminute9 = new javax.swing.JComboBox<>();
        s9 = new javax.swing.JComboBox<>();
        shour10 = new javax.swing.JComboBox<>();
        sminute10 = new javax.swing.JComboBox<>();
        s10 = new javax.swing.JComboBox<>();
        ffdot3 = new javax.swing.JLabel();
        ffdot4 = new javax.swing.JLabel();
        ffdot5 = new javax.swing.JLabel();
        ffdot6 = new javax.swing.JLabel();
        ffdot7 = new javax.swing.JLabel();
        ffdot8 = new javax.swing.JLabel();
        ffdot9 = new javax.swing.JLabel();
        ffdot10 = new javax.swing.JLabel();
        ffdot1 = new javax.swing.JLabel();
        tdot1 = new javax.swing.JLabel();
        tdot2 = new javax.swing.JLabel();
        tdot3 = new javax.swing.JLabel();
        tdot4 = new javax.swing.JLabel();
        tdot5 = new javax.swing.JLabel();
        tdot6 = new javax.swing.JLabel();
        tdot7 = new javax.swing.JLabel();
        tdot8 = new javax.swing.JLabel();
        tdot9 = new javax.swing.JLabel();
        tdot10 = new javax.swing.JLabel();
        dash1 = new javax.swing.JLabel();
        dash2 = new javax.swing.JLabel();
        dash3 = new javax.swing.JLabel();
        dash4 = new javax.swing.JLabel();
        dash5 = new javax.swing.JLabel();
        dash6 = new javax.swing.JLabel();
        dash7 = new javax.swing.JLabel();
        dash8 = new javax.swing.JLabel();
        dash9 = new javax.swing.JLabel();
        dash10 = new javax.swing.JLabel();
        jLabel208 = new javax.swing.JLabel();
        fhour1 = new javax.swing.JComboBox<>();
        jLabel209 = new javax.swing.JLabel();
        fminute1 = new javax.swing.JComboBox<>();
        fdot1 = new javax.swing.JLabel();
        sdot1 = new javax.swing.JLabel();
        f1 = new javax.swing.JComboBox<>();
        fhour2 = new javax.swing.JComboBox<>();
        fdot2 = new javax.swing.JLabel();
        sdot2 = new javax.swing.JLabel();
        sdot3 = new javax.swing.JLabel();
        sdot4 = new javax.swing.JLabel();
        sdot5 = new javax.swing.JLabel();
        sdot6 = new javax.swing.JLabel();
        sdot7 = new javax.swing.JLabel();
        sdot8 = new javax.swing.JLabel();
        sdot9 = new javax.swing.JLabel();
        sdot10 = new javax.swing.JLabel();
        fdot10 = new javax.swing.JLabel();
        fdot9 = new javax.swing.JLabel();
        fdot8 = new javax.swing.JLabel();
        fdot7 = new javax.swing.JLabel();
        fdot6 = new javax.swing.JLabel();
        fdot5 = new javax.swing.JLabel();
        fdot4 = new javax.swing.JLabel();
        fdot3 = new javax.swing.JLabel();
        fhour3 = new javax.swing.JComboBox<>();
        fhour4 = new javax.swing.JComboBox<>();
        fhour5 = new javax.swing.JComboBox<>();
        fhour6 = new javax.swing.JComboBox<>();
        fhour7 = new javax.swing.JComboBox<>();
        fhour8 = new javax.swing.JComboBox<>();
        fhour9 = new javax.swing.JComboBox<>();
        fhour10 = new javax.swing.JComboBox<>();
        fminute10 = new javax.swing.JComboBox<>();
        fminute9 = new javax.swing.JComboBox<>();
        fminute8 = new javax.swing.JComboBox<>();
        fminute7 = new javax.swing.JComboBox<>();
        fminute6 = new javax.swing.JComboBox<>();
        fminute5 = new javax.swing.JComboBox<>();
        fminute4 = new javax.swing.JComboBox<>();
        fminute3 = new javax.swing.JComboBox<>();
        fminute2 = new javax.swing.JComboBox<>();
        f2 = new javax.swing.JComboBox<>();
        f3 = new javax.swing.JComboBox<>();
        f4 = new javax.swing.JComboBox<>();
        f5 = new javax.swing.JComboBox<>();
        f6 = new javax.swing.JComboBox<>();
        f7 = new javax.swing.JComboBox<>();
        f8 = new javax.swing.JComboBox<>();
        f9 = new javax.swing.JComboBox<>();
        f10 = new javax.swing.JComboBox<>();
        lec10 = new javax.swing.JLabel();
        lec9 = new javax.swing.JLabel();
        lec8 = new javax.swing.JLabel();
        lec7 = new javax.swing.JLabel();
        lec6 = new javax.swing.JLabel();
        lec5 = new javax.swing.JLabel();
        lec4 = new javax.swing.JLabel();
        lec3 = new javax.swing.JLabel();
        lec2 = new javax.swing.JLabel();
        lec1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator14 = new javax.swing.JSeparator();
        jSeparator15 = new javax.swing.JSeparator();
        jSeparator16 = new javax.swing.JSeparator();
        jSeparator17 = new javax.swing.JSeparator();
        red = new javax.swing.JTextField();
        save2 = new javax.swing.JPanel();
        jLabel84 = new javax.swing.JLabel();
        sem = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        sec = new javax.swing.JComboBox<>();
        jLabel47 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        sectionPanel = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        searchSection = new javax.swing.JPanel();
        jLabel85 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        save5 = new javax.swing.JPanel();
        jLabel87 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        fag = new javax.swing.JComboBox<>();
        tag = new javax.swing.JComboBox<>();
        jLabel46 = new javax.swing.JLabel();
        c = new javax.swing.JComboBox<>();
        yl = new javax.swing.JComboBox<>();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        s = new javax.swing.JComboBox<>();
        signUpPanel = new javax.swing.JPanel();
        labelE = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        fname = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        mname = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        position = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        gender = new javax.swing.JComboBox<>();
        age = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        userType = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        address = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        status = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        lname = new javax.swing.JTextField();
        contact = new javax.swing.JTextField();
        extension = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        username = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        password = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        cpassword = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        cpassX = new javax.swing.JLabel();
        passX = new javax.swing.JLabel();
        cpassC = new javax.swing.JLabel();
        passC = new javax.swing.JLabel();
        registerBtn = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        userInfoBtn = new javax.swing.JPanel();
        label1 = new javax.swing.JLabel();
        scheduleBtn = new javax.swing.JPanel();
        label2 = new javax.swing.JLabel();
        sectionNameBtn = new javax.swing.JPanel();
        label3 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setBackground(new java.awt.Color(51, 51, 51));

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        signUpBtn.setBackground(new java.awt.Color(51, 51, 51));
        signUpBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signUpBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signUpBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signUpBtnMouseExited(evt);
            }
        });

        label4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label4.setForeground(new java.awt.Color(255, 255, 0));
        label4.setText("Sign Up");

        javax.swing.GroupLayout signUpBtnLayout = new javax.swing.GroupLayout(signUpBtn);
        signUpBtn.setLayout(signUpBtnLayout);
        signUpBtnLayout.setHorizontalGroup(
            signUpBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(signUpBtnLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(label4)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        signUpBtnLayout.setVerticalGroup(
            signUpBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(signUpBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label4)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel6.add(signUpBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, 120, 40));

        board.setBackground(new java.awt.Color(255, 0, 51));

        userInfoPanel.setBackground(new java.awt.Color(51, 51, 51));
        userInfoPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userInfoPanelMouseClicked(evt);
            }
        });
        userInfoPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane5.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane5.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        userTable.setBackground(new java.awt.Color(51, 51, 51));
        userTable.setForeground(new java.awt.Color(255, 255, 0));
        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "EmployeeId", "Username", "Lastname", "Firstname", "Middlename", "Gender", "Age", "Status", "Email", "Contact", "Address", "Position", "User Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        userTable.setAlignmentX(100.0F);
        userTable.setAlignmentY(10.5F);
        userTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane5.setViewportView(userTable);
        if (userTable.getColumnModel().getColumnCount() > 0) {
            userTable.getColumnModel().getColumn(0).setResizable(false);
            userTable.getColumnModel().getColumn(1).setResizable(false);
            userTable.getColumnModel().getColumn(2).setResizable(false);
            userTable.getColumnModel().getColumn(3).setResizable(false);
            userTable.getColumnModel().getColumn(4).setResizable(false);
            userTable.getColumnModel().getColumn(5).setResizable(false);
            userTable.getColumnModel().getColumn(6).setResizable(false);
            userTable.getColumnModel().getColumn(7).setResizable(false);
            userTable.getColumnModel().getColumn(8).setResizable(false);
            userTable.getColumnModel().getColumn(9).setResizable(false);
            userTable.getColumnModel().getColumn(10).setResizable(false);
            userTable.getColumnModel().getColumn(11).setResizable(false);
            userTable.getColumnModel().getColumn(12).setResizable(false);
        }

        userInfoPanel.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 730, 309));

        searchLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        searchLabel.setForeground(new java.awt.Color(102, 102, 102));
        searchLabel.setText(" Search id ...");
        userInfoPanel.add(searchLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 580, 30));
        userInfoPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(557, 340, 30, 30));

        searchField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        searchField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchFieldMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                searchFieldMouseReleased(evt);
            }
        });
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchFieldKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchFieldKeyTyped(evt);
            }
        });
        userInfoPanel.add(searchField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 580, 29));

        searchBtn1.setBackground(new java.awt.Color(0, 102, 102));
        searchBtn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchBtn1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                searchBtn1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchBtn1MouseExited(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Search");

        javax.swing.GroupLayout searchBtn1Layout = new javax.swing.GroupLayout(searchBtn1);
        searchBtn1.setLayout(searchBtn1Layout);
        searchBtn1Layout.setHorizontalGroup(
            searchBtn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchBtn1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel4)
                .addContainerGap(57, Short.MAX_VALUE))
        );
        searchBtn1Layout.setVerticalGroup(
            searchBtn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        userInfoPanel.add(searchBtn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 340, -1, -1));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 0))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "User Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 0))); // NOI18N

        eidlabel.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        eidlabel.setForeground(new java.awt.Color(255, 255, 0));
        eidlabel.setText("Employee ID:");

        contactlabel.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        contactlabel.setForeground(new java.awt.Color(255, 255, 0));
        contactlabel.setText("Contact:");

        emaillabel.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        emaillabel.setForeground(new java.awt.Color(255, 255, 0));
        emaillabel.setText("Email:");

        positionlabel.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        positionlabel.setForeground(new java.awt.Color(255, 255, 0));
        positionlabel.setText("Position:");

        usertypelabel.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        usertypelabel.setForeground(new java.awt.Color(255, 255, 0));
        usertypelabel.setText("User Type:");

        statuslabel.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        statuslabel.setForeground(new java.awt.Color(255, 255, 0));
        statuslabel.setText("Status:");

        addresslabel.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        addresslabel.setForeground(new java.awt.Color(255, 255, 0));
        addresslabel.setText("Address:");

        agelabel.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        agelabel.setForeground(new java.awt.Color(255, 255, 0));
        agelabel.setText("Age:");

        usernamelabel.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        usernamelabel.setForeground(new java.awt.Color(255, 255, 0));
        usernamelabel.setText("Username:");

        genderlabel.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        genderlabel.setForeground(new java.awt.Color(255, 255, 0));
        genderlabel.setText("Gender:");

        fullnamelabel.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        fullnamelabel.setForeground(new java.awt.Color(255, 255, 0));
        fullnamelabel.setText("Fullname:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addresslabel)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(agelabel)
                            .addComponent(usernamelabel)
                            .addComponent(genderlabel)
                            .addComponent(fullnamelabel)
                            .addComponent(statuslabel))
                        .addGap(209, 209, 209)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usertypelabel)
                            .addComponent(positionlabel)
                            .addComponent(emaillabel)
                            .addComponent(contactlabel)
                            .addComponent(eidlabel))))
                .addContainerGap(298, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(eidlabel)
                        .addGap(18, 18, 18)
                        .addComponent(contactlabel)
                        .addGap(18, 18, 18)
                        .addComponent(emaillabel)
                        .addGap(18, 18, 18)
                        .addComponent(positionlabel)
                        .addGap(18, 18, 18)
                        .addComponent(usertypelabel)
                        .addGap(26, 26, 26)
                        .addComponent(addresslabel))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(fullnamelabel)
                        .addGap(18, 18, 18)
                        .addComponent(genderlabel)
                        .addGap(18, 18, 18)
                        .addComponent(usernamelabel)
                        .addGap(18, 18, 18)
                        .addComponent(agelabel)
                        .addGap(18, 18, 18)
                        .addComponent(statuslabel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, -1, -1));

        doneBtn.setBackground(new java.awt.Color(0, 102, 102));
        doneBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                doneBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                doneBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                doneBtnMouseExited(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Done");

        javax.swing.GroupLayout doneBtnLayout = new javax.swing.GroupLayout(doneBtn);
        doneBtn.setLayout(doneBtnLayout);
        doneBtnLayout.setHorizontalGroup(
            doneBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(doneBtnLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel5)
                .addContainerGap(57, Short.MAX_VALUE))
        );
        doneBtnLayout.setVerticalGroup(
            doneBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        jPanel1.add(doneBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 270, -1, -1));

        updateBtn.setBackground(new java.awt.Color(0, 102, 102));
        updateBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                updateBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                updateBtnMouseExited(evt);
            }
        });

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Update");

        javax.swing.GroupLayout updateBtnLayout = new javax.swing.GroupLayout(updateBtn);
        updateBtn.setLayout(updateBtnLayout);
        updateBtnLayout.setHorizontalGroup(
            updateBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updateBtnLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel15)
                .addContainerGap(57, Short.MAX_VALUE))
        );
        updateBtnLayout.setVerticalGroup(
            updateBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        jPanel1.add(updateBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 270, -1, -1));

        userInfoPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 730, 310));

        updatePanel.setBackground(new java.awt.Color(51, 51, 51));
        updatePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(51, 51, 51));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "General Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 0))); // NOI18N
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setForeground(new java.awt.Color(255, 255, 0));
        jLabel25.setText("Last Name");
        jPanel9.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, -1, -1));

        jLabel26.setForeground(new java.awt.Color(255, 255, 0));
        jLabel26.setText("First Name");
        jPanel9.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, -1, -1));

        fname1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fname1ActionPerformed(evt);
            }
        });
        jPanel9.add(fname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 200, -1));

        jLabel27.setForeground(new java.awt.Color(255, 255, 0));
        jLabel27.setText("Middle Name");
        jPanel9.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, -1, -1));

        mname1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mname1ActionPerformed(evt);
            }
        });
        jPanel9.add(mname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, 185, -1));

        jLabel28.setForeground(new java.awt.Color(255, 255, 0));
        jLabel28.setText("Contact");
        jPanel9.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, -1, -1));
        jPanel9.add(position2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 180, -1));

        jLabel29.setForeground(new java.awt.Color(255, 255, 0));
        jLabel29.setText("Position");
        jPanel9.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, -1, -1));

        gender1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female" }));
        jPanel9.add(gender1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 70, -1));

        age2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
        jPanel9.add(age2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, -1, -1));

        jLabel30.setForeground(new java.awt.Color(255, 255, 0));
        jLabel30.setText("Age");
        jPanel9.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 100, -1, -1));

        jLabel31.setForeground(new java.awt.Color(255, 255, 0));
        jLabel31.setText("Gender");
        jPanel9.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 100, -1, -1));

        userType1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "User", "Admin" }));
        jPanel9.add(userType1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 120, 73, -1));

        jLabel32.setForeground(new java.awt.Color(255, 255, 0));
        jLabel32.setText("User Type");
        jPanel9.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 150, -1, -1));
        jPanel9.add(address1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 390, -1));

        jLabel33.setForeground(new java.awt.Color(255, 255, 0));
        jLabel33.setText("Address");
        jPanel9.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, -1, -1));

        email1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                email1ActionPerformed(evt);
            }
        });
        jPanel9.add(email1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 120, 180, -1));

        jLabel34.setForeground(new java.awt.Color(255, 255, 0));
        jLabel34.setText("Email");
        jPanel9.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 150, -1, -1));

        status1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Single", "Married" }));
        jPanel9.add(status1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 70, 100, -1));

        jLabel35.setForeground(new java.awt.Color(255, 255, 0));
        jLabel35.setText("Status");
        jPanel9.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 100, -1, -1));

        lname1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lname1ActionPerformed(evt);
            }
        });
        jPanel9.add(lname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 200, -1));
        jPanel9.add(contact1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 190, -1));

        extension1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Jr", "Sr" }));
        jPanel9.add(extension1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 20, -1, -1));

        jLabel36.setForeground(new java.awt.Color(255, 255, 0));
        jLabel36.setText("Extension");
        jPanel9.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 50, -1, -1));

        updatePanel.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 720, 180));

        jPanel10.setBackground(new java.awt.Color(51, 51, 51));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Account Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 0))); // NOI18N
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel10.add(username1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 190, -1));

        jLabel37.setForeground(new java.awt.Color(255, 255, 0));
        jLabel37.setText("Username");
        jPanel10.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, -1, -1));
        jPanel10.add(password1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 190, -1));

        jLabel38.setForeground(new java.awt.Color(255, 255, 0));
        jLabel38.setText("Password");
        jPanel10.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, -1, -1));
        jPanel10.add(cpassword1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 190, -1));

        jLabel39.setForeground(new java.awt.Color(255, 255, 0));
        jLabel39.setText("Confirm Password");
        jPanel10.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, -1, -1));

        updatePanel.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 720, 100));

        saveBtn.setBackground(new java.awt.Color(0, 102, 102));
        saveBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                saveBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                saveBtnMouseExited(evt);
            }
        });

        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Save");

        javax.swing.GroupLayout saveBtnLayout = new javax.swing.GroupLayout(saveBtn);
        saveBtn.setLayout(saveBtnLayout);
        saveBtnLayout.setHorizontalGroup(
            saveBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveBtnLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel41)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        saveBtnLayout.setVerticalGroup(
            saveBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        updatePanel.add(saveBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 300, -1, 30));

        cancelBtn.setBackground(new java.awt.Color(0, 102, 102));
        cancelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cancelBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cancelBtnMouseExited(evt);
            }
        });

        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Cancel");

        javax.swing.GroupLayout cancelBtnLayout = new javax.swing.GroupLayout(cancelBtn);
        cancelBtn.setLayout(cancelBtnLayout);
        cancelBtnLayout.setHorizontalGroup(
            cancelBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cancelBtnLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel40)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        cancelBtnLayout.setVerticalGroup(
            cancelBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        updatePanel.add(cancelBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 300, -1, 30));

        userInfoPanel.add(updatePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        schedPanel.setBackground(new java.awt.Color(51, 51, 51));
        schedPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                schedPanelMouseClicked(evt);
            }
        });
        schedPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 0));
        jLabel3.setText("Section");
        schedPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 360, -1, -1));

        nos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "BSAIT" }));
        nos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nosActionPerformed(evt);
            }
        });
        schedPanel.add(nos, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, 80, -1));

        jScrollPane1.setBackground(new java.awt.Color(102, 102, 102));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        defaultSchedPanel.setBackground(new java.awt.Color(255, 255, 255));
        defaultSchedPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        defaultSchedPanel.setFocusable(false);
        defaultSchedPanel.setLayout(null);

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel72.setText("COURSE CODE");
        defaultSchedPanel.add(jLabel72);
        jLabel72.setBounds(30, 40, 90, 15);

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel73.setText("SUBJECT TITLE");
        defaultSchedPanel.add(jLabel73);
        jLabel73.setBounds(280, 40, 90, 15);

        jLabel74.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel74.setText("LEC");
        defaultSchedPanel.add(jLabel74);
        jLabel74.setBounds(560, 40, 30, 15);

        jLabel75.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel75.setText("LAB");
        defaultSchedPanel.add(jLabel75);
        jLabel75.setBounds(620, 40, 30, 15);

        jLabel76.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel76.setText("SCHEDULE");
        defaultSchedPanel.add(jLabel76);
        jLabel76.setBounds(900, 40, 70, 15);

        jLabel77.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel77.setText("ROOM");
        defaultSchedPanel.add(jLabel77);
        jLabel77.setBounds(1340, 30, 40, 15);

        code1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        code1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        code1.setText("CODE");
        defaultSchedPanel.add(code1);
        code1.setBounds(0, 100, 130, 15);

        code6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        code6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        code6.setText("CODE");
        defaultSchedPanel.add(code6);
        code6.setBounds(0, 300, 130, 15);

        code2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        code2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        code2.setText("CODE");
        defaultSchedPanel.add(code2);
        code2.setBounds(0, 140, 130, 15);

        code3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        code3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        code3.setText("CODE");
        defaultSchedPanel.add(code3);
        code3.setBounds(0, 180, 130, 15);

        code8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        code8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        code8.setText("CODE");
        defaultSchedPanel.add(code8);
        code8.setBounds(0, 380, 130, 15);

        code7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        code7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        code7.setText("CODE");
        defaultSchedPanel.add(code7);
        code7.setBounds(0, 340, 130, 15);

        code5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        code5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        code5.setText("CODE");
        defaultSchedPanel.add(code5);
        code5.setBounds(0, 260, 130, 15);

        code4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        code4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        code4.setText("CODE");
        defaultSchedPanel.add(code4);
        code4.setBounds(0, 220, 130, 15);

        code10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        code10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        code10.setText("CODE");
        defaultSchedPanel.add(code10);
        code10.setBounds(0, 460, 130, 15);

        code9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        code9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        code9.setText("CODE");
        defaultSchedPanel.add(code9);
        code9.setBounds(0, 420, 130, 15);

        title1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        title1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title1.setText("TITLE");
        defaultSchedPanel.add(title1);
        title1.setBounds(130, 100, 410, 15);

        title4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        title4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title4.setText("TITLE");
        defaultSchedPanel.add(title4);
        title4.setBounds(130, 220, 410, 15);

        title3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        title3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title3.setText("TITLE");
        defaultSchedPanel.add(title3);
        title3.setBounds(130, 180, 410, 15);

        title2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        title2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title2.setText("TITLE");
        defaultSchedPanel.add(title2);
        title2.setBounds(130, 140, 410, 15);

        title5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        title5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title5.setText("TITLE");
        defaultSchedPanel.add(title5);
        title5.setBounds(130, 260, 410, 15);

        title6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        title6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title6.setText("TITLE");
        defaultSchedPanel.add(title6);
        title6.setBounds(130, 300, 410, 15);

        title7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        title7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title7.setText("TITLE");
        defaultSchedPanel.add(title7);
        title7.setBounds(130, 340, 410, 15);

        title8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        title8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title8.setText("TITLE");
        defaultSchedPanel.add(title8);
        title8.setBounds(130, 380, 410, 15);

        title9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        title9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title9.setText("TITLE");
        defaultSchedPanel.add(title9);
        title9.setBounds(130, 420, 410, 15);

        title10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        title10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title10.setText("TITLE");
        defaultSchedPanel.add(title10);
        title10.setBounds(130, 460, 410, 15);

        lab1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lab1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lab1.setText("0.0");
        defaultSchedPanel.add(lab1);
        lab1.setBounds(600, 100, 50, 15);

        lab2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lab2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lab2.setText("0.0");
        defaultSchedPanel.add(lab2);
        lab2.setBounds(600, 140, 50, 15);

        lab3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lab3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lab3.setText("0.0");
        defaultSchedPanel.add(lab3);
        lab3.setBounds(600, 180, 50, 15);

        lab4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lab4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lab4.setText("0.0");
        defaultSchedPanel.add(lab4);
        lab4.setBounds(600, 220, 50, 15);

        lab5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lab5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lab5.setText("0.0");
        defaultSchedPanel.add(lab5);
        lab5.setBounds(600, 260, 50, 15);

        lab6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lab6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lab6.setText("0.0");
        defaultSchedPanel.add(lab6);
        lab6.setBounds(600, 300, 50, 15);

        lab7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lab7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lab7.setText("0.0");
        defaultSchedPanel.add(lab7);
        lab7.setBounds(600, 340, 50, 15);

        lab8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lab8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lab8.setText("0.0");
        defaultSchedPanel.add(lab8);
        lab8.setBounds(600, 380, 50, 15);

        lab9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lab9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lab9.setText("0.0");
        defaultSchedPanel.add(lab9);
        lab9.setBounds(600, 420, 50, 15);

        lab10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lab10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lab10.setText("0.0");
        defaultSchedPanel.add(lab10);
        lab10.setBounds(600, 460, 50, 15);

        jLabel174.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel174.setText("MINUTES");
        defaultSchedPanel.add(jLabel174);
        jLabel174.setBounds(990, 80, 60, 15);

        jLabel175.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel175.setText("HOUR");
        defaultSchedPanel.add(jLabel175);
        jLabel175.setBounds(910, 80, 40, 15);

        jLabel176.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel176.setText("DAY");
        defaultSchedPanel.add(jLabel176);
        jLabel176.setBounds(1150, 80, 30, 15);

        ffdot2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ffdot2.setForeground(new java.awt.Color(255, 255, 0));
        ffdot2.setText(":");
        defaultSchedPanel.add(ffdot2);
        ffdot2.setBounds(1050, 140, 10, 20);

        day1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        day1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DAY", "Monday And Friday", "Monday and Tuesday", "Monday and Wdnesday", "Monday and Thursday", "Tuesday and Wednesday", "Tuesday and Thursday", "Tuesday and Friday", "Wednesday and Thursday", "Wednesday and Friday", "Monday to Wednesday", "Monday to Thursday", "Monday to Friday", "Saturday only", "Sunday only" }));
        day1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                day1MouseClicked(evt);
            }
        });
        day1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                day1ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(day1);
        day1.setBounds(1130, 100, 160, 20);

        day2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        day2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DAY", "Monday And Friday", "Monday and Tuesday", "Monday and Wdnesday", "Monday and Thursday", "Tuesday and Wednesday", "Tuesday and Thursday", "Tuesday and Friday", "Wednesday and Thursday", "Wednesday and Friday", "Monday to Wednesday", "Monday to Thursday", "Monday to Friday", "Saturday only", "Sunday only" }));
        day2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                day2ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(day2);
        day2.setBounds(1130, 140, 160, 20);

        day3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        day3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DAY", "Monday And Friday", "Monday and Tuesday", "Monday and Wdnesday", "Monday and Thursday", "Tuesday and Wednesday", "Tuesday and Thursday", "Tuesday and Friday", "Wednesday and Thursday", "Wednesday and Friday", "Monday to Wednesday", "Monday to Thursday", "Monday to Friday", "Saturday only", "Sunday only" }));
        day3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                day3ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(day3);
        day3.setBounds(1130, 180, 160, 20);

        day4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        day4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DAY", "Monday And Friday", "Monday and Tuesday", "Monday and Wdnesday", "Monday and Thursday", "Tuesday and Wednesday", "Tuesday and Thursday", "Tuesday and Friday", "Wednesday and Thursday", "Wednesday and Friday", "Monday to Wednesday", "Monday to Thursday", "Monday to Friday", "Saturday only", "Sunday only" }));
        day4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                day4ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(day4);
        day4.setBounds(1130, 220, 160, 20);

        day10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        day10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DAY", "Monday And Friday", "Monday and Tuesday", "Monday and Wdnesday", "Monday and Thursday", "Tuesday and Wednesday", "Tuesday and Thursday", "Tuesday and Friday", "Wednesday and Thursday", "Wednesday and Friday", "Monday to Wednesday", "Monday to Thursday", "Monday to Friday", "Saturday only", "Sunday only" }));
        day10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                day10ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(day10);
        day10.setBounds(1130, 460, 160, 20);

        day5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        day5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DAY", "Monday And Friday", "Monday and Tuesday", "Monday and Wdnesday", "Monday and Thursday", "Tuesday and Wednesday", "Tuesday and Thursday", "Tuesday and Friday", "Wednesday and Thursday", "Wednesday and Friday", "Monday to Wednesday", "Monday to Thursday", "Monday to Friday", "Saturday only", "Sunday only" }));
        day5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                day5ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(day5);
        day5.setBounds(1130, 260, 160, 20);

        day7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        day7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DAY", "Monday And Friday", "Monday and Tuesday", "Monday and Wdnesday", "Monday and Thursday", "Tuesday and Wednesday", "Tuesday and Thursday", "Tuesday and Friday", "Wednesday and Thursday", "Wednesday and Friday", "Monday to Wednesday", "Monday to Thursday", "Monday to Friday", "Saturday only", "Sunday only" }));
        day7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                day7ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(day7);
        day7.setBounds(1130, 340, 160, 20);

        day8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        day8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DAY", "Monday And Friday", "Monday and Tuesday", "Monday and Wdnesday", "Monday and Thursday", "Tuesday and Wednesday", "Tuesday and Thursday", "Tuesday and Friday", "Wednesday and Thursday", "Wednesday and Friday", "Monday to Wednesday", "Monday to Thursday", "Monday to Friday", "Saturday only", "Sunday only" }));
        day8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                day8ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(day8);
        day8.setBounds(1130, 380, 160, 20);

        day9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        day9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DAY", "Monday And Friday", "Monday and Tuesday", "Monday and Wdnesday", "Monday and Thursday", "Tuesday and Wednesday", "Tuesday and Thursday", "Tuesday and Friday", "Wednesday and Thursday", "Wednesday and Friday", "Monday to Wednesday", "Monday to Thursday", "Monday to Friday", "Saturday only", "Sunday only" }));
        day9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                day9ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(day9);
        day9.setBounds(1130, 420, 160, 20);

        day6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        day6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DAY", "Monday And Friday", "Monday and Tuesday", "Monday and Wdnesday", "Monday and Thursday", "Tuesday and Wednesday", "Tuesday and Thursday", "Tuesday and Friday", "Wednesday and Thursday", "Wednesday and Friday", "Monday to Wednesday", "Monday to Thursday", "Monday to Friday", "Saturday only", "Sunday only" }));
        day6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                day6ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(day6);
        day6.setBounds(1130, 300, 160, 20);

        room1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        room1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                room1ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(room1);
        room1.setBounds(1310, 100, 140, 30);

        room2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        room2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                room2ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(room2);
        room2.setBounds(1310, 140, 140, 30);

        room4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        room4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                room4ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(room4);
        room4.setBounds(1310, 220, 140, 30);

        room3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        room3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                room3ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(room3);
        room3.setBounds(1310, 180, 140, 30);

        room8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        room8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                room8ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(room8);
        room8.setBounds(1310, 380, 140, 30);

        room7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        room7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                room7ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(room7);
        room7.setBounds(1310, 340, 140, 30);

        room6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        room6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                room6ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(room6);
        room6.setBounds(1310, 300, 140, 30);

        room5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        room5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                room5ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(room5);
        room5.setBounds(1310, 260, 140, 30);

        room9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        room9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                room9ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(room9);
        room9.setBounds(1310, 420, 140, 30);

        room10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        room10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                room10ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(room10);
        room10.setBounds(1310, 460, 140, 30);

        shour1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        shour1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        shour1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shour1ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(shour1);
        shour1.setBounds(900, 100, 60, 20);

        sminute1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sminute1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        sminute1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sminute1ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(sminute1);
        sminute1.setBounds(980, 100, 60, 20);

        s1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        s1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));
        s1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s1ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(s1);
        s1.setBounds(1060, 100, 50, 20);

        shour2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        shour2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        shour2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shour2ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(shour2);
        shour2.setBounds(900, 140, 60, 20);

        sminute2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sminute2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        sminute2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sminute2ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(sminute2);
        sminute2.setBounds(980, 140, 60, 20);

        s2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        s2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));
        s2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s2ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(s2);
        s2.setBounds(1060, 140, 50, 20);

        shour3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        shour3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        shour3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shour3ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(shour3);
        shour3.setBounds(900, 180, 60, 20);

        sminute3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sminute3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        sminute3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sminute3ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(sminute3);
        sminute3.setBounds(980, 180, 60, 20);

        s3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        s3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));
        s3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s3ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(s3);
        s3.setBounds(1060, 180, 50, 20);

        shour4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        shour4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        shour4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shour4ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(shour4);
        shour4.setBounds(900, 220, 60, 20);

        sminute4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sminute4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        sminute4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sminute4ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(sminute4);
        sminute4.setBounds(980, 220, 60, 20);

        s4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        s4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));
        s4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s4ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(s4);
        s4.setBounds(1060, 220, 50, 20);

        shour5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        shour5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        shour5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shour5ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(shour5);
        shour5.setBounds(900, 260, 60, 20);

        sminute5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sminute5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        sminute5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sminute5ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(sminute5);
        sminute5.setBounds(980, 260, 60, 20);

        s5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        s5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));
        s5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s5ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(s5);
        s5.setBounds(1060, 260, 50, 20);

        shour6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        shour6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        shour6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shour6ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(shour6);
        shour6.setBounds(900, 300, 60, 20);

        sminute6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sminute6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        sminute6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sminute6ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(sminute6);
        sminute6.setBounds(980, 300, 60, 20);

        s6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        s6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));
        s6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s6ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(s6);
        s6.setBounds(1060, 300, 50, 20);

        shour7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        shour7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        shour7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shour7ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(shour7);
        shour7.setBounds(900, 340, 60, 20);

        sminute7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sminute7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        sminute7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sminute7ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(sminute7);
        sminute7.setBounds(980, 340, 60, 20);

        s7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        s7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));
        s7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s7ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(s7);
        s7.setBounds(1060, 340, 50, 20);

        shour8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        shour8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        shour8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shour8ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(shour8);
        shour8.setBounds(900, 380, 60, 20);

        sminute8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sminute8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        sminute8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sminute8ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(sminute8);
        sminute8.setBounds(980, 380, 60, 20);

        s8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        s8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));
        s8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s8ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(s8);
        s8.setBounds(1060, 380, 50, 20);

        shour9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        shour9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        shour9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shour9ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(shour9);
        shour9.setBounds(900, 420, 60, 20);

        sminute9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sminute9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        sminute9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sminute9ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(sminute9);
        sminute9.setBounds(980, 420, 60, 20);

        s9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        s9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));
        s9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s9ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(s9);
        s9.setBounds(1060, 420, 50, 20);

        shour10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        shour10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        shour10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shour10ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(shour10);
        shour10.setBounds(900, 460, 60, 20);

        sminute10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sminute10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        sminute10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sminute10ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(sminute10);
        sminute10.setBounds(980, 460, 60, 20);

        s10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        s10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));
        s10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s10ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(s10);
        s10.setBounds(1060, 460, 50, 20);

        ffdot3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ffdot3.setForeground(new java.awt.Color(255, 255, 0));
        ffdot3.setText(":");
        defaultSchedPanel.add(ffdot3);
        ffdot3.setBounds(1050, 180, 10, 20);

        ffdot4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ffdot4.setForeground(new java.awt.Color(255, 255, 0));
        ffdot4.setText(":");
        defaultSchedPanel.add(ffdot4);
        ffdot4.setBounds(1050, 220, 10, 20);

        ffdot5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ffdot5.setForeground(new java.awt.Color(255, 255, 0));
        ffdot5.setText(":");
        defaultSchedPanel.add(ffdot5);
        ffdot5.setBounds(1050, 260, 10, 20);

        ffdot6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ffdot6.setForeground(new java.awt.Color(255, 255, 0));
        ffdot6.setText(":");
        defaultSchedPanel.add(ffdot6);
        ffdot6.setBounds(1050, 300, 10, 20);

        ffdot7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ffdot7.setForeground(new java.awt.Color(255, 255, 0));
        ffdot7.setText(":");
        defaultSchedPanel.add(ffdot7);
        ffdot7.setBounds(1050, 340, 10, 20);

        ffdot8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ffdot8.setForeground(new java.awt.Color(255, 255, 0));
        ffdot8.setText(":");
        defaultSchedPanel.add(ffdot8);
        ffdot8.setBounds(1050, 380, 10, 20);

        ffdot9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ffdot9.setForeground(new java.awt.Color(255, 255, 0));
        ffdot9.setText(":");
        defaultSchedPanel.add(ffdot9);
        ffdot9.setBounds(1050, 420, 10, 20);

        ffdot10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ffdot10.setForeground(new java.awt.Color(255, 255, 0));
        ffdot10.setText(":");
        defaultSchedPanel.add(ffdot10);
        ffdot10.setBounds(1050, 460, 10, 20);

        ffdot1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ffdot1.setForeground(new java.awt.Color(255, 255, 0));
        ffdot1.setText(":");
        defaultSchedPanel.add(ffdot1);
        ffdot1.setBounds(1050, 100, 10, 20);

        tdot1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tdot1.setForeground(new java.awt.Color(255, 255, 0));
        tdot1.setText(":");
        defaultSchedPanel.add(tdot1);
        tdot1.setBounds(970, 100, 10, 20);

        tdot2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tdot2.setForeground(new java.awt.Color(255, 255, 0));
        tdot2.setText(":");
        defaultSchedPanel.add(tdot2);
        tdot2.setBounds(970, 140, 10, 20);

        tdot3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tdot3.setForeground(new java.awt.Color(255, 255, 0));
        tdot3.setText(":");
        defaultSchedPanel.add(tdot3);
        tdot3.setBounds(970, 180, 10, 20);

        tdot4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tdot4.setForeground(new java.awt.Color(255, 255, 0));
        tdot4.setText(":");
        defaultSchedPanel.add(tdot4);
        tdot4.setBounds(970, 220, 10, 20);

        tdot5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tdot5.setForeground(new java.awt.Color(255, 255, 0));
        tdot5.setText(":");
        defaultSchedPanel.add(tdot5);
        tdot5.setBounds(970, 260, 10, 20);

        tdot6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tdot6.setForeground(new java.awt.Color(255, 255, 0));
        tdot6.setText(":");
        defaultSchedPanel.add(tdot6);
        tdot6.setBounds(970, 300, 10, 20);

        tdot7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tdot7.setForeground(new java.awt.Color(255, 255, 0));
        tdot7.setText(":");
        defaultSchedPanel.add(tdot7);
        tdot7.setBounds(970, 340, 10, 20);

        tdot8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tdot8.setForeground(new java.awt.Color(255, 255, 0));
        tdot8.setText(":");
        defaultSchedPanel.add(tdot8);
        tdot8.setBounds(970, 380, 10, 20);

        tdot9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tdot9.setForeground(new java.awt.Color(255, 255, 0));
        tdot9.setText(":");
        defaultSchedPanel.add(tdot9);
        tdot9.setBounds(970, 420, 10, 20);

        tdot10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tdot10.setForeground(new java.awt.Color(255, 255, 0));
        tdot10.setText(":");
        defaultSchedPanel.add(tdot10);
        tdot10.setBounds(970, 460, 10, 20);

        dash1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dash1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dash1.setText("-");
        defaultSchedPanel.add(dash1);
        dash1.setBounds(880, 100, 20, 20);

        dash2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dash2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dash2.setText("-");
        defaultSchedPanel.add(dash2);
        dash2.setBounds(880, 140, 20, 20);

        dash3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dash3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dash3.setText("-");
        defaultSchedPanel.add(dash3);
        dash3.setBounds(880, 180, 20, 20);

        dash4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dash4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dash4.setText("-");
        defaultSchedPanel.add(dash4);
        dash4.setBounds(880, 220, 20, 20);

        dash5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dash5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dash5.setText("-");
        defaultSchedPanel.add(dash5);
        dash5.setBounds(880, 260, 20, 20);

        dash6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dash6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dash6.setText("-");
        defaultSchedPanel.add(dash6);
        dash6.setBounds(880, 300, 20, 20);

        dash7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dash7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dash7.setText("-");
        defaultSchedPanel.add(dash7);
        dash7.setBounds(880, 340, 20, 20);

        dash8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dash8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dash8.setText("-");
        defaultSchedPanel.add(dash8);
        dash8.setBounds(880, 380, 20, 20);

        dash9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dash9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dash9.setText("-");
        defaultSchedPanel.add(dash9);
        dash9.setBounds(880, 420, 20, 20);

        dash10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dash10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dash10.setText("-");
        defaultSchedPanel.add(dash10);
        dash10.setBounds(880, 460, 20, 20);

        jLabel208.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel208.setText("HOUR");
        defaultSchedPanel.add(jLabel208);
        jLabel208.setBounds(680, 80, 40, 15);

        fhour1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fhour1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        fhour1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fhour1ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(fhour1);
        fhour1.setBounds(670, 100, 60, 20);

        jLabel209.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel209.setText("MINUTES");
        defaultSchedPanel.add(jLabel209);
        jLabel209.setBounds(760, 80, 60, 15);

        fminute1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fminute1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        fminute1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fminute1ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(fminute1);
        fminute1.setBounds(750, 100, 60, 20);

        fdot1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        fdot1.setForeground(new java.awt.Color(255, 255, 0));
        fdot1.setText(":");
        defaultSchedPanel.add(fdot1);
        fdot1.setBounds(740, 100, 10, 20);

        sdot1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sdot1.setForeground(new java.awt.Color(255, 255, 0));
        sdot1.setText(":");
        defaultSchedPanel.add(sdot1);
        sdot1.setBounds(820, 100, 10, 20);

        f1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        f1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));
        f1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f1ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(f1);
        f1.setBounds(830, 100, 50, 20);

        fhour2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fhour2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        fhour2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fhour2ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(fhour2);
        fhour2.setBounds(670, 140, 60, 20);

        fdot2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        fdot2.setForeground(new java.awt.Color(255, 255, 0));
        fdot2.setText(":");
        defaultSchedPanel.add(fdot2);
        fdot2.setBounds(740, 140, 10, 20);

        sdot2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sdot2.setForeground(new java.awt.Color(255, 255, 0));
        sdot2.setText(":");
        defaultSchedPanel.add(sdot2);
        sdot2.setBounds(820, 140, 10, 20);

        sdot3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sdot3.setForeground(new java.awt.Color(255, 255, 0));
        sdot3.setText(":");
        defaultSchedPanel.add(sdot3);
        sdot3.setBounds(820, 180, 10, 20);

        sdot4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sdot4.setForeground(new java.awt.Color(255, 255, 0));
        sdot4.setText(":");
        defaultSchedPanel.add(sdot4);
        sdot4.setBounds(820, 220, 10, 20);

        sdot5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sdot5.setForeground(new java.awt.Color(255, 255, 0));
        sdot5.setText(":");
        defaultSchedPanel.add(sdot5);
        sdot5.setBounds(820, 260, 10, 20);

        sdot6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sdot6.setForeground(new java.awt.Color(255, 255, 0));
        sdot6.setText(":");
        defaultSchedPanel.add(sdot6);
        sdot6.setBounds(820, 300, 10, 20);

        sdot7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sdot7.setForeground(new java.awt.Color(255, 255, 0));
        sdot7.setText(":");
        defaultSchedPanel.add(sdot7);
        sdot7.setBounds(820, 340, 10, 20);

        sdot8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sdot8.setForeground(new java.awt.Color(255, 255, 0));
        sdot8.setText(":");
        defaultSchedPanel.add(sdot8);
        sdot8.setBounds(820, 380, 10, 20);

        sdot9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sdot9.setForeground(new java.awt.Color(255, 255, 0));
        sdot9.setText(":");
        defaultSchedPanel.add(sdot9);
        sdot9.setBounds(820, 420, 10, 20);

        sdot10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sdot10.setForeground(new java.awt.Color(255, 255, 0));
        sdot10.setText(":");
        defaultSchedPanel.add(sdot10);
        sdot10.setBounds(820, 460, 10, 20);

        fdot10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        fdot10.setForeground(new java.awt.Color(255, 255, 0));
        fdot10.setText(":");
        defaultSchedPanel.add(fdot10);
        fdot10.setBounds(740, 460, 10, 20);

        fdot9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        fdot9.setForeground(new java.awt.Color(255, 255, 0));
        fdot9.setText(":");
        defaultSchedPanel.add(fdot9);
        fdot9.setBounds(740, 420, 10, 20);

        fdot8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        fdot8.setForeground(new java.awt.Color(255, 255, 0));
        fdot8.setText(":");
        defaultSchedPanel.add(fdot8);
        fdot8.setBounds(740, 380, 10, 20);

        fdot7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        fdot7.setForeground(new java.awt.Color(255, 255, 0));
        fdot7.setText(":");
        defaultSchedPanel.add(fdot7);
        fdot7.setBounds(740, 340, 10, 20);

        fdot6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        fdot6.setForeground(new java.awt.Color(255, 255, 0));
        fdot6.setText(":");
        defaultSchedPanel.add(fdot6);
        fdot6.setBounds(740, 300, 10, 20);

        fdot5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        fdot5.setForeground(new java.awt.Color(255, 255, 0));
        fdot5.setText(":");
        defaultSchedPanel.add(fdot5);
        fdot5.setBounds(740, 260, 10, 20);

        fdot4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        fdot4.setForeground(new java.awt.Color(255, 255, 0));
        fdot4.setText(":");
        defaultSchedPanel.add(fdot4);
        fdot4.setBounds(740, 220, 10, 20);

        fdot3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        fdot3.setForeground(new java.awt.Color(255, 255, 0));
        fdot3.setText(":");
        defaultSchedPanel.add(fdot3);
        fdot3.setBounds(740, 180, 10, 20);

        fhour3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fhour3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        fhour3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fhour3ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(fhour3);
        fhour3.setBounds(670, 180, 60, 20);

        fhour4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fhour4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        fhour4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fhour4ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(fhour4);
        fhour4.setBounds(670, 220, 60, 20);

        fhour5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fhour5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        fhour5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fhour5ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(fhour5);
        fhour5.setBounds(670, 260, 60, 20);

        fhour6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fhour6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        fhour6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fhour6ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(fhour6);
        fhour6.setBounds(670, 300, 60, 20);

        fhour7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fhour7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        fhour7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fhour7ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(fhour7);
        fhour7.setBounds(670, 340, 60, 20);

        fhour8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fhour8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        fhour8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fhour8ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(fhour8);
        fhour8.setBounds(670, 380, 60, 20);

        fhour9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fhour9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        fhour9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fhour9ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(fhour9);
        fhour9.setBounds(670, 420, 60, 20);

        fhour10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fhour10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        fhour10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fhour10ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(fhour10);
        fhour10.setBounds(670, 460, 60, 20);

        fminute10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fminute10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        fminute10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fminute10ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(fminute10);
        fminute10.setBounds(750, 460, 60, 20);

        fminute9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fminute9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        fminute9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fminute9ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(fminute9);
        fminute9.setBounds(750, 420, 60, 20);

        fminute8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fminute8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        fminute8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fminute8ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(fminute8);
        fminute8.setBounds(750, 380, 60, 20);

        fminute7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fminute7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        fminute7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fminute7ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(fminute7);
        fminute7.setBounds(750, 340, 60, 20);

        fminute6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fminute6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        fminute6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fminute6ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(fminute6);
        fminute6.setBounds(750, 300, 60, 20);

        fminute5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fminute5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        fminute5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fminute5ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(fminute5);
        fminute5.setBounds(750, 260, 60, 20);

        fminute4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fminute4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        fminute4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fminute4ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(fminute4);
        fminute4.setBounds(750, 220, 60, 20);

        fminute3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fminute3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        fminute3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fminute3ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(fminute3);
        fminute3.setBounds(750, 180, 60, 20);

        fminute2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fminute2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        fminute2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fminute2ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(fminute2);
        fminute2.setBounds(750, 140, 60, 20);

        f2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        f2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));
        f2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f2ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(f2);
        f2.setBounds(830, 140, 50, 20);

        f3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        f3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));
        f3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f3ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(f3);
        f3.setBounds(830, 180, 50, 20);

        f4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        f4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));
        f4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f4ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(f4);
        f4.setBounds(830, 220, 50, 20);

        f5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        f5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));
        f5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f5ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(f5);
        f5.setBounds(830, 260, 50, 20);

        f6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        f6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));
        f6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f6ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(f6);
        f6.setBounds(830, 300, 50, 20);

        f7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        f7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));
        f7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f7ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(f7);
        f7.setBounds(830, 340, 50, 20);

        f8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        f8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));
        f8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f8ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(f8);
        f8.setBounds(830, 380, 50, 20);

        f9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        f9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));
        f9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f9ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(f9);
        f9.setBounds(830, 420, 50, 20);

        f10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        f10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));
        f10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f10ActionPerformed(evt);
            }
        });
        defaultSchedPanel.add(f10);
        f10.setBounds(830, 460, 50, 20);

        lec10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lec10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lec10.setText("0.0");
        defaultSchedPanel.add(lec10);
        lec10.setBounds(540, 460, 60, 15);

        lec9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lec9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lec9.setText("0.0");
        defaultSchedPanel.add(lec9);
        lec9.setBounds(540, 420, 60, 15);

        lec8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lec8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lec8.setText("0.0");
        defaultSchedPanel.add(lec8);
        lec8.setBounds(540, 380, 60, 15);

        lec7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lec7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lec7.setText("0.0");
        defaultSchedPanel.add(lec7);
        lec7.setBounds(540, 340, 60, 15);

        lec6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lec6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lec6.setText("0.0");
        defaultSchedPanel.add(lec6);
        lec6.setBounds(540, 300, 60, 15);

        lec5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lec5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lec5.setText("0.0");
        defaultSchedPanel.add(lec5);
        lec5.setBounds(540, 260, 60, 15);

        lec4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lec4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lec4.setText("0.0");
        defaultSchedPanel.add(lec4);
        lec4.setBounds(540, 220, 60, 15);

        lec3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lec3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lec3.setText("0.0");
        defaultSchedPanel.add(lec3);
        lec3.setBounds(540, 180, 60, 15);

        lec2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lec2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lec2.setText("0.0");
        defaultSchedPanel.add(lec2);
        lec2.setBounds(540, 140, 60, 15);

        lec1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lec1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lec1.setText("0.0");
        defaultSchedPanel.add(lec1);
        lec1.setBounds(540, 100, 60, 15);

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        defaultSchedPanel.add(jSeparator1);
        jSeparator1.setBounds(0, 170, 1450, 10);

        jSeparator2.setForeground(new java.awt.Color(255, 255, 0));
        defaultSchedPanel.add(jSeparator2);
        jSeparator2.setBounds(0, 140, 1450, 0);

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        defaultSchedPanel.add(jSeparator3);
        jSeparator3.setBounds(0, 210, 1450, 10);

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        defaultSchedPanel.add(jSeparator4);
        jSeparator4.setBounds(0, 250, 1450, 10);

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        defaultSchedPanel.add(jSeparator5);
        jSeparator5.setBounds(0, 290, 1450, 10);

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        defaultSchedPanel.add(jSeparator6);
        jSeparator6.setBounds(0, 370, 1450, 10);

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        defaultSchedPanel.add(jSeparator7);
        jSeparator7.setBounds(0, 330, 1450, 10);

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        defaultSchedPanel.add(jSeparator8);
        jSeparator8.setBounds(0, 410, 1450, 10);

        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        defaultSchedPanel.add(jSeparator9);
        jSeparator9.setBounds(0, 450, 1450, 10);

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));
        defaultSchedPanel.add(jSeparator10);
        jSeparator10.setBounds(0, 130, 1450, 10);

        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator11.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        defaultSchedPanel.add(jSeparator11);
        jSeparator11.setBounds(1300, 0, 20, 500);

        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));
        defaultSchedPanel.add(jSeparator12);
        jSeparator12.setBounds(0, 70, 1450, 10);

        jSeparator13.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        defaultSchedPanel.add(jSeparator13);
        jSeparator13.setBounds(0, 20, 1450, 10);

        jSeparator14.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator14.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator14.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        defaultSchedPanel.add(jSeparator14);
        jSeparator14.setBounds(130, -10, 20, 500);

        jSeparator15.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator15.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator15.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        defaultSchedPanel.add(jSeparator15);
        jSeparator15.setBounds(540, -20, 20, 500);

        jSeparator16.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator16.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator16.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        defaultSchedPanel.add(jSeparator16);
        jSeparator16.setBounds(600, -10, 20, 500);

        jSeparator17.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator17.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator17.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        defaultSchedPanel.add(jSeparator17);
        jSeparator17.setBounds(650, 0, 20, 500);

        red.setText("jTextField2");
        red.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 51), 2, true));
        defaultSchedPanel.add(red);
        red.setBounds(1200, 460, 58, 18);

        jScrollPane1.setViewportView(defaultSchedPanel);

        schedPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 360));

        save2.setBackground(new java.awt.Color(0, 102, 102));
        save2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                save2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                save2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                save2MouseExited(evt);
            }
        });

        jLabel84.setForeground(new java.awt.Color(255, 255, 255));
        jLabel84.setText("Save");

        javax.swing.GroupLayout save2Layout = new javax.swing.GroupLayout(save2);
        save2.setLayout(save2Layout);
        save2Layout.setHorizontalGroup(
            save2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(save2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel84)
                .addContainerGap(42, Short.MAX_VALUE))
        );
        save2Layout.setVerticalGroup(
            save2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel84, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
        );

        schedPanel.add(save2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 360, -1, -1));

        sem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1st Semester", "2nd Semester" }));
        sem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                semActionPerformed(evt);
            }
        });
        schedPanel.add(sem, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 360, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 0));
        jLabel13.setText("Year Level");
        schedPanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 360, -1, -1));

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 0));
        jLabel45.setText("Semester");
        schedPanel.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 360, -1, -1));

        sec.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        sec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                secMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                secMouseEntered(evt);
            }
        });
        sec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secActionPerformed(evt);
            }
        });
        schedPanel.add(sec, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 360, 80, -1));

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 0));
        jLabel47.setText("Course");
        schedPanel.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, -1));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        schedPanel.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 360, 80, -1));

        sectionPanel.setBackground(new java.awt.Color(51, 51, 51));
        sectionPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        sectionPanel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, -1, 20));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        sectionPanel.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 40, 190, -1));

        searchSection.setBackground(new java.awt.Color(0, 102, 102));
        searchSection.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchSectionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                searchSectionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchSectionMouseExited(evt);
            }
        });

        jLabel85.setForeground(new java.awt.Color(255, 255, 255));
        jLabel85.setText("Search Section Name");

        javax.swing.GroupLayout searchSectionLayout = new javax.swing.GroupLayout(searchSection);
        searchSection.setLayout(searchSectionLayout);
        searchSectionLayout.setHorizontalGroup(
            searchSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchSectionLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel85)
                .addGap(25, 25, 25))
        );
        searchSectionLayout.setVerticalGroup(
            searchSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel85, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
        );

        sectionPanel.add(searchSection, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 120, -1));

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 0));
        jLabel42.setText("Course");
        sectionPanel.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 0));
        jLabel43.setText("Average Grade");
        sectionPanel.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 0));
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("-");
        sectionPanel.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, 15, -1));

        save5.setBackground(new java.awt.Color(0, 102, 102));
        save5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                save5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                save5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                save5MouseExited(evt);
            }
        });

        jLabel87.setForeground(new java.awt.Color(255, 255, 255));
        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel87.setText("Add Section");

        javax.swing.GroupLayout save5Layout = new javax.swing.GroupLayout(save5);
        save5.setLayout(save5Layout);
        save5Layout.setHorizontalGroup(
            save5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(save5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        save5Layout.setVerticalGroup(
            save5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel87, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
        );

        sectionPanel.add(save5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 290, -1, -1));

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Course", "Year Level", "Section Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
        }

        sectionPanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(407, 38, 343, 316));

        fag.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "99", "98", "97", "96", "95", "94", "93", "92", "91", "90", "89", "87", "88", "85", "86", "85", "84", "83", "82", "81", "80", "79", "78", "77", "76", "75" }));
        fag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fagActionPerformed(evt);
            }
        });
        sectionPanel.add(fag, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 60, -1));

        tag.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "99", "98", "97", "96", "95", "94", "93", "92", "91", "90", "89", "87", "88", "85", "86", "85", "84", "83", "82", "81", "80", "79", "78", "77", "76", "75" }));
        sectionPanel.add(tag, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, 60, -1));

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 0));
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setText("%");
        sectionPanel.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 250, 15, -1));

        c.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "BSAIT" }));
        sectionPanel.add(c, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 100, -1));

        yl.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1st Year", "2nd Year", "3rd Year", "4th Year" }));
        sectionPanel.add(yl, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 100, -1));

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 0));
        jLabel48.setText("Section");
        sectionPanel.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, -1));

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 0));
        jLabel49.setText("Year Level");
        sectionPanel.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, -1));

        s.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1", "2", "3", "4" }));
        sectionPanel.add(s, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 100, -1));

        signUpPanel.setBackground(new java.awt.Color(51, 51, 51));
        signUpPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelE.setForeground(new java.awt.Color(255, 255, 0));
        labelE.setText("Employee ID:");
        signUpPanel.add(labelE, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 20));

        jPanel7.setBackground(new java.awt.Color(51, 51, 51));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "General Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 0))); // NOI18N
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setForeground(new java.awt.Color(255, 255, 0));
        jLabel2.setText("Last Name");
        jPanel7.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, -1, -1));

        jLabel6.setForeground(new java.awt.Color(255, 255, 0));
        jLabel6.setText("First Name");
        jPanel7.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, -1, -1));

        fname.setForeground(new java.awt.Color(153, 153, 153));
        fname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fname.setText("Enter firstname..");
        fname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fnameMouseClicked(evt);
            }
        });
        fname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fnameActionPerformed(evt);
            }
        });
        fname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fnameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fnameKeyReleased(evt);
            }
        });
        jPanel7.add(fname, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 200, -1));

        jLabel7.setForeground(new java.awt.Color(255, 255, 0));
        jLabel7.setText("Middle Name");
        jPanel7.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, -1, -1));

        mname.setForeground(new java.awt.Color(153, 153, 153));
        mname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mname.setText("Enter middlename..");
        mname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnameMouseClicked(evt);
            }
        });
        mname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnameActionPerformed(evt);
            }
        });
        mname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mnameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mnameKeyReleased(evt);
            }
        });
        jPanel7.add(mname, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, 185, -1));

        jLabel8.setForeground(new java.awt.Color(255, 255, 0));
        jLabel8.setText("Contact");
        jPanel7.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, -1, -1));

        position.setForeground(new java.awt.Color(153, 153, 153));
        position.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        position.setText("Enter position..");
        position.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                positionMouseClicked(evt);
            }
        });
        position.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                positionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                positionKeyReleased(evt);
            }
        });
        jPanel7.add(position, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 180, -1));

        jLabel9.setForeground(new java.awt.Color(255, 255, 0));
        jLabel9.setText("Position");
        jPanel7.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, -1, -1));

        gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female" }));
        gender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genderActionPerformed(evt);
            }
        });
        jPanel7.add(gender, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 70, -1));

        age.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
        age.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ageActionPerformed(evt);
            }
        });
        jPanel7.add(age, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, -1, -1));

        jLabel10.setForeground(new java.awt.Color(255, 255, 0));
        jLabel10.setText("Age");
        jPanel7.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 100, -1, -1));

        jLabel11.setForeground(new java.awt.Color(255, 255, 0));
        jLabel11.setText("Gender");
        jPanel7.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 100, -1, -1));

        userType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "User", "Admin" }));
        userType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userTypeActionPerformed(evt);
            }
        });
        jPanel7.add(userType, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 120, 73, -1));

        jLabel12.setForeground(new java.awt.Color(255, 255, 0));
        jLabel12.setText("User Type");
        jPanel7.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 150, -1, -1));

        address.setForeground(new java.awt.Color(153, 153, 153));
        address.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        address.setText("Enter address..");
        address.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addressMouseClicked(evt);
            }
        });
        address.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                addressKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                addressKeyReleased(evt);
            }
        });
        jPanel7.add(address, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 390, -1));

        jLabel18.setForeground(new java.awt.Color(255, 255, 0));
        jLabel18.setText("Address");
        jPanel7.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, -1, -1));

        email.setForeground(new java.awt.Color(153, 153, 153));
        email.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        email.setText("Enter email..");
        email.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                emailMouseClicked(evt);
            }
        });
        email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailActionPerformed(evt);
            }
        });
        email.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                emailKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                emailKeyReleased(evt);
            }
        });
        jPanel7.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 120, 180, -1));

        jLabel19.setForeground(new java.awt.Color(255, 255, 0));
        jLabel19.setText("Email");
        jPanel7.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 150, -1, -1));

        status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Single", "Married" }));
        status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusActionPerformed(evt);
            }
        });
        jPanel7.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 70, 100, -1));

        jLabel20.setForeground(new java.awt.Color(255, 255, 0));
        jLabel20.setText("Status");
        jPanel7.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 100, -1, -1));

        lname.setForeground(new java.awt.Color(153, 153, 153));
        lname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lname.setText("Enter lastname..");
        lname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lnameMouseClicked(evt);
            }
        });
        lname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lnameActionPerformed(evt);
            }
        });
        lname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lnameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lnameKeyReleased(evt);
            }
        });
        jPanel7.add(lname, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 190, -1));

        contact.setForeground(new java.awt.Color(153, 153, 153));
        contact.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        contact.setText("Enter contact..");
        contact.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contactMouseClicked(evt);
            }
        });
        contact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                contactKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                contactKeyReleased(evt);
            }
        });
        jPanel7.add(contact, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 190, -1));

        extension.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Jr", "Sr" }));
        jPanel7.add(extension, new org.netbeans.lib.awtextra.AbsoluteConstraints(634, 20, 70, -1));

        jLabel16.setForeground(new java.awt.Color(255, 255, 0));
        jLabel16.setText("Extension");
        jPanel7.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 50, -1, -1));

        signUpPanel.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 24, 720, 180));

        jPanel8.setBackground(new java.awt.Color(51, 51, 51));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Account Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 0))); // NOI18N
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        username.setForeground(new java.awt.Color(153, 153, 153));
        username.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        username.setText("Enter username..");
        username.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usernameMouseClicked(evt);
            }
        });
        username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                usernameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                usernameKeyReleased(evt);
            }
        });
        jPanel8.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 190, -1));

        jLabel22.setForeground(new java.awt.Color(255, 255, 0));
        jLabel22.setText("Username");
        jPanel8.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, -1, -1));

        password.setForeground(new java.awt.Color(153, 153, 153));
        password.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        password.setText("Enter password..");
        password.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                passwordMouseClicked(evt);
            }
        });
        password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                passwordKeyReleased(evt);
            }
        });
        jPanel8.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 200, -1));

        jLabel23.setForeground(new java.awt.Color(255, 255, 0));
        jLabel23.setText("Password");
        jPanel8.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, -1, -1));

        cpassword.setForeground(new java.awt.Color(153, 153, 153));
        cpassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cpassword.setText("Enter confirm password..");
        cpassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cpasswordMouseClicked(evt);
            }
        });
        cpassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cpasswordKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cpasswordKeyReleased(evt);
            }
        });
        jPanel8.add(cpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 190, -1));

        jLabel24.setForeground(new java.awt.Color(255, 255, 0));
        jLabel24.setText("Confirm Password");
        jPanel8.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, -1, -1));
        jPanel8.add(cpassX, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 30, -1, -1));
        jPanel8.add(passX, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, -1, -1));
        jPanel8.add(cpassC, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 30, -1, -1));
        jPanel8.add(passC, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, -1, -1));

        signUpPanel.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 720, 100));

        registerBtn.setBackground(new java.awt.Color(0, 102, 102));
        registerBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerBtnMouseExited(evt);
            }
        });

        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Register");

        javax.swing.GroupLayout registerBtnLayout = new javax.swing.GroupLayout(registerBtn);
        registerBtn.setLayout(registerBtnLayout);
        registerBtnLayout.setHorizontalGroup(
            registerBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerBtnLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel21)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        registerBtnLayout.setVerticalGroup(
            registerBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        signUpPanel.add(registerBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 320, -1, 30));

        javax.swing.GroupLayout boardLayout = new javax.swing.GroupLayout(board);
        board.setLayout(boardLayout);
        boardLayout.setHorizontalGroup(
            boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(boardLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(schedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(boardLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(signUpPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(boardLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(sectionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        boardLayout.setVerticalGroup(
            boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(boardLayout.createSequentialGroup()
                    .addGap(6, 6, 6)
                    .addComponent(schedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(boardLayout.createSequentialGroup()
                    .addGap(6, 6, 6)
                    .addComponent(signUpPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(boardLayout.createSequentialGroup()
                    .addGap(6, 6, 6)
                    .addComponent(sectionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(6, 6, 6)))
        );

        jPanel6.add(board, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 770, 400));

        userInfoBtn.setBackground(new java.awt.Color(51, 51, 51));
        userInfoBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userInfoBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                userInfoBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                userInfoBtnMouseExited(evt);
            }
        });

        label1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label1.setForeground(new java.awt.Color(255, 255, 0));
        label1.setText("User Info");

        javax.swing.GroupLayout userInfoBtnLayout = new javax.swing.GroupLayout(userInfoBtn);
        userInfoBtn.setLayout(userInfoBtnLayout);
        userInfoBtnLayout.setHorizontalGroup(
            userInfoBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userInfoBtnLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(label1)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        userInfoBtnLayout.setVerticalGroup(
            userInfoBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userInfoBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel6.add(userInfoBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, -1));

        scheduleBtn.setBackground(new java.awt.Color(51, 51, 51));
        scheduleBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scheduleBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                scheduleBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                scheduleBtnMouseExited(evt);
            }
        });

        label2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label2.setForeground(new java.awt.Color(255, 255, 0));
        label2.setText("Schedule");

        javax.swing.GroupLayout scheduleBtnLayout = new javax.swing.GroupLayout(scheduleBtn);
        scheduleBtn.setLayout(scheduleBtnLayout);
        scheduleBtnLayout.setHorizontalGroup(
            scheduleBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scheduleBtnLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(label2)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        scheduleBtnLayout.setVerticalGroup(
            scheduleBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scheduleBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label2)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel6.add(scheduleBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 120, -1));

        sectionNameBtn.setBackground(new java.awt.Color(51, 51, 51));
        sectionNameBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sectionNameBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sectionNameBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                sectionNameBtnMouseExited(evt);
            }
        });

        label3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label3.setForeground(new java.awt.Color(255, 255, 0));
        label3.setText("Section Name");

        javax.swing.GroupLayout sectionNameBtnLayout = new javax.swing.GroupLayout(sectionNameBtn);
        sectionNameBtn.setLayout(sectionNameBtnLayout);
        sectionNameBtnLayout.setHorizontalGroup(
            sectionNameBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sectionNameBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label3)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        sectionNameBtnLayout.setVerticalGroup(
            sectionNameBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sectionNameBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label3)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel6.add(sectionNameBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 120, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 0));
        jLabel14.setText("Administrator");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(0, 665, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel14)
                .addGap(28, 28, 28)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void userInfoBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userInfoBtnMouseClicked
        if(searchField.getText().equals(""))searchLabel.setVisible(true);    
        controll.setColorOfButton(new Color(51,51,51));
            controll.setColorLabel(new Color(255,255,0));
            controll.setButton(userInfoBtn);
            panelPicker();
            controll.setJLabel(label1);
            controll.setColorOfButton(new Color(255,0,51));
            controll.setJLabel(label1);
            controll.setColorLabel(new Color(255,255,255));
    }//GEN-LAST:event_userInfoBtnMouseClicked

    private void userInfoBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userInfoBtnMouseEntered
        if(!controll.getButton().equals(userInfoBtn)){
            userInfoBtn.setBackground(new Color(153,153,153));
        }
    }//GEN-LAST:event_userInfoBtnMouseEntered

    private void userInfoBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userInfoBtnMouseExited
        if(!controll.getButton().equals(userInfoBtn)){
            userInfoBtn.setBackground(new Color(51,51,51));
        }
    }//GEN-LAST:event_userInfoBtnMouseExited

    private void scheduleBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scheduleBtnMouseClicked
        if(searchField.getText().equals(""))searchLabel.setVisible(true);    
        controll.setColorOfButton(new Color(51,51,51));
            controll.setColorLabel(new Color(255,255,0));
            controll.setButton(scheduleBtn);
            panelPicker();
            controll.setJLabel(label2);
            controll.setColorOfButton(new Color(255,0,51));
            controll.setJLabel(label2);
            controll.setColorLabel(new Color(255,255,255));
            
    }//GEN-LAST:event_scheduleBtnMouseClicked

    private void scheduleBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scheduleBtnMouseEntered
        if(!controll.getButton().equals(scheduleBtn)){
            scheduleBtn.setBackground(new Color(153,153,153));
        }
    }//GEN-LAST:event_scheduleBtnMouseEntered

    private void scheduleBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scheduleBtnMouseExited
        if(!controll.getButton().equals(scheduleBtn)){
            scheduleBtn.setBackground(new Color(51,51,51));
        }
    }//GEN-LAST:event_scheduleBtnMouseExited

    private void sectionNameBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sectionNameBtnMouseClicked
        if(searchField.getText().equals(""))searchLabel.setVisible(true);    
        controll.setColorOfButton(new Color(51,51,51));
            controll.setColorLabel(new Color(255,255,0));
            controll.setButton(sectionNameBtn);
            panelPicker();
            controll.setJLabel(label3);
            controll.setColorOfButton(new Color(255,0,51));
            controll.setJLabel(label3);
            controll.setColorLabel(new Color(255,255,255));
    }//GEN-LAST:event_sectionNameBtnMouseClicked

    private void sectionNameBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sectionNameBtnMouseEntered
        if(!controll.getButton().equals(sectionNameBtn)){
            sectionNameBtn.setBackground(new Color(153,153,153));
        }
    }//GEN-LAST:event_sectionNameBtnMouseEntered

    private void sectionNameBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sectionNameBtnMouseExited
        if(!controll.getButton().equals(sectionNameBtn)){
            sectionNameBtn.setBackground(new Color(51,51,51));
        }
    }//GEN-LAST:event_sectionNameBtnMouseExited

    private void signUpBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signUpBtnMouseClicked
        if(searchField.getText().equals(""))searchLabel.setVisible(true);
        showEmployeeId();    
        controll.setColorOfButton(new Color(51,51,51));
            controll.setColorLabel(new Color(255,255,0));
            controll.setButton(signUpBtn);
            panelPicker();
            controll.setJLabel(label4);
            controll.setColorOfButton(new Color(255,0,51));
            controll.setJLabel(label4);
            controll.setColorLabel(new Color(255,255,255));
    }//GEN-LAST:event_signUpBtnMouseClicked

    private void signUpBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signUpBtnMouseEntered
        if(!controll.getButton().equals(signUpBtn)){
            signUpBtn.setBackground(new Color(153,153,153));
        }
    }//GEN-LAST:event_signUpBtnMouseEntered

    private void signUpBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signUpBtnMouseExited
        if(!controll.getButton().equals(signUpBtn)){
            signUpBtn.setBackground(new Color(51,51,51));
        }
    }//GEN-LAST:event_signUpBtnMouseExited

    private void mnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnameActionPerformed

    private void registerBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerBtnMouseClicked
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        String sExtension = extension.getSelectedItem().toString();
        String sGender = gender.getSelectedItem().toString();
        String sStatus = status.getSelectedItem().toString();
        String sUserType = userType.getSelectedItem().toString();
        if("Select".equals(sExtension))
            sExtension = "";
        if(lname.getText().equals("")||
                lname.getText().equals("Enter lastname..")){
            JOptionPane.showMessageDialog(this,"Lastname is empty");
            lname.setBorder(redline);
            return;
        }
        if(fname.getText().equals("")||
                fname.getText().equals("Enter firstname..")){
            JOptionPane.showMessageDialog(this,"Firstname is empty");
            fname.setBorder(redline);
            return;
        }  
        if(mname.getText().equals("")||
                mname.getText().equals("Enter middlename..")){
            JOptionPane.showMessageDialog(this,"Middlename is empty");
            mname.setBorder(redline);
            return;
        }
        if(contact.getText().equals("")||
                contact.getText().equals("Enter contact..")){
            JOptionPane.showMessageDialog(this,"Contact is empty");
            contact.setBorder(redline);
            return;
        }
        if(position.getText().equals("")||
                position.getText().equals("Enter position..")){
            JOptionPane.showMessageDialog(this,"Position is empty");
            position.setBorder(redline);
            return;
        }
        if(gender.getSelectedItem().toString().equals("Select")){
            JOptionPane.showMessageDialog(this,"Gender is empty");
            gender.setBorder(redline);
            return;
        }
        if(age.getSelectedItem().toString().equals("Select")){
            JOptionPane.showMessageDialog(this,"Age is empty");
            age.setBorder(redline);
            return;
        }
        if(status.getSelectedItem().toString().equals("Select")){
            JOptionPane.showMessageDialog(this,"Status is empty");
            status.setBorder(redline);
            return;
        }
        if(address.getText().equals("")||
                address.getText().equals("Enter address..")){
            JOptionPane.showMessageDialog(this,"Address is empty");
            address.setBorder(redline);
            return;
        }
        if(email.getText().equals("")||
                email.getText().equals("Enter contact..")){
            JOptionPane.showMessageDialog(this,"Contact is empty");
            email.setBorder(redline);
            return;
        }
        if(userType.getSelectedItem().toString().equals("Select")){
            JOptionPane.showMessageDialog(this,"User type is empty");
            userType.setBorder(redline);
            return;
        }
        if(username.getText().equals("")||
                username.getText().equals("Enter username..")){
            JOptionPane.showMessageDialog(this,"Username is empty");
            username.setBorder(redline);
            return;
        }
        if(password.getText().equals("")||
                password.getText().equals("Enter password..")){
            JOptionPane.showMessageDialog(this,"Password is empty");
            passX.setVisible(true);
            cpassX.setVisible(true);
            password.setBorder(redline);
            cpassword.setBorder(redline);
            return;
        }
        if(cpassword.getText().equals("")||
                cpassword.getText().equals("Enter confirm password..")){
            JOptionPane.showMessageDialog(this,"Confirm password is empty"); 
            passX.setVisible(true);
            cpassX.setVisible(true);
            password.setBorder(redline);
            cpassword.setBorder(redline);
            return;
        }
        if(!lname.getText().equals("")&&
                !fname.getText().equals("")&&
                !mname.getText().equals("")&&
                !contact.getText().equals("")&&
                !contact.getText().equals("")&&
                !gender.getSelectedItem().toString().equals("Select")&&
                !status.getSelectedItem().toString().equals("Select")&&
                !address.getText().equals("")&&
                !email.getText().equals("")&&
                !userType.getSelectedItem().toString().equals("Select")&&
                !username.getText().equals("")&&
                !password.getText().equals("")&&
                !cpassword.getText().equals("")){
            if(password.getText().equals(cpassword.getText())){
                String iAge = age.getSelectedItem().toString();
                try{
                //Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(dbURL, USER, PASS);
                conn.setAutoCommit(false);
                //Set auto commit to false
                
                String sql = "INSERT INTO user_info(userId,firstname,middlename,lastname,extension,position,gender,"
                        + "age,status,address,userType)SELECT ?,?,?,?,?,?,?,?,?,?,? WHERE NOT EXISTS(SELECT userId,"
                        + "firstname,middlename,lastname FROM user_info WHERE userId= ? AND firstname = ? AND "
                        + "middlename = ? AND lastname = ?)LIMIT 1;";
                ps = conn.prepareStatement(sql);
                ps.setString(1, eid);
                ps.setString(2, fname.getText());
                ps.setString(3, mname.getText());
                ps.setString(4, lname.getText());
                ps.setString(5, sExtension);
                ps.setString(6,position.getText());
                ps.setString(7, sGender);
                ps.setString(8, iAge);
                ps.setString(9, sStatus);
                ps.setString(10, address.getText());
                ps.setString(11, sUserType);
                ps.setString(12, eid);
                ps.setString(13, fname.getText());
                ps.setString(14, mname.getText());
                ps.setString(15, lname.getText());
                int rowAffected = ps.executeUpdate();
                
                if(rowAffected==1){
                     
                    String sql2 = "INSERT INTO user_contact(userId,contact,email)SELECT ?,?,?"
                            + " WHERE NOT EXISTS(SELECT userId,contact,email FROM user_contact "
                            + "WHERE userId = ? AND contact = ? AND email = ?)LIMIT 1;";
                    ps2 = conn.prepareStatement(sql2);
                    ps2.setString(1,eid);
                    ps2.setString(2,contact.getText());
                    ps2.setString(3, email.getText());
                    ps2.setString(4,eid);
                    ps2.setString(5,contact.getText());
                    ps2.setString(6, email.getText());
                    int rowAffected2 = ps2.executeUpdate();
                    
                    if(rowAffected2==1){
                        
                        String sql3 = "INSERT INTO user_account(userId,username,password)SELECT ?,?,?"
                            + " WHERE NOT EXISTS(SELECT userId,username,password FROM user_account "
                            + "WHERE userId = ? AND username = ? AND password = ?)LIMIT 1;";
                        ps3 = conn.prepareStatement(sql3);
                        ps3.setString(1,eid);
                        ps3.setString(2,username.getText());
                        ps3.setString(3, password.getText());
                        ps3.setString(4,eid);
                        ps3.setString(5,username.getText());
                        ps3.setString(6, password.getText());
                        int rowAffected3 = ps3.executeUpdate();
                        
                        if(rowAffected3==1){
                            int ans = JOptionPane.showConfirmDialog(this, "Do you want to save?","Message",JOptionPane.YES_NO_OPTION); 
                                if(ans == JOptionPane.YES_OPTION){
                                    JOptionPane.showMessageDialog(this, "You are now successfully registered");
                                    conn.commit();//we call commit because the transaction process is success
                                    model = (DefaultTableModel)userTable.getModel();
                                    model.setRowCount(0);
                                    ShowTable();
                                    fname.setText("");
                                    mname.setText("");
                                    lname.setText("");
                                    extension.setSelectedItem("Select");
                                    contact.setText("");
                                    position.setText("");
                                    gender.setSelectedItem("Select");
                                    age.setSelectedItem("Select");
                                    status.setSelectedItem("Select");
                                    address.setText("");
                                    email.setText("");
                                    userType.setSelectedItem("Select");
                                    username.setText("");
                                    password.setText("");
                                    cpassword.setText("");
                                }else{
                                    conn.rollback();
                                }
                            
                        }else{
                            conn.rollback();
                            JOptionPane.showMessageDialog(this, "Something went wrong please try again");
                        }
                    }else{
                        conn.rollback();
                        JOptionPane.showMessageDialog(this, "Something went wrong please try again");
                    }
                }else{
                    conn.rollback();
                    JOptionPane.showMessageDialog(this, "Something went wrong please try again");
                }
                }catch (SQLException e) {
                    //rollback the transaction
                    try{
                        if(conn !=null){
                            conn.rollback();
                        }
                    }catch(SQLException s){
                        System.out.println(s);
                    }
                    System.out.println(e.getMessage());
                }finally{
                    try{
                        if(rs != null) rs.close();
                        if(ps != null) ps.close();
                        if(ps2 != null) ps2.close();
                        if(ps3 != null) ps3.close();
                        if(conn != null) conn.close();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                }
            }else{
                JOptionPane.showMessageDialog(this, "Password and confirm password must same");
                passX.setVisible(true);
                cpassX.setVisible(true);
            }
        }
    }//GEN-LAST:event_registerBtnMouseClicked

    private void registerBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerBtnMouseEntered
        
                registerBtn.setBackground(new Color(0,204,204));
            
        
    }//GEN-LAST:event_registerBtnMouseEntered

    private void registerBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerBtnMouseExited
        
                registerBtn.setBackground(new Color(0,102,102));
           
    }//GEN-LAST:event_registerBtnMouseExited

    private void nosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nosActionPerformed
        nos.setBorder(fhour10.getBorder());
        validateSchedFields();
    }//GEN-LAST:event_nosActionPerformed

    private void day8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_day8ActionPerformed
        day8.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_day8ActionPerformed

    private void day7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_day7ActionPerformed
        day7.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_day7ActionPerformed

    private void s2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s2ActionPerformed
        fhour2.setBorder(fhour1.getBorder());
        fminute2.setBorder(fhour1.getBorder());
        shour2.setBorder(fhour1.getBorder());
        sminute2.setBorder(fhour1.getBorder());
        f2.setBorder(fhour1.getBorder());
        s2.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_s2ActionPerformed

    private void sminute3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sminute3ActionPerformed
        fhour3.setBorder(fhour1.getBorder());
        fminute3.setBorder(fhour1.getBorder());
        shour3.setBorder(fhour1.getBorder());
        sminute3.setBorder(fhour1.getBorder());
        f3.setBorder(fhour1.getBorder());
        s3.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_sminute3ActionPerformed

    private void sminute5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sminute5ActionPerformed
        fhour5.setBorder(fhour1.getBorder());
        fminute5.setBorder(fhour1.getBorder());
        shour5.setBorder(fhour1.getBorder());
        sminute5.setBorder(fhour1.getBorder());
        f5.setBorder(fhour1.getBorder());
        s5.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_sminute5ActionPerformed

    private void shour6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shour6ActionPerformed
        fhour6.setBorder(fhour1.getBorder());
        fminute6.setBorder(fhour1.getBorder());
        shour6.setBorder(fhour1.getBorder());
        fminute6.setBorder(fhour1.getBorder());
        f6.setBorder(fhour1.getBorder());
        s6.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_shour6ActionPerformed

    private void s8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s8ActionPerformed
        fhour8.setBorder(fhour1.getBorder());
        fminute8.setBorder(fhour1.getBorder());
        shour8.setBorder(fhour1.getBorder());
        sminute8.setBorder(fhour1.getBorder());
        f8.setBorder(fhour1.getBorder());
        s8.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_s8ActionPerformed

    private void save2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_save2MouseClicked
        String sn = "";
        String d1 ="0.0";
        String d2 ="0.0";
        String d3 ="0.0";
        String d4 ="0.0";
        String d5 ="0.0";
        String d6 ="0.0";
        String d7 ="0.0";
        String d8 ="0.0";
        String d9 ="0.0";
        String d10 ="0.0";
        
        Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;
        PreparedStatement ps = null;
        String firstSched = fhour1.getSelectedItem().toString()+":"+fminute1.getSelectedItem().toString()
                +" "+f1.getSelectedItem().toString()+"-"+shour1.getSelectedItem().toString()+
                ":"+sminute1.getSelectedItem().toString()+" "+s1.getSelectedItem().toString()
                +" "+day1.getSelectedItem().toString();
        String secondSched = fhour2.getSelectedItem().toString()+":"+fminute2.getSelectedItem().toString()
                +" "+f2.getSelectedItem().toString()+"-"+shour2.getSelectedItem().toString()+
                ":"+sminute2.getSelectedItem().toString()+" "+s2.getSelectedItem().toString()
                +" "+day2.getSelectedItem().toString();
        String thirdSched = fhour3.getSelectedItem().toString()+":"+fminute3.getSelectedItem().toString()
                +" "+f3.getSelectedItem().toString()+"-"+shour3.getSelectedItem().toString()+
                ":"+sminute3.getSelectedItem().toString()+" "+s3.getSelectedItem().toString()
                +" "+day3.getSelectedItem().toString();
        String fourthSched = fhour4.getSelectedItem().toString()+":"+fminute4.getSelectedItem().toString()
                +" "+f4.getSelectedItem().toString()+"-"+shour4.getSelectedItem().toString()+
                ":"+sminute4.getSelectedItem().toString()+" "+s4.getSelectedItem().toString()
                +" "+day4.getSelectedItem().toString();
        String fifthSched = fhour5.getSelectedItem().toString()+":"+fminute5.getSelectedItem().toString()
                +" "+f5.getSelectedItem().toString()+"-"+shour5.getSelectedItem().toString()+
                ":"+sminute5.getSelectedItem().toString()+" "+s5.getSelectedItem().toString()
                +" "+day5.getSelectedItem().toString();
        String sixthSched = fhour6.getSelectedItem().toString()+":"+fminute6.getSelectedItem().toString()
                +" "+f6.getSelectedItem().toString()+"-"+shour6.getSelectedItem().toString()+
                ":"+sminute6.getSelectedItem().toString()+" "+s6.getSelectedItem().toString()
                +" "+day6.getSelectedItem().toString();
        String seventhSched = fhour7.getSelectedItem().toString()+":"+fminute7.getSelectedItem().toString()
                +" "+f7.getSelectedItem().toString()+"-"+shour7.getSelectedItem().toString()+
                ":"+sminute7.getSelectedItem().toString()+" "+s7.getSelectedItem().toString()
                +" "+day7.getSelectedItem().toString();
        String eighthSched = fhour8.getSelectedItem().toString()+":"+fminute8.getSelectedItem().toString()
                +" "+f8.getSelectedItem().toString()+"-"+shour8.getSelectedItem().toString()+
                ":"+sminute8.getSelectedItem().toString()+" "+s8.getSelectedItem().toString()
                +" "+day8.getSelectedItem().toString();
        String ninthSched = fhour9.getSelectedItem().toString()+":"+fminute9.getSelectedItem().toString()
                +" "+f9.getSelectedItem().toString()+"-"+shour9.getSelectedItem().toString()+
                ":"+sminute9.getSelectedItem().toString()+" "+s9.getSelectedItem().toString()
                +" "+day9.getSelectedItem().toString();
        String tenthSched = fhour10.getSelectedItem().toString()+":"+fminute10.getSelectedItem().toString()
                +" "+f10.getSelectedItem().toString()+"-"+shour10.getSelectedItem().toString()+
                ":"+sminute10.getSelectedItem().toString()+" "+s10.getSelectedItem().toString()
                +" "+day10.getSelectedItem().toString();
        if(lab1.getText().equals("-"))
            d1="0.0";
        if(!lab1.getText().equals("-"))
            d1=lab1.getText();
        if(lab2.getText().equals("-"))
            d2="0.0";
        if(!lab2.getText().equals("-"))
            d2=lab2.getText();
        if(lab3.getText().equals("-"))
            d3="0.0";
        if(!lab3.getText().equals("-"))
            d3=lab3.getText();
        if(lab4.getText().equals("-"))
            d4="0.0";
        if(!lab4.getText().equals("-"))
            d4=lab4.getText();
        if(lab5.getText().equals("-"))
            d5="0.0";
        if(!lab5.getText().equals("-"))
            d5=lab5.getText();
        if(lab6.getText().equals("-"))
            d6="0.0";
        if(!lab6.getText().equals("-"))
            d6=lab6.getText();
        if(lab7.getText().equals("-"))
            d1="0.0";
        if(!lab7.getText().equals("-"))
            d7=lab7.getText();
        if(lab8.getText().equals("-"))
            d8="0.0";
        if(!lab8.getText().equals("-"))
            d8=lab8.getText();
        if(lab9.getText().equals("-"))
            d9="0.0";
        if(!lab9.getText().equals("-"))
            d9=lab9.getText();
        if(lab10.getText().equals("-"))
            d10="0.0";
        if(!lab10.getText().equals("-"))
            d10=lab10.getText();
        
            
        
        if(jComboBox2.getSelectedItem().toString().equals("1st Year"))
                sn=nos.getSelectedItem().toString()+" 1";
            if(jComboBox2.getSelectedItem().toString().equals("2nd Year"))
                sn=nos.getSelectedItem().toString()+" 2";
            if(jComboBox2.getSelectedItem().toString().equals("3rd Year"))
                sn=nos.getSelectedItem().toString()+" 3";
            if(jComboBox2.getSelectedItem().toString().equals("4th Year"))
                sn=nos.getSelectedItem().toString()+" 4";
            if(sec.getSelectedItem().toString().equals("1"))
                sn=sn+"-1";
            if(sec.getSelectedItem().toString().equals("2"))
                sn=sn+"-2";
            if(sec.getSelectedItem().toString().equals("3"))
                sn=sn+"-3";
            if(sec.getSelectedItem().toString().equals("4"))
                sn=sn+"-4";
            
        if(!nos.getSelectedItem().toString().equals("Select")&&
                !sem.getSelectedItem().toString().equals("Select")){
            if(nos.getSelectedItem().toString().equals("BSAIT")&&
                    jComboBox2.getSelectedItem().toString().equals("1st Year")){
                switch(sem.getSelectedItem().toString()){
                    case "1st Semester":
                        if(sec.getSelectedItem().equals("Select")){
                            JOptionPane.showMessageDialog(this,"Section in needed");
                            sec.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour1.getSelectedItem().equals(shour1.getSelectedItem())&&
                                f1.getSelectedItem().equals(s1.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour1.setBorder(red.getBorder());
                            shour1.setBorder(red.getBorder());
                            f1.setBorder(red.getBorder());
                            s1.setBorder(red.getBorder());
                            fminute1.setBorder(red.getBorder());
                            sminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour2.getSelectedItem().equals(shour2.getSelectedItem())&&
                                f2.getSelectedItem().equals(s2.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour2.setBorder(red.getBorder());
                            shour2.setBorder(red.getBorder());
                            f2.setBorder(red.getBorder());
                            s2.setBorder(red.getBorder());
                            fminute2.setBorder(red.getBorder());
                            sminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour3.getSelectedItem().equals(shour3.getSelectedItem())&&
                                f3.getSelectedItem().equals(s3.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour3.setBorder(red.getBorder());
                            shour3.setBorder(red.getBorder());
                            f3.setBorder(red.getBorder());
                            s3.setBorder(red.getBorder());
                            fminute3.setBorder(red.getBorder());
                            sminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour4.getSelectedItem().equals(shour4.getSelectedItem())&&
                                f4.getSelectedItem().equals(s4.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour4.setBorder(red.getBorder());
                            shour4.setBorder(red.getBorder());
                            f4.setBorder(red.getBorder());
                            s4.setBorder(red.getBorder());
                            fminute4.setBorder(red.getBorder());
                            sminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour5.getSelectedItem().equals(shour5.getSelectedItem())&&
                                f5.getSelectedItem().equals(s5.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour5.setBorder(red.getBorder());
                            shour5.setBorder(red.getBorder());
                            f5.setBorder(red.getBorder());
                            s5.setBorder(red.getBorder());
                            fminute5.setBorder(red.getBorder());
                            sminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour6.getSelectedItem().equals(shour6.getSelectedItem())&&
                                f6.getSelectedItem().equals(s6.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour6.setBorder(red.getBorder());
                            shour6.setBorder(red.getBorder());
                            f6.setBorder(red.getBorder());
                            s6.setBorder(red.getBorder());
                            fminute6.setBorder(red.getBorder());
                            sminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour7.getSelectedItem().equals(shour7.getSelectedItem())&&
                                f7.getSelectedItem().equals(s7.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour7.setBorder(red.getBorder());
                            shour7.setBorder(red.getBorder());
                            f7.setBorder(red.getBorder());
                            s7.setBorder(red.getBorder());
                            fminute7.setBorder(red.getBorder());
                            sminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour8.getSelectedItem().equals(shour8.getSelectedItem())&&
                                f8.getSelectedItem().equals(s8.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour8.setBorder(red.getBorder());
                            shour8.setBorder(red.getBorder());
                            f8.setBorder(red.getBorder());
                            s8.setBorder(red.getBorder());
                            fminute8.setBorder(red.getBorder());
                            sminute8.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour9.getSelectedItem().equals(shour9.getSelectedItem())&&
                                f9.getSelectedItem().equals(s9.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour9.setBorder(red.getBorder());
                            shour9.setBorder(red.getBorder());
                            f9.setBorder(red.getBorder());
                            s9.setBorder(red.getBorder());
                            fminute9.setBorder(red.getBorder());
                            sminute9.setBorder(red.getBorder());
                            return;
                        }
                        //first subject
                        if(fhour1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour1.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(shour1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour1.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(day1.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day1.setBorder(red.getBorder());
                            return;
                        }
                        if(room1.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room1.setBorder(red.getBorder());
                            return;
                        }
                        //second subject
                        if(fhour2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour2.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(shour2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour2.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(day2.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day2.setBorder(red.getBorder());
                            return;
                        }
                        if(room2.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room2.setBorder(red.getBorder());
                            return;
                        }
                        //third subject
                        if(fhour3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour3.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(shour3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour3.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(day3.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day3.setBorder(red.getBorder());
                            return;
                        }
                        if(room3.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room3.setBorder(red.getBorder());
                            return;
                        }
                        //fourth subject
                        if(fhour4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour4.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(shour4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour4.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(day4.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day4.setBorder(red.getBorder());
                            return;
                        }
                        if(room4.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room4.setBorder(red.getBorder());
                            return;
                        }
                        //fifth subject
                        if(fhour5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour5.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(shour5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour5.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(day5.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day5.setBorder(red.getBorder());
                            return;
                        }
                        if(room5.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room5.setBorder(red.getBorder());
                            return;
                        }
                        //sixth subject
                        if(fhour6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour6.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(shour6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour6.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(day6.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day6.setBorder(red.getBorder());
                            return;
                        }
                        if(room6.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room6.setBorder(red.getBorder());
                            return;
                        }
                        //seventh subject
                        if(fhour7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour7.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(shour7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour7.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(day7.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day7.setBorder(red.getBorder());
                            return;
                        }
                        if(room7.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room7.setBorder(red.getBorder());
                            return;
                        }
                        //eight subject
                        if(fhour8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour8.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute8.setBorder(red.getBorder());
                            return;
                        }
                        if(shour8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour8.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute8.setBorder(red.getBorder());
                            return;
                        }
                        if(day8.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day8.setBorder(red.getBorder());
                            return;
                        }
                        if(room8.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room8.setBorder(red.getBorder());
                            return;
                        }
                        //ninth subject
                        if(fhour9.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour9.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute9.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute9.setBorder(red.getBorder());
                            return;
                        }
                        if(shour9.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour9.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute9.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute9.setBorder(red.getBorder());
                            return;
                        }
                        if(day9.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day9.setBorder(red.getBorder());
                            return;
                        }
                        if(room9.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room9.setBorder(red.getBorder());
                            return;
                        }
                        
                        try{
                            conn = DriverManager.getConnection(dbURL, USER, PASS);
                            stmt = conn.createStatement();
                            conn.setAutoCommit(false);
                            String sql1= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES  ("+getSectionId(sn)+",'"+code1.getText()+"','"+title1.getText()
                                    +"',"+Double.valueOf(lec1.getText())+","+Double.valueOf(d1)+",'"+firstSched+"','"
                                    +room1.getText()+"');";
                            String sql2= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code2.getText()+"','"+title2.getText()
                                    +"',"+Double.valueOf(lec2.getText())+","+Double.valueOf(d2)+",'"+secondSched+"','"
                                    +room2.getText()+"');";
                            String sql3= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code3.getText()+"','"+title3.getText()
                                    +"',"+Double.valueOf(lec3.getText())+","+Double.valueOf(d3)+",'"+thirdSched+"','"
                                    +room3.getText()+"');";
                            String sql4= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code4.getText()+"','"+title4.getText()
                                    +"',"+Double.valueOf(lec4.getText())+","+Double.valueOf(d4)+",'"+fourthSched+"','"
                                    +room4.getText()+"');";
                            String sql5= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code5.getText()+"','"+title5.getText()
                                    +"',"+Double.valueOf(lec5.getText())+","+Double.valueOf(d5)+",'"+fifthSched+"','"
                                    +room5.getText()+"');";
                            String sql6= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code6.getText()+"','"+title6.getText()
                                    +"',"+Double.valueOf(lec6.getText())+","+Double.valueOf(d6)+",'"+sixthSched+"','"
                                    +room6.getText()+"');";
                            String sql7= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code7.getText()+"','"+title7.getText()
                                    +"',"+Double.valueOf(lec7.getText())+","+Double.valueOf(d7)+",'"+seventhSched+"','"
                                    +room7.getText()+"');";
                            String sql8= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code8.getText()+"','"+title8.getText()
                                    +"',"+Double.valueOf(lec8.getText())+","+Double.valueOf(d8)+",'"+eighthSched+"','"
                                    +room8.getText()+"')";
                            String sql9= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code9.getText()+"','"+title9.getText()
                                    +"',"+Double.valueOf(lec9.getText())+","+Double.valueOf(d9)+",'"+ninthSched+"','"
                                    +room9.getText()+"');";
                            stmt.addBatch(sql1);
                            stmt.addBatch(sql2);
                            stmt.addBatch(sql3);
                            stmt.addBatch(sql4);
                            stmt.addBatch(sql5);
                            stmt.addBatch(sql6);
                            stmt.addBatch(sql7);
                            stmt.addBatch(sql8);
                            stmt.addBatch(sql9);
                            int[] rowAffected = stmt.executeBatch();
                            
                            if(rowAffected[8]==1){
                                JOptionPane.showMessageDialog(this,"New schedule is inserted");
                                conn.commit();
                            }else{
                                JOptionPane.showMessageDialog(this,"Something went wrong please try again");
                                conn.rollback();
                            }
                            
                        }catch (SQLException e) {
                    //rollback the transaction
                    try{
                        if(conn !=null){
                            conn.rollback();
                        }
                    }catch(SQLException s){
                        System.out.println(s);
                    }
                    System.out.println(e.getMessage());
                }finally{
                    try{
                        if(rs != null) rs.close();
                        if(ps != null) ps.close();
                        if(conn != null) conn.close();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                        }
                        break;
                    case "2nd Semester":
                        
                        if(sec.getSelectedItem().equals("Select")){
                            JOptionPane.showMessageDialog(this,"Section in needed");
                            sec.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour1.getSelectedItem().equals(shour1.getSelectedItem())&&
                                f1.getSelectedItem().equals(s1.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour1.setBorder(red.getBorder());
                            shour1.setBorder(red.getBorder());
                            f1.setBorder(red.getBorder());
                            s1.setBorder(red.getBorder());
                            fminute1.setBorder(red.getBorder());
                            sminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour2.getSelectedItem().equals(shour2.getSelectedItem())&&
                                f2.getSelectedItem().equals(s2.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour2.setBorder(red.getBorder());
                            shour2.setBorder(red.getBorder());
                            f2.setBorder(red.getBorder());
                            s2.setBorder(red.getBorder());
                            fminute2.setBorder(red.getBorder());
                            sminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour3.getSelectedItem().equals(shour3.getSelectedItem())&&
                                f3.getSelectedItem().equals(s3.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour3.setBorder(red.getBorder());
                            shour3.setBorder(red.getBorder());
                            f3.setBorder(red.getBorder());
                            s3.setBorder(red.getBorder());
                            fminute3.setBorder(red.getBorder());
                            sminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour4.getSelectedItem().equals(shour4.getSelectedItem())&&
                                f4.getSelectedItem().equals(s4.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour4.setBorder(red.getBorder());
                            shour4.setBorder(red.getBorder());
                            f4.setBorder(red.getBorder());
                            s4.setBorder(red.getBorder());
                            fminute4.setBorder(red.getBorder());
                            sminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour5.getSelectedItem().equals(shour5.getSelectedItem())&&
                                f5.getSelectedItem().equals(s5.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour5.setBorder(red.getBorder());
                            shour5.setBorder(red.getBorder());
                            f5.setBorder(red.getBorder());
                            s5.setBorder(red.getBorder());
                            fminute5.setBorder(red.getBorder());
                            sminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour6.getSelectedItem().equals(shour6.getSelectedItem())&&
                                f6.getSelectedItem().equals(s6.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour6.setBorder(red.getBorder());
                            shour6.setBorder(red.getBorder());
                            f6.setBorder(red.getBorder());
                            s6.setBorder(red.getBorder());
                            fminute6.setBorder(red.getBorder());
                            sminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour7.getSelectedItem().equals(shour7.getSelectedItem())&&
                                f7.getSelectedItem().equals(s7.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour7.setBorder(red.getBorder());
                            shour7.setBorder(red.getBorder());
                            f7.setBorder(red.getBorder());
                            s7.setBorder(red.getBorder());
                            fminute7.setBorder(red.getBorder());
                            sminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour8.getSelectedItem().equals(shour8.getSelectedItem())&&
                                f8.getSelectedItem().equals(s8.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour8.setBorder(red.getBorder());
                            shour8.setBorder(red.getBorder());
                            f8.setBorder(red.getBorder());
                            s8.setBorder(red.getBorder());
                            fminute8.setBorder(red.getBorder());
                            sminute8.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour9.getSelectedItem().equals(shour9.getSelectedItem())&&
                                f9.getSelectedItem().equals(s9.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour9.setBorder(red.getBorder());
                            shour9.setBorder(red.getBorder());
                            f9.setBorder(red.getBorder());
                            s9.setBorder(red.getBorder());
                            fminute9.setBorder(red.getBorder());
                            sminute9.setBorder(red.getBorder());
                            return;
                        }
                        //first subject
                        if(fhour1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour1.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(shour1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour1.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(day1.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day1.setBorder(red.getBorder());
                            return;
                        }
                        if(room1.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room1.setBorder(red.getBorder());
                            return;
                        }
                        //second subject
                        if(fhour2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour2.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(shour2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour2.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(day2.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day2.setBorder(red.getBorder());
                            return;
                        }
                        if(room2.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room2.setBorder(red.getBorder());
                            return;
                        }
                        //third subject
                        if(fhour3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour3.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(shour3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour3.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(day3.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day3.setBorder(red.getBorder());
                            return;
                        }
                        if(room3.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room3.setBorder(red.getBorder());
                            return;
                        }
                        //fourth subject
                        if(fhour4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour4.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(shour4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour4.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(day4.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day4.setBorder(red.getBorder());
                            return;
                        }
                        if(room4.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room4.setBorder(red.getBorder());
                            return;
                        }
                        //fifth subject
                        if(fhour5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour5.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(shour5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour5.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(day5.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day5.setBorder(red.getBorder());
                            return;
                        }
                        if(room5.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room5.setBorder(red.getBorder());
                            return;
                        }
                        //sixth subject
                        if(fhour6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour6.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(shour6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour6.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(day6.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day6.setBorder(red.getBorder());
                            return;
                        }
                        if(room6.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room6.setBorder(red.getBorder());
                            return;
                        }
                        //seventh subject
                        if(fhour7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour7.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(shour7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour7.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(day7.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day7.setBorder(red.getBorder());
                            return;
                        }
                        if(room7.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room7.setBorder(red.getBorder());
                            return;
                        }
                        //eight subject
                        if(fhour8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour8.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute8.setBorder(red.getBorder());
                            return;
                        }
                        if(shour8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour8.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute8.setBorder(red.getBorder());
                            return;
                        }
                        if(day8.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day8.setBorder(red.getBorder());
                            return;
                        }
                        if(room8.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room8.setBorder(red.getBorder());
                            return;
                        }
                        //ninth subject
                        if(fhour9.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour9.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute9.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute9.setBorder(red.getBorder());
                            return;
                        }
                        if(shour9.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour9.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute9.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute9.setBorder(red.getBorder());
                            return;
                        }
                        if(day9.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day9.setBorder(red.getBorder());
                            return;
                        }
                        if(room9.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room9.setBorder(red.getBorder());
                            return;
                        }
                        try{
                            conn = DriverManager.getConnection(dbURL, USER, PASS);
                            stmt = conn.createStatement();
                            conn.setAutoCommit(false);
                            String sql1= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES  ("+getSectionId(sn)+",'"+code1.getText()+"','"+title1.getText()
                                    +"',"+Double.valueOf(lec1.getText())+","+Double.valueOf(d1)+",'"+firstSched+"','"
                                    +room1.getText()+"');";
                            String sql2= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code2.getText()+"','"+title2.getText()
                                    +"',"+Double.valueOf(lec2.getText())+","+Double.valueOf(d2)+",'"+secondSched+"','"
                                    +room2.getText()+"');";
                            String sql3= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code3.getText()+"','"+title3.getText()
                                    +"',"+Double.valueOf(lec3.getText())+","+Double.valueOf(d3)+",'"+thirdSched+"','"
                                    +room3.getText()+"');";
                            String sql4= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code4.getText()+"','"+title4.getText()
                                    +"',"+Double.valueOf(lec4.getText())+","+Double.valueOf(d4)+",'"+fourthSched+"','"
                                    +room4.getText()+"');";
                            String sql5= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code5.getText()+"','"+title5.getText()
                                    +"',"+Double.valueOf(lec5.getText())+","+Double.valueOf(d5)+",'"+fifthSched+"','"
                                    +room5.getText()+"');";
                            String sql6= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code6.getText()+"','"+title6.getText()
                                    +"',"+Double.valueOf(lec6.getText())+","+Double.valueOf(d6)+",'"+sixthSched+"','"
                                    +room6.getText()+"');";
                            String sql7= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code7.getText()+"','"+title7.getText()
                                    +"',"+Double.valueOf(lec7.getText())+","+Double.valueOf(d7)+",'"+seventhSched+"','"
                                    +room7.getText()+"');";
                            String sql8= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code8.getText()+"','"+title8.getText()
                                    +"',"+Double.valueOf(lec8.getText())+","+Double.valueOf(d8)+",'"+eighthSched+"','"
                                    +room8.getText()+"')";
                            String sql9= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code9.getText()+"','"+title9.getText()
                                    +"',"+Double.valueOf(lec9.getText())+","+Double.valueOf(d9)+",'"+ninthSched+"','"
                                    +room9.getText()+"');";
                            stmt.addBatch(sql1);
                            stmt.addBatch(sql2);
                            stmt.addBatch(sql3);
                            stmt.addBatch(sql4);
                            stmt.addBatch(sql5);
                            stmt.addBatch(sql6);
                            stmt.addBatch(sql7);
                            stmt.addBatch(sql8);
                            stmt.addBatch(sql9);
                            
                          
                            int[] rowAffected = stmt.executeBatch();
                            
                            if(rowAffected[8]==1){
                                JOptionPane.showMessageDialog(this,"New schedule is inserted");
                                conn.commit();
                            }else{
                                JOptionPane.showMessageDialog(this,"Something went wrong please try again");
                                conn.rollback();
                            }
                            
                        }catch (SQLException e) {
                    //rollback the transaction
                    try{
                        if(conn !=null){
                            conn.rollback();
                        }
                    }catch(SQLException s){
                        System.out.println(s);
                    }
                    System.out.println(e.getMessage());
                }finally{
                    try{
                        if(rs != null) rs.close();
                        if(ps != null) ps.close();
                        if(conn != null) conn.close();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                        }
                        
                        break;
                    default:
                        break;
                }
            }else if(nos.getSelectedItem().toString().equals("BSAIT")&&
                    jComboBox2.getSelectedItem().toString().equals("2nd Year")){
                switch(sem.getSelectedItem().toString()){
                    case "1st Semester":
                        
                        if(sec.getSelectedItem().equals("Select")){
                            JOptionPane.showMessageDialog(this,"Section in needed");
                            sec.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour1.getSelectedItem().equals(shour1.getSelectedItem())&&
                                f1.getSelectedItem().equals(s1.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour1.setBorder(red.getBorder());
                            shour1.setBorder(red.getBorder());
                            f1.setBorder(red.getBorder());
                            s1.setBorder(red.getBorder());
                            fminute1.setBorder(red.getBorder());
                            sminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour2.getSelectedItem().equals(shour2.getSelectedItem())&&
                                f2.getSelectedItem().equals(s2.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour2.setBorder(red.getBorder());
                            shour2.setBorder(red.getBorder());
                            f2.setBorder(red.getBorder());
                            s2.setBorder(red.getBorder());
                            fminute2.setBorder(red.getBorder());
                            sminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour3.getSelectedItem().equals(shour3.getSelectedItem())&&
                                f3.getSelectedItem().equals(s3.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour3.setBorder(red.getBorder());
                            shour3.setBorder(red.getBorder());
                            f3.setBorder(red.getBorder());
                            s3.setBorder(red.getBorder());
                            fminute3.setBorder(red.getBorder());
                            sminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour4.getSelectedItem().equals(shour4.getSelectedItem())&&
                                f4.getSelectedItem().equals(s4.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour4.setBorder(red.getBorder());
                            shour4.setBorder(red.getBorder());
                            f4.setBorder(red.getBorder());
                            s4.setBorder(red.getBorder());
                            fminute4.setBorder(red.getBorder());
                            sminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour5.getSelectedItem().equals(shour5.getSelectedItem())&&
                                f5.getSelectedItem().equals(s5.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour5.setBorder(red.getBorder());
                            shour5.setBorder(red.getBorder());
                            f5.setBorder(red.getBorder());
                            s5.setBorder(red.getBorder());
                            fminute5.setBorder(red.getBorder());
                            sminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour6.getSelectedItem().equals(shour6.getSelectedItem())&&
                                f6.getSelectedItem().equals(s6.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour6.setBorder(red.getBorder());
                            shour6.setBorder(red.getBorder());
                            f6.setBorder(red.getBorder());
                            s6.setBorder(red.getBorder());
                            fminute6.setBorder(red.getBorder());
                            sminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour7.getSelectedItem().equals(shour7.getSelectedItem())&&
                                f7.getSelectedItem().equals(s7.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour7.setBorder(red.getBorder());
                            shour7.setBorder(red.getBorder());
                            f7.setBorder(red.getBorder());
                            s7.setBorder(red.getBorder());
                            fminute7.setBorder(red.getBorder());
                            sminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour8.getSelectedItem().equals(shour8.getSelectedItem())&&
                                f8.getSelectedItem().equals(s8.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour8.setBorder(red.getBorder());
                            shour8.setBorder(red.getBorder());
                            f8.setBorder(red.getBorder());
                            s8.setBorder(red.getBorder());
                            fminute8.setBorder(red.getBorder());
                            sminute8.setBorder(red.getBorder());
                            return;
                        }
                        //first subject
                        if(fhour1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour1.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(shour1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour1.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(day1.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day1.setBorder(red.getBorder());
                            return;
                        }
                        if(room1.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room1.setBorder(red.getBorder());
                            return;
                        }
                        //second subject
                        if(fhour2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour2.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(shour2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour2.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(day2.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day2.setBorder(red.getBorder());
                            return;
                        }
                        if(room2.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room2.setBorder(red.getBorder());
                            return;
                        }
                        //third subject
                        if(fhour3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour3.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(shour3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour3.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(day3.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day3.setBorder(red.getBorder());
                            return;
                        }
                        if(room3.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room3.setBorder(red.getBorder());
                            return;
                        }
                        //fourth subject
                        if(fhour4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour4.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(shour4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour4.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(day4.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day4.setBorder(red.getBorder());
                            return;
                        }
                        if(room4.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room4.setBorder(red.getBorder());
                            return;
                        }
                        //fifth subject
                        if(fhour5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour5.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(shour5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour5.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(day5.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day5.setBorder(red.getBorder());
                            return;
                        }
                        if(room5.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room5.setBorder(red.getBorder());
                            return;
                        }
                        //sixth subject
                        if(fhour6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour6.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(shour6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour6.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(day6.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day6.setBorder(red.getBorder());
                            return;
                        }
                        if(room6.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room6.setBorder(red.getBorder());
                            return;
                        }
                        //seventh subject
                        if(fhour7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour7.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(shour7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour7.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(day7.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day7.setBorder(red.getBorder());
                            return;
                        }
                        if(room7.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room7.setBorder(red.getBorder());
                            return;
                        }
                        //eight subject
                        if(fhour8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour8.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute8.setBorder(red.getBorder());
                            return;
                        }
                        if(shour8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour8.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute8.setBorder(red.getBorder());
                            return;
                        }
                        if(day8.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day8.setBorder(red.getBorder());
                            return;
                        }
                        if(room8.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room8.setBorder(red.getBorder());
                            return;
                        }
                        try{
                            conn = DriverManager.getConnection(dbURL, USER, PASS);
                            stmt = conn.createStatement();
                            conn.setAutoCommit(false);
                            String sql1= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES  ("+getSectionId(sn)+",'"+code1.getText()+"','"+title1.getText()
                                    +"',"+Double.valueOf(lec1.getText())+","+Double.valueOf(d1)+",'"+firstSched+"','"
                                    +room1.getText()+"');";
                            String sql2= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code2.getText()+"','"+title2.getText()
                                    +"',"+Double.valueOf(lec2.getText())+","+Double.valueOf(d2)+",'"+secondSched+"','"
                                    +room2.getText()+"');";
                            String sql3= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code3.getText()+"','"+title3.getText()
                                    +"',"+Double.valueOf(lec3.getText())+","+Double.valueOf(d3)+",'"+thirdSched+"','"
                                    +room3.getText()+"');";
                            String sql4= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code4.getText()+"','"+title4.getText()
                                    +"',"+Double.valueOf(lec4.getText())+","+Double.valueOf(d4)+",'"+fourthSched+"','"
                                    +room4.getText()+"');";
                            String sql5= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code5.getText()+"','"+title5.getText()
                                    +"',"+Double.valueOf(lec5.getText())+","+Double.valueOf(d5)+",'"+fifthSched+"','"
                                    +room5.getText()+"');";
                            String sql6= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code6.getText()+"','"+title6.getText()
                                    +"',"+Double.valueOf(lec6.getText())+","+Double.valueOf(d6)+",'"+sixthSched+"','"
                                    +room6.getText()+"');";
                            String sql7= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code7.getText()+"','"+title7.getText()
                                    +"',"+Double.valueOf(lec7.getText())+","+Double.valueOf(d7)+",'"+seventhSched+"','"
                                    +room7.getText()+"');";
                            String sql8= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code8.getText()+"','"+title8.getText()
                                    +"',"+Double.valueOf(lec8.getText())+","+Double.valueOf(d8)+",'"+eighthSched+"','"
                                    +room8.getText()+"')";
                            stmt.addBatch(sql1);
                            stmt.addBatch(sql2);
                            stmt.addBatch(sql3);
                            stmt.addBatch(sql4);
                            stmt.addBatch(sql5);
                            stmt.addBatch(sql6);
                            stmt.addBatch(sql7);
                            stmt.addBatch(sql8);
                            int[] rowAffected = stmt.executeBatch();
                            
                            if(rowAffected[7]==1){
                                JOptionPane.showMessageDialog(this,"New schedule is inserted");
                                conn.commit();
                            }else{
                                JOptionPane.showMessageDialog(this,"Something went wrong please try again");
                                conn.rollback();
                            }
                            
                        }catch (SQLException e) {
                    //rollback the transaction
                    try{
                        if(conn !=null){
                            conn.rollback();
                        }
                    }catch(SQLException s){
                        System.out.println(s);
                    }
                    System.out.println(e.getMessage());
                }finally{
                    try{
                        if(rs != null) rs.close();
                        if(ps != null) ps.close();
                        if(conn != null) conn.close();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                        }
                        break;
                    case "2nd Semester":
                        
                        if(sec.getSelectedItem().equals("Select")){
                            JOptionPane.showMessageDialog(this,"Section in needed");
                            sec.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour1.getSelectedItem().equals(shour1.getSelectedItem())&&
                                f1.getSelectedItem().equals(s1.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour1.setBorder(red.getBorder());
                            shour1.setBorder(red.getBorder());
                            f1.setBorder(red.getBorder());
                            s1.setBorder(red.getBorder());
                            fminute1.setBorder(red.getBorder());
                            sminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour2.getSelectedItem().equals(shour2.getSelectedItem())&&
                                f2.getSelectedItem().equals(s2.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour2.setBorder(red.getBorder());
                            shour2.setBorder(red.getBorder());
                            f2.setBorder(red.getBorder());
                            s2.setBorder(red.getBorder());
                            fminute2.setBorder(red.getBorder());
                            sminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour3.getSelectedItem().equals(shour3.getSelectedItem())&&
                                f3.getSelectedItem().equals(s3.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour3.setBorder(red.getBorder());
                            shour3.setBorder(red.getBorder());
                            f3.setBorder(red.getBorder());
                            s3.setBorder(red.getBorder());
                            fminute3.setBorder(red.getBorder());
                            sminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour4.getSelectedItem().equals(shour4.getSelectedItem())&&
                                f4.getSelectedItem().equals(s4.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour4.setBorder(red.getBorder());
                            shour4.setBorder(red.getBorder());
                            f4.setBorder(red.getBorder());
                            s4.setBorder(red.getBorder());
                            fminute4.setBorder(red.getBorder());
                            sminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour5.getSelectedItem().equals(shour5.getSelectedItem())&&
                                f5.getSelectedItem().equals(s5.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour5.setBorder(red.getBorder());
                            shour5.setBorder(red.getBorder());
                            f5.setBorder(red.getBorder());
                            s5.setBorder(red.getBorder());
                            fminute5.setBorder(red.getBorder());
                            sminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour6.getSelectedItem().equals(shour6.getSelectedItem())&&
                                f6.getSelectedItem().equals(s6.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour6.setBorder(red.getBorder());
                            shour6.setBorder(red.getBorder());
                            f6.setBorder(red.getBorder());
                            s6.setBorder(red.getBorder());
                            fminute6.setBorder(red.getBorder());
                            sminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour7.getSelectedItem().equals(shour7.getSelectedItem())&&
                                f7.getSelectedItem().equals(s7.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour7.setBorder(red.getBorder());
                            shour7.setBorder(red.getBorder());
                            f7.setBorder(red.getBorder());
                            s7.setBorder(red.getBorder());
                            fminute7.setBorder(red.getBorder());
                            sminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour8.getSelectedItem().equals(shour8.getSelectedItem())&&
                                f8.getSelectedItem().equals(s8.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour8.setBorder(red.getBorder());
                            shour8.setBorder(red.getBorder());
                            f8.setBorder(red.getBorder());
                            s8.setBorder(red.getBorder());
                            fminute8.setBorder(red.getBorder());
                            sminute8.setBorder(red.getBorder());
                            return;
                        }
                        //firstsubject
                        if(fhour1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour1.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(shour1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour1.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(day1.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day1.setBorder(red.getBorder());
                            return;
                        }
                        if(room1.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room1.setBorder(red.getBorder());
                            return;
                        }
                        //second subject
                        if(fhour2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour2.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(shour2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour2.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(day2.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day2.setBorder(red.getBorder());
                            return;
                        }
                        if(room2.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room2.setBorder(red.getBorder());
                            return;
                        }
                        //third subject
                        if(fhour3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour3.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(shour3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour3.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(day3.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day3.setBorder(red.getBorder());
                            return;
                        }
                        if(room3.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room3.setBorder(red.getBorder());
                            return;
                        }
                        //fourth subject
                        if(fhour4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour4.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(shour4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour4.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(day4.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day4.setBorder(red.getBorder());
                            return;
                        }
                        if(room4.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room4.setBorder(red.getBorder());
                            return;
                        }
                        //fifth subject
                        if(fhour5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour5.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(shour5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour5.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(day5.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day5.setBorder(red.getBorder());
                            return;
                        }
                        if(room5.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room5.setBorder(red.getBorder());
                            return;
                        }
                        //sixth subject
                        if(fhour6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour6.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(shour6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour6.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(day6.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day6.setBorder(red.getBorder());
                            return;
                        }
                        if(room6.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room6.setBorder(red.getBorder());
                            return;
                        }
                        //seventh subject
                        if(fhour7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour7.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(shour7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour7.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(day7.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day7.setBorder(red.getBorder());
                            return;
                        }
                        if(room7.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room7.setBorder(red.getBorder());
                            return;
                        }
                        //eight subject
                        if(fhour8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour8.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute8.setBorder(red.getBorder());
                            return;
                        }
                        if(shour8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour8.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute8.setBorder(red.getBorder());
                            return;
                        }
                        if(day8.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day8.setBorder(red.getBorder());
                            return;
                        }
                        if(room8.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room8.setBorder(red.getBorder());
                            return;
                        }
                        try{
                            conn = DriverManager.getConnection(dbURL, USER, PASS);
                            stmt = conn.createStatement();
                            conn.setAutoCommit(false);
                            String sql1= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES  ("+getSectionId(sn)+",'"+code1.getText()+"','"+title1.getText()
                                    +"',"+Double.valueOf(lec1.getText())+","+Double.valueOf(d1)+",'"+firstSched+"','"
                                    +room1.getText()+"');";
                            String sql2= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code2.getText()+"','"+title2.getText()
                                    +"',"+Double.valueOf(lec2.getText())+","+Double.valueOf(d2)+",'"+secondSched+"','"
                                    +room2.getText()+"');";
                            String sql3= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code3.getText()+"','"+title3.getText()
                                    +"',"+Double.valueOf(lec3.getText())+","+Double.valueOf(d3)+",'"+thirdSched+"','"
                                    +room3.getText()+"');";
                            String sql4= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code4.getText()+"','"+title4.getText()
                                    +"',"+Double.valueOf(lec4.getText())+","+Double.valueOf(d4)+",'"+fourthSched+"','"
                                    +room4.getText()+"');";
                            String sql5= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code5.getText()+"','"+title5.getText()
                                    +"',"+Double.valueOf(lec5.getText())+","+Double.valueOf(d5)+",'"+fifthSched+"','"
                                    +room5.getText()+"');";
                            String sql6= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code6.getText()+"','"+title6.getText()
                                    +"',"+Double.valueOf(lec6.getText())+","+Double.valueOf(d6)+",'"+sixthSched+"','"
                                    +room6.getText()+"');";
                            String sql7= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code7.getText()+"','"+title7.getText()
                                    +"',"+Double.valueOf(lec7.getText())+","+Double.valueOf(d7)+",'"+seventhSched+"','"
                                    +room7.getText()+"');";
                            String sql8= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code8.getText()+"','"+title8.getText()
                                    +"',"+Double.valueOf(lec8.getText())+","+Double.valueOf(d8)+",'"+eighthSched+"','"
                                    +room8.getText()+"')";
                            stmt.addBatch(sql1);
                            stmt.addBatch(sql2);
                            stmt.addBatch(sql3);
                            stmt.addBatch(sql4);
                            stmt.addBatch(sql5);
                            stmt.addBatch(sql6);
                            stmt.addBatch(sql7);
                            stmt.addBatch(sql8);
                            int[] rowAffected = stmt.executeBatch();
                            
                            if(rowAffected[7]==1){
                                JOptionPane.showMessageDialog(this,"New schedule is inserted");
                                conn.commit();
                            }else{
                                JOptionPane.showMessageDialog(this,"Something went wrong please try again");
                                conn.rollback();
                            }
                            
                        }catch (SQLException e) {
                    //rollback the transaction
                    try{
                        if(conn !=null){
                            conn.rollback();
                        }
                    }catch(SQLException s){
                        System.out.println(s);
                    }
                    System.out.println(e.getMessage());
                }finally{
                    try{
                        if(rs != null) rs.close();
                        if(ps != null) ps.close();
                        if(conn != null) conn.close();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                        }
                        break;
                    default:
                        break;
                }
            }else if(nos.getSelectedItem().toString().equals("BSAIT")&&
                    jComboBox2.getSelectedItem().toString().equals("3rd Year")){
                switch(sem.getSelectedItem().toString()){
                    case "1st Semester":
                        
                        if(sec.getSelectedItem().equals("Select")){
                            JOptionPane.showMessageDialog(this,"Section in needed");
                            sec.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour1.getSelectedItem().equals(shour1.getSelectedItem())&&
                                f1.getSelectedItem().equals(s1.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour1.setBorder(red.getBorder());
                            shour1.setBorder(red.getBorder());
                            f1.setBorder(red.getBorder());
                            s1.setBorder(red.getBorder());
                            fminute1.setBorder(red.getBorder());
                            sminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour2.getSelectedItem().equals(shour2.getSelectedItem())&&
                                f2.getSelectedItem().equals(s2.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour2.setBorder(red.getBorder());
                            shour2.setBorder(red.getBorder());
                            f2.setBorder(red.getBorder());
                            s2.setBorder(red.getBorder());
                            fminute2.setBorder(red.getBorder());
                            sminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour3.getSelectedItem().equals(shour3.getSelectedItem())&&
                                f3.getSelectedItem().equals(s3.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour3.setBorder(red.getBorder());
                            shour3.setBorder(red.getBorder());
                            f3.setBorder(red.getBorder());
                            s3.setBorder(red.getBorder());
                            fminute3.setBorder(red.getBorder());
                            sminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour4.getSelectedItem().equals(shour4.getSelectedItem())&&
                                f4.getSelectedItem().equals(s4.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour4.setBorder(red.getBorder());
                            shour4.setBorder(red.getBorder());
                            f4.setBorder(red.getBorder());
                            s4.setBorder(red.getBorder());
                            fminute4.setBorder(red.getBorder());
                            sminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour5.getSelectedItem().equals(shour5.getSelectedItem())&&
                                f5.getSelectedItem().equals(s5.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour5.setBorder(red.getBorder());
                            shour5.setBorder(red.getBorder());
                            f5.setBorder(red.getBorder());
                            s5.setBorder(red.getBorder());
                            fminute5.setBorder(red.getBorder());
                            sminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour6.getSelectedItem().equals(shour6.getSelectedItem())&&
                                f6.getSelectedItem().equals(s6.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour6.setBorder(red.getBorder());
                            shour6.setBorder(red.getBorder());
                            f6.setBorder(red.getBorder());
                            s6.setBorder(red.getBorder());
                            fminute6.setBorder(red.getBorder());
                            sminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour7.getSelectedItem().equals(shour7.getSelectedItem())&&
                                f7.getSelectedItem().equals(s7.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour7.setBorder(red.getBorder());
                            shour7.setBorder(red.getBorder());
                            f7.setBorder(red.getBorder());
                            s7.setBorder(red.getBorder());
                            fminute7.setBorder(red.getBorder());
                            sminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour8.getSelectedItem().equals(shour8.getSelectedItem())&&
                                f8.getSelectedItem().equals(s8.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour8.setBorder(red.getBorder());
                            shour8.setBorder(red.getBorder());
                            f8.setBorder(red.getBorder());
                            s8.setBorder(red.getBorder());
                            fminute8.setBorder(red.getBorder());
                            sminute8.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour9.getSelectedItem().equals(shour9.getSelectedItem())&&
                                f9.getSelectedItem().equals(s9.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour9.setBorder(red.getBorder());
                            shour9.setBorder(red.getBorder());
                            f9.setBorder(red.getBorder());
                            s9.setBorder(red.getBorder());
                            fminute9.setBorder(red.getBorder());
                            sminute9.setBorder(red.getBorder());
                            return;
                        }
                        //first subject 
                        if(fhour1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour1.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(shour1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour1.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(day1.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day1.setBorder(red.getBorder());
                            return;
                        }
                        if(room1.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room1.setBorder(red.getBorder());
                            return;
                        }
                        //second subject
                        if(fhour2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour2.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(shour2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour2.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(day2.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day2.setBorder(red.getBorder());
                            return;
                        }
                        if(room2.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room2.setBorder(red.getBorder());
                            return;
                        }
                        //third subject
                        if(fhour3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour3.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(shour3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour3.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(day3.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day3.setBorder(red.getBorder());
                            return;
                        }
                        if(room3.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room3.setBorder(red.getBorder());
                            return;
                        }
                        //fourth subject
                        if(fhour4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour4.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(shour4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour4.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(day4.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day4.setBorder(red.getBorder());
                            return;
                        }
                        if(room4.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room4.setBorder(red.getBorder());
                            return;
                        }
                        //fifth subject
                        if(fhour5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour5.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(shour5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour5.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(day5.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day5.setBorder(red.getBorder());
                            return;
                        }
                        if(room5.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room5.setBorder(red.getBorder());
                            return;
                        }
                        //sixth subject
                        if(fhour6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour6.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(shour6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour6.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(day6.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day6.setBorder(red.getBorder());
                            return;
                        }
                        if(room6.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room6.setBorder(red.getBorder());
                            return;
                        }
                        //seventh subject
                        if(fhour7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour7.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(shour7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour7.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(day7.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day7.setBorder(red.getBorder());
                            return;
                        }
                        if(room7.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room7.setBorder(red.getBorder());
                            return;
                        }
                        //eight subject
                        if(fhour8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour8.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute8.setBorder(red.getBorder());
                            return;
                        }
                        if(shour8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour8.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute8.setBorder(red.getBorder());
                            return;
                        }
                        if(day8.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day8.setBorder(red.getBorder());
                            return;
                        }
                        if(room8.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room8.setBorder(red.getBorder());
                            return;
                        }
                        //ninth subject
                        if(fhour9.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour9.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute9.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute9.setBorder(red.getBorder());
                            return;
                        }
                        if(shour9.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour9.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute9.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute9.setBorder(red.getBorder());
                            return;
                        }
                        if(day9.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day9.setBorder(red.getBorder());
                            return;
                        }
                        if(room9.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room9.setBorder(red.getBorder());
                            return;
                        }
                        try{
                            conn = DriverManager.getConnection(dbURL, USER, PASS);
                            stmt = conn.createStatement();
                            conn.setAutoCommit(false);
                            String sql1= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES  ("+getSectionId(sn)+",'"+code1.getText()+"','"+title1.getText()
                                    +"',"+Double.valueOf(lec1.getText())+","+Double.valueOf(d1)+",'"+firstSched+"','"
                                    +room1.getText()+"');";
                            String sql2= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code2.getText()+"','"+title2.getText()
                                    +"',"+Double.valueOf(lec2.getText())+","+Double.valueOf(d2)+",'"+secondSched+"','"
                                    +room2.getText()+"');";
                            String sql3= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code3.getText()+"','"+title3.getText()
                                    +"',"+Double.valueOf(lec3.getText())+","+Double.valueOf(d3)+",'"+thirdSched+"','"
                                    +room3.getText()+"');";
                            String sql4= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code4.getText()+"','"+title4.getText()
                                    +"',"+Double.valueOf(lec4.getText())+","+Double.valueOf(d4)+",'"+fourthSched+"','"
                                    +room4.getText()+"');";
                            String sql5= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code5.getText()+"','"+title5.getText()
                                    +"',"+Double.valueOf(lec5.getText())+","+Double.valueOf(d5)+",'"+fifthSched+"','"
                                    +room5.getText()+"');";
                            String sql6= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code6.getText()+"','"+title6.getText()
                                    +"',"+Double.valueOf(lec6.getText())+","+Double.valueOf(d6)+",'"+sixthSched+"','"
                                    +room6.getText()+"');";
                            String sql7= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code7.getText()+"','"+title7.getText()
                                    +"',"+Double.valueOf(lec7.getText())+","+Double.valueOf(d7)+",'"+seventhSched+"','"
                                    +room7.getText()+"');";
                            String sql8= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code8.getText()+"','"+title8.getText()
                                    +"',"+Double.valueOf(lec8.getText())+","+Double.valueOf(d8)+",'"+eighthSched+"','"
                                    +room8.getText()+"')";
                            String sql9= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code9.getText()+"','"+title9.getText()
                                    +"',"+Double.valueOf(lec9.getText())+","+Double.valueOf(d9)+",'"+ninthSched+"','"
                                    +room9.getText()+"');";
                            stmt.addBatch(sql1);
                            stmt.addBatch(sql2);
                            stmt.addBatch(sql3);
                            stmt.addBatch(sql4);
                            stmt.addBatch(sql5);
                            stmt.addBatch(sql6);
                            stmt.addBatch(sql7);
                            stmt.addBatch(sql8);
                            stmt.addBatch(sql9);
                            
                            int[] rowAffected = stmt.executeBatch();
                            
                            if(rowAffected[8]==1){
                                JOptionPane.showMessageDialog(this,"New schedule is inserted");
                                conn.commit();
                            }else{
                                JOptionPane.showMessageDialog(this,"Something went wrong please try again");
                                conn.rollback();
                            }
                            
                        }catch (SQLException e) {
                    //rollback the transaction
                    try{
                        if(conn !=null){
                            conn.rollback();
                        }
                    }catch(SQLException s){
                        System.out.println(s);
                    }
                    System.out.println(e.getMessage());
                }finally{
                    try{
                        if(rs != null) rs.close();
                        if(ps != null) ps.close();
                        if(conn != null) conn.close();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                        }
                        break;
                    case "2nd Semester":
                        
                        if(sec.getSelectedItem().equals("Select")){
                            JOptionPane.showMessageDialog(this,"Section in needed");
                            sec.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour1.getSelectedItem().equals(shour1.getSelectedItem())&&
                                f1.getSelectedItem().equals(s1.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour1.setBorder(red.getBorder());
                            shour1.setBorder(red.getBorder());
                            f1.setBorder(red.getBorder());
                            s1.setBorder(red.getBorder());
                            fminute1.setBorder(red.getBorder());
                            sminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour2.getSelectedItem().equals(shour2.getSelectedItem())&&
                                f2.getSelectedItem().equals(s2.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour2.setBorder(red.getBorder());
                            shour2.setBorder(red.getBorder());
                            f2.setBorder(red.getBorder());
                            s2.setBorder(red.getBorder());
                            fminute2.setBorder(red.getBorder());
                            sminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour3.getSelectedItem().equals(shour3.getSelectedItem())&&
                                f3.getSelectedItem().equals(s3.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour3.setBorder(red.getBorder());
                            shour3.setBorder(red.getBorder());
                            f3.setBorder(red.getBorder());
                            s3.setBorder(red.getBorder());
                            fminute3.setBorder(red.getBorder());
                            sminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour4.getSelectedItem().equals(shour4.getSelectedItem())&&
                                f4.getSelectedItem().equals(s4.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour4.setBorder(red.getBorder());
                            shour4.setBorder(red.getBorder());
                            f4.setBorder(red.getBorder());
                            s4.setBorder(red.getBorder());
                            fminute4.setBorder(red.getBorder());
                            sminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour5.getSelectedItem().equals(shour5.getSelectedItem())&&
                                f5.getSelectedItem().equals(s5.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour5.setBorder(red.getBorder());
                            shour5.setBorder(red.getBorder());
                            f5.setBorder(red.getBorder());
                            s5.setBorder(red.getBorder());
                            fminute5.setBorder(red.getBorder());
                            sminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour6.getSelectedItem().equals(shour6.getSelectedItem())&&
                                f6.getSelectedItem().equals(s6.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour6.setBorder(red.getBorder());
                            shour6.setBorder(red.getBorder());
                            f6.setBorder(red.getBorder());
                            s6.setBorder(red.getBorder());
                            fminute6.setBorder(red.getBorder());
                            sminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour7.getSelectedItem().equals(shour7.getSelectedItem())&&
                                f7.getSelectedItem().equals(s7.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour7.setBorder(red.getBorder());
                            shour7.setBorder(red.getBorder());
                            f7.setBorder(red.getBorder());
                            s7.setBorder(red.getBorder());
                            fminute7.setBorder(red.getBorder());
                            sminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour8.getSelectedItem().equals(shour8.getSelectedItem())&&
                                f8.getSelectedItem().equals(s8.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour8.setBorder(red.getBorder());
                            shour8.setBorder(red.getBorder());
                            f8.setBorder(red.getBorder());
                            s8.setBorder(red.getBorder());
                            fminute8.setBorder(red.getBorder());
                            sminute8.setBorder(red.getBorder());
                            return;
                        }
                        //first subject
                        if(fhour1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour1.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(shour1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour1.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(day1.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day1.setBorder(red.getBorder());
                            return;
                        }
                        if(room1.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room1.setBorder(red.getBorder());
                            return;
                        }
                        //second subject
                        if(fhour2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour2.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(shour2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour2.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(day2.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day2.setBorder(red.getBorder());
                            return;
                        }
                        if(room2.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room2.setBorder(red.getBorder());
                            return;
                        }
                        //third subject
                        if(fhour3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour3.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(shour3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour3.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(day3.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day3.setBorder(red.getBorder());
                            return;
                        }
                        if(room3.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room3.setBorder(red.getBorder());
                            return;
                        }
                        //fourth subject
                        if(fhour4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour4.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(shour4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour4.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(day4.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day4.setBorder(red.getBorder());
                            return;
                        }
                        if(room4.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room4.setBorder(red.getBorder());
                            return;
                        }
                        //fifth subject
                        if(fhour5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour5.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(shour5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour5.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(day5.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day5.setBorder(red.getBorder());
                            return;
                        }
                        if(room5.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room5.setBorder(red.getBorder());
                            return;
                        }
                        //sixth subject
                        if(fhour6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour6.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(shour6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour6.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(day6.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day6.setBorder(red.getBorder());
                            return;
                        }
                        if(room6.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room6.setBorder(red.getBorder());
                            return;
                        }
                        //seventh subject
                        if(fhour7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour7.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(shour7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour7.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(day7.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day7.setBorder(red.getBorder());
                            return;
                        }
                        if(room7.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room7.setBorder(red.getBorder());
                            return;
                        }
                        //eight subject
                        if(fhour8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour8.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute8.setBorder(red.getBorder());
                            return;
                        }
                        if(shour8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour8.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute8.setBorder(red.getBorder());
                            return;
                        }
                        if(day8.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day8.setBorder(red.getBorder());
                            return;
                        }
                        if(room8.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room8.setBorder(red.getBorder());
                            return;
                        }
                        try{
                            conn = DriverManager.getConnection(dbURL, USER, PASS);
                            stmt = conn.createStatement();
                            conn.setAutoCommit(false);
                            String sql1= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES  ("+getSectionId(sn)+",'"+code1.getText()+"','"+title1.getText()
                                    +"',"+Double.valueOf(lec1.getText())+","+Double.valueOf(d1)+",'"+firstSched+"','"
                                    +room1.getText()+"');";
                            String sql2= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code2.getText()+"','"+title2.getText()
                                    +"',"+Double.valueOf(lec2.getText())+","+Double.valueOf(d2)+",'"+secondSched+"','"
                                    +room2.getText()+"');";
                            String sql3= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code3.getText()+"','"+title3.getText()
                                    +"',"+Double.valueOf(lec3.getText())+","+Double.valueOf(d3)+",'"+thirdSched+"','"
                                    +room3.getText()+"');";
                            String sql4= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code4.getText()+"','"+title4.getText()
                                    +"',"+Double.valueOf(lec4.getText())+","+Double.valueOf(d4)+",'"+fourthSched+"','"
                                    +room4.getText()+"');";
                            String sql5= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code5.getText()+"','"+title5.getText()
                                    +"',"+Double.valueOf(lec5.getText())+","+Double.valueOf(d5)+",'"+fifthSched+"','"
                                    +room5.getText()+"');";
                            String sql6= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code6.getText()+"','"+title6.getText()
                                    +"',"+Double.valueOf(lec6.getText())+","+Double.valueOf(d6)+",'"+sixthSched+"','"
                                    +room6.getText()+"');";
                            String sql7= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code7.getText()+"','"+title7.getText()
                                    +"',"+Double.valueOf(lec7.getText())+","+Double.valueOf(d7)+",'"+seventhSched+"','"
                                    +room7.getText()+"');";
                            String sql8= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code8.getText()+"','"+title8.getText()
                                    +"',"+Double.valueOf(lec8.getText())+","+Double.valueOf(d8)+",'"+eighthSched+"','"
                                    +room8.getText()+"')";
                            stmt.addBatch(sql1);
                            stmt.addBatch(sql2);
                            stmt.addBatch(sql3);
                            stmt.addBatch(sql4);
                            stmt.addBatch(sql5);
                            stmt.addBatch(sql6);
                            stmt.addBatch(sql7);
                            stmt.addBatch(sql8);
                            int[] rowAffected = stmt.executeBatch();
                            
                            if(rowAffected[7]==1){
                                JOptionPane.showMessageDialog(this,"New schedule is inserted");
                                conn.commit();
                            }else{
                                JOptionPane.showMessageDialog(this,"Something went wrong please try again");
                                conn.rollback();
                            }
                            
                        }catch (SQLException e) {
                    //rollback the transaction
                    try{
                        if(conn !=null){
                            conn.rollback();
                        }
                    }catch(SQLException s){
                        System.out.println(s);
                    }
                    System.out.println(e.getMessage());
                }finally{
                    try{
                        if(rs != null) rs.close();
                        if(ps != null) ps.close();
                        if(conn != null) conn.close();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                        }
                        break;
                    default:
                        break;
                }
            }else if(nos.getSelectedItem().toString().equals("BSAIT")&&
                    jComboBox2.getSelectedItem().toString().equals("4th Year")){
                switch(sem.getSelectedItem().toString()){
                    case "1st Semester":
                        
                        if(sec.getSelectedItem().equals("Select")){
                            JOptionPane.showMessageDialog(this,"Section in needed");
                            sec.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour1.getSelectedItem().equals(shour1.getSelectedItem())&&
                                f1.getSelectedItem().equals(s1.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour1.setBorder(red.getBorder());
                            shour1.setBorder(red.getBorder());
                            f1.setBorder(red.getBorder());
                            s1.setBorder(red.getBorder());
                            fminute1.setBorder(red.getBorder());
                            sminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour2.getSelectedItem().equals(shour2.getSelectedItem())&&
                                f2.getSelectedItem().equals(s2.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour2.setBorder(red.getBorder());
                            shour2.setBorder(red.getBorder());
                            f2.setBorder(red.getBorder());
                            s2.setBorder(red.getBorder());
                            fminute2.setBorder(red.getBorder());
                            sminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour3.getSelectedItem().equals(shour3.getSelectedItem())&&
                                f3.getSelectedItem().equals(s3.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour3.setBorder(red.getBorder());
                            shour3.setBorder(red.getBorder());
                            f3.setBorder(red.getBorder());
                            s3.setBorder(red.getBorder());
                            fminute3.setBorder(red.getBorder());
                            sminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour4.getSelectedItem().equals(shour4.getSelectedItem())&&
                                f4.getSelectedItem().equals(s4.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour4.setBorder(red.getBorder());
                            shour4.setBorder(red.getBorder());
                            f4.setBorder(red.getBorder());
                            s4.setBorder(red.getBorder());
                            fminute4.setBorder(red.getBorder());
                            sminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour5.getSelectedItem().equals(shour5.getSelectedItem())&&
                                f5.getSelectedItem().equals(s5.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour5.setBorder(red.getBorder());
                            shour5.setBorder(red.getBorder());
                            f5.setBorder(red.getBorder());
                            s5.setBorder(red.getBorder());
                            fminute5.setBorder(red.getBorder());
                            sminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour6.getSelectedItem().equals(shour6.getSelectedItem())&&
                                f6.getSelectedItem().equals(s6.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour6.setBorder(red.getBorder());
                            shour6.setBorder(red.getBorder());
                            f6.setBorder(red.getBorder());
                            s6.setBorder(red.getBorder());
                            fminute6.setBorder(red.getBorder());
                            sminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour7.getSelectedItem().equals(shour7.getSelectedItem())&&
                                f7.getSelectedItem().equals(s7.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour7.setBorder(red.getBorder());
                            shour7.setBorder(red.getBorder());
                            f7.setBorder(red.getBorder());
                            s7.setBorder(red.getBorder());
                            fminute7.setBorder(red.getBorder());
                            sminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour8.getSelectedItem().equals(shour8.getSelectedItem())&&
                                f8.getSelectedItem().equals(s8.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour8.setBorder(red.getBorder());
                            shour8.setBorder(red.getBorder());
                            f8.setBorder(red.getBorder());
                            s8.setBorder(red.getBorder());
                            fminute8.setBorder(red.getBorder());
                            sminute8.setBorder(red.getBorder());
                            return;
                        }
                        //first subject
                        if(fhour1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour1.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(shour1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour1.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(day1.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day1.setBorder(red.getBorder());
                            return;
                        }
                        if(room1.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room1.setBorder(red.getBorder());
                            return;
                        }
                        //second subject
                        if(fhour2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour2.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(shour2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour2.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute2.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute2.setBorder(red.getBorder());
                            return;
                        }
                        if(day2.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day2.setBorder(red.getBorder());
                            return;
                        }
                        if(room2.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room2.setBorder(red.getBorder());
                            return;
                        }
                        //third subject
                        if(fhour3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour3.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(shour3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour3.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute3.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute3.setBorder(red.getBorder());
                            return;
                        }
                        if(day3.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day3.setBorder(red.getBorder());
                            return;
                        }
                        if(room3.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room3.setBorder(red.getBorder());
                            return;
                        }
                        //fourth subject
                        if(fhour4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour4.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(shour4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour4.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute4.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute4.setBorder(red.getBorder());
                            return;
                        }
                        if(day4.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day4.setBorder(red.getBorder());
                            return;
                        }
                        if(room4.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room4.setBorder(red.getBorder());
                            return;
                        }
                        //fifth subject
                        if(fhour5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour5.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(shour5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour5.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute5.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute5.setBorder(red.getBorder());
                            return;
                        }
                        if(day5.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day5.setBorder(red.getBorder());
                            return;
                        }
                        if(room5.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room5.setBorder(red.getBorder());
                            return;
                        }
                        //sixth subject
                        if(fhour6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour6.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(shour6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour6.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute6.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute6.setBorder(red.getBorder());
                            return;
                        }
                        if(day6.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day6.setBorder(red.getBorder());
                            return;
                        }
                        if(room6.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room6.setBorder(red.getBorder());
                            return;
                        }
                        //seventh subject
                        if(fhour7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour7.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(shour7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour7.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute7.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute7.setBorder(red.getBorder());
                            return;
                        }
                        if(day7.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day7.setBorder(red.getBorder());
                            return;
                        }
                        if(room7.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room7.setBorder(red.getBorder());
                            return;
                        }
                        //eight subject
                        if(fhour8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour8.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute8.setBorder(red.getBorder());
                            return;
                        }
                        if(shour8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour8.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute8.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute8.setBorder(red.getBorder());
                            return;
                        }
                        if(day8.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day8.setBorder(red.getBorder());
                            return;
                        }
                        if(room8.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room8.setBorder(red.getBorder());
                            return;
                        }
                        try{
                            conn = DriverManager.getConnection(dbURL, USER, PASS);
                            stmt = conn.createStatement();
                            conn.setAutoCommit(false);
                            String sql1= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES  ("+getSectionId(sn)+",'"+code1.getText()+"','"+title1.getText()
                                    +"',"+Double.valueOf(lec1.getText())+","+Double.valueOf(d1)+",'"+firstSched+"','"
                                    +room1.getText()+"');";
                            String sql2= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code2.getText()+"','"+title2.getText()
                                    +"',"+Double.valueOf(lec2.getText())+","+Double.valueOf(d2)+",'"+secondSched+"','"
                                    +room2.getText()+"');";
                            String sql3= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code3.getText()+"','"+title3.getText()
                                    +"',"+Double.valueOf(lec3.getText())+","+Double.valueOf(d3)+",'"+thirdSched+"','"
                                    +room3.getText()+"');";
                            String sql4= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code4.getText()+"','"+title4.getText()
                                    +"',"+Double.valueOf(lec4.getText())+","+Double.valueOf(d4)+",'"+fourthSched+"','"
                                    +room4.getText()+"');";
                            String sql5= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code5.getText()+"','"+title5.getText()
                                    +"',"+Double.valueOf(lec5.getText())+","+Double.valueOf(d5)+",'"+fifthSched+"','"
                                    +room5.getText()+"');";
                            String sql6= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code6.getText()+"','"+title6.getText()
                                    +"',"+Double.valueOf(lec6.getText())+","+Double.valueOf(d6)+",'"+sixthSched+"','"
                                    +room6.getText()+"');";
                            String sql7= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code7.getText()+"','"+title7.getText()
                                    +"',"+Double.valueOf(lec7.getText())+","+Double.valueOf(d7)+",'"+seventhSched+"','"
                                    +room7.getText()+"');";
                            String sql8= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES ("+getSectionId(sn)+",'"+code8.getText()+"','"+title8.getText()
                                    +"',"+Double.valueOf(lec8.getText())+","+Double.valueOf(d8)+",'"+eighthSched+"','"
                                    +room8.getText()+"')";
                            stmt.addBatch(sql1);
                            stmt.addBatch(sql2);
                            stmt.addBatch(sql3);
                            stmt.addBatch(sql4);
                            stmt.addBatch(sql5);
                            stmt.addBatch(sql6);
                            stmt.addBatch(sql7);
                            stmt.addBatch(sql8);
                            
                            int[] rowAffected = stmt.executeBatch();
                            
                            if(rowAffected[7]==1){
                                JOptionPane.showMessageDialog(this,"New schedule is inserted");
                                conn.commit();
                            }else{
                                JOptionPane.showMessageDialog(this,"Something went wrong please try again");
                                conn.rollback();
                            }
                            
                        }catch (SQLException e) {
                    //rollback the transaction
                    try{
                        if(conn !=null){
                            conn.rollback();
                        }
                    }catch(SQLException s){
                        System.out.println(s);
                    }
                    System.out.println(e.getMessage());
                }finally{
                    try{
                        if(rs != null) rs.close();
                        if(ps != null) ps.close();
                        if(conn != null) conn.close();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                        }
                        break;
                    case "2nd Semester":
                        
                        if(sec.getSelectedItem().equals("Select")){
                            JOptionPane.showMessageDialog(this,"Section in needed");
                            sec.setBorder(red.getBorder());
                            return;
                        }
                        if(fhour1.getSelectedItem().equals(shour1.getSelectedItem())&&
                                f1.getSelectedItem().equals(s1.getSelectedItem())){
                            JOptionPane.showMessageDialog(this,"Please input correct time format");
                            fhour1.setBorder(red.getBorder());
                            shour1.setBorder(red.getBorder());
                            f1.setBorder(red.getBorder());
                            s1.setBorder(red.getBorder());
                            fminute1.setBorder(red.getBorder());
                            sminute1.setBorder(red.getBorder());
                            return;
                        }
                        //first subject
                        if(fhour1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            fhour1.setBorder(red.getBorder());
                            return;
                        }
                        if(fminute1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            fminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(shour1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule hour must not be zero value");
                            shour1.setBorder(red.getBorder());
                            return;
                        }
                        if(sminute1.getSelectedItem().equals("--")){
                            JOptionPane.showMessageDialog(this,"Schedule minute is required");
                            sminute1.setBorder(red.getBorder());
                            return;
                        }
                        if(day1.getSelectedItem().equals("DAY")){
                            JOptionPane.showMessageDialog(this,"Schedule day is empty");
                            day1.setBorder(red.getBorder());
                            return;
                        }
                        if(room1.getText().equals("")){
                            JOptionPane.showMessageDialog(this,"Field room is empty");
                            room1.setBorder(red.getBorder());
                            return;
                        }
                        try{
                            conn = DriverManager.getConnection(dbURL, USER, PASS);
                            stmt = conn.createStatement();
                            conn.setAutoCommit(false);
                            String sql1= "INSERT INTO schedulle(sectionId,coursecode,subjecttitle,"
                                    + "lecture,laboratory,schedulledate,room)"
                                    + "VALUES  ("+getSectionId(sn)+",'"+code1.getText()+"','"+title1.getText()
                                    +"',"+0.0+","+Double.valueOf(d1)+",'"+firstSched+"','"
                                    +room1.getText()+"');";
                            stmt.addBatch(sql1);
                            int[] rowAffected = stmt.executeBatch();
                            
                            if(rowAffected[0]==1){
                                JOptionPane.showMessageDialog(this,"New schedule is inserted");
                                conn.commit();
                            }else{
                                JOptionPane.showMessageDialog(this,"Something went wrong please try again");
                                conn.rollback();
                            }
                            
                        }catch (SQLException e) {
                    //rollback the transaction
                    try{
                        if(conn !=null){
                            conn.rollback();
                        }
                    }catch(SQLException s){
                        System.out.println(s);
                    }
                    System.out.println(e.getMessage());
                }finally{
                    try{
                        if(rs != null) rs.close();
                        if(ps != null) ps.close();
                        if(conn != null) conn.close();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                        }
                        break;
                    default:
                        break;
                }
            }
        }else{
            JOptionPane.showMessageDialog(this,"Please select Course, Year and Semester ");
        }
    }//GEN-LAST:event_save2MouseClicked

    private void save2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_save2MouseEntered
        save2.setBackground(new Color(0,204,204));
    }//GEN-LAST:event_save2MouseEntered

    private void save2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_save2MouseExited
        save2.setBackground(new Color(0,102,102));
    }//GEN-LAST:event_save2MouseExited

    private void lnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lnameActionPerformed

    private void fnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fnameActionPerformed

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailActionPerformed

    private void updateBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateBtnMouseExited
        updateBtn.setBackground(new Color(0,102,102));
    }//GEN-LAST:event_updateBtnMouseExited

    private void updateBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateBtnMouseEntered
        updateBtn.setBackground(new Color(0,204,204));
    }//GEN-LAST:event_updateBtnMouseEntered

    private void updateBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateBtnMouseClicked
        String empId = searchField.getText();
        searchUserId(empId);
        controll2.setPanel(jPanel1);
        controll2.setUnshowPanel();
        controll2.setPanel(updatePanel);
        controll2.setShowPanel();
        try{
            Connection conn =DriverManager.getConnection(dbURL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement("SELECT ui.userId,ui.firstname,ui.middlename,"
                    + "ui.lastname,ui.extension,ui.position,ui.gender,ui.age,ui.status,ui.address,ui.userType,"
                    + "uc.contact,uc.email,ua.username,ua.password FROM user_info AS ui,user_contact AS uc,user_account AS"
                    + " ua WHERE ui.userId='"+empId+"' AND uc.userId=ui.userId AND ua.userId = ui.userId;");
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                lname1.setText(result.getString(4));
                fname1.setText(result.getString(2));
                mname1.setText(result.getString(3));
                extension1.setSelectedItem(result.getString(5));
                contact1.setText(result.getString(12));
                position2.setText(result.getString(6));
                gender1.setSelectedItem(result.getString(7));
                age2.setSelectedItem(Integer.toString(result.getInt(8)));
                status1.setSelectedItem(result.getString(9));
                address1.setText(result.getString(10));
                email1.setText(result.getString(13));
                userType1.setSelectedItem(result.getString(11));
                username1.setText(result.getString(14));
                password1.setText(result.getString(15));
                cpassword1.setText(result.getString(15));
            }
        }catch(SQLException err){
            System.out.println(err);
        }
    }//GEN-LAST:event_updateBtnMouseClicked

    private void doneBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_doneBtnMouseExited
        doneBtn.setBackground(new Color(0,102,102));
    }//GEN-LAST:event_doneBtnMouseExited

    private void doneBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_doneBtnMouseEntered
        doneBtn.setBackground(new Color(0,204,204));
    }//GEN-LAST:event_doneBtnMouseEntered

    private void doneBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_doneBtnMouseClicked
        searchField.setEnabled(true);
        searchBtn1.setEnabled(false);
        jScrollPane5.setVisible(true);
        controll2.setPanel(jPanel1);
        controll2.setUnshowPanel();
    }//GEN-LAST:event_doneBtnMouseClicked

    private void searchBtn1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchBtn1MouseExited
        if(jScrollPane5.isVisible()){
            controll2.setColorOfButton(new Color(0,102,102));
        }
        if(jPanel1.isVisible()){
            controll2.setColorOfButton(new Color(0,102,102));
        }
        
    }//GEN-LAST:event_searchBtn1MouseExited

    private void searchBtn1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchBtn1MouseEntered

        if(jScrollPane5.isVisible()){
            controll2.setButton(searchBtn1);
            controll2.setColorOfButton(new Color(0,204,204));
        }
        
    }//GEN-LAST:event_searchBtn1MouseEntered

    private void searchBtn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchBtn1MouseClicked
        String empId = searchField.getText();
        searchUserId(empId);
        
        if(jScrollPane5.isVisible()){
        try{
            //Class.forName("com.mysql.jdbc.Driver");
            if(!("").equals(empId)){
                Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
                conn.setAutoCommit(false);
                String sql = "SELECT ui.userId,ui.firstname,ui.middlename,"
                    + "ui.lastname,ui.extension,ui.position,ui.gender,ui.age,ui.status,ui.address,ui.userType,"
                    + "uc.contact,uc.email,ua.username FROM user_info AS ui,user_contact AS uc,user_account AS"
                    + " ua WHERE ui.userId='"+empId+"' AND uc.userId=ui.userId AND ua.userId = ui.userId";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet result = ps.executeQuery();
                while(result.next()){
                        searchField.setEnabled(false);
                        searchBtn1.setEnabled(false);
                        jScrollPane5.setVisible(false);
                        controll2.setPanel(jPanel1);
                        controll2.setShowPanel();
                        fullnamelabel.setText("Fullname:     "+result.getString(2)
                            +" "+result.getString(3)
                            +" "+result.getString(4));
                        eidlabel.setText("Employee ID:     "+result.getString(1));
                        genderlabel.setText("Gender:     "+result.getString(7));
                        agelabel.setText("Age:     "+result.getString(8));
                        statuslabel.setText("Status:     "+result.getString(8));
                        addresslabel.setText("Address:     "+result.getString(10));
                        contactlabel.setText("Contact:     "+result.getString(12));
                        emaillabel.setText("Email:     "+result.getString(13));
                        positionlabel.setText("Position:     "+result.getString(6));
                        usertypelabel.setText("User Type:     "+result.getString(11));
                        conn.commit();
                }
                conn.close();
            }else{
                JOptionPane.showMessageDialog(this, "Please enter your id");
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        }
    }//GEN-LAST:event_searchBtn1MouseClicked

    private void fname1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fname1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fname1ActionPerformed

    private void mname1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mname1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mname1ActionPerformed

    private void email1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_email1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_email1ActionPerformed

    private void lname1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lname1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lname1ActionPerformed

    private void cancelBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelBtnMouseClicked
        controll2.setPanel(updatePanel);
        controll2.setUnshowPanel();
        searchField.setEnabled(true);
        jScrollPane5.setVisible(true);
        if(jScrollPane5.isVisible()){
            JOptionPane.showMessageDialog(this,"jscrollpane5 is visible");
        }else{
            JOptionPane.showMessageDialog(this,"jscrollpane5 is not visible");
        }
    }//GEN-LAST:event_cancelBtnMouseClicked

    private void cancelBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelBtnMouseEntered
        cancelBtn.setBackground(new Color(0,204,204));
    }//GEN-LAST:event_cancelBtnMouseEntered

    private void cancelBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelBtnMouseExited
        cancelBtn.setBackground(new Color(0,102,102));
    }//GEN-LAST:event_cancelBtnMouseExited

    private void saveBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveBtnMouseClicked
        String sExtension = extension1.getSelectedItem().toString();
        String sGender = gender1.getSelectedItem().toString();
        String sStatus = status1.getSelectedItem().toString();
        String sUserType = userType1.getSelectedItem().toString();
        if("Select".equals(sExtension))
            sExtension = "";
        
        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        ResultSet rs = null;
        
            //Class.forName("com.mysql.jdbc.Driver");
            
            if(!lname1.getText().equals("")&&
                !fname1.getText().equals("")&&
                !mname1.getText().equals("")&&
                !contact1.getText().equals("")&&
                !gender1.getSelectedItem().toString().equals("Select")&&
                !address1.getText().equals("")&&
                !status1.getSelectedItem().toString().equals("Select")&&
                !email1.getText().equals("")&&
                !position2.getText().equals("")&&
                !age2.getSelectedItem().toString().equals("Select")&&
                !userType1.getSelectedItem().toString().equals("Select")&&
                !username1.getText().equals("")&&
                !password1.getText().equals("")&&
                !cpassword1.getText().equals("")){
                if(password1.getText().equals(cpassword1.getText())){
                    try{
                        conn = DriverManager.getConnection(dbURL, USER, PASS);
                        conn.setAutoCommit(false);
	                
                        String sql= "UPDATE user_info SET firstname = '"+fname1.getText()+"',"
                                + "middlename = '"+mname1.getText()+"',lastname = '"+lname1.getText()+"',"
                                + "extension ='"+sExtension+"',position = '"+position2.getText()+"', "
                                + "gender = '"+sGender+"',age = '"+age2.getSelectedItem().toString()+"',"
                                + "status = '"+sStatus+"',address = '"+address1.getText()+"',"
                                + "userType = '"+sUserType+"' WHERE userId = '"+searchField.getText()+"'";
                        ps = conn.prepareStatement(sql);
                        int rowAffected = ps.executeUpdate();
                
                        if(rowAffected==1){
                        
                            String sql2 = "UPDATE user_contact SET contact = '"+contact1.getText()+"', "
                                    + "email = '"+email1.getText()+"' WHERE userId = '"+searchField.getText()+"'";
                            ps2 = conn.prepareStatement(sql2);
                            int rowAffected2 = ps2.executeUpdate();
                            
                            if(rowAffected2==1){
                                String sql3 = "UPDATE user_account SET username = '"+username1.getText()+"', "
                                    + "password = '"+password1.getText()+"' WHERE userId = '"+searchField.getText()+"'";
                                ps3 = conn.prepareStatement(sql3);
                                int rowAffected3 = ps3.executeUpdate();
                                
                                if(rowAffected3==1){
                                    conn.commit();
                                    int ans = JOptionPane.showConfirmDialog(this, "Do you want to save?","Message",JOptionPane.YES_NO_OPTION); 
                                    if(ans == JOptionPane.YES_OPTION){
                                        JOptionPane.showMessageDialog(this,"User ID "+searchField.getText()+" is successfully updated");
                                        model = (DefaultTableModel)userTable.getModel();
                                        model.setRowCount(0);
                                        ShowTable();
                                        //reset the table
                                        controll2.setPanel(updatePanel);
                                        controll2.setUnshowPanel();
                                        searchField.setEnabled(true);
                                        searchField.setText("");
                                        jScrollPane5.setVisible(true);
                                    }else{
                                        conn.rollback();
                                    }
                                }else{
                                    conn.rollback();
                                    JOptionPane.showMessageDialog(this, "Unsuccess to update");
                                }
                            }else{
                                conn.rollback();
                                JOptionPane.showMessageDialog(this, "Unsuccess to update");
                            }
                        }else{
                            conn.rollback();
                            JOptionPane.showMessageDialog(this, "Unsuccess to update");
                        }
                    }catch (SQLException e) {
                    //rollback the transaction
                    try{
                        if(conn !=null){
                            conn.rollback();
                        }
                    }catch(SQLException s){
                        System.out.println(s);
                    }
                    System.out.println(e.getMessage());
                }finally{
                    try{
                        if(rs != null) rs.close();
                        if(ps != null) ps.close();
                        if(ps2 != null) ps2.close();
                        if(ps3 != null) ps3.close();
                        if(conn != null) conn.close();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                }
                    
                }else{
                    JOptionPane.showMessageDialog(this,"Password and confirm password must same");
                }
            }else{
                JOptionPane.showMessageDialog(this,"All fields are required");
            }
    }//GEN-LAST:event_saveBtnMouseClicked

    private void saveBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveBtnMouseEntered
        if(!lname1.getText().equals("")||
                !fname1.getText().equals("")||
                !mname1.getText().equals("")||
                !contact1.getText().equals("")||
                !gender1.getSelectedItem().toString().equals("Select")||
                !address1.getText().equals("")||
                !status1.getSelectedItem().toString().equals("Select")||
                !email1.getText().equals("")||
                !position2.getText().equals("")||
                !age2.getSelectedItem().toString().equals("Select")||
                !userType1.getSelectedItem().toString().equals("Select")||
                !username1.getText().equals("")||
                !password1.getText().equals("")||
                !cpassword1.getText().equals("")){
                    saveBtn.setBackground(new Color(0,204,204));
        }
    }//GEN-LAST:event_saveBtnMouseEntered

    private void saveBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveBtnMouseExited
        if(!lname1.getText().equals("")||
                !fname1.getText().equals("")||
                !mname1.getText().equals("")||
                !contact1.getText().equals("")||
                !gender1.getSelectedItem().toString().equals("Select")||
                !address1.getText().equals("")||
                !status1.getSelectedItem().toString().equals("Select")||
                !email1.getText().equals("")||
                !position2.getText().equals("")||
                !age2.getSelectedItem().toString().equals("Select")||
                !userType1.getSelectedItem().toString().equals("Select")||
                !username1.getText().equals("")||
                !password1.getText().equals("")||
                !cpassword1.getText().equals("")){
                    saveBtn.setBackground(new Color(0,102,102));
        }
    }//GEN-LAST:event_saveBtnMouseExited

    private void userTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userTypeActionPerformed
        switch(userType.getSelectedItem().toString()){
            case "Select":
                showEmployeeId();
                labelE.setText("Employee ID: "+eid);
                break;
            case "Admin":
                showEmployeeId();
                labelE.setText("Employee ID: "+eid);
                break;
            case "User":
                showUserId();
                labelE.setText("User ID: "+eid);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_userTypeActionPerformed

    private void fhour6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fhour6ActionPerformed
        fhour6.setBorder(fhour1.getBorder());
        fminute6.setBorder(fhour1.getBorder());
        shour6.setBorder(fhour1.getBorder());
        fminute6.setBorder(fhour1.getBorder());
        f6.setBorder(fhour1.getBorder());
        s6.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_fhour6ActionPerformed

    private void fminute5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fminute5ActionPerformed
        fhour5.setBorder(fhour1.getBorder());
        fminute5.setBorder(fhour1.getBorder());
        shour5.setBorder(fhour1.getBorder());
        sminute5.setBorder(fhour1.getBorder());
        f5.setBorder(fhour1.getBorder());
        s5.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_fminute5ActionPerformed

    private void fminute3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fminute3ActionPerformed
        fhour3.setBorder(fhour1.getBorder());
        fminute3.setBorder(fhour1.getBorder());
        shour3.setBorder(fhour1.getBorder());
        sminute3.setBorder(fhour1.getBorder());
        f3.setBorder(fhour1.getBorder());
        s3.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_fminute3ActionPerformed

    private void f2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f2ActionPerformed
        fhour2.setBorder(fhour1.getBorder());
        fminute2.setBorder(fhour1.getBorder());
        shour2.setBorder(fhour1.getBorder());
        sminute2.setBorder(fhour1.getBorder());
        f2.setBorder(fhour1.getBorder());
        s2.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_f2ActionPerformed

    private void f8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f8ActionPerformed
        fhour8.setBorder(fhour1.getBorder());
        fminute8.setBorder(fhour1.getBorder());
        shour8.setBorder(fhour1.getBorder());
        sminute8.setBorder(fhour1.getBorder());
        f8.setBorder(fhour1.getBorder());
        s8.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_f8ActionPerformed

    private void semActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_semActionPerformed
            sem.setBorder(fhour10.getBorder());
            validateSchedFields();
    }//GEN-LAST:event_semActionPerformed

    private void searchSectionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchSectionMouseClicked
        if(!jTextField1.getText().equals("")){
            model2 = (DefaultTableModel)jTable1.getModel();
            model2.setRowCount(0);
            sqlWhere="WHERE name = '"+jTextField1.getText()+"'";
            ShowTable2();
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try{
            conn = DriverManager.getConnection(dbURL, USER, PASS);
            conn.setAutoCommit(false);
            String sql = "SELECT course,yearlevel,name,gradeFrom,gradeTo FROM section WHERE name = '"+jTextField1.getText()+"'; ";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                c.setSelectedItem(rs.getString(1));
                yl.setSelectedItem(rs.getString(2));
                int length = rs.getString(3).length();
                String lastLetter = Character.toString(rs.getString(3).charAt(length-1));
                s.setSelectedItem(lastLetter);
                fag.setSelectedItem(rs.getString(4));
                tag.setSelectedItem(rs.getString(5));
            }
            jLabel87.setText("Update");
            conn.commit();
            }catch(SQLException s){
                        System.out.println(s);
            }finally{
                    try{
                        if(rs != null) rs.close();
                        if(ps != null) ps.close();
                        if(conn != null) conn.close();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
            }
        }else{
            
            model2 = (DefaultTableModel)jTable1.getModel();
            model2.setRowCount(0);
            sqlWhere="";
            ShowTable2();
        }
        
    }//GEN-LAST:event_searchSectionMouseClicked

    private void searchSectionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchSectionMouseEntered
        searchSection.setBackground(new Color(0,204,204));
    }//GEN-LAST:event_searchSectionMouseEntered

    private void searchSectionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchSectionMouseExited
        searchSection.setBackground(new Color(0,102,102));
    }//GEN-LAST:event_searchSectionMouseExited

    private void save5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_save5MouseClicked
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        if(c.getSelectedItem().toString().equals("Select")){
            JOptionPane.showMessageDialog(this, "Course is empty");
            return;
        }
        if(yl.getSelectedItem().toString().equals("Select")){
            JOptionPane.showMessageDialog(this, "Year level is empty");
            return;
        }
        if(s.getSelectedItem().toString().equals("Select")){
            JOptionPane.showMessageDialog(this, "Section is empty");
            return;
        }
        if(fag.getSelectedItem().toString().equals("Select")){
            JOptionPane.showMessageDialog(this, "Average from is empty");
            return;
        }
        if(tag.getSelectedItem().toString().equals("Select")){
            JOptionPane.showMessageDialog(this, "Average to is empty");
            return;
        }
        else{
            String sn="";
            double dgfrom = Double.parseDouble(fag.getSelectedItem().toString());
            double dgto = Double.parseDouble(tag.getSelectedItem().toString());
            if(yl.getSelectedItem().toString().equals("1st Year"))
                sn=c.getSelectedItem().toString()+" 1";
            if(yl.getSelectedItem().toString().equals("2nd Year"))
                sn=c.getSelectedItem().toString()+" 2";
            if(yl.getSelectedItem().toString().equals("3rd Year"))
                sn=c.getSelectedItem().toString()+" 3";
            if(yl.getSelectedItem().toString().equals("4th Year"))
                sn=c.getSelectedItem().toString()+" 4";
            if(s.getSelectedItem().toString().equals("1"))
                sn=sn+"-1";
            if(s.getSelectedItem().toString().equals("2"))
                sn=sn+"-2";
            if(s.getSelectedItem().toString().equals("3"))
                sn=sn+"-3";
            if(s.getSelectedItem().toString().equals("4"))
                sn=sn+"-4";
            JOptionPane.showMessageDialog(this, sn);
            try{
                conn = DriverManager.getConnection(dbURL, USER, PASS);
                conn.setAutoCommit(false);
                String sql="INSERT INTO section (course,yearlevel,name,gradeFrom,gradeTo)"
                        + "SELECT ?,?,?,?,? WHERE NOT EXISTS (SELECT name FROM section WHERE "
                        + "name = ?)LIMIT 1;";
                ps = conn.prepareStatement(sql);
                ps.setString(1, c.getSelectedItem().toString());
                ps.setString(2, yl.getSelectedItem().toString());
                ps.setString(3, sn);
                ps.setDouble(4, dgfrom);
                ps.setDouble(5, dgto);
                ps.setString(6, c.getSelectedItem().toString());
                int rowAffected = ps.executeUpdate();
                if(rowAffected == 1){
                    conn.commit();
                    model2 = (DefaultTableModel)jTable1.getModel();
                    model2.setRowCount(0);
                    ShowTable2();
                    JOptionPane.showMessageDialog(this, "New section is added");
                    
                }else{
                    conn.rollback();
                    JOptionPane.showMessageDialog(this, "Something went wrong please try again");
                }
            }catch(SQLException s){
                        System.out.println(s);
            }finally{
                    try{
                        if(rs != null) rs.close();
                        if(ps != null) ps.close();
                        if(conn != null) conn.close();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
            }
            
        }
    }//GEN-LAST:event_save5MouseClicked

    private void save5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_save5MouseEntered
        save5.setBackground(new Color(0,204,204));
    }//GEN-LAST:event_save5MouseEntered

    private void save5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_save5MouseExited
        save5.setBackground(new Color(0,102,102));
    }//GEN-LAST:event_save5MouseExited

    private void fagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fagActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fagActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
       jComboBox2.setBorder(fhour1.getBorder());
       
        /* if(nos.getSelectedItem().toString().equals("Select")&&
                 jComboBox2.getSelectedItem().toString().equals("Select")&&
                                    sem.getSelectedItem().toString().equals("Select")){
                                code1.setText("CODE");title1.setText("TITLE");lec1.setText("0.0");lab1.setText("0.0");
                                fhour1.setVisible(true);fdot1.setVisible(true);fminute1.setVisible(true);sdot1.setVisible(true);
                                f1.setVisible(true);dash1.setVisible(true);
                                shour1.setVisible(true);sminute1.setVisible(true);s1.setVisible(true);
                                day1.setVisible(true);room1.setVisible(true);
                                
                                code2.setText("CODE");title2.setText("TITLE");lec2.setText("0.0");lab2.setText("0.0");
                                fhour2.setVisible(true);fdot2.setVisible(true);fminute2.setVisible(true);sdot2.setVisible(true);
                                f2.setVisible(true);dash2.setVisible(true);
                                shour2.setVisible(true);sminute2.setVisible(true);s2.setVisible(true);
                                day2.setVisible(true);room2.setVisible(true);
                                
                                code3.setText("CODE");title3.setText("TITLE");lec3.setText("0.0");lab3.setText("0.0");
                                fhour3.setVisible(true);fdot3.setVisible(true);fminute3.setVisible(true);sdot3.setVisible(true);
                                f3.setVisible(true);dash3.setVisible(true);
                                shour3.setVisible(true);sminute3.setVisible(true);s3.setVisible(true);
                                day3.setVisible(true);room3.setVisible(true);
                                
                                code4.setText("CODE");title4.setText("TITLE");lec4.setText("0.0");lab4.setText("0.0");
                                fhour4.setVisible(true);fdot4.setVisible(true);fminute4.setVisible(true);sdot4.setVisible(true);
                                f4.setVisible(true);dash4.setVisible(true);
                                shour4.setVisible(true);sminute4.setVisible(true);s4.setVisible(true);
                                day4.setVisible(true);room4.setVisible(true);
                                
                                code5.setText("CODE");title5.setText("TITLE");lec5.setText("0.0");lab5.setText("0.0");
                                fhour5.setVisible(true);fdot5.setVisible(true);fminute5.setVisible(true);sdot5.setVisible(true);
                                f5.setVisible(true);dash5.setVisible(true);
                                shour5.setVisible(true);sminute5.setVisible(true);s5.setVisible(true);
                                day5.setVisible(true);room5.setVisible(true);
                                
                                code6.setText("CODE");title6.setText("TITLE");lec6.setText("0.0");lab6.setText("0.0");
                                fhour6.setVisible(true);fdot6.setVisible(true);fminute6.setVisible(true);sdot6.setVisible(true);
                                f6.setVisible(true);dash6.setVisible(true);
                                shour6.setVisible(true);sminute6.setVisible(true);s6.setVisible(true);
                                day6.setVisible(true);room6.setVisible(true);
                                
                                code7.setText("CODE");title7.setText("TITLE");lec7.setText("0.0");lab7.setText("0.0");
                                fhour7.setVisible(true);fdot7.setVisible(true);fminute7.setVisible(true);sdot7.setVisible(true);
                                f7.setVisible(true);dash7.setVisible(true);
                                shour7.setVisible(true);sminute7.setVisible(true);s7.setVisible(true);
                                day7.setVisible(true);room7.setVisible(true);
                                
                                code8.setText("CODE");title8.setText("TITLE");lec8.setText("0.0");lab8.setText("0.0");
                                fhour8.setVisible(true);fdot8.setVisible(true);fminute8.setVisible(true);sdot8.setVisible(true);
                                f8.setVisible(true);dash8.setVisible(true);
                                shour8.setVisible(true);sminute8.setVisible(true);s8.setVisible(true);
                                day8.setVisible(true);room8.setVisible(true);
                                
                                code9.setText("CODE");title9.setText("TITLE");lec9.setText("0.0");lab9.setText("0.0");
                                fhour9.setVisible(true);fdot9.setVisible(true);fminute9.setVisible(true);sdot9.setVisible(true);
                                f9.setVisible(true);dash9.setVisible(true);
                                shour9.setVisible(true);sminute9.setVisible(true);s9.setVisible(true);
                                day9.setVisible(true);room9.setVisible(true);
                                
                                code10.setText("CODE");title10.setText("TITLE");lec10.setText("0.0");lab10.setText("0.0");
                                fhour10.setVisible(true);fdot10.setVisible(true);fminute10.setVisible(true);sdot10.setVisible(true);
                                f10.setVisible(true);dash10.setVisible(true);
                                shour10.setVisible(true);sminute10.setVisible(true);s10.setVisible(true);
                                day10.setVisible(true);room10.setVisible(true);
                            }
                            if(nos.getSelectedItem().toString().equals("BSAIT")&&
                                    jComboBox2.getSelectedItem().toString().equals("1st Year")&&
                                    sem.getSelectedItem().toString().equals("Select")){
                                code1.setText("CODE");title1.setText("TITLE");lec1.setText("0.0");lab1.setText("0.0");
                                fhour1.setVisible(true);fdot1.setVisible(true);fminute1.setVisible(true);sdot1.setVisible(true);
                                f1.setVisible(true);dash1.setVisible(true);
                                shour1.setVisible(true);sminute1.setVisible(true);s1.setVisible(true);
                                day1.setVisible(true);room1.setVisible(true);
                                
                                code2.setText("CODE");title2.setText("TITLE");lec2.setText("0.0");lab2.setText("0.0");
                                fhour2.setVisible(true);fdot2.setVisible(true);fminute2.setVisible(true);sdot2.setVisible(true);
                                f2.setVisible(true);dash2.setVisible(true);
                                shour2.setVisible(true);sminute2.setVisible(true);s2.setVisible(true);
                                day2.setVisible(true);room2.setVisible(true);
                                
                                code3.setText("CODE");title3.setText("TITLE");lec3.setText("0.0");lab3.setText("0.0");
                                fhour3.setVisible(true);fdot3.setVisible(true);fminute3.setVisible(true);sdot3.setVisible(true);
                                f3.setVisible(true);dash3.setVisible(true);
                                shour3.setVisible(true);sminute3.setVisible(true);s3.setVisible(true);
                                day3.setVisible(true);room3.setVisible(true);
                                
                                code4.setText("CODE");title4.setText("TITLE");lec4.setText("0.0");lab4.setText("0.0");
                                fhour4.setVisible(true);fdot4.setVisible(true);fminute4.setVisible(true);sdot4.setVisible(true);
                                f4.setVisible(true);dash4.setVisible(true);
                                shour4.setVisible(true);sminute4.setVisible(true);s4.setVisible(true);
                                day4.setVisible(true);room4.setVisible(true);
                                
                                code5.setText("CODE");title5.setText("TITLE");lec5.setText("0.0");lab5.setText("0.0");
                                fhour5.setVisible(true);fdot5.setVisible(true);fminute5.setVisible(true);sdot5.setVisible(true);
                                f5.setVisible(true);dash5.setVisible(true);
                                shour5.setVisible(true);sminute5.setVisible(true);s5.setVisible(true);
                                day5.setVisible(true);room5.setVisible(true);
                                
                                code6.setText("CODE");title6.setText("TITLE");lec6.setText("0.0");lab6.setText("0.0");
                                fhour6.setVisible(true);fdot6.setVisible(true);fminute6.setVisible(true);sdot6.setVisible(true);
                                f6.setVisible(true);dash6.setVisible(true);
                                shour6.setVisible(true);sminute6.setVisible(true);s6.setVisible(true);
                                day6.setVisible(true);room6.setVisible(true);
                                
                                code7.setText("CODE");title7.setText("TITLE");lec7.setText("0.0");lab7.setText("0.0");
                                fhour7.setVisible(true);fdot7.setVisible(true);fminute7.setVisible(true);sdot7.setVisible(true);
                                f7.setVisible(true);dash7.setVisible(true);
                                shour7.setVisible(true);sminute7.setVisible(true);s7.setVisible(true);
                                day7.setVisible(true);room7.setVisible(true);
                                
                                code8.setText("CODE");title8.setText("TITLE");lec8.setText("0.0");lab8.setText("0.0");
                                fhour8.setVisible(true);fdot8.setVisible(true);fminute8.setVisible(true);sdot8.setVisible(true);
                                f8.setVisible(true);dash8.setVisible(true);
                                shour8.setVisible(true);sminute8.setVisible(true);s8.setVisible(true);
                                day8.setVisible(true);room8.setVisible(true);
                                
                                code9.setText("CODE");title9.setText("TITLE");lec9.setText("0.0");lab9.setText("0.0");
                                fhour9.setVisible(true);fdot9.setVisible(true);fminute9.setVisible(true);sdot9.setVisible(true);
                                f9.setVisible(true);dash9.setVisible(true);
                                shour9.setVisible(true);sminute9.setVisible(true);s9.setVisible(true);
                                day9.setVisible(true);room9.setVisible(true);
                                
                                code10.setText("CODE");title10.setText("TITLE");lec10.setText("0.0");lab10.setText("0.0");
                                fhour10.setVisible(true);fdot10.setVisible(true);fminute10.setVisible(true);sdot10.setVisible(true);
                                f10.setVisible(true);dash10.setVisible(true);
                                shour10.setVisible(true);sminute10.setVisible(true);s10.setVisible(true);
                                day10.setVisible(true);room10.setVisible(true);
                            }
                            if(nos.getSelectedItem().toString().equals("BSAIT")&&
                                    jComboBox2.getSelectedItem().toString().equals("1st Year")&&
                                    sem.getSelectedItem().toString().equals("1st Semester")){
                                code1.setText("GEC 1");title1.setText("Art Appreciation");lec1.setText("3.0");lab1.setText("-");
                                code2.setText("GEC 2");title2.setText("Reading in the Philippine History");lec2.setText("3.0");lab2.setText("-");
                                code3.setText("GEC 5");title3.setText("Understanding the Self");lec3.setText("3.0");lab3.setText("3.0");
                                code4.setText("IT 111");title4.setText("Introduction to Computing");lec4.setText("2.0");lab4.setText("3.0");
                                code5.setText("IT 112");title5.setText("Computer Programming 1(Java)");lec5.setText("2.0");lab5.setText("3.0");
                                code6.setText("AIT 111");title6.setText("Aviation Fundamentals");lec6.setText("2.0");lab6.setText("-");
                                code7.setText("IT 113");title7.setText("Web Development(HTML5 & CSS3 )");lec7.setText("2.0");lab7.setText("3.0");
                                code8.setText("PE 1");title8.setText("Physical Education 1");lec8.setText("2.0");lab8.setText("-");
                                code9.setText("NSTP 1");title9.setText("CWTS/ROTC1");lec9.setText("3.0");lab9.setText("-");
                                code10.setText("");code10.setText("");title10.setText("");lec10.setText("");lab10.setText("");
                                fhour10.setVisible(false);fdot10.setVisible(false);fminute10.setVisible(false);sdot10.setVisible(false);
                                f10.setVisible(false);dash10.setVisible(false);
                                shour10.setVisible(false);sminute10.setVisible(false);s10.setVisible(false);
                                day10.setVisible(false);room10.setVisible(false);
                            }
                            else if(nos.getSelectedItem().toString().equals("BSAIT")&&
                                    jComboBox2.getSelectedItem().toString().equals("1st Year")&&
                                    sem.getSelectedItem().toString().equals("2nd Semester")){
                                code1.setText("GEC 6");title1.setText("Mathematics in the Modern World");lec1.setText("3.0");lab1.setText("-");
                                code2.setText("GEC 8");title2.setText("Ethics");lec2.setText("3.0");lab2.setText("-");
                                code3.setText("PE 2");title3.setText("Physical Ecuation 2");lec3.setText("2.0");lab3.setText("-");
                                code4.setText("IT 121");title4.setText("Computer Networking 1");lec4.setText("2.0");lab4.setText("3.0");
                                code5.setText("IT 122");title5.setText("Computer Programming 2(Advance Java)");lec5.setText("2.0");lab5.setText("3.0");
                                code6.setText("IT 123");title6.setText("Intro to Human Computer Interaction");lec6.setText("3.0");lab6.setText("-");
                                code7.setText("IT 124");title7.setText("Discrete Mathematics");lec7.setText("3.0");lab7.setText("-");
                                code8.setText("AIT Elec 1");title8.setText("Professional Elective 1");lec8.setText("3.0");lab8.setText("-");
                                code9.setText("NSTP 2");title9.setText("CWTS 2/ROTC 2");lec9.setText("3.0");lab9.setText("-");
                                code10.setText("");title10.setText("");lec10.setText("");lab10.setText("");
                                fhour10.setVisible(false);fdot10.setVisible(false);fminute10.setVisible(false);sdot10.setVisible(false);
                                f10.setVisible(false);dash10.setVisible(false);
                                shour10.setVisible(false);sminute10.setVisible(false);s10.setVisible(false);
                                day10.setVisible(false);room10.setVisible(false);
                            }
                            else if(nos.getSelectedItem().toString().equals("BSAIT")&&
                                    jComboBox2.getSelectedItem().toString().equals("2nd Year")&&
                                    sem.getSelectedItem().toString().equals("1st Semester")){
                                code1.setText("GEC 4");title1.setText("Purposive Communication");lec1.setText("3.0");lab1.setText("-");
                                code2.setText("IT 211");title2.setText("Computer Networking 2");lec2.setText("2.0");lab2.setText("3.0");
                                code3.setText("IT 212");title3.setText("Object Oriented Programming 1 (Java)");lec3.setText("2.0");lab3.setText("3.0");
                                code4.setText("IT 213");title4.setText("Data Structure & Algorithms (Java)");lec4.setText("2.0");lab4.setText("3.0");
                                code5.setText("IT 214");title5.setText("Advance Web Programming (PHP,MYSQL&XI)");lec5.setText("2.0");lab5.setText("3.0");
                                code6.setText("IT 215");title6.setText("Quantitative Method (Modeling and Simulator)");lec6.setText("3.0");lab6.setText("-");
                                code7.setText("PE 3");title7.setText("Physical Education 3");lec7.setText("2.0");lab7.setText("");
                                code8.setText("AIT Elec 2");title8.setText("Profesional Elective 3");lec8.setText("2.0");lab8.setText("-");
                                code9.setText("");title9.setText("");lec9.setText("");lab9.setText("");
                                fhour9.setVisible(false);fdot9.setVisible(false);fminute9.setVisible(false);sdot9.setVisible(false);
                                f9.setVisible(false);dash9.setVisible(false);
                                shour9.setVisible(false);sminute9.setVisible(false);s9.setVisible(false);
                                day9.setVisible(false);room9.setVisible(false);
                                code10.setText("");title10.setText("");lec10.setText("");lab10.setText("");
                                fhour10.setVisible(false);fdot10.setVisible(false);fminute10.setVisible(false);sdot10.setVisible(false);
                                f10.setVisible(false);dash10.setVisible(false);
                                shour10.setVisible(false);sminute10.setVisible(false);s10.setVisible(false);
                                day10.setVisible(false);room10.setVisible(false);
                            }
                            else if(nos.getSelectedItem().toString().equals("BSAIT")&&
                                    jComboBox2.getSelectedItem().toString().equals("2nd Year")&&
                                    sem.getSelectedItem().toString().equals("2nd Semester")){
                                code1.setText("GEC 1");title1.setText("Character Building");lec1.setText("3.0");lab1.setText("-");
                                code2.setText("GEC 11");title2.setText("Introduksyong ng Pamamahayag");lec2.setText("3.0");lab2.setText("-");
                                code3.setText("IT 221");title3.setText("Aviation Information Management I");lec3.setText("2.0");lab3.setText("3.0");
                                code4.setText("IT 222");title4.setText("Computer Networking 3");lec4.setText("2.0");lab4.setText("3.0");
                                code5.setText("IT 223");title5.setText("Aviation Secure Web Development");lec5.setText("2.0");lab5.setText("3.0");
                                code6.setText("IT 224");title6.setText("Aviation System Requirement Analysis & Design");lec6.setText("3.0");lab6.setText("-");
                                code7.setText("AIT Elec 3");title7.setText("Professional Elective 3");lec7.setText("3.0");lab7.setText("-");
                                code8.setText("PE 4");title8.setText("Physcal Education 4");lec8.setText("2.0");lab8.setText("-");
                                code9.setText("");title9.setText("");lec9.setText("");lab9.setText("");
                                fhour9.setVisible(false);fdot9.setVisible(false);fminute9.setVisible(false);sdot9.setVisible(false);
                                f9.setVisible(false);dash9.setVisible(false);
                                shour9.setVisible(false);sminute9.setVisible(false);s9.setVisible(false);
                                day9.setVisible(false);room9.setVisible(false);
                                code10.setText("");title10.setText("");lec10.setText("");lab10.setText("");
                                fhour10.setVisible(false);fdot10.setVisible(false);fminute10.setVisible(false);sdot10.setVisible(false);
                                f10.setVisible(false);dash10.setVisible(false);
                                shour10.setVisible(false);sminute10.setVisible(false);s10.setVisible(false);
                                day10.setVisible(false);room10.setVisible(false);
                            }
                            else if(nos.getSelectedItem().toString().equals("BSAIT")&&
                                    jComboBox2.getSelectedItem().toString().equals("3rd Year")&&
                                    sem.getSelectedItem().toString().equals("1st Semester")){
                                code1.setText("GEC 9");title1.setText("Life & Works of Rizal");lec1.setText("3.0");lab1.setText("-");
                                code2.setText("GEC 3");title2.setText("Science Technology and Society");lec2.setText("3.0");lab2.setText("-");
                                code3.setText("GEC 10");title3.setText("Panitikan ng Pilipinas");lec3.setText("3.0");lab3.setText("-");
                                code4.setText("IT 311");title4.setText("Aviation Database Systems");lec4.setText("2.0");lab4.setText("3.0");
                                code5.setText("IT 312");title5.setText("System Integration and Architecture 1");lec5.setText("3.0");lab5.setText("-");
                                code6.setText("IT 313");title6.setText("Aviation Information Assurance & Security");lec6.setText("3.0");lab6.setText("-");
                                code7.setText("IT 314");title7.setText("Object Oriented Programming 2");lec7.setText("2.0");lab7.setText("3.0");
                                code8.setText("IT 315");title8.setText("Social & Professional Issues");lec8.setText("3.0");lab8.setText("-");
                                code9.setText("AIT Elec 4");title9.setText("Professional Elective 4");lec9.setText("3.0");lab9.setText("-");
                                code10.setText(""); title10.setText("");lec10.setText("");lab10.setText("");
                                fhour10.setVisible(false);fdot10.setVisible(false);fminute10.setVisible(false);sdot10.setVisible(false);
                                f10.setVisible(false);dash10.setVisible(false);
                                shour10.setVisible(false);sminute10.setVisible(false);s10.setVisible(false);
                                day10.setVisible(false);room10.setVisible(false);
                            }
                            else if(nos.getSelectedItem().toString().equals("BSAIT")&&
                                    jComboBox2.getSelectedItem().toString().equals("3rd Year")&&
                                    sem.getSelectedItem().toString().equals("2nd Semester")){
                                code1.setText("AIT Elec 5");title1.setText("");lec1.setText("");lab1.setText("");
                                code2.setText("IT 321");title2.setText("");lec2.setText("");lab2.setText("");
                                code3.setText("IT 322");title3.setText("");lec3.setText("");lab3.setText("");
                                code4.setText("IT 323");title4.setText("");lec4.setText("");lab4.setText("");
                                code5.setText("IT 324");title5.setText("");lec5.setText("");lab5.setText("");
                                code6.setText("IT 325");title6.setText("");lec6.setText("");lab6.setText("");
                                code7.setText("IT 326");title7.setText("");lec7.setText("");lab7.setText("");
                                code8.setText("IT 327");title8.setText("");lec8.setText("");lab8.setText("");
                                code9.setText("");title9.setText("");lec9.setText("");lab9.setText("");
                                fhour10.setVisible(false);fdot10.setVisible(false);fminute10.setVisible(false);sdot10.setVisible(false);
                                f9.setVisible(false);dash9.setVisible(false);
                                shour9.setVisible(false);sminute9.setVisible(false);s9.setVisible(false);
                                day9.setVisible(false);room9.setVisible(false);
                                code10.setText("");title10.setText("");lec10.setText("");lab10.setText("");
                                fhour10.setVisible(false);fdot10.setVisible(false);fminute10.setVisible(false);sdot10.setVisible(false);
                                f10.setVisible(false);dash10.setVisible(false);
                                shour10.setVisible(false);sminute10.setVisible(false);s10.setVisible(false);
                                day10.setVisible(false);room10.setVisible(false);
                            }
                            else if(nos.getSelectedItem().toString().equals("BSAIT")&&
                                    jComboBox2.getSelectedItem().toString().equals("4th Year")&&
                                    sem.getSelectedItem().toString().equals("1st Semester")){
                                code1.setText("GEC 12");title1.setText("Environmental Science");lec1.setText("3.0");lab1.setText("-");
                                code2.setText("GEC 7");title2.setText("Contemporary World");lec2.setText("3.0");lab2.setText("-");
                                code3.setText("IT 411");title3.setText("Capstone Project 2");lec3.setText("3.0");lab3.setText("-");
                                code4.setText("IT 412");title4.setText("Aviation System Administration & Maintenance");lec4.setText("2.0");lab4.setText("3.0");
                                code5.setText("IT 413");title5.setText("Project Quality Management System");lec5.setText("3.0");lab5.setText("-");
                                code6.setText("IT 414");title6.setText("Aviation Database Administration");lec6.setText("2.0");lab6.setText("3.0");
                                code7.setText("IT 415");title7.setText("Virtualization & Cloud Platforms");lec7.setText("3.0");lab7.setText("-");
                                code8.setText("AIT Elec 6");title8.setText("Professional Elective 6");lec8.setText("3.0");lab8.setText("-");
                                code9.setText("");title9.setText("");lec9.setText("");lab9.setText("");
                                fhour9.setVisible(false);fdot9.setVisible(false);fminute9.setVisible(false);sdot9.setVisible(false);
                                f9.setVisible(false);dash9.setVisible(false);
                                shour9.setVisible(false);sminute9.setVisible(false);s9.setVisible(false);
                                day9.setVisible(false);room9.setVisible(false);
                                code10.setText("");title10.setText("");lec10.setText("");lab10.setText("");
                                fhour10.setVisible(false);fdot10.setVisible(false);fminute10.setVisible(false);sdot10.setVisible(false);
                                f10.setVisible(false);dash10.setVisible(false);
                                shour10.setVisible(false);sminute10.setVisible(false);s10.setVisible(false);
                                day10.setVisible(false);room10.setVisible(false);
                            }
                            else if(nos.getSelectedItem().toString().equals("BSAIT")&&
                                    jComboBox2.getSelectedItem().toString().equals("4th Year")&&
                                    sem.getSelectedItem().toString().equals("2nd Semester")){
                                code1.setText("IT 421");title1.setText("Internship/On The Job Training");lec1.setText("-");lab1.setText("18.0");
                                
                                code2.setText("");title2.setText("");lec2.setText("");lab2.setText("");
                                fhour2.setVisible(false);fdot2.setVisible(false);fminute2.setVisible(false);sdot2.setVisible(false);
                                f2.setVisible(false);dash2.setVisible(false);
                                shour2.setVisible(false);sminute2.setVisible(false);s2.setVisible(false);
                                day2.setVisible(false);room2.setVisible(false);
                                
                                code3.setText("");title3.setText("");lec3.setText("");lab3.setText("");
                                fhour3.setVisible(false);fdot3.setVisible(false);fminute3.setVisible(false);sdot3.setVisible(false);
                                f3.setVisible(false);dash3.setVisible(false);
                                shour3.setVisible(false);sminute3.setVisible(false);s3.setVisible(false);
                                day3.setVisible(false);room3.setVisible(false);
                                
                                code4.setText("");title4.setText("");lec4.setText("");lab4.setText("");
                                fhour4.setVisible(false);fdot4.setVisible(false);fminute4.setVisible(false);sdot4.setVisible(false);
                                f4.setVisible(false);dash4.setVisible(false);
                                shour4.setVisible(false);sminute4.setVisible(false);s4.setVisible(false);
                                day4.setVisible(false);room4.setVisible(false);
                                
                                code5.setText("");title5.setText("");lec5.setText("");lab5.setText("");
                                fhour5.setVisible(false);fdot5.setVisible(false);fminute5.setVisible(false);sdot5.setVisible(false);
                                f5.setVisible(false);dash5.setVisible(false);
                                shour5.setVisible(false);sminute5.setVisible(false);s5.setVisible(false);
                                day5.setVisible(false);room5.setVisible(false);
                                
                                code6.setText("");title6.setText("");lec6.setText("");lab6.setText("");
                                fhour6.setVisible(false);fdot6.setVisible(false);fminute6.setVisible(false);sdot6.setVisible(false);
                                f6.setVisible(false);dash6.setVisible(false);
                                shour6.setVisible(false);sminute6.setVisible(false);s6.setVisible(false);
                                day6.setVisible(false);room6.setVisible(false);
                                
                                code7.setText("");title7.setText("");lec7.setText("");lab7.setText("");
                                fhour7.setVisible(false);fdot7.setVisible(false);fminute7.setVisible(false);sdot7.setVisible(false);
                                f7.setVisible(false);dash7.setVisible(false);
                                shour7.setVisible(false);sminute7.setVisible(false);s7.setVisible(false);
                                day7.setVisible(false);room7.setVisible(false);
                                
                                code8.setText("");title8.setText("");lec8.setText("");lab8.setText("");
                                fhour8.setVisible(false);fdot8.setVisible(false);fminute8.setVisible(false);sdot8.setVisible(false);
                                f8.setVisible(false);dash8.setVisible(false);
                                shour8.setVisible(false);sminute8.setVisible(false);s8.setVisible(false);
                                day8.setVisible(false);room8.setVisible(false);
                                
                                code9.setText("");title9.setText("");lec9.setText("");lab9.setText("");
                                fhour9.setVisible(false);fdot9.setVisible(false);fminute9.setVisible(false);sdot9.setVisible(false);
                                f9.setVisible(false);dash9.setVisible(false);
                                shour9.setVisible(false);sminute9.setVisible(false);s9.setVisible(false);
                                day9.setVisible(false);room9.setVisible(false);
                                
                                code10.setText("");title10.setText("");lec10.setText("");lab10.setText("");
                                fhour10.setVisible(false);fdot10.setVisible(false);fminute10.setVisible(false);sdot10.setVisible(false);
                                f10.setVisible(false);dash10.setVisible(false);
                                shour10.setVisible(false);sminute10.setVisible(false);s10.setVisible(false);
                                day10.setVisible(false);room10.setVisible(false);
                            }*/
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        if(!jTextField1.getText().equals("")){
            c.setSelectedItem("Select");
            yl.setSelectedItem("Select");
            s.setSelectedItem("Select");
            fag.setSelectedItem("Select");
            tag.setSelectedItem("Select");
            jLabel87.setText("Add Section");
        }
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void secActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secActionPerformed
             sec.setBorder(fhour1.getBorder());
        
    }//GEN-LAST:event_secActionPerformed

    private void secMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_secMouseClicked
        
    }//GEN-LAST:event_secMouseClicked

    private void secMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_secMouseEntered
        if(nos.getSelectedItem().toString().equals("Select")){
            JOptionPane.showMessageDialog(this,"Course is empty");
            nos.setBorder(red.getBorder());
            return;
        }
        if(jComboBox2.getSelectedItem().toString().equals("Select")){
            JOptionPane.showMessageDialog(this,"Year Level is empty");
            jComboBox2.setBorder(red.getBorder());
            return;
        }
        if(sem.getSelectedItem().toString().equals("Select")){
            JOptionPane.showMessageDialog(this,"Semester is empty");
            sem.setBorder(red.getBorder());
            return;
        }
            getSectionName();
        
    }//GEN-LAST:event_secMouseEntered

    private void day1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_day1MouseClicked
        
    }//GEN-LAST:event_day1MouseClicked

    private void shour2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shour2ActionPerformed
       fhour2.setBorder(fhour1.getBorder());
        fminute2.setBorder(fhour1.getBorder());
        shour2.setBorder(fhour1.getBorder());
        sminute2.setBorder(fhour1.getBorder());
        f2.setBorder(fhour1.getBorder());
        s2.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_shour2ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        if(jTextField1.getText().equals("")){
            model2 = (DefaultTableModel)jTable1.getModel();
            model2.setRowCount(0);
            sqlWhere=jTextField1.getText();
            ShowTable2();
            c.setSelectedItem("Select");
            yl.setSelectedItem("Select");
            s.setSelectedItem("Select");
            fag.setSelectedItem("Select");
            tag.setSelectedItem("Select");
            jLabel87.setText("Add Section");
        }
    }//GEN-LAST:event_jTextField1KeyTyped

    private void searchFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchFieldMouseClicked
     searchLabel.setVisible(false);        
    }//GEN-LAST:event_searchFieldMouseClicked

    private void searchFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchFieldKeyTyped
        
    }//GEN-LAST:event_searchFieldKeyTyped

    private void searchFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchFieldMouseReleased
        
    }//GEN-LAST:event_searchFieldMouseReleased

    private void searchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchFieldKeyReleased
        if(searchField.getText().equals(""))searchLabel.setVisible(true);
    }//GEN-LAST:event_searchFieldKeyReleased

    private void fhour1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fhour1ActionPerformed
        fhour1.setBorder(fhour2.getBorder());
        fminute1.setBorder(fhour2.getBorder());
        shour1.setBorder(fhour2.getBorder());
        sminute1.setBorder(fhour2.getBorder());
        f1.setBorder(fhour2.getBorder());
        s1.setBorder(fhour2.getBorder());
    }//GEN-LAST:event_fhour1ActionPerformed

    private void fhour2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fhour2ActionPerformed
        fhour2.setBorder(fhour1.getBorder());
        fminute2.setBorder(fhour1.getBorder());
        shour2.setBorder(fhour1.getBorder());
        sminute2.setBorder(fhour1.getBorder());
        f2.setBorder(fhour1.getBorder());
        s2.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_fhour2ActionPerformed

    private void fhour3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fhour3ActionPerformed
        fhour3.setBorder(fhour1.getBorder());
        fminute3.setBorder(fhour1.getBorder());
        shour3.setBorder(fhour1.getBorder());
        sminute3.setBorder(fhour1.getBorder());
        f3.setBorder(fhour1.getBorder());
        s3.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_fhour3ActionPerformed

    private void fhour4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fhour4ActionPerformed
        fhour4.setBorder(fhour1.getBorder());
        fminute4.setBorder(fhour1.getBorder());
        shour4.setBorder(fhour1.getBorder());
        sminute4.setBorder(fhour1.getBorder());
        f4.setBorder(fhour1.getBorder());
        s4.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_fhour4ActionPerformed

    private void fhour5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fhour5ActionPerformed
        fhour5.setBorder(fhour1.getBorder());
        fminute5.setBorder(fhour1.getBorder());
        shour5.setBorder(fhour1.getBorder());
        sminute5.setBorder(fhour1.getBorder());
        f5.setBorder(fhour1.getBorder());
        s5.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_fhour5ActionPerformed

    private void fhour7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fhour7ActionPerformed
        fhour7.setBorder(fhour1.getBorder());
        fminute7.setBorder(fhour1.getBorder());
        shour7.setBorder(fhour1.getBorder());
        sminute7.setBorder(fhour1.getBorder());
        f7.setBorder(fhour1.getBorder());
        s7.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_fhour7ActionPerformed

    private void fhour8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fhour8ActionPerformed
        fhour8.setBorder(fhour1.getBorder());
        fminute8.setBorder(fhour1.getBorder());
        shour8.setBorder(fhour1.getBorder());
        sminute8.setBorder(fhour1.getBorder());
        f8.setBorder(fhour1.getBorder());
        s8.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_fhour8ActionPerformed

    private void fhour9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fhour9ActionPerformed
        fhour9.setBorder(fhour1.getBorder());
        fminute9.setBorder(fhour1.getBorder());
        shour9.setBorder(fhour1.getBorder());
        sminute9.setBorder(fhour1.getBorder());
        f9.setBorder(fhour1.getBorder());
        s9.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_fhour9ActionPerformed

    private void fhour10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fhour10ActionPerformed
        fhour10.setBorder(fhour1.getBorder());
        fminute10.setBorder(fhour1.getBorder());
        shour10.setBorder(fhour1.getBorder());
        sminute10.setBorder(fhour1.getBorder());
        f10.setBorder(fhour1.getBorder());
        s10.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_fhour10ActionPerformed

    private void fminute1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fminute1ActionPerformed
        fminute1.setBorder(fhour2.getBorder());
        fhour1.setBorder(fhour2.getBorder());
        shour1.setBorder(fhour2.getBorder());
        sminute1.setBorder(fhour2.getBorder());
        f1.setBorder(fhour2.getBorder());
        s1.setBorder(fhour2.getBorder());
    }//GEN-LAST:event_fminute1ActionPerformed

    private void fminute2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fminute2ActionPerformed
        fhour2.setBorder(fhour1.getBorder());
        fminute2.setBorder(fhour1.getBorder());
        shour2.setBorder(fhour1.getBorder());
        sminute2.setBorder(fhour1.getBorder());
        f2.setBorder(fhour1.getBorder());
        s2.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_fminute2ActionPerformed

    private void fminute4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fminute4ActionPerformed
        fhour4.setBorder(fhour1.getBorder());
        fminute4.setBorder(fhour1.getBorder());
        shour4.setBorder(fhour1.getBorder());
        sminute4.setBorder(fhour1.getBorder());
        f4.setBorder(fhour1.getBorder());
        s4.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_fminute4ActionPerformed

    private void fminute6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fminute6ActionPerformed
        fhour6.setBorder(fhour1.getBorder());
        fminute6.setBorder(fhour1.getBorder());
        shour6.setBorder(fhour1.getBorder());
        fminute6.setBorder(fhour1.getBorder());
        f6.setBorder(fhour1.getBorder());
        s6.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_fminute6ActionPerformed

    private void fminute7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fminute7ActionPerformed
        fhour7.setBorder(fhour1.getBorder());
        fminute7.setBorder(fhour1.getBorder());
        shour7.setBorder(fhour1.getBorder());
        sminute7.setBorder(fhour1.getBorder());
        f7.setBorder(fhour1.getBorder());
        s7.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_fminute7ActionPerformed

    private void fminute8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fminute8ActionPerformed
        fhour8.setBorder(fhour1.getBorder());
        fminute8.setBorder(fhour1.getBorder());
        shour8.setBorder(fhour1.getBorder());
        sminute8.setBorder(fhour1.getBorder());
        f8.setBorder(fhour1.getBorder());
        s8.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_fminute8ActionPerformed

    private void fminute9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fminute9ActionPerformed
        fhour9.setBorder(fhour1.getBorder());
        fminute9.setBorder(fhour1.getBorder());
        shour9.setBorder(fhour1.getBorder());
        sminute9.setBorder(fhour1.getBorder());
        f9.setBorder(fhour1.getBorder());
        s9.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_fminute9ActionPerformed

    private void fminute10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fminute10ActionPerformed
        fhour10.setBorder(fhour1.getBorder());
        fminute10.setBorder(fhour1.getBorder());
        shour10.setBorder(fhour1.getBorder());
        sminute10.setBorder(fhour1.getBorder());
        f10.setBorder(fhour1.getBorder());
        s10.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_fminute10ActionPerformed

    private void shour1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shour1ActionPerformed
        shour1.setBorder(fhour2.getBorder());
        fhour1.setBorder(fhour2.getBorder());
        fminute1.setBorder(fhour2.getBorder());
        sminute1.setBorder(fhour2.getBorder());
        f1.setBorder(fhour2.getBorder());
        s1.setBorder(fhour2.getBorder());
    }//GEN-LAST:event_shour1ActionPerformed

    private void shour3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shour3ActionPerformed
        fhour3.setBorder(fhour1.getBorder());
        fminute3.setBorder(fhour1.getBorder());
        shour3.setBorder(fhour1.getBorder());
        sminute3.setBorder(fhour1.getBorder());
        f3.setBorder(fhour1.getBorder());
        s3.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_shour3ActionPerformed

    private void shour4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shour4ActionPerformed
        fhour4.setBorder(fhour1.getBorder());
        fminute4.setBorder(fhour1.getBorder());
        shour4.setBorder(fhour1.getBorder());
        sminute4.setBorder(fhour1.getBorder());
        f4.setBorder(fhour1.getBorder());
        s4.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_shour4ActionPerformed

    private void shour5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shour5ActionPerformed
        fhour5.setBorder(fhour1.getBorder());
        fminute5.setBorder(fhour1.getBorder());
        shour5.setBorder(fhour1.getBorder());
        sminute5.setBorder(fhour1.getBorder());
        f5.setBorder(fhour1.getBorder());
        s5.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_shour5ActionPerformed

    private void shour7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shour7ActionPerformed
        fhour7.setBorder(fhour1.getBorder());
        fminute7.setBorder(fhour1.getBorder());
        shour7.setBorder(fhour1.getBorder());
        sminute7.setBorder(fhour1.getBorder());
        f7.setBorder(fhour1.getBorder());
        s7.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_shour7ActionPerformed

    private void shour8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shour8ActionPerformed
        fhour8.setBorder(fhour1.getBorder());
        fminute8.setBorder(fhour1.getBorder());
        shour8.setBorder(fhour1.getBorder());
        sminute8.setBorder(fhour1.getBorder());
        f8.setBorder(fhour1.getBorder());
        s8.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_shour8ActionPerformed

    private void shour9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shour9ActionPerformed
        fhour9.setBorder(fhour1.getBorder());
        fminute9.setBorder(fhour1.getBorder());
        shour9.setBorder(fhour1.getBorder());
        sminute9.setBorder(fhour1.getBorder());
        f9.setBorder(fhour1.getBorder());
        s9.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_shour9ActionPerformed

    private void shour10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shour10ActionPerformed
        fhour10.setBorder(fhour1.getBorder());
        fminute10.setBorder(fhour1.getBorder());
        shour10.setBorder(fhour1.getBorder());
        sminute10.setBorder(fhour1.getBorder());
        f10.setBorder(fhour1.getBorder());
        s10.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_shour10ActionPerformed

    private void sminute1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sminute1ActionPerformed
        fhour1.setBorder(fhour2.getBorder());
        fminute1.setBorder(fhour2.getBorder());
        shour1.setBorder(fhour2.getBorder());
        sminute1.setBorder(fhour2.getBorder());
        f1.setBorder(fhour2.getBorder());
        s1.setBorder(fhour2.getBorder());
    }//GEN-LAST:event_sminute1ActionPerformed

    private void sminute2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sminute2ActionPerformed
        fhour2.setBorder(fhour1.getBorder());
        fminute2.setBorder(fhour1.getBorder());
        shour2.setBorder(fhour1.getBorder());
        sminute2.setBorder(fhour1.getBorder());
        f2.setBorder(fhour1.getBorder());
        s2.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_sminute2ActionPerformed

    private void sminute4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sminute4ActionPerformed
        fhour4.setBorder(fhour1.getBorder());
        fminute4.setBorder(fhour1.getBorder());
        shour4.setBorder(fhour1.getBorder());
        sminute4.setBorder(fhour1.getBorder());
        f4.setBorder(fhour1.getBorder());
        s4.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_sminute4ActionPerformed

    private void sminute6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sminute6ActionPerformed
        fhour6.setBorder(fhour1.getBorder());
        fminute6.setBorder(fhour1.getBorder());
        shour6.setBorder(fhour1.getBorder());
        fminute6.setBorder(fhour1.getBorder());
        f6.setBorder(fhour1.getBorder());
        s6.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_sminute6ActionPerformed

    private void sminute7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sminute7ActionPerformed
        fhour7.setBorder(fhour1.getBorder());
        fminute7.setBorder(fhour1.getBorder());
        shour7.setBorder(fhour1.getBorder());
        sminute7.setBorder(fhour1.getBorder());
        f7.setBorder(fhour1.getBorder());
        s7.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_sminute7ActionPerformed

    private void sminute8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sminute8ActionPerformed
        fhour8.setBorder(fhour1.getBorder());
        fminute8.setBorder(fhour1.getBorder());
        shour8.setBorder(fhour1.getBorder());
        sminute8.setBorder(fhour1.getBorder());
        f8.setBorder(fhour1.getBorder());
        s8.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_sminute8ActionPerformed

    private void sminute9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sminute9ActionPerformed
        fhour9.setBorder(fhour1.getBorder());
        fminute9.setBorder(fhour1.getBorder());
        shour9.setBorder(fhour1.getBorder());
        sminute9.setBorder(fhour1.getBorder());
        f9.setBorder(fhour1.getBorder());
        s9.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_sminute9ActionPerformed

    private void sminute10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sminute10ActionPerformed
        fhour10.setBorder(fhour1.getBorder());
        fminute10.setBorder(fhour1.getBorder());
        shour10.setBorder(fhour1.getBorder());
        sminute10.setBorder(fhour1.getBorder());
        f10.setBorder(fhour1.getBorder());
        s10.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_sminute10ActionPerformed

    private void day1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_day1ActionPerformed
        day1.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_day1ActionPerformed

    private void day2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_day2ActionPerformed
        day2.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_day2ActionPerformed

    private void day3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_day3ActionPerformed
        day3.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_day3ActionPerformed

    private void day4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_day4ActionPerformed
        day4.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_day4ActionPerformed

    private void day5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_day5ActionPerformed
        day5.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_day5ActionPerformed

    private void day6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_day6ActionPerformed
        day6.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_day6ActionPerformed

    private void day9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_day9ActionPerformed
        day9.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_day9ActionPerformed

    private void day10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_day10ActionPerformed
        day10.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_day10ActionPerformed

    private void room1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_room1ActionPerformed
        room1.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_room1ActionPerformed

    private void room2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_room2ActionPerformed
        room2.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_room2ActionPerformed

    private void room3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_room3ActionPerformed
        room3.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_room3ActionPerformed

    private void room4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_room4ActionPerformed
        room4.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_room4ActionPerformed

    private void room5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_room5ActionPerformed
        room5.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_room5ActionPerformed

    private void room6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_room6ActionPerformed
        room6.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_room6ActionPerformed

    private void room7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_room7ActionPerformed
        room7.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_room7ActionPerformed

    private void room8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_room8ActionPerformed
        room8.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_room8ActionPerformed

    private void room9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_room9ActionPerformed
        room9.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_room9ActionPerformed

    private void room10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_room10ActionPerformed
        room10.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_room10ActionPerformed

    private void f1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f1ActionPerformed
        fhour1.setBorder(fhour2.getBorder());
        fminute1.setBorder(fhour2.getBorder());
        shour1.setBorder(fhour2.getBorder());
        sminute1.setBorder(fhour2.getBorder());
        f1.setBorder(fhour2.getBorder());
        s1.setBorder(fhour2.getBorder());
    }//GEN-LAST:event_f1ActionPerformed

    private void s1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s1ActionPerformed
        fhour1.setBorder(fhour2.getBorder());
        fminute1.setBorder(fhour2.getBorder());
        shour1.setBorder(fhour2.getBorder());
        sminute1.setBorder(fhour2.getBorder());
        f1.setBorder(fhour2.getBorder());
        s1.setBorder(fhour2.getBorder());
    }//GEN-LAST:event_s1ActionPerformed

    private void f3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f3ActionPerformed
        fhour3.setBorder(fhour1.getBorder());
        fminute3.setBorder(fhour1.getBorder());
        shour3.setBorder(fhour1.getBorder());
        sminute3.setBorder(fhour1.getBorder());
        f3.setBorder(fhour1.getBorder());
        s3.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_f3ActionPerformed

    private void s3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s3ActionPerformed
        fhour3.setBorder(fhour1.getBorder());
        fminute3.setBorder(fhour1.getBorder());
        shour3.setBorder(fhour1.getBorder());
        sminute3.setBorder(fhour1.getBorder());
        f3.setBorder(fhour1.getBorder());
        s3.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_s3ActionPerformed

    private void f4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f4ActionPerformed
        fhour4.setBorder(fhour1.getBorder());
        fminute4.setBorder(fhour1.getBorder());
        shour4.setBorder(fhour1.getBorder());
        sminute4.setBorder(fhour1.getBorder());
        f4.setBorder(fhour1.getBorder());
        s4.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_f4ActionPerformed

    private void s4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s4ActionPerformed
        fhour4.setBorder(fhour1.getBorder());
        fminute4.setBorder(fhour1.getBorder());
        shour4.setBorder(fhour1.getBorder());
        sminute4.setBorder(fhour1.getBorder());
        f4.setBorder(fhour1.getBorder());
        s4.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_s4ActionPerformed

    private void f5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f5ActionPerformed
        fhour5.setBorder(fhour1.getBorder());
        fminute5.setBorder(fhour1.getBorder());
        shour5.setBorder(fhour1.getBorder());
        sminute5.setBorder(fhour1.getBorder());
        f5.setBorder(fhour1.getBorder());
        s5.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_f5ActionPerformed

    private void s5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s5ActionPerformed
        fhour5.setBorder(fhour1.getBorder());
        fminute5.setBorder(fhour1.getBorder());
        shour5.setBorder(fhour1.getBorder());
        sminute5.setBorder(fhour1.getBorder());
        f5.setBorder(fhour1.getBorder());
        s5.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_s5ActionPerformed

    private void f6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f6ActionPerformed
        fhour6.setBorder(fhour1.getBorder());
        fminute6.setBorder(fhour1.getBorder());
        shour6.setBorder(fhour1.getBorder());
        fminute6.setBorder(fhour1.getBorder());
        f6.setBorder(fhour1.getBorder());
        s6.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_f6ActionPerformed

    private void s6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s6ActionPerformed
        fhour6.setBorder(fhour1.getBorder());
        fminute6.setBorder(fhour1.getBorder());
        shour6.setBorder(fhour1.getBorder());
        fminute6.setBorder(fhour1.getBorder());
        f6.setBorder(fhour1.getBorder());
        s6.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_s6ActionPerformed

    private void f7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f7ActionPerformed
        fhour7.setBorder(fhour1.getBorder());
        fminute7.setBorder(fhour1.getBorder());
        shour7.setBorder(fhour1.getBorder());
        sminute7.setBorder(fhour1.getBorder());
        f7.setBorder(fhour1.getBorder());
        s7.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_f7ActionPerformed

    private void s7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s7ActionPerformed
        fhour7.setBorder(fhour1.getBorder());
        fminute7.setBorder(fhour1.getBorder());
        shour7.setBorder(fhour1.getBorder());
        sminute7.setBorder(fhour1.getBorder());
        f7.setBorder(fhour1.getBorder());
        s7.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_s7ActionPerformed

    private void f9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f9ActionPerformed
        fhour9.setBorder(fhour1.getBorder());
        fminute9.setBorder(fhour1.getBorder());
        shour9.setBorder(fhour1.getBorder());
        sminute9.setBorder(fhour1.getBorder());
        f9.setBorder(fhour1.getBorder());
        s9.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_f9ActionPerformed

    private void s9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s9ActionPerformed
        fhour9.setBorder(fhour1.getBorder());
        fminute9.setBorder(fhour1.getBorder());
        shour9.setBorder(fhour1.getBorder());
        sminute9.setBorder(fhour1.getBorder());
        f9.setBorder(fhour1.getBorder());
        s9.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_s9ActionPerformed

    private void f10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f10ActionPerformed
        fhour10.setBorder(fhour1.getBorder());
        fminute10.setBorder(fhour1.getBorder());
        shour10.setBorder(fhour1.getBorder());
        sminute10.setBorder(fhour1.getBorder());
        f10.setBorder(fhour1.getBorder());
        s10.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_f10ActionPerformed

    private void s10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s10ActionPerformed
        fhour10.setBorder(fhour1.getBorder());
        fminute10.setBorder(fhour1.getBorder());
        shour10.setBorder(fhour1.getBorder());
        sminute10.setBorder(fhour1.getBorder());
        f10.setBorder(fhour1.getBorder());
        s10.setBorder(fhour1.getBorder());
    }//GEN-LAST:event_s10ActionPerformed

    private void userInfoPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userInfoPanelMouseClicked
        
    }//GEN-LAST:event_userInfoPanelMouseClicked

    private void schedPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_schedPanelMouseClicked
        if(searchField.getText().equals(""))searchLabel.setVisible(true);
    }//GEN-LAST:event_schedPanelMouseClicked

    private void searchFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchFieldKeyPressed
        searchLabel.setVisible(false);
    }//GEN-LAST:event_searchFieldKeyPressed

    private void passwordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordKeyPressed
        if(password.getText().equals("Enter password..")){
            password.setText("");
            password.setForeground(Color.black);
        }
        passX.setVisible(false);
        cpassX.setVisible(false);
    }//GEN-LAST:event_passwordKeyPressed

    private void cpasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cpasswordKeyPressed
        if(cpassword.getText().equals("Enter confirm password..")){
            cpassword.setText("");
            cpassword.setForeground(Color.black);
        }
        passX.setVisible(false);
        cpassX.setVisible(false);
    }//GEN-LAST:event_cpasswordKeyPressed

    private void passwordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordKeyReleased
        if(password.getText().equals(cpassword.getText())){
            passC.setVisible(true);
            cpassC.setVisible(true);;
        }
        else{
            passC.setVisible(false);
            cpassC.setVisible(false);
        }
        signupPlaceholder();
    }//GEN-LAST:event_passwordKeyReleased

    private void cpasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cpasswordKeyReleased
        if(password.getText().equals(cpassword.getText())&&
                !password.getText().equals("Enter password..")||
                !cpassword.getText().equals("Enter confirm password..")){
            passC.setVisible(true);
            cpassC.setVisible(true);
        }
        else{
            passC.setVisible(false);
            cpassC.setVisible(false);
        }
        signupPlaceholder();
    }//GEN-LAST:event_cpasswordKeyReleased

    private void lnameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lnameKeyPressed
        if(lname.getText().equals("Enter lastname..")){
            lname.setText("");
            lname.setForeground(Color.black);
        }
        lname.setBorder(searchField.getBorder());
    }//GEN-LAST:event_lnameKeyPressed

    private void fnameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fnameKeyPressed
        if(fname.getText().equals("Enter firstname..")){
            fname.setText("");
            fname.setForeground(Color.black);
        }
        fname.setBorder(searchField.getBorder());
    }//GEN-LAST:event_fnameKeyPressed

    private void fnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fnameKeyReleased
        signupPlaceholder();
    }//GEN-LAST:event_fnameKeyReleased

    private void mnameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mnameKeyPressed
        if(mname.getText().equals("Enter middlename..")){
            mname.setText("");
            mname.setForeground(Color.black);
        }
        mname.setBorder(searchField.getBorder());
    }//GEN-LAST:event_mnameKeyPressed

    private void mnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mnameKeyReleased
        signupPlaceholder();
    }//GEN-LAST:event_mnameKeyReleased

    private void contactKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contactKeyPressed
        if(contact.getText().equals("Enter contact..")){
            contact.setText("");
            contact.setForeground(Color.black);
        }
        contact.setBorder(searchField.getBorder());
    }//GEN-LAST:event_contactKeyPressed

    private void contactKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contactKeyReleased
        signupPlaceholder();
    }//GEN-LAST:event_contactKeyReleased

    private void positionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_positionKeyPressed
        if(position.getText().equals("Enter position..")){
            position.setText("");
            position.setForeground(Color.black);
        }
        position.setBorder(searchField.getBorder());
    }//GEN-LAST:event_positionKeyPressed

    private void positionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_positionKeyReleased
        signupPlaceholder();
    }//GEN-LAST:event_positionKeyReleased

    private void addressKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addressKeyPressed
        if(address.getText().equals("Enter address..")){
            address.setText("");
            address.setForeground(Color.black);
        }
        address.setBorder(searchField.getBorder());
    }//GEN-LAST:event_addressKeyPressed

    private void addressKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addressKeyReleased
        signupPlaceholder();
    }//GEN-LAST:event_addressKeyReleased

    private void emailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emailKeyPressed
        if(email.getText().equals("Enter email..")){
            email.setText("");
            email.setForeground(Color.black);
        }
    }//GEN-LAST:event_emailKeyPressed

    private void emailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emailKeyReleased
        signupPlaceholder();
    }//GEN-LAST:event_emailKeyReleased

    private void usernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameKeyPressed
        if(username.getText().equals("Enter username..")){
            username.setText("");
            username.setForeground(Color.black);
        }
        username.setBorder(searchField.getBorder());
    }//GEN-LAST:event_usernameKeyPressed

    private void usernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameKeyReleased
        signupPlaceholder();
    }//GEN-LAST:event_usernameKeyReleased

    private void lnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lnameKeyReleased
        signupPlaceholder();
    }//GEN-LAST:event_lnameKeyReleased

    private void passwordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_passwordMouseClicked
        //passX.setVisible(false);
          //  cpassX.setVisible(false);
          password.setBorder(searchField.getBorder());
          cpassword.setBorder(searchField.getBorder());
    }//GEN-LAST:event_passwordMouseClicked

    private void cpasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cpasswordMouseClicked
        //passX.setVisible(false);
          //  cpassX.setVisible(false);
          password.setBorder(searchField.getBorder());
          cpassword.setBorder(searchField.getBorder());
    }//GEN-LAST:event_cpasswordMouseClicked

    private void genderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genderActionPerformed
        // TODO add your handling code here:
        gender.setBorder(searchField.getBorder());
    }//GEN-LAST:event_genderActionPerformed

    private void ageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ageActionPerformed
        // TODO add your handling code here:
        age.setBorder(searchField.getBorder());
    }//GEN-LAST:event_ageActionPerformed

    private void statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusActionPerformed
        // TODO add your handling code here:
        status.setBorder(searchField.getBorder());
    }//GEN-LAST:event_statusActionPerformed

    private void lnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lnameMouseClicked
        // TODO add your handling code here:
        lname.setBorder(searchField.getBorder());
    }//GEN-LAST:event_lnameMouseClicked

    private void fnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fnameMouseClicked
        // TODO add your handling code here:
        fname.setBorder(searchField.getBorder());
    }//GEN-LAST:event_fnameMouseClicked

    private void mnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnameMouseClicked
        // TODO add your handling code here:
        mname.setBorder(searchField.getBorder());
    }//GEN-LAST:event_mnameMouseClicked

    private void contactMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contactMouseClicked
        // TODO add your handling code here:
        contact.setBorder(searchField.getBorder());
    }//GEN-LAST:event_contactMouseClicked

    private void positionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_positionMouseClicked
        // TODO add your handling code here:
        position.setBorder(searchField.getBorder());
    }//GEN-LAST:event_positionMouseClicked

    private void addressMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addressMouseClicked
        // TODO add your handling code here:
        address.setBorder(searchField.getBorder());
    }//GEN-LAST:event_addressMouseClicked

    private void emailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_emailMouseClicked
        // TODO add your handling code here:
        email.setBorder(searchField.getBorder());
    }//GEN-LAST:event_emailMouseClicked

    private void usernameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usernameMouseClicked
        // TODO add your handling code here:
        username.setBorder(searchField.getBorder());
    }//GEN-LAST:event_usernameMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField address;
    private javax.swing.JTextField address1;
    private javax.swing.JLabel addresslabel;
    private javax.swing.JComboBox<String> age;
    private javax.swing.JComboBox<String> age2;
    private javax.swing.JLabel agelabel;
    private javax.swing.JPanel board;
    private javax.swing.JComboBox<String> c;
    private javax.swing.JPanel cancelBtn;
    private javax.swing.JLabel code1;
    private javax.swing.JLabel code10;
    private javax.swing.JLabel code2;
    private javax.swing.JLabel code3;
    private javax.swing.JLabel code4;
    private javax.swing.JLabel code5;
    private javax.swing.JLabel code6;
    private javax.swing.JLabel code7;
    private javax.swing.JLabel code8;
    private javax.swing.JLabel code9;
    private javax.swing.JTextField contact;
    private javax.swing.JTextField contact1;
    private javax.swing.JLabel contactlabel;
    private javax.swing.JLabel cpassC;
    private javax.swing.JLabel cpassX;
    private javax.swing.JTextField cpassword;
    private javax.swing.JTextField cpassword1;
    private javax.swing.JLabel dash1;
    private javax.swing.JLabel dash10;
    private javax.swing.JLabel dash2;
    private javax.swing.JLabel dash3;
    private javax.swing.JLabel dash4;
    private javax.swing.JLabel dash5;
    private javax.swing.JLabel dash6;
    private javax.swing.JLabel dash7;
    private javax.swing.JLabel dash8;
    private javax.swing.JLabel dash9;
    private javax.swing.JComboBox<String> day1;
    private javax.swing.JComboBox<String> day10;
    private javax.swing.JComboBox<String> day2;
    private javax.swing.JComboBox<String> day3;
    private javax.swing.JComboBox<String> day4;
    private javax.swing.JComboBox<String> day5;
    private javax.swing.JComboBox<String> day6;
    private javax.swing.JComboBox<String> day7;
    private javax.swing.JComboBox<String> day8;
    private javax.swing.JComboBox<String> day9;
    private javax.swing.JPanel defaultSchedPanel;
    private javax.swing.JPanel doneBtn;
    private javax.swing.JLabel eidlabel;
    private javax.swing.JTextField email;
    private javax.swing.JTextField email1;
    private javax.swing.JLabel emaillabel;
    private javax.swing.JComboBox<String> extension;
    private javax.swing.JComboBox<String> extension1;
    private javax.swing.JComboBox<String> f1;
    private javax.swing.JComboBox<String> f10;
    private javax.swing.JComboBox<String> f2;
    private javax.swing.JComboBox<String> f3;
    private javax.swing.JComboBox<String> f4;
    private javax.swing.JComboBox<String> f5;
    private javax.swing.JComboBox<String> f6;
    private javax.swing.JComboBox<String> f7;
    private javax.swing.JComboBox<String> f8;
    private javax.swing.JComboBox<String> f9;
    private javax.swing.JComboBox<String> fag;
    private javax.swing.JLabel fdot1;
    private javax.swing.JLabel fdot10;
    private javax.swing.JLabel fdot2;
    private javax.swing.JLabel fdot3;
    private javax.swing.JLabel fdot4;
    private javax.swing.JLabel fdot5;
    private javax.swing.JLabel fdot6;
    private javax.swing.JLabel fdot7;
    private javax.swing.JLabel fdot8;
    private javax.swing.JLabel fdot9;
    private javax.swing.JLabel ffdot1;
    private javax.swing.JLabel ffdot10;
    private javax.swing.JLabel ffdot2;
    private javax.swing.JLabel ffdot3;
    private javax.swing.JLabel ffdot4;
    private javax.swing.JLabel ffdot5;
    private javax.swing.JLabel ffdot6;
    private javax.swing.JLabel ffdot7;
    private javax.swing.JLabel ffdot8;
    private javax.swing.JLabel ffdot9;
    private javax.swing.JComboBox<String> fhour1;
    private javax.swing.JComboBox<String> fhour10;
    private javax.swing.JComboBox<String> fhour2;
    private javax.swing.JComboBox<String> fhour3;
    private javax.swing.JComboBox<String> fhour4;
    private javax.swing.JComboBox<String> fhour5;
    private javax.swing.JComboBox<String> fhour6;
    private javax.swing.JComboBox<String> fhour7;
    private javax.swing.JComboBox<String> fhour8;
    private javax.swing.JComboBox<String> fhour9;
    private javax.swing.JComboBox<String> fminute1;
    private javax.swing.JComboBox<String> fminute10;
    private javax.swing.JComboBox<String> fminute2;
    private javax.swing.JComboBox<String> fminute3;
    private javax.swing.JComboBox<String> fminute4;
    private javax.swing.JComboBox<String> fminute5;
    private javax.swing.JComboBox<String> fminute6;
    private javax.swing.JComboBox<String> fminute7;
    private javax.swing.JComboBox<String> fminute8;
    private javax.swing.JComboBox<String> fminute9;
    private javax.swing.JTextField fname;
    private javax.swing.JTextField fname1;
    private javax.swing.JLabel fullnamelabel;
    private javax.swing.JComboBox<String> gender;
    private javax.swing.JComboBox<String> gender1;
    private javax.swing.JLabel genderlabel;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel175;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel208;
    private javax.swing.JLabel jLabel209;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lab1;
    private javax.swing.JLabel lab10;
    private javax.swing.JLabel lab2;
    private javax.swing.JLabel lab3;
    private javax.swing.JLabel lab4;
    private javax.swing.JLabel lab5;
    private javax.swing.JLabel lab6;
    private javax.swing.JLabel lab7;
    private javax.swing.JLabel lab8;
    private javax.swing.JLabel lab9;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel label2;
    private javax.swing.JLabel label3;
    private javax.swing.JLabel label4;
    private javax.swing.JLabel labelE;
    private javax.swing.JLabel lec1;
    private javax.swing.JLabel lec10;
    private javax.swing.JLabel lec2;
    private javax.swing.JLabel lec3;
    private javax.swing.JLabel lec4;
    private javax.swing.JLabel lec5;
    private javax.swing.JLabel lec6;
    private javax.swing.JLabel lec7;
    private javax.swing.JLabel lec8;
    private javax.swing.JLabel lec9;
    private javax.swing.JTextField lname;
    private javax.swing.JTextField lname1;
    private javax.swing.JTextField mname;
    private javax.swing.JTextField mname1;
    private javax.swing.JComboBox<String> nos;
    private javax.swing.JLabel passC;
    private javax.swing.JLabel passX;
    private javax.swing.JTextField password;
    private javax.swing.JTextField password1;
    private javax.swing.JTextField position;
    private javax.swing.JTextField position2;
    private javax.swing.JLabel positionlabel;
    private javax.swing.JTextField red;
    private javax.swing.JPanel registerBtn;
    private javax.swing.JTextField room1;
    private javax.swing.JTextField room10;
    private javax.swing.JTextField room2;
    private javax.swing.JTextField room3;
    private javax.swing.JTextField room4;
    private javax.swing.JTextField room5;
    private javax.swing.JTextField room6;
    private javax.swing.JTextField room7;
    private javax.swing.JTextField room8;
    private javax.swing.JTextField room9;
    private javax.swing.JComboBox<String> s;
    private javax.swing.JComboBox<String> s1;
    private javax.swing.JComboBox<String> s10;
    private javax.swing.JComboBox<String> s2;
    private javax.swing.JComboBox<String> s3;
    private javax.swing.JComboBox<String> s4;
    private javax.swing.JComboBox<String> s5;
    private javax.swing.JComboBox<String> s6;
    private javax.swing.JComboBox<String> s7;
    private javax.swing.JComboBox<String> s8;
    private javax.swing.JComboBox<String> s9;
    private javax.swing.JPanel save2;
    private javax.swing.JPanel save5;
    private javax.swing.JPanel saveBtn;
    private javax.swing.JPanel schedPanel;
    private javax.swing.JPanel scheduleBtn;
    private javax.swing.JLabel sdot1;
    private javax.swing.JLabel sdot10;
    private javax.swing.JLabel sdot2;
    private javax.swing.JLabel sdot3;
    private javax.swing.JLabel sdot4;
    private javax.swing.JLabel sdot5;
    private javax.swing.JLabel sdot6;
    private javax.swing.JLabel sdot7;
    private javax.swing.JLabel sdot8;
    private javax.swing.JLabel sdot9;
    private javax.swing.JPanel searchBtn1;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JPanel searchSection;
    private javax.swing.JComboBox<String> sec;
    private javax.swing.JPanel sectionNameBtn;
    private javax.swing.JPanel sectionPanel;
    private javax.swing.JComboBox<String> sem;
    private javax.swing.JComboBox<String> shour1;
    private javax.swing.JComboBox<String> shour10;
    private javax.swing.JComboBox<String> shour2;
    private javax.swing.JComboBox<String> shour3;
    private javax.swing.JComboBox<String> shour4;
    private javax.swing.JComboBox<String> shour5;
    private javax.swing.JComboBox<String> shour6;
    private javax.swing.JComboBox<String> shour7;
    private javax.swing.JComboBox<String> shour8;
    private javax.swing.JComboBox<String> shour9;
    private javax.swing.JPanel signUpBtn;
    private javax.swing.JPanel signUpPanel;
    private javax.swing.JComboBox<String> sminute1;
    private javax.swing.JComboBox<String> sminute10;
    private javax.swing.JComboBox<String> sminute2;
    private javax.swing.JComboBox<String> sminute3;
    private javax.swing.JComboBox<String> sminute4;
    private javax.swing.JComboBox<String> sminute5;
    private javax.swing.JComboBox<String> sminute6;
    private javax.swing.JComboBox<String> sminute7;
    private javax.swing.JComboBox<String> sminute8;
    private javax.swing.JComboBox<String> sminute9;
    private javax.swing.JComboBox<String> status;
    private javax.swing.JComboBox<String> status1;
    private javax.swing.JLabel statuslabel;
    private javax.swing.JComboBox<String> tag;
    private javax.swing.JLabel tdot1;
    private javax.swing.JLabel tdot10;
    private javax.swing.JLabel tdot2;
    private javax.swing.JLabel tdot3;
    private javax.swing.JLabel tdot4;
    private javax.swing.JLabel tdot5;
    private javax.swing.JLabel tdot6;
    private javax.swing.JLabel tdot7;
    private javax.swing.JLabel tdot8;
    private javax.swing.JLabel tdot9;
    private javax.swing.JLabel title1;
    private javax.swing.JLabel title10;
    private javax.swing.JLabel title2;
    private javax.swing.JLabel title3;
    private javax.swing.JLabel title4;
    private javax.swing.JLabel title5;
    private javax.swing.JLabel title6;
    private javax.swing.JLabel title7;
    private javax.swing.JLabel title8;
    private javax.swing.JLabel title9;
    private javax.swing.JPanel updateBtn;
    private javax.swing.JPanel updatePanel;
    private javax.swing.JPanel userInfoBtn;
    private javax.swing.JPanel userInfoPanel;
    private javax.swing.JTable userTable;
    private javax.swing.JComboBox<String> userType;
    private javax.swing.JComboBox<String> userType1;
    private javax.swing.JTextField username;
    private javax.swing.JTextField username1;
    private javax.swing.JLabel usernamelabel;
    private javax.swing.JLabel usertypelabel;
    private javax.swing.JComboBox<String> yl;
    // End of variables declaration//GEN-END:variables
}
