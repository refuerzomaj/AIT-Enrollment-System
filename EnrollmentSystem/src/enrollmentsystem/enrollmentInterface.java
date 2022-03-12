/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enrollmentsystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

/**
 *
 * @author Jomardon Refuerzo Gabitanan
 */
public class enrollmentInterface extends javax.swing.JFrame {

    /**
     * Creates new form enrollmentInterface
     */
    private String dbURL = "jdbc:mysql://localhost/aviation_enrollment_system" ;
    private String USER = "root";
    private String PASS = "";
    private controller controll,controll2;
    private ButtonGroup bg;
    private String buttonVal;
    private loginForm lf;
    private String sId,currenSchoolYear,studentSchoolyear;
    private double agradeDouble = 0.0;
    private String secName;
    private String sqlWhere="";
    private boolean searchOldStudent = false;
    private boolean validation = false;
    private DecimalFormat df = new DecimalFormat("#.##");
    private Border redline = BorderFactory.createLineBorder(new Color(255,51,51),2);
    public enrollmentInterface() {
        initComponents();
        studentType();
        defaultPanel();
        frameLocation();
        getCurrentSchoolYear();
        getCurrentDateandTime();
        gradeEmpty();
        textBoxLabel();
    }

    public void frameLocation(){
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
    public void gradeEmpty(){
        agradefield.setText("0.0");
    }
    public void getGrade(String param){
        try{
                Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
                
                String sql = "SELECT name,gradeFrom,gradeTo FROM section WHERE yearlevel='"+param+"';";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet result = ps.executeQuery();
                while(result.next()){
                    if(result.getDouble(2)<95&&
                            result.getDouble(3)>95){
                        secName = result.getString(1);
                    }
                }
                try{
                    if(ps!=null)ps.close();
                    if(result!=null)result.close();
                    if(conn!=null)conn.close();
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
                
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void getCurrentSchoolYear(){
        try{
                Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
                String sql = "SELECT DATE_FORMAT(NOW(),'%Y');";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet result = ps.executeQuery();
                while(result.next()){
                    int toyear = result.getInt(1)+1;
                    currenSchoolYear = result.getInt(1)+"-"+toyear;
                    schoolyearfield.setText(currenSchoolYear);
                }
                try{
                    if(ps!=null)ps.close();
                    if(result!=null)result.close();
                    if(conn!=null)conn.close();
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void getStudentCurrentYearEnrolled(String sid){
        try{
                Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
                String sql = "SELECT schoolyear FROM school_info WHERE studentId='"+sid+"';";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet result = ps.executeQuery();
                while(result.next()){
                    studentSchoolyear=result.getString(1);
                }
                try{
                    if(ps!=null)ps.close();
                    if(result!=null)result.close();
                    if(conn!=null)conn.close();
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void getNewStudentID(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
                conn = DriverManager.getConnection(dbURL, USER, PASS);
                conn.setAutoCommit(false);
                String sql = "SELECT COUNT(studentId) FROM student_personal_info;";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while(rs.next()){
                    String idRes = Integer.toString(rs.getInt(1)+1);
                    int lengthRes = idRes.length();
                    switch(lengthRes){
                case 1:
                    buttonVal="Enroll";
                    jLabel21.setText(buttonVal);
                    searchIdField.setText("STUDENT-000"+idRes);
                    conn.commit();
                    break;
                case 2:
                    buttonVal="Enroll";
                    jLabel21.setText(buttonVal);
                    searchIdField.setText("STUDENT-00"+idRes);
                    conn.commit();
                    break;
                case 3:
                    buttonVal="Enroll";
                    jLabel21.setText(buttonVal);
                    searchIdField.setText("STUDENT-0"+idRes);
                    conn.commit();
                    break;
                default:
                    break;
                    }
                }
                try{
                    if(ps!=null)ps.close();
                    if(rs!=null)rs.close();
                    if(conn!=null)conn.close();
                }catch(SQLException ex){
                    ex.printStackTrace();
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
                    }
                    catch (NumberFormatException e){
                        JOptionPane.showMessageDialog(this,e);
                    }
                    finally{
                    try{
                        if(rs != null) rs.close();
                        if(ps != null) ps.close();
                        if(conn != null) conn.close();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                }
    }
    public void textBoxLabel(){
        if(lnamefield.getText().equals("")){ 
            lnamefield.setText("Enter lastname..");
            lnamefield.setForeground(new Color (153,153,153));
        }
        if(fnamefield.getText().equals("")){ 
            fnamefield.setText("Enter firstname..");
            fnamefield.setForeground(new Color (153,153,153));
        }
        if(mnamefield.getText().equals("")){ 
            mnamefield.setText("Enter middlename..");
            mnamefield.setForeground(new Color (153,153,153));
        }
        if(agefield.getText().equals("")){ 
            agefield.setText("0");
            agefield.setForeground(new Color (153,153,153));
        }
        if(bdatefield.getText().equals("")){ 
            bdatefield.setText("Enter bdate..");
            bdatefield.setForeground(new Color (153,153,153));
        }
        if(bplacefield.getText().equals("")){ 
            bplacefield.setText("Enter bplace..");
            bplacefield.setForeground(new Color (153,153,153));
        }
        if(addressfield.getText().equals("")){ 
            addressfield.setText("Enter address..");
            addressfield.setForeground(new Color (153,153,153));
        }
        if(agradefield.getText().equals("")){
            agradefield.setText("0.0");
            agradefield.setForeground(new Color (153,153,153));
        }
        if(emailfield.getText().equals("")){ 
            emailfield.setText("Enter email..");
            emailfield.setForeground(new Color (153,153,153));
        }
        if(contactfield.getText().equals("")){ 
            contactfield.setText("Enter contact..");
            contactfield.setForeground(new Color (153,153,153));
        }
        if(gnamefield.getText().equals("")){ 
            gnamefield.setText("Enter guardian name..");
            gnamefield.setForeground(new Color (153,153,153));
        }
        if(gcontactfield.getText().equals("")){ 
            gcontactfield.setText("Enter guardian contact..");
            gcontactfield.setForeground(new Color (153,153,153));
        }
        if(mothernamefield.getText().equals("")){ 
            mothernamefield.setText("Enter mother name..");
            mothernamefield.setForeground(new Color (153,153,153));
        }
        if(mothermobilefield.getText().equals("")){ 
            mothermobilefield.setText("Enter mother contact..");
            mothermobilefield.setForeground(new Color (153,153,153));
        }
        if(motheraddressfield.getText().equals("")){
            motheraddressfield.setText("Enter mother address..");
            motheraddressfield.setForeground(new Color (153,153,153));
        }
        if(motheroccupationfield.getText().equals("")){ 
            motheroccupationfield.setText("Enter mother occupation..");
            motheroccupationfield.setForeground(new Color (153,153,153));
        }
        if(fathernamefield.getText().equals("")){ 
            fathernamefield.setText("Enter father name..");
            fathernamefield.setForeground(new Color (153,153,153));
        }
        if(fathermobilefield.getText().equals("")){ 
            fathermobilefield.setText("Enter father contact..");
            fathermobilefield.setForeground(new Color (153,153,153));
        }
        if(fatheraddressfield.getText().equals("")){ 
            fatheraddressfield.setText("Enter father address..");
            fatheraddressfield.setForeground(new Color (153,153,153));
        }
        if(fatheroccupationfield.getText().equals("")){ 
            fatheroccupationfield.setText("Enter father occupation..");
            fatheroccupationfield.setForeground(new Color (153,153,153));
        }
    }    
    public void getCurrentDateandTime(){
        
        Thread clock = new Thread(){
            public void run(){
                
                try{
                    for(;;){
                        Connection conn=null;
        PreparedStatement ps =null;
        ResultSet result = null;
        PreparedStatement ps2 =null;
        ResultSet result2 = null;
        try{
                conn = DriverManager.getConnection(dbURL, USER, PASS);
                conn.setAutoCommit(false);
                String sql = "SELECT DATE_FORMAT(NOW(),'%M %d %Y');";
                ps = conn.prepareStatement(sql);
                result = ps.executeQuery();
                while(result.next()){
                    dateLabel.setText("Date : "+ result.getString(1));
                }
                String sql2 ="SELECT DATE_FORMAT(NOW(),'%h:%i:%s %p')";
                ps2 = conn.prepareStatement(sql2);
                result2 = ps2.executeQuery();
                while(result2.next()){
                    timeLabel.setText("Time : "+ result2.getString(1));
                }
                conn.commit();
                try{
                    if(ps!=null)ps.close();
                    if(result!=null)result.close();
                    if(conn!=null)conn.close();
                }catch(SQLException ex){
                    ex.printStackTrace();
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
                        if(result != null) result.close();
                        if(ps != null) ps.close();
                        if(ps2 != null) ps2.close();
                        if(conn != null) conn.close();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                }}
        }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };clock.start();
    }
    public void studentType(){
        bg = new ButtonGroup();
        bg.add(newstudentfield);
        bg.add(oldstudentfield);
    }
    public void redBorder(){
        fnamefield.setBorder(jComboBox1.getBorder());
        mnamefield.setBorder(jComboBox1.getBorder());
        lnamefield.setBorder(jComboBox1.getBorder());
        bplacefield.setBorder(jComboBox1.getBorder());
        bdatefield.setBorder(jComboBox1.getBorder());
        agefield.setBorder(jComboBox1.getBorder());
        addressfield.setBorder(jComboBox1.getBorder());
        agradefield.setBorder(jComboBox1.getBorder());
        emailfield.setBorder(jComboBox1.getBorder());
        contactfield.setBorder(jComboBox1.getBorder());
        gnamefield.setBorder(jComboBox1.getBorder());
        gcontactfield.setBorder(jComboBox1.getBorder());
        paymentmethodfield.setBorder(jComboBox1.getBorder());
        mothernamefield.setBorder(jComboBox1.getBorder());
        mothermobilefield.setBorder(jComboBox1.getBorder());
        motheraddressfield.setBorder(jComboBox1.getBorder());
        motheroccupationfield.setBorder(jComboBox1.getBorder());
        fathernamefield.setBorder(jComboBox1.getBorder());
        fathermobilefield.setBorder(jComboBox1.getBorder());
        fatheraddressfield.setBorder(jComboBox1.getBorder());
        fatheroccupationfield.setBorder(jComboBox1.getBorder());
    }
    public void crossImage(){
        fnameX.setVisible(false);
        mnameX.setVisible(false);
        lnameX.setVisible(false);
        bplaceX.setVisible(false);
        bdateX.setVisible(false);
        genderX.setVisible(false);
        ageX.setVisible(false);
        addressX.setVisible(false);
        yearlevelX.setVisible(false);
        courseX.setVisible(false);
        semesterX.setVisible(false);
        agradeX.setVisible(false);
        emailX.setVisible(false);
        contactX.setVisible(false);
        gnameX.setVisible(false);
        gcontactX.setVisible(false);
        paymentX.setVisible(false);
        mothernameX.setVisible(false);
        mothercontactX.setVisible(false);
        motheraddressX.setVisible(false);
        motheroccupationX.setVisible(false);
        fathernameX.setVisible(false);
        fathermobileX.setVisible(false);
        fatheraddressX.setVisible(false);
        fatheroccupationX.setVisible(false);
        motherincomeX.setVisible(false);
        fatherincomeX.setVisible(false);
    }
    public void checkImage(){
        fnameC.setVisible(false);
        mnameC.setVisible(false);
        lnameC.setVisible(false);
        genderC.setVisible(false);
        bplaceC.setVisible(false);
        bdateC.setVisible(false);
        ageC.setVisible(false);
        addressC.setVisible(false);
        yearlevelC.setVisible(false);
        courseC.setVisible(false);
        semesterC.setVisible(false);
        agradeC.setVisible(false);
        emailC.setVisible(false);
        contactC.setVisible(false);
        gnameC.setVisible(false);
        gcontactC.setVisible(false);
        paymentC.setVisible(false);
        mothernameC.setVisible(false);
        mothercontactC.setVisible(false);
        motheraddressC.setVisible(false);
        motheroccupationC.setVisible(false);
        motherincomeC.setVisible(false);
        fatherincomeC.setVisible(false);
        fathernameC.setVisible(false);
        fathermobileC.setVisible(false);
        fatheraddressC.setVisible(false);
        fatheroccupationC.setVisible(false);
    }
    public void defaultPanel(){
        buttonVal="Save & Enroll";
        oldstudentfield.setSelected(true);
        save.setPreferredSize(new Dimension(186,26));
        controll = new controller();
        controll.setPanel(billingPanel);
        controll.setUnshowPanel();
        controll.setPanel(generalPanel);
        controll.setShowPanel();
        controll.setButton(generalBtn);
        controll.setColorOfButton(new Color(255,0,0));
        redBorder();
        checkImage();
        crossImage();
    }
    public void fieldsValidation(){
        if(searchIdField.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Please enter your id first");
            return;
        }
        if(lnamefield.getText().equals("")||
                lnamefield.getText().equals("Enter lastname..")){
            JOptionPane.showMessageDialog(this,"Lastname field is empty");
            lnamefield.setBorder(redline);
            lnameX.setVisible(true);
            return;
        }
        lnameC.setVisible(true);
        if(fnamefield.getText().equals("")||
                fnamefield.getText().equals("Enter firstname..")){
            JOptionPane.showMessageDialog(this,"Firstname field is empty");
            fnamefield.setBorder(redline);
            fnameX.setVisible(true);
            return;
        }
        fnameC.setVisible(true);
        if(mnamefield.getText().equals("")||
                mnamefield.getText().equals("Enter middlename..")){
            JOptionPane.showMessageDialog(this,"Middlename field is empty");
            mnamefield.setBorder(redline);
            mnameX.setVisible(true);
            return;
        }
        mnameC.setVisible(true);
        if(genderField.getSelectedItem().equals("Select")){
            JOptionPane.showMessageDialog(this,"Gender field is empty");
            genderX.setVisible(true);
            return;
        }
        genderC.setVisible(true);
        if(agefield.getText().equals("")||
                agefield.getText().equals("0")){
            JOptionPane.showMessageDialog(this,"Age field is empty");
            agefield.setBorder(redline);
            ageX.setVisible(true);
            return;
        }
        ageC.setVisible(true);
        if(bdatefield.getText().equals("")||
                bdatefield.getText().equals("Enter bdate..")){
            JOptionPane.showMessageDialog(this,"Birthdate field is empty");
            bdatefield.setBorder(redline);
            bdateX.setVisible(true);
            return;
        }
        bdateC.setVisible(true);
        if(bplacefield.getText().equals("")||
                bplacefield.getText().equals("Enter bplace..")){
            JOptionPane.showMessageDialog(this,"Birthplace field is empty");
            bplacefield.setBorder(redline);
            bplaceX.setVisible(true);
            return;
        }
        bplaceC.setVisible(true);
        if(addressfield.getText().equals("")||
                addressfield.getText().equals("Enter address..")){
            JOptionPane.showMessageDialog(this,"Address field is empty");
            addressfield.setVisible(false);
            addressX.setVisible(true);
            return;
        }
        addressC.setVisible(true);
        if(yearlevelfield.getSelectedItem().toString().equals("Select")){
            JOptionPane.showMessageDialog(this,"Year level field is empty");
            yearlevelfield.setBorder(redline);
            yearlevelX.setVisible(true);
            return;
        }
        yearlevelC.setVisible(true);
        if(coursefield.getSelectedItem().toString().equals("Select")){
            JOptionPane.showMessageDialog(this,"Course field field is empty");
            coursefield.setBorder(redline);
            courseX.setVisible(true);
            return;
        }
        courseC.setVisible(true);
        if(semesterfield.getSelectedItem().toString().equals("Select")){
            JOptionPane.showMessageDialog(this,"Semester field is empty");
            semesterfield.setBorder(redline);
            semesterX.setVisible(true);
            return;
        }
        semesterC.setVisible(true);
        if(agradefield.getText().equals(0.0)){
            JOptionPane.showMessageDialog(this,"Average grade field is empty");
            agradefield.setBorder(redline);
            agradeX.setVisible(true);
            return;
        }
        agradeC.setVisible(true);
        if(emailfield.getText().equals("")||
                emailfield.getText().equals("Enter email..")){
            JOptionPane.showMessageDialog(this,"Email field is empty");
            emailfield.setBorder(redline);
            emailX.setVisible(true);
            return;
        }
        emailC.setVisible(true);
        if(contactfield.getText().equals("")||
                contactfield.getText().equals("Enter contact..")){
            JOptionPane.showMessageDialog(this,"Contact field is empty");
            contactfield.setBorder(redline);
            contactX.setVisible(true);
            return;
        }
        contactC.setVisible(true);
        if(gnamefield.getText().equals("")||
                gnamefield.getText().equals("Enter guardian name..")){
            JOptionPane.showMessageDialog(this,"Guardian name field is empty");
            gnamefield.setBorder(redline);
            gnameX.setVisible(true);
            return;
        }
        gnameC.setVisible(true);
        if(gcontactfield.getText().equals("")||
                gcontactfield.getText().equals("Enter guardian contact..")){
            JOptionPane.showMessageDialog(this,"Guardian contact field is empty");
            gcontactfield.setBorder(redline);
            gcontactX.setVisible(true);
            return;
        }
        gcontactC.setVisible(true);
        if(paymentmethodfield.getSelectedItem().toString().equals("Select")){
            JOptionPane.showMessageDialog(this,"Payment type field is empty");
            paymentmethodfield.setBorder(redline);
            paymentX.setVisible(true);
            return;
        }
        paymentC.setVisible(true);
        if(mothernamefield.getText().equals("")||
                mothernamefield.getText().equals("Enter mother name..")){
            JOptionPane.showMessageDialog(this,"Mother name field is empty");
            mothernamefield.setBorder(redline);
            mothernameX.setVisible(true);
            return;
        }
        mothernameC.setVisible(true);
        if(mothermobilefield.getText().equals("")||
                mothermobilefield.getText().equals("Enter mother contact..")){
            JOptionPane.showMessageDialog(this,"Mother mobile field is empty");
            mothermobilefield.setBorder(redline);
            mothercontactX.setVisible(true);
            return;
        }
        mothercontactC.setVisible(true);
        if(motheraddressfield.getText().equals("")||
                motheraddressfield.getText().equals("Enter mother address..")){
            JOptionPane.showMessageDialog(this,"Mother address field is empty");
            motheraddressfield.setBorder(redline);
            motheraddressX.setVisible(true);
            return;
        }
        motheraddressC.setVisible(true);
        if(motheroccupationfield.getText().equals("")||
                motheroccupationfield.getText().equals("Enter mother occupation..")){
            JOptionPane.showMessageDialog(this,"Mother occupation field is empty");
            motheroccupationfield.setBorder(redline);
            motheroccupationX.setVisible(true);
            return;
        }
        motheroccupationC.setVisible(true);
        if(motherincomefield.getSelectedItem().toString().equals("Select")){
            JOptionPane.showMessageDialog(this,"Mother income field is empty");
            motherincomefield.setBorder(redline);
            motherincomeX.setVisible(true);
            return;
        }
        motherincomeC.setVisible(true);
        if(fathernamefield.getText().equals("")||
                fathernamefield.getText().equals("Enter father field..")){
            JOptionPane.showMessageDialog(this,"Father name field is empty");
            fathernamefield.setBorder(redline);
            fathernameX.setVisible(true);
            return;
        }
        fathernameC.setVisible(true);
        if(fathermobilefield.getText().equals("")||
                fathermobilefield.getText().equals("Enter father contact..")){
            JOptionPane.showMessageDialog(this,"Father mobile field is empty");
            fathermobilefield.setBorder(redline);
            fathermobileX.setVisible(true);
            return;
        }
        fathermobileC.setVisible(true);
        if(fatheraddressfield.getText().equals("")||
                fatheraddressfield.getText().equals("Enter father address..")){
            JOptionPane.showMessageDialog(this,"Father address field is empty");
            fatheraddressfield.setBorder(redline);
            fatheraddressX.setVisible(true);
            return;
        }
        fatheraddressC.setVisible(true);
        if(fatheroccupationfield.getText().equals("")||
                fatheroccupationfield.getText().equals("Enter father occupation..")){
            JOptionPane.showMessageDialog(this,"Father occupation field is empty");
            fatheroccupationfield.setBorder(redline);
            fatheroccupationX.setVisible(true);
            return;
        }
        fatheroccupationC.setVisible(true);
        if(fatherincomefield.getSelectedItem().toString().equals("Select")){
            JOptionPane.showMessageDialog(this,"Father income field is empty");
            fatherincomeX.setVisible(true);
            return;
        }
        fatherincomeC.setVisible(true);
            
        validation=true;
    }
    public void panelPicker(){
        if(!controll.getPanel().equals(generalPanel)&&
                controll.getButton().equals(generalBtn)){
            controll.setUnshowPanel();
            controll.setPanel(generalPanel);
            controll.setShowPanel();
        }else if(!controll.getPanel().equals(billingPanel)&&
                controll.getButton().equals(billingBtn)){
            controll.setUnshowPanel();
            controll.setPanel(billingPanel);
            controll.setShowPanel();
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        board = new javax.swing.JPanel();
        generalPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        displayName = new javax.swing.JTextField();
        displayYear = new javax.swing.JTextField();
        displayContact = new javax.swing.JTextField();
        displayEmail = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        lnamefield = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        fnamefield = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        mnamefield = new javax.swing.JTextField();
        extensionField = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        fnameX = new javax.swing.JLabel();
        mnameX = new javax.swing.JLabel();
        lnameX = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lnameC = new javax.swing.JLabel();
        fnameC = new javax.swing.JLabel();
        mnameC = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        genderField = new javax.swing.JComboBox<>();
        yearlevelfield = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        agefield = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        bdatefield = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        bplacefield = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        addressfield = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        schoolyearfield = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        agradefield = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        semesterfield = new javax.swing.JComboBox<>();
        coursefield = new javax.swing.JComboBox<>();
        genderX = new javax.swing.JLabel();
        ageX = new javax.swing.JLabel();
        bdateX = new javax.swing.JLabel();
        bplaceX = new javax.swing.JLabel();
        yearlevelX = new javax.swing.JLabel();
        semesterX = new javax.swing.JLabel();
        courseX = new javax.swing.JLabel();
        agradeX = new javax.swing.JLabel();
        addressX = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        genderC = new javax.swing.JLabel();
        ageC = new javax.swing.JLabel();
        bdateC = new javax.swing.JLabel();
        bplaceC = new javax.swing.JLabel();
        yearlevelC = new javax.swing.JLabel();
        agradeC = new javax.swing.JLabel();
        courseC = new javax.swing.JLabel();
        semesterC = new javax.swing.JLabel();
        addressC = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        gcontactfield = new javax.swing.JTextField();
        gnamefield = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        gnameX = new javax.swing.JLabel();
        gcontactX = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        gnameC = new javax.swing.JLabel();
        gcontactC = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        emailfield = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        contactfield = new javax.swing.JTextField();
        emailX = new javax.swing.JLabel();
        contactX = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        emailC = new javax.swing.JLabel();
        contactC = new javax.swing.JLabel();
        billingPanel = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        fatheroccupationfield = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        fatheraddressfield = new javax.swing.JTextField();
        fathernamefield = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        fathermobilefield = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        fatherincomefield = new javax.swing.JComboBox<>();
        fathernameC = new javax.swing.JLabel();
        fathernameX = new javax.swing.JLabel();
        fathermobileX = new javax.swing.JLabel();
        fathermobileC = new javax.swing.JLabel();
        fatheraddressX = new javax.swing.JLabel();
        fatheraddressC = new javax.swing.JLabel();
        fatherincomeX = new javax.swing.JLabel();
        fatherincomeC = new javax.swing.JLabel();
        fatheroccupationX = new javax.swing.JLabel();
        fatheroccupationC = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        mothernamefield = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        motheraddressfield = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        mothermobilefield = new javax.swing.JTextField();
        motheroccupationfield = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        motherincomefield = new javax.swing.JComboBox<>();
        mothernameC = new javax.swing.JLabel();
        mothernameX = new javax.swing.JLabel();
        mothercontactC = new javax.swing.JLabel();
        mothercontactX = new javax.swing.JLabel();
        motheroccupationC = new javax.swing.JLabel();
        motheroccupationX = new javax.swing.JLabel();
        motherincomeC = new javax.swing.JLabel();
        motherincomeX = new javax.swing.JLabel();
        motheraddressC = new javax.swing.JLabel();
        motheraddressX = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        paymentmethodfield = new javax.swing.JComboBox<>();
        paymentX = new javax.swing.JLabel();
        paymentC = new javax.swing.JLabel();
        billingBtn = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        oldstudentfield = new javax.swing.JRadioButton();
        newstudentfield = new javax.swing.JRadioButton();
        searchIdField = new javax.swing.JTextField();
        generalBtn = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        closeButton = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        save = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 0));
        jLabel1.setText("Enrollment");

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        board.setBackground(new java.awt.Color(255, 0, 0));

        generalPanel.setBackground(new java.awt.Color(51, 51, 51));
        generalPanel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                generalPanelKeyPressed(evt);
            }
        });
        generalPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 0));
        jLabel4.setText("Name : ");
        generalPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 0));
        jLabel5.setText("Year : ");
        generalPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 0));
        jLabel6.setText("Email : ");
        generalPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 0));
        jLabel7.setText("Contact : ");
        generalPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, -1));

        displayName.setEditable(false);
        displayName.setBackground(new java.awt.Color(51, 51, 51));
        displayName.setForeground(new java.awt.Color(255, 255, 255));
        generalPanel.add(displayName, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 231, -1));

        displayYear.setEditable(false);
        displayYear.setBackground(new java.awt.Color(51, 51, 51));
        displayYear.setForeground(new java.awt.Color(255, 255, 255));
        generalPanel.add(displayYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 231, -1));

        displayContact.setEditable(false);
        displayContact.setBackground(new java.awt.Color(51, 51, 51));
        displayContact.setForeground(new java.awt.Color(255, 255, 255));
        generalPanel.add(displayContact, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, 194, -1));

        displayEmail.setEditable(false);
        displayEmail.setBackground(new java.awt.Color(51, 51, 51));
        displayEmail.setForeground(new java.awt.Color(255, 255, 255));
        generalPanel.add(displayEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, 194, -1));

        jPanel8.setBackground(new java.awt.Color(51, 51, 51));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lnamefield.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lnamefield.setForeground(new java.awt.Color(153, 153, 153));
        lnamefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lnamefield.setText("Enter lastname..");
        lnamefield.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lnamefield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lnamefieldMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lnamefieldMouseEntered(evt);
            }
        });
        lnamefield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lnamefieldActionPerformed(evt);
            }
        });
        lnamefield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lnamefieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lnamefieldKeyReleased(evt);
            }
        });
        jPanel8.add(lnamefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 12, 145, 23));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 0));
        jLabel8.setText("Lastname");
        jPanel8.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        fnamefield.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fnamefield.setForeground(new java.awt.Color(153, 153, 153));
        fnamefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fnamefield.setText("Enter firstname..");
        fnamefield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fnamefieldMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                fnamefieldMouseEntered(evt);
            }
        });
        fnamefield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fnamefieldActionPerformed(evt);
            }
        });
        fnamefield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fnamefieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fnamefieldKeyReleased(evt);
            }
        });
        jPanel8.add(fnamefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 12, 143, 23));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 0));
        jLabel9.setText("Firstname");
        jPanel8.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, -1, -1));

        mnamefield.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mnamefield.setForeground(new java.awt.Color(153, 153, 153));
        mnamefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mnamefield.setText("Enter middlename..");
        mnamefield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnamefieldMouseClicked(evt);
            }
        });
        mnamefield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnamefieldActionPerformed(evt);
            }
        });
        mnamefield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mnamefieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mnamefieldKeyReleased(evt);
            }
        });
        jPanel8.add(mnamefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(349, 12, 140, 23));

        extensionField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Jr" }));
        extensionField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                extensionFieldMouseClicked(evt);
            }
        });
        extensionField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                extensionFieldActionPerformed(evt);
            }
        });
        jPanel8.add(extensionField, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 112, 23));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 0));
        jLabel11.setText("Extension");
        jPanel8.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 40, -1, -1));

        fnameX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel8.add(fnameX, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, -1, -1));

        mnameX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel8.add(mnameX, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, -1, -1));

        lnameX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel8.add(lnameX, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 0));
        jLabel28.setText("Middlename");
        jPanel8.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, -1, -1));

        lnameC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel8.add(lnameC, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, -1));

        fnameC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel8.add(fnameC, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, -1, -1));

        mnameC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel8.add(mnameC, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, -1, -1));

        generalPanel.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 660, 60));

        jPanel9.setBackground(new java.awt.Color(51, 51, 51));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 0));
        jLabel12.setText("Gender:");
        jPanel9.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        genderField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female" }));
        genderField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                genderFieldMouseClicked(evt);
            }
        });
        genderField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genderFieldActionPerformed(evt);
            }
        });
        jPanel9.add(genderField, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 90, 23));

        yearlevelfield.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "First Year", "Second Year", "Third Year", "Fourth Year" }));
        yearlevelfield.setToolTipText("");
        yearlevelfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                yearlevelfieldMouseClicked(evt);
            }
        });
        yearlevelfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearlevelfieldActionPerformed(evt);
            }
        });
        jPanel9.add(yearlevelfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 101, 23));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 0));
        jLabel13.setText("Year Level:");
        jPanel9.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        agefield.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        agefield.setForeground(new java.awt.Color(153, 153, 153));
        agefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        agefield.setText("0");
        agefield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                agefieldMouseClicked(evt);
            }
        });
        agefield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agefieldActionPerformed(evt);
            }
        });
        agefield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                agefieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                agefieldKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                agefieldKeyTyped(evt);
            }
        });
        jPanel9.add(agefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 40, 23));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 0));
        jLabel14.setText("Age:");
        jPanel9.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, -1, -1));

        bdatefield.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bdatefield.setForeground(new java.awt.Color(153, 153, 153));
        bdatefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        bdatefield.setText("Enter bdate..");
        bdatefield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bdatefieldMouseClicked(evt);
            }
        });
        bdatefield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bdatefieldActionPerformed(evt);
            }
        });
        bdatefield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bdatefieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                bdatefieldKeyReleased(evt);
            }
        });
        jPanel9.add(bdatefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 84, 23));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 0));
        jLabel15.setText("Birthdate:");
        jPanel9.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, -1, -1));

        bplacefield.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bplacefield.setForeground(new java.awt.Color(153, 153, 153));
        bplacefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        bplacefield.setText("Enter bplace..");
        bplacefield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bplacefieldMouseClicked(evt);
            }
        });
        bplacefield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bplacefieldActionPerformed(evt);
            }
        });
        bplacefield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bplacefieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                bplacefieldKeyReleased(evt);
            }
        });
        jPanel9.add(bplacefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 84, 23));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 0));
        jLabel16.setText("Birthplace:");
        jPanel9.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, -1, -1));

        addressfield.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        addressfield.setForeground(new java.awt.Color(153, 153, 153));
        addressfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        addressfield.setText("Enter address..");
        addressfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addressfieldMouseClicked(evt);
            }
        });
        addressfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressfieldActionPerformed(evt);
            }
        });
        addressfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                addressfieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                addressfieldKeyReleased(evt);
            }
        });
        jPanel9.add(addressfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 51, 550, 23));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 0));
        jLabel17.setText("Address:");
        jPanel9.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 110, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 0));
        jLabel18.setText("Course:");
        jPanel9.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, -1, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 0));
        jLabel19.setText("School Year:");
        jPanel9.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        schoolyearfield.setEditable(false);
        schoolyearfield.setBackground(java.awt.Color.white);
        schoolyearfield.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        schoolyearfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel9.add(schoolyearfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 84, 23));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 0));
        jLabel20.setText("Average Grade:");
        jPanel9.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, -1, -1));

        agradefield.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        agradefield.setForeground(new java.awt.Color(153, 153, 153));
        agradefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        agradefield.setText("0.0");
        agradefield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                agradefieldMouseClicked(evt);
            }
        });
        agradefield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agradefieldActionPerformed(evt);
            }
        });
        agradefield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                agradefieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                agradefieldKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                agradefieldKeyTyped(evt);
            }
        });
        jPanel9.add(agradefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 110, 36, 23));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 0));
        jLabel26.setText("Semester");
        jPanel9.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, -1, -1));

        semesterfield.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1st Semester", "2nd Semester" }));
        semesterfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                semesterfieldMouseClicked(evt);
            }
        });
        semesterfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                semesterfieldActionPerformed(evt);
            }
        });
        jPanel9.add(semesterfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 80, 101, 23));

        coursefield.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "BSAIT" }));
        coursefield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                coursefieldMouseClicked(evt);
            }
        });
        coursefield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursefieldActionPerformed(evt);
            }
        });
        jPanel9.add(coursefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, 101, 23));

        genderX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel9.add(genderX, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, -1, -1));

        ageX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel9.add(ageX, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, -1, -1));

        bdateX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel9.add(bdateX, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, -1, -1));

        bplaceX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel9.add(bplaceX, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, -1, -1));

        yearlevelX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel9.add(yearlevelX, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, -1, -1));

        semesterX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel9.add(semesterX, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 80, -1, -1));

        courseX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel9.add(courseX, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 80, -1, -1));

        agradeX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel9.add(agradeX, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 110, -1, -1));

        addressX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel9.add(addressX, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 50, -1, -1));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("User Information");
        jPanel9.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        genderC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel9.add(genderC, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, -1, -1));

        ageC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel9.add(ageC, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, -1, -1));

        bdateC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel9.add(bdateC, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, -1, -1));

        bplaceC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel9.add(bplaceC, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, -1, -1));

        yearlevelC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel9.add(yearlevelC, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, -1, -1));

        agradeC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel9.add(agradeC, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 110, -1, -1));

        courseC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel9.add(courseC, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 80, -1, -1));

        semesterC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel9.add(semesterC, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 80, -1, -1));

        addressC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel9.add(addressC, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 50, -1, -1));

        generalPanel.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 660, 140));

        jPanel11.setBackground(new java.awt.Color(51, 51, 51));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 0));
        jLabel24.setText("Contact:");
        jPanel11.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 44, -1, -1));

        gcontactfield.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        gcontactfield.setForeground(new java.awt.Color(153, 153, 153));
        gcontactfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        gcontactfield.setText("Enter guardian contact..");
        gcontactfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gcontactfieldMouseClicked(evt);
            }
        });
        gcontactfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gcontactfieldActionPerformed(evt);
            }
        });
        gcontactfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                gcontactfieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                gcontactfieldKeyReleased(evt);
            }
        });
        jPanel11.add(gcontactfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 210, 23));

        gnamefield.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        gnamefield.setForeground(new java.awt.Color(153, 153, 153));
        gnamefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        gnamefield.setText("Enter guardian name..");
        gnamefield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gnamefieldMouseClicked(evt);
            }
        });
        gnamefield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gnamefieldActionPerformed(evt);
            }
        });
        gnamefield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                gnamefieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                gnamefieldKeyReleased(evt);
            }
        });
        jPanel11.add(gnamefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 210, 23));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 0));
        jLabel25.setText("Name:");
        jPanel11.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        gnameX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel11.add(gnameX, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, -1, -1));

        gcontactX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel11.add(gcontactX, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Guardian");
        jPanel11.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        gnameC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel11.add(gnameC, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, -1, -1));

        gcontactC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel11.add(gcontactC, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, -1, -1));

        generalPanel.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 310, 330, 80));

        jPanel10.setBackground(new java.awt.Color(51, 51, 51));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 0));
        jLabel22.setText("Email:");
        jPanel10.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        emailfield.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        emailfield.setForeground(new java.awt.Color(153, 153, 153));
        emailfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        emailfield.setText("Enter email..");
        emailfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                emailfieldMouseClicked(evt);
            }
        });
        emailfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailfieldActionPerformed(evt);
            }
        });
        emailfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                emailfieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                emailfieldKeyReleased(evt);
            }
        });
        jPanel10.add(emailfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 200, 23));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 0));
        jLabel23.setText("Contact:");
        jPanel10.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        contactfield.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        contactfield.setForeground(new java.awt.Color(153, 153, 153));
        contactfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        contactfield.setText("Enter contact..");
        contactfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contactfieldMouseClicked(evt);
            }
        });
        contactfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactfieldActionPerformed(evt);
            }
        });
        contactfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                contactfieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                contactfieldKeyReleased(evt);
            }
        });
        jPanel10.add(contactfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 200, 23));

        emailX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel10.add(emailX, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, -1, -1));

        contactX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel10.add(contactX, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, -1, -1));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("User Contact");
        jPanel10.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        emailC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel10.add(emailC, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, -1, -1));

        contactC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel10.add(contactC, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, -1, -1));

        generalPanel.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 320, 80));

        billingPanel.setBackground(new java.awt.Color(51, 51, 51));
        billingPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(51, 51, 51));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fathers Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 0));
        jLabel49.setText("Monthly Income:");
        jPanel15.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(339, 91, -1, -1));

        fatheroccupationfield.setForeground(new java.awt.Color(153, 153, 153));
        fatheroccupationfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fatheroccupationfield.setText("Enter father occupation..");
        fatheroccupationfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fatheroccupationfieldMouseClicked(evt);
            }
        });
        fatheroccupationfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fatheroccupationfieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fatheroccupationfieldKeyReleased(evt);
            }
        });
        jPanel15.add(fatheroccupationfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 89, 224, 23));

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 0));
        jLabel41.setText("Occupation:");
        jPanel15.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 91, -1, -1));

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 0));
        jLabel45.setText("Address:");
        jPanel15.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 60, -1, -1));

        fatheraddressfield.setForeground(new java.awt.Color(153, 153, 153));
        fatheraddressfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fatheraddressfield.setText("Enter father address..");
        fatheraddressfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fatheraddressfieldMouseClicked(evt);
            }
        });
        fatheraddressfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fatheraddressfieldActionPerformed(evt);
            }
        });
        fatheraddressfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fatheraddressfieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fatheraddressfieldKeyReleased(evt);
            }
        });
        jPanel15.add(fatheraddressfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 58, 490, 23));

        fathernamefield.setForeground(new java.awt.Color(153, 153, 153));
        fathernamefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fathernamefield.setText("Enter father name..");
        fathernamefield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fathernamefieldMouseClicked(evt);
            }
        });
        fathernamefield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fathernamefieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fathernamefieldKeyReleased(evt);
            }
        });
        jPanel15.add(fathernamefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 27, 225, 23));

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 0));
        jLabel42.setText("Mobile#:");
        jPanel15.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, -1, -1));

        fathermobilefield.setForeground(new java.awt.Color(153, 153, 153));
        fathermobilefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fathermobilefield.setText("Enter father contact..");
        fathermobilefield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fathermobilefieldMouseClicked(evt);
            }
        });
        fathermobilefield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fathermobilefieldActionPerformed(evt);
            }
        });
        fathermobilefield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fathermobilefieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fathermobilefieldKeyReleased(evt);
            }
        });
        jPanel15.add(fathermobilefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(404, 27, 150, 23));

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 0));
        jLabel46.setText("Fullname:");
        jPanel15.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 29, -1, -1));

        fatherincomefield.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "2000-5000", "5500-10000", "10500-15000", "15500-20000", "20500-30000", "30500-50000" }));
        fatherincomefield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fatherincomefieldMouseClicked(evt);
            }
        });
        fatherincomefield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fatherincomefieldActionPerformed(evt);
            }
        });
        jPanel15.add(fatherincomefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(453, 89, 101, 23));

        fathernameC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel15.add(fathernameC, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, -1, -1));

        fathernameX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel15.add(fathernameX, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, -1, -1));

        fathermobileX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel15.add(fathermobileX, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, -1, -1));

        fathermobileC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel15.add(fathermobileC, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, -1, -1));

        fatheraddressX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel15.add(fatheraddressX, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, -1, -1));

        fatheraddressC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel15.add(fatheraddressC, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, -1, -1));

        fatherincomeX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel15.add(fatherincomeX, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 90, -1, -1));

        fatherincomeC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel15.add(fatherincomeC, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 90, -1, -1));

        fatheroccupationX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel15.add(fatheroccupationX, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 90, -1, -1));

        fatheroccupationC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel15.add(fatheroccupationC, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 90, -1, -1));

        billingPanel.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 600, 130));

        jPanel16.setBackground(new java.awt.Color(51, 51, 51));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mothers Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 0));
        jLabel43.setText("Fullname:");
        jPanel16.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 29, -1, -1));

        mothernamefield.setForeground(new java.awt.Color(153, 153, 153));
        mothernamefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mothernamefield.setText("Enter mother name..");
        mothernamefield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mothernamefieldMouseClicked(evt);
            }
        });
        mothernamefield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mothernamefieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mothernamefieldKeyReleased(evt);
            }
        });
        jPanel16.add(mothernamefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 27, 226, 23));

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 0));
        jLabel44.setText("Address:");
        jPanel16.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 60, -1, -1));

        motheraddressfield.setForeground(new java.awt.Color(153, 153, 153));
        motheraddressfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        motheraddressfield.setText("Enter mother address..");
        motheraddressfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                motheraddressfieldMouseClicked(evt);
            }
        });
        motheraddressfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                motheraddressfieldActionPerformed(evt);
            }
        });
        motheraddressfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                motheraddressfieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                motheraddressfieldKeyReleased(evt);
            }
        });
        jPanel16.add(motheraddressfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 58, 487, 23));

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 0));
        jLabel39.setText("Mobile#:");
        jPanel16.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, -1, -1));

        mothermobilefield.setForeground(new java.awt.Color(153, 153, 153));
        mothermobilefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mothermobilefield.setText("Enter mother contact..");
        mothermobilefield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mothermobilefieldMouseClicked(evt);
            }
        });
        mothermobilefield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mothermobilefieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mothermobilefieldKeyReleased(evt);
            }
        });
        jPanel16.add(mothermobilefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 27, 150, 23));

        motheroccupationfield.setForeground(new java.awt.Color(153, 153, 153));
        motheroccupationfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        motheroccupationfield.setText("Enter mother occupation..");
        motheroccupationfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                motheroccupationfieldMouseClicked(evt);
            }
        });
        motheroccupationfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                motheroccupationfieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                motheroccupationfieldKeyReleased(evt);
            }
        });
        jPanel16.add(motheroccupationfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 89, 223, 23));

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 0));
        jLabel40.setText("Occupation:");
        jPanel16.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 91, -1, -1));

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 0));
        jLabel48.setText("Monthly Income:");
        jPanel16.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 91, -1, -1));

        motherincomefield.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "2000-5000", "5500-10000", "10500-15000", "15500-20000", "20500-30000", "30500-50000" }));
        motherincomefield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                motherincomefieldMouseClicked(evt);
            }
        });
        motherincomefield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                motherincomefieldActionPerformed(evt);
            }
        });
        jPanel16.add(motherincomefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(454, 89, 101, 23));

        mothernameC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel16.add(mothernameC, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, -1, -1));

        mothernameX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel16.add(mothernameX, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, -1, -1));

        mothercontactC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel16.add(mothercontactC, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, -1, -1));

        mothercontactX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel16.add(mothercontactX, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, -1, -1));

        motheroccupationC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel16.add(motheroccupationC, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 90, -1, -1));

        motheroccupationX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel16.add(motheroccupationX, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 90, -1, -1));

        motherincomeC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel16.add(motherincomeC, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 90, -1, -1));

        motherincomeX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel16.add(motherincomeX, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 90, -1, -1));

        motheraddressC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        jPanel16.add(motheraddressC, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, -1, -1));

        motheraddressX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        jPanel16.add(motheraddressX, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, -1, -1));

        billingPanel.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 600, 130));

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 0));
        jLabel47.setText("Payment Method:");
        billingPanel.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 13, -1, -1));

        paymentmethodfield.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Cash", "Installment" }));
        paymentmethodfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paymentmethodfieldMouseClicked(evt);
            }
        });
        paymentmethodfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentmethodfieldActionPerformed(evt);
            }
        });
        billingPanel.add(paymentmethodfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 11, 101, 23));

        paymentX.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\cross.png")); // NOI18N
        billingPanel.add(paymentX, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, -1));

        paymentC.setIcon(new javax.swing.ImageIcon("C:\\Users\\MILES\\Documents\\NetBeansProjects\\EnrollmentSystem\\src\\images\\check.png")); // NOI18N
        billingPanel.add(paymentC, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, -1));

        javax.swing.GroupLayout boardLayout = new javax.swing.GroupLayout(board);
        board.setLayout(boardLayout);
        boardLayout.setHorizontalGroup(
            boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(generalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(boardLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(billingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        boardLayout.setVerticalGroup(
            boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(generalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(boardLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(billingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel2.add(board, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 690, 420));

        billingBtn.setBackground(new java.awt.Color(153, 153, 153));
        billingBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                billingBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                billingBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                billingBtnMouseExited(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Billing");

        javax.swing.GroupLayout billingBtnLayout = new javax.swing.GroupLayout(billingBtn);
        billingBtn.setLayout(billingBtnLayout);
        billingBtnLayout.setHorizontalGroup(
            billingBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(billingBtnLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        billingBtnLayout.setVerticalGroup(
            billingBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
        );

        jPanel2.add(billingBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 30, -1, 20));

        oldstudentfield.setBackground(new java.awt.Color(102, 102, 102));
        oldstudentfield.setForeground(new java.awt.Color(255, 255, 0));
        oldstudentfield.setText("Old Student");
        oldstudentfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                oldstudentfieldMouseClicked(evt);
            }
        });
        jPanel2.add(oldstudentfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        newstudentfield.setBackground(new java.awt.Color(102, 102, 102));
        newstudentfield.setForeground(new java.awt.Color(255, 255, 0));
        newstudentfield.setText("New Student");
        newstudentfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newstudentfieldMouseClicked(evt);
            }
        });
        newstudentfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newstudentfieldActionPerformed(evt);
            }
        });
        jPanel2.add(newstudentfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, -1));

        searchIdField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        searchIdField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchIdFieldActionPerformed(evt);
            }
        });
        searchIdField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchIdFieldKeyReleased(evt);
            }
        });
        jPanel2.add(searchIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 144, 23));

        generalBtn.setBackground(new java.awt.Color(153, 153, 153));
        generalBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                generalBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                generalBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                generalBtnMouseExited(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("General");

        javax.swing.GroupLayout generalBtnLayout = new javax.swing.GroupLayout(generalBtn);
        generalBtn.setLayout(generalBtnLayout);
        generalBtnLayout.setHorizontalGroup(
            generalBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generalBtnLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        generalBtnLayout.setVerticalGroup(
            generalBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
        );

        jPanel2.add(generalBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, 100, 20));

        jPanel3.setBackground(new java.awt.Color(153, 0, 0));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        closeButton.setForeground(new java.awt.Color(255, 255, 255));
        closeButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeButton.setText("X");
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeButtonMouseExited(evt);
            }
        });
        closeButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                closeButtonKeyPressed(evt);
            }
        });
        jPanel3.add(closeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 0, 51, 35));

        timeLabel.setForeground(new java.awt.Color(255, 255, 255));
        timeLabel.setText("Time");
        jPanel3.add(timeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        dateLabel.setForeground(new java.awt.Color(255, 255, 255));
        dateLabel.setText("Date");
        jPanel3.add(dateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, -1, -1));

        save.setBackground(new java.awt.Color(0, 102, 102));
        save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                saveMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                saveMouseExited(evt);
            }
        });

        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Save & Enroll");

        javax.swing.GroupLayout saveLayout = new javax.swing.GroupLayout(save);
        save.setLayout(saveLayout);
        saveLayout.setHorizontalGroup(
            saveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        saveLayout.setVerticalGroup(
            saveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 0));
        jLabel27.setText("Log out");
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel27MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel27MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel27MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(460, 460, 460)
                        .addComponent(jLabel27))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 714, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_closeButtonMouseClicked

    private void closeButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseEntered
        closeButton.setForeground(Color.PINK);
    }//GEN-LAST:event_closeButtonMouseEntered

    private void closeButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseExited
        closeButton.setForeground(Color.white);
    }//GEN-LAST:event_closeButtonMouseExited

    private void closeButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_closeButtonKeyPressed
        this.setVisible(false);
    }//GEN-LAST:event_closeButtonKeyPressed

    private void generalBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_generalBtnMouseClicked
        controll.setColorOfButton(new Color(153,153,153));
        controll.setButton(generalBtn);
        panelPicker();
        controll.setColorOfButton(new Color(255,0,0));
    }//GEN-LAST:event_generalBtnMouseClicked

    private void generalBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_generalBtnMouseEntered
        if(!controll.getButton().equals(generalBtn)){
            generalBtn.setBackground(new Color(204,204,204));
        }
    }//GEN-LAST:event_generalBtnMouseEntered

    private void generalBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_generalBtnMouseExited
        if(!controll.getButton().equals(generalBtn)){
            generalBtn.setBackground(new Color(153,153,153));
        }
    }//GEN-LAST:event_generalBtnMouseExited

    private void billingBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_billingBtnMouseClicked
        controll.setColorOfButton(new Color(153,153,153));
        controll.setButton(billingBtn);
        panelPicker();
        controll.setColorOfButton(new Color(255,0,0));
    }//GEN-LAST:event_billingBtnMouseClicked

    private void billingBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_billingBtnMouseEntered
        if(!controll.getButton().equals(billingBtn)){
            billingBtn.setBackground(new Color(204,204,204));
        }
    }//GEN-LAST:event_billingBtnMouseEntered

    private void billingBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_billingBtnMouseExited
        if(!controll.getButton().equals(billingBtn)){
            billingBtn.setBackground(new Color(153,153,153));
        }
    }//GEN-LAST:event_billingBtnMouseExited

    private void saveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveMouseClicked
        
        Connection conn = null;
        Connection conn2 = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;
        PreparedStatement ps5 = null;
        PreparedStatement ps6 = null;
        PreparedStatement ps7 = null;
        PreparedStatement ps8 = null;
        PreparedStatement p = null;
            getStudentCurrentYearEnrolled(searchIdField.getText());
            switch(buttonVal){
                case "Save & Enroll":
                    try{
                        if(searchIdField.getText().equals("")){
                            JOptionPane.showMessageDialog(this, "Please enter your current student id");
                        }
                        else if(searchOldStudent==false){
                            JOptionPane.showMessageDialog(this, "Please enter correct student id");
                        }
                        else if(searchOldStudent==true){
                            if(currenSchoolYear.equals(studentSchoolyear)){
                                JOptionPane.showMessageDialog(this, "You are already enrolled");
                            }else{
                                fieldsValidation();
                                agradeDouble=Double.valueOf(agradefield.getText());
                        conn = DriverManager.getConnection(dbURL, USER, PASS);
                        conn2 = DriverManager.getConnection(dbURL, USER, PASS);
                        conn2.setAutoCommit(false);
                        conn.setAutoCommit(false);
                        String yearLev = "";
                        if(yearlevelfield.getSelectedItem().toString().equals("First Year"))
                            yearLev="1st Year";
                        
                        if(yearlevelfield.getSelectedItem().toString().equals("Second Year"))
                            yearLev="2nd Year";
                        
                        if(yearlevelfield.getSelectedItem().toString().equals("Third Year"))
                            yearLev="3rd Year";
                        
                        if(yearlevelfield.getSelectedItem().toString().equals("Fourth Year"))
                            yearLev="4th Year";
                        

                        System.out.println(yearLev);
                        getGrade(yearLev);
                        if(validation==false)
                            return;
                        //JOptionPane.showMessageDialog(null,secName);
                        String sql="UPDATE student_personal_info SET firstname=?, lastname=?,middlename=?,extension=?,"
                                + "gender=?,age=?,birthdate=?,birthplace=?,address=? WHERE studentId=?";
                        ps = conn.prepareStatement(sql);
                        ps.setString(10, searchIdField.getText());
                        ps.setString(1, fnamefield.getText());
                        ps.setString(2, lnamefield.getText());
                        ps.setString(3, mnamefield.getText());
                        if(extensionField.getSelectedItem().toString().equals("Select"))
                            ps.setString(4,"");
                        if(!extensionField.getSelectedItem().toString().equals("Select"))
                            ps.setString(4,extensionField.getSelectedItem().toString());
                        ps.setString(5, genderField.getSelectedItem().toString());
                        ps.setString(6, agefield.getText());
                        ps.setString(7, bdatefield.getText());
                        ps.setString(8, bplacefield.getText());
                        ps.setString(9, addressfield.getText());
                        int rowAffected = ps.executeUpdate();
                        if(rowAffected == 1){
                            System.out.println(1);
                            String sql2 = "UPDATE student_contact SET contact=?, email=? WHERE "
                                    + "studentId=?";
                            ps2 = conn.prepareStatement(sql2);
                            ps2.setString(3,searchIdField.getText());
                            ps2.setString(1,contactfield.getText());
                            ps2.setString(2, emailfield.getText());
                            int rowAffected2 = ps2.executeUpdate();
                            if(rowAffected2==1){
                                System.out.println(2);
                                String sql3="UPDATE guardian SET name=?,contact=? WHERE studentId=?";
                                ps3 = conn.prepareStatement(sql3);
                                ps3.setString(3,searchIdField.getText());
                                ps3.setString(1,gnamefield.getText());
                                ps3.setString(2, gcontactfield.getText());
                                int rowAffected3 = ps3.executeUpdate();

                                if(rowAffected3==1){
                                    System.out.println(3);
                                    String sql4 = "UPDATE payment SET payment_type=? WHERE studentId=?";
                                    ps4 = conn.prepareStatement(sql4);
                                    ps4.setString(2, searchIdField.getText());
                                    ps4.setString(1, paymentmethodfield.getSelectedItem().toString());
                                    int rowAffected4 = ps4.executeUpdate();
                                    int payId=0;
                                    
                                    String pSql = "SELECT paymentId FROM `payment` WHERE studentId='"+searchIdField.getText()+"';";
                                    p = conn2.prepareStatement(pSql);
                                    ResultSet result = p.executeQuery();
                                    while(result.next()){
                                        payId = result.getInt(1);
                                    }
                                    conn2.close();
                                    //JOptionPane.showMessageDialog(this, Integer.toString(payId));

                                    if(rowAffected4 == 1){
                                        System.out.println(4);
                                        String sql5 ="UPDATE mothers_info SET name=?,contact=?,address=?,occupation=?,monthly_income=?"
                                                + "WHERE studentId=?";
                                        ps5 = conn.prepareStatement(sql5);
                                        ps5.setString(6, searchIdField.getText());
                                        ps5.setString(1, mothernamefield.getText());
                                        ps5.setString(2,mothermobilefield.getText());
                                        ps5.setString(3, motheraddressfield.getText());
                                        ps5.setString(4, motheroccupationfield.getText());
                                        ps5.setString(5, motherincomefield.getSelectedItem().toString());
                                        int rowAffected5 = ps5.executeUpdate();

                                        if(rowAffected5 == 1){
                                            System.out.println(5);
                                            String sql6 ="UPDATE fathers_info SET name=?,contact=?,address=?,occupation=?,monthly_income=?"
                                                + "WHERE studentId=?";
                                            ps6 = conn.prepareStatement(sql6);
                                            ps6.setString(6, searchIdField.getText());
                                            ps6.setString(1, fathernamefield.getText());
                                            ps6.setString(2, fathermobilefield.getText());
                                            ps6.setString(3, fatheraddressfield.getText());
                                            ps6.setString(4, fatheroccupationfield.getText());
                                            ps6.setString(5, fatherincomefield.getSelectedItem().toString());
                                            int rowAffected6 = ps6.executeUpdate();

                                            if(rowAffected6==1){
                                                System.out.println(6);
                                                String sql7 = "UPDATE school_info SET sectionname=?,yearlevel=?,course=?,semester=?"
                                                        + ",schoolyear=?,averagegrade=? WHERE studentId=?";
                                                ps7 = conn.prepareStatement(sql7);
                                                ps7.setString(7, searchIdField.getText());
                                                ps7.setString(2, yearlevelfield.getSelectedItem().toString());
                                                ps7.setString(3, coursefield.getSelectedItem().toString());
                                                ps7.setString(4, semesterfield.getSelectedItem().toString());
                                                ps7.setString(5, schoolyearfield.getText());
                                                ps7.setDouble(6, agradeDouble);
                                                ps7.setString(1, secName);
                                                int rowAffected7 = ps7.executeUpdate();
                                                
                                                if(rowAffected7==1){
                                                    System.out.println(7);
                                                    String sql8="UPDATE enrolled SET dateenrolled=(SELECT DATE_FORMAT(NOW(),'%M %d %Y')), "
                                                            + "timeenrolled= (SELECT DATE_FORMAT(NOW(),'%h:%i %p'))"
                                                            + " WHERE paymentId='"+payId+"';";
                                                            System.err.println(payId);
                                                    ps8 = conn.prepareStatement(sql8);
                                                    int rowAffected8 = ps8.executeUpdate();
                                                    if(rowAffected8 == 1){
                                                        System.out.println(8);
                                                        int ans = JOptionPane.showConfirmDialog(this, "Do you want to save?","Message",JOptionPane.YES_NO_OPTION); 
                                                        if(ans == JOptionPane.YES_OPTION){
                                                            JOptionPane.showMessageDialog(this, "You are now enrolled");
                                                            conn.commit();//we call commit because the transaction process is success
                                                            fnamefield.setText("");
                                                            fnameC.setVisible(false);
                                                            mnamefield.setText("");
                                                            mnameC.setVisible(false);
                                                            lnamefield.setText("");
                                                            lnameC.setVisible(false);
                                                            extensionField.setSelectedItem("Select");
                                                            genderField.setSelectedItem("Select");
                                                            genderC.setVisible(false);
                                                            bplacefield.setText("");
                                                            bplaceC.setVisible(false);
                                                            bdatefield.setText("");
                                                            bdateC.setVisible(false);
                                                            yearlevelfield.setSelectedItem("Select");
                                                            schoolyearfield.setText("");
                                                            yearlevelC.setVisible(false);
                                                            coursefield.setSelectedItem("Select");
                                                            courseC.setVisible(false);
                                                            agefield.setText("");
                                                            ageC.setVisible(false);
                                                            addressfield.setText("");
                                                            addressC.setVisible(false);
                                                            agradefield.setText("");
                                                            agradeC.setVisible(false);
                                                            emailfield.setText("");
                                                            emailC.setVisible(false);
                                                            contactfield.setText("");
                                                            contactC.setVisible(false);
                                                            gnamefield.setText("");
                                                            gnameC.setVisible(false);
                                                            gcontactfield.setText("");
                                                            gcontactC.setVisible(false);
                                                            paymentmethodfield.setSelectedItem("Select");
                                                            paymentC.setVisible(false);
                                                            mothernamefield.setText("");
                                                            mothernameC.setVisible(false);
                                                            mothermobilefield.setText("");
                                                            mothercontactC.setVisible(false);
                                                            motheraddressfield.setText("");
                                                            motheraddressC.setVisible(false);
                                                            motheroccupationfield.setText("");
                                                            motheroccupationC.setVisible(false);
                                                            motherincomefield.setSelectedItem("Select");
                                                            motherincomeC.setVisible(false);
                                                            fathernamefield.setText("");
                                                            fathernameC.setVisible(false);
                                                            fathermobilefield.setText("");
                                                            fathermobileC.setVisible(false);
                                                            fatheraddressfield.setText("");
                                                            fatheraddressC.setVisible(false);
                                                            fatheroccupationfield.setText("");
                                                            fatheroccupationC.setVisible(false);
                                                            fatherincomefield.setSelectedItem("Select");
                                                            fatherincomeC.setVisible(false);
                                                            displayName.setText("");
                                                            displayEmail.setText("");
                                                            displayYear.setText("");
                                                            displayContact.setText("");
                                                            getNewStudentID();
                                                        }else{
                                                            conn.rollback();
                                                        }
                                                        
                                                    }else{
                                                    conn.rollback();
                                                    JOptionPane.showMessageDialog(this, "Something went wrong "); 
                                                    }
                                                }else{
                                                    conn.rollback();
                                                    JOptionPane.showMessageDialog(this, "Something went wrong ");
                                                }

                                            }else{
                                                conn.rollback();
                                                JOptionPane.showMessageDialog(this, "Something went wrong ");
                                            }

                                        }else{
                                            conn.rollback();
                                            JOptionPane.showMessageDialog(this, "Something went wrong");
                                        }

                                    }else{
                                        conn.rollback();
                                        JOptionPane.showMessageDialog(this, "Something went wrong");
                                    }

                                }else{
                                    conn.rollback();
                                    JOptionPane.showMessageDialog(this, "Something went wrong");
                                }

                            }else{
                                conn.rollback();
                                JOptionPane.showMessageDialog(this, "Something went wrong");
                            }

                        }else{
                            conn.rollback();
                            JOptionPane.showMessageDialog(this, "Something went wrong");
                        }
                            }
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
                        if(ps4 != null) ps4.close();
                        if(ps5 != null) ps5.close();
                        if(ps6 != null) ps6.close();
                        if(ps7 != null) ps7.close();
                        if(ps8 != null) ps8.close();
                        if(conn != null) conn.close();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                }
                    break;
                case "Enroll":
                    fieldsValidation();
                    try{
                        agradeDouble = Double.parseDouble(agradefield.getText());
                        conn = DriverManager.getConnection(dbURL, USER, PASS);
                        conn.setAutoCommit(false);
                        String yearLev = "";
                        if(yearlevelfield.getSelectedItem().toString().equals("First Year"))
                            yearLev="1st Year";
                        
                        if(yearlevelfield.getSelectedItem().toString().equals("Second Year"))
                            yearLev="2nd Year";
                        
                        if(yearlevelfield.getSelectedItem().toString().equals("Third Year"))
                            yearLev="3rd Year";
                        
                        if(yearlevelfield.getSelectedItem().toString().equals("Fourth Year"))
                            yearLev="4th Year";
                        if(validation==false)
                            return;
                        //System.out.println(yearLev);
                        getGrade(yearLev);
                        //JOptionPane.showMessageDialog(null,secName);
                        String sql="INSERT INTO student_personal_info(studentId,firstname,lastname,middlename,"
                                + "extension,gender,age,birthdate,birthplace,address)SELECT ?,?,?,?,?,?,?,?,?,? WHERE "
                                + "NOT EXISTS (SELECT studentId,firstname,lastname,middlename,extension FROM student_personal_info"
                                + " WHERE studentId = ? AND firstname = ? AND lastname = ? AND extension = ?)LIMIT 1;";
                        System.out.print(1);
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, searchIdField.getText());
                        ps.setString(2, fnamefield.getText());
                        ps.setString(3, lnamefield.getText());
                        ps.setString(4, mnamefield.getText());
                        if(extensionField.getSelectedItem().toString().equals("Select"))
                            ps.setString(5,"");
                        if(!extensionField.getSelectedItem().toString().equals("Select"))
                            ps.setString(5,extensionField.getSelectedItem().toString());
                        ps.setString(6, genderField.getSelectedItem().toString());
                        ps.setString(7, agefield.getText());
                        ps.setString(8, bdatefield.getText());
                        ps.setString(9, bplacefield.getText());
                        ps.setString(10, addressfield.getText());
                        ps.setString(11, searchIdField.getText());
                        ps.setString(12, fnamefield.getText());
                        ps.setString(13, lnamefield.getText());
                        ps.setString(14, mnamefield.getText());
                        int rowAffected = ps.executeUpdate();
                        if(rowAffected == 1){
                            System.out.print(2);
                            String sql2 = "INSERT INTO student_contact(studentId,contact,email)SELECT ?,?,?"
                            + " WHERE NOT EXISTS(SELECT studentId,contact,email FROM student_contact "
                            + "WHERE studentId = ? AND contact = ? AND email = ?)LIMIT 1;";
                            ps2 = conn.prepareStatement(sql2);
                            ps2.setString(1,searchIdField.getText());
                            ps2.setString(2,contactfield.getText());
                            ps2.setString(3, emailfield.getText());
                            ps2.setString(4,searchIdField.getText());
                            ps2.setString(5,contactfield.getText());
                            ps2.setString(6, emailfield.getText());
                            int rowAffected2 = ps2.executeUpdate();
                            if(rowAffected2==1){
                                System.out.print(3);
                                String sql3="INSERT INTO guardian(studentId,name,contact)SELECT ?,?,? "
                                        + "WHERE NOT EXISTS (SELECT studentId,name,contact FROM guardian "
                                        + "WHERE studentId = ? AND name = ? AND contact = ?)LIMIT 1;";
                                ps3 = conn.prepareStatement(sql3);
                                ps3.setString(1,searchIdField.getText());
                                ps3.setString(2,gnamefield.getText());
                                ps3.setString(3, gcontactfield.getText());
                                ps3.setString(4,searchIdField.getText());
                                ps3.setString(5, gnamefield.getText());
                                ps3.setString(6, gcontactfield.getText());
                                int rowAffected3 = ps3.executeUpdate();

                                if(rowAffected3==1){
                                    System.out.print(4);
                                    String sql4 = "INSERT INTO payment(studentId,payment_type)SELECT ?,? "
                                            + "WHERE NOT EXISTS (SELECT studentId,payment_type FROM payment "
                                            + "WHERE studentId = ? AND payment_type = ?)LIMIT 1;";
                                    ps4 = conn.prepareStatement(sql4,Statement.RETURN_GENERATED_KEYS);
                                    ps4.setString(1, searchIdField.getText());
                                    ps4.setString(2, paymentmethodfield.getSelectedItem().toString());
                                    ps4.setString(3, searchIdField.getText());
                                    ps4.setString(4, paymentmethodfield.getSelectedItem().toString());
                                    int rowAffected4 = ps4.executeUpdate();

                                    int payId=0;

                                    rs = ps4.getGeneratedKeys();
                                    if(rs.next())
                                        payId=rs.getInt(1);
                                    //JOptionPane.showMessageDialog(this, Integer.toString(payId));

                                    if(rowAffected4 == 1){
                                        System.out.print(5);
                                        String sql5 ="INSERT INTO mothers_info(studentId,name,contact,address,occupation"
                                                + ",monthly_income)SELECT ?,?,?,?,?,? WHERE NOT EXISTS (SELECT studentId"
                                                + ",name FROM mothers_info WHERE studentId = ? AND name = ?)LIMIT 1;";
                                        ps5 = conn.prepareStatement(sql5);
                                        ps5.setString(1, searchIdField.getText());
                                        ps5.setString(2, mothernamefield.getText());
                                        ps5.setString(3,mothermobilefield.getText());
                                        ps5.setString(4, motheraddressfield.getText());
                                        ps5.setString(5, motheroccupationfield.getText());
                                        ps5.setString(6, motherincomefield.getSelectedItem().toString());
                                        ps5.setString(7, searchIdField.getText());
                                        ps5.setString(8, mothernamefield.getText());
                                        int rowAffected5 = ps5.executeUpdate();

                                        if(rowAffected5 == 1){
                                            System.out.print(6);
                                            String sql6 ="INSERT INTO fathers_info(studentId,name,contact,address,occupation"
                                                + ",monthly_income)SELECT ?,?,?,?,?,? WHERE NOT EXISTS (SELECT studentId"
                                                + ",name FROM fathers_info WHERE studentId = ? AND name = ?)LIMIT 1;";
                                            ps6 = conn.prepareStatement(sql6);
                                            ps6.setString(1, searchIdField.getText());
                                            ps6.setString(2, fathernamefield.getText());
                                            ps6.setString(3, fathermobilefield.getText());
                                            ps6.setString(4, fatheraddressfield.getText());
                                            ps6.setString(5, fatheroccupationfield.getText());
                                            ps6.setString(6, fatherincomefield.getSelectedItem().toString());
                                            ps6.setString(7, searchIdField.getText());
                                            ps6.setString(8, fathernamefield.getText());
                                            int rowAffected6 = ps6.executeUpdate();

                                            if(rowAffected6==1){
                                                System.out.print(7);
                                                String sql7 = "INSERT INTO school_info(studentId,yearlevel,course,semester,"
                                                        + "schoolyear,averagegrade,sectionname)SELECT ?,?,?,?,?,?,? WHERE NOT EXISTS(SELECT studentId,"
                                                        + "yearlevel,course FROM school_info WHERE studentId=? AND yearlevel = ? AND"
                                                        + " course = ?)LIMIT 1;";
                                                ps7 = conn.prepareStatement(sql7);
                                                ps7.setString(1, searchIdField.getText());
                                                ps7.setString(2, yearlevelfield.getSelectedItem().toString());
                                                ps7.setString(3, coursefield.getSelectedItem().toString());
                                                ps7.setString(4, semesterfield.getSelectedItem().toString());
                                                ps7.setString(5, schoolyearfield.getText());
                                                ps7.setDouble(6, agradeDouble);
                                                ps7.setString(7, searchIdField.getText());
                                                ps7.setString(8, yearlevelfield.getSelectedItem().toString());
                                                ps7.setString(9, coursefield.getSelectedItem().toString());
                                                ps7.setString(10, secName);
                                                int rowAffected7 = ps7.executeUpdate();
                                                
                                                if(rowAffected7==1){
                                                    System.out.print(8);
                                                    String sql8="INSERT INTO enrolled(paymentId,dateenrolled,timeenrolled)SELECT "+payId+""
                                                            + ",(SELECT DATE_FORMAT(NOW(),'%M %d %Y')),(SELECT DATE_FORMAT(NOW(),'%h:%i %p')) "
                                                            + "WHERE NOT EXISTS (SELECT paymentId FROM enrolled WHERE paymentId = "+payId+")LIMIT 1;";
                                                    ps8 = conn.prepareStatement(sql8);
                                                    int rowAffected8 = ps8.executeUpdate();
                                                    if(rowAffected8 == 1){
                                                        int ans = JOptionPane.showConfirmDialog(this, "Do you want to save?","Message",JOptionPane.YES_NO_OPTION); 
                                                        if(ans == JOptionPane.YES_OPTION){
                                                            JOptionPane.showMessageDialog(this, "You are now enrolled");
                                                            conn.commit();//we call commit because the transaction process is success
                                                            fnamefield.setText("");
                                                            fnameC.setVisible(false);
                                                            mnamefield.setText("");
                                                            mnameC.setVisible(false);
                                                            lnamefield.setText("");
                                                            lnameC.setVisible(false);
                                                            extensionField.setSelectedItem("Select");
                                                            genderField.setSelectedItem("Select");
                                                            genderC.setVisible(false);
                                                            bplacefield.setText("");
                                                            bplaceC.setVisible(false);
                                                            bdatefield.setText("");
                                                            bdateC.setVisible(false);
                                                            yearlevelfield.setSelectedItem("");
                                                            yearlevelC.setVisible(false);
                                                            coursefield.setSelectedItem("Select");
                                                            courseC.setVisible(false);
                                                            agefield.setText("");
                                                            ageC.setVisible(false);
                                                            addressfield.setText("");
                                                            addressC.setVisible(false);
                                                            agradefield.setText("");
                                                            agradeC.setVisible(false);
                                                            emailfield.setText("");
                                                            emailC.setVisible(false);
                                                            contactfield.setText("");
                                                            contactC.setVisible(false);
                                                            gnamefield.setText("");
                                                            gnameC.setVisible(false);
                                                            gcontactfield.setText("");
                                                            gcontactC.setVisible(false);
                                                            paymentmethodfield.setSelectedItem("Select");
                                                            paymentC.setVisible(false);
                                                            mothernamefield.setText("");
                                                            mothernameC.setVisible(false);
                                                            mothermobilefield.setText("");
                                                            mothercontactC.setVisible(false);
                                                            motheraddressfield.setText("");
                                                            motheraddressC.setVisible(false);
                                                            motheroccupationfield.setText("");
                                                            motheroccupationC.setVisible(false);
                                                            motherincomefield.setSelectedItem("Select");
                                                            motherincomeC.setVisible(false);
                                                            fathernamefield.setText("");
                                                            fathernameC.setVisible(false);
                                                            fathermobilefield.setText("");
                                                            fathermobileC.setVisible(false);
                                                            fatheraddressfield.setText("");
                                                            fatheraddressC.setVisible(false);
                                                            fatheroccupationfield.setText("");
                                                            fatheroccupationC.setVisible(false);
                                                            fatherincomefield.setSelectedItem("Select");
                                                            fatherincomeC.setVisible(false);
                                                            displayName.setText("");
                                                            displayEmail.setText("");
                                                            displayYear.setText("");
                                                            displayContact.setText("");
                                                            getNewStudentID();
                                                        }else{
                                                            conn.rollback();
                                                        }
                                                        
                                                    }else{
                                                    conn.rollback();
                                                    JOptionPane.showMessageDialog(this, "Something went wrong "); 
                                                    }
                                                }else{
                                                    conn.rollback();
                                                    JOptionPane.showMessageDialog(this, "Something went wrong ");
                                                }

                                            }else{
                                                conn.rollback();
                                                JOptionPane.showMessageDialog(this, "Something went wrong ");
                                            }

                                        }else{
                                            conn.rollback();
                                            JOptionPane.showMessageDialog(this, "Unsuccess to register");
                                        }

                                    }else{
                                        conn.rollback();
                                        JOptionPane.showMessageDialog(this, "Unsuccess to register");
                                    }

                                }else{
                                    conn.rollback();
                                    JOptionPane.showMessageDialog(this, "Unsuccess to register");
                                }

                            }else{
                                conn.rollback();
                                JOptionPane.showMessageDialog(this, "Unsuccess to register");
                            }

                        }else{
                            conn.rollback();
                            JOptionPane.showMessageDialog(this, "Unsuccess to register");
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
                    }
                    catch(NumberFormatException e){
                        System.out.println(e);
                    }
                    finally{
                    try{
                        if(rs != null) rs.close();
                        if(ps != null) ps.close();
                        if(ps2 != null) ps2.close();
                        if(ps3 != null) ps3.close();
                        if(ps4 != null) ps4.close();
                        if(ps5 != null) ps5.close();
                        if(ps6 != null) ps6.close();
                        if(ps7 != null) ps7.close();
                        if(ps8 != null) ps8.close();
                        if(conn != null) conn.close();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                }
                    break;
            }
        

    }//GEN-LAST:event_saveMouseClicked

    private void saveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveMouseEntered
        controll2 = new controller();
        controll2.setButton(save);
        controll2.setColorOfButton(new Color(0,204,204));


    }//GEN-LAST:event_saveMouseEntered

    private void saveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveMouseExited

            controll2.setColorOfButton(new Color(0,102,102));

    }//GEN-LAST:event_saveMouseExited

    private void mnamefieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mnamefieldKeyReleased
        String names="";
        textBoxLabel();
        if(!mnamefield.getText().equals("Enter middlename.."))mnameC.setVisible(true);
        if(mnamefield.getText().equals("Enter middlename.."))mnameC.setVisible(false);
        if(!fnamefield.getText().equals("Enter firstname.."))
            names = fnamefield.getText();
        if(!mnamefield.getText().equals("Enter middlename.."))
            names = names + " " + mnamefield.getText();
        if(!lnamefield.getText().equals("Enter lastname.."))
            names = names + " " + lnamefield.getText();
        if(!extensionField.getSelectedItem().equals("Select"))
            names = names + extensionField.getSelectedItem().toString();
        displayName.setText(names);
    }//GEN-LAST:event_mnamefieldKeyReleased

    private void newstudentfieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newstudentfieldMouseClicked
        checkImage();
        crossImage();
        redBorder();
        sId = "";
        validation=false;
        try{
                lnamefield.setText("");
                mnamefield.setText("");
                fnamefield.setText("");
                extensionField.setSelectedItem("Select");
                genderField.setSelectedItem("Select");
                agefield.setText("");
                bdatefield.setText("");
                bplacefield.setText("");
                displayName.setText("");
                displayYear.setText("");
                displayEmail.setText("");
                displayContact.setText("");
                addressfield.setText("");
                yearlevelfield.setSelectedItem("Select");
                coursefield.setSelectedItem("Select");
                semesterfield.setSelectedItem("Select");
                schoolyearfield.setText(currenSchoolYear);
                agradefield.setText("");
                emailfield.setText("");
                contactfield.setText("");
                gnamefield.setText("");
                gcontactfield.setText("");
                paymentmethodfield.setSelectedItem("Select");
                mothernamefield.setText("");
                mothermobilefield.setText("");
                motheraddressfield.setText("");
                motheroccupationfield.setText("");
                motherincomefield.setSelectedItem("Select");
                fathernamefield.setText("");
                fathermobilefield.setText("");
                fatheraddressfield.setText("");
                fatheroccupationfield.setText("");
                fatherincomefield.setSelectedItem("Select");
                textBoxLabel();
                getNewStudentID();
                searchIdField.setEnabled(false);
        }catch(Exception e){
            e.printStackTrace();
        }

    }//GEN-LAST:event_newstudentfieldMouseClicked

    private void oldstudentfieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_oldstudentfieldMouseClicked
        checkImage();
        crossImage();
        redBorder();  
        searchOldStudent=false;
        validation=false;
        lnamefield.setText("");
                mnamefield.setText("");
                fnamefield.setText("");
                extensionField.setSelectedItem("Select");
                genderField.setSelectedItem("Select");
                agefield.setText("");
                bdatefield.setText("");
                bplacefield.setText("");
                displayName.setText("");
                displayYear.setText("");
                displayEmail.setText("");
                displayContact.setText("");
                addressfield.setText("");
                yearlevelfield.setSelectedItem("Select");
                coursefield.setSelectedItem("Select");
                semesterfield.setSelectedItem("Select");
                schoolyearfield.setText(currenSchoolYear);
                agradefield.setText("");
                emailfield.setText("");
                contactfield.setText("");
                gnamefield.setText("");
                gcontactfield.setText("");
                paymentmethodfield.setSelectedItem("Select");
                mothernamefield.setText("");
                mothermobilefield.setText("");
                motheraddressfield.setText("");
                motheroccupationfield.setText("");
                motherincomefield.setSelectedItem("Select");
                fathernamefield.setText("");
                fathermobilefield.setText("");
                fatheraddressfield.setText("");
                fatheroccupationfield.setText("");
                fatherincomefield.setSelectedItem("Select");
        buttonVal = "Save & Enroll";
        jLabel21.setText(buttonVal);
        searchIdField.setText("");
        searchIdField.setEnabled(true);
        textBoxLabel();
    }//GEN-LAST:event_oldstudentfieldMouseClicked

    private void contactfieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contactfieldKeyReleased
        if(!contactfield.getText().equals("Enter contact.."))contactC.setVisible(true);
        textBoxLabel();
        if(contactfield.getText().equals("Enter contact"))contactC.setVisible(false);
        displayContact.setText(contactfield.getText());
    }//GEN-LAST:event_contactfieldKeyReleased

    private void emailfieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emailfieldKeyReleased
        
        textBoxLabel();
        if(!emailfield.getText().equals("Enter email.."))emailC.setVisible(true);
        if(emailfield.getText().equals("Enter email.."))emailC.setVisible(false);
        displayEmail.setText(emailfield.getText());
    }//GEN-LAST:event_emailfieldKeyReleased

    private void lnamefieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lnamefieldKeyReleased
        textBoxLabel();
        String names="";
        if(!lnamefield.getText().equals("Enter lastname.."))lnameC.setVisible(true);
        if(lnamefield.getText().equals("Enter lastname.."))lnameC.setVisible(false);
        if(!fnamefield.getText().equals("Enter firstname.."))
            names = fnamefield.getText();
        if(!mnamefield.getText().equals("Enter middlename.."))
            names = names + " " + mnamefield.getText();
        if(!lnamefield.getText().equals("Enter lastname.."))
            names = names + " " + lnamefield.getText();
        if(!extensionField.getSelectedItem().equals("Select"))
            names = names + extensionField.getSelectedItem().toString();
        displayName.setText(names);
    }//GEN-LAST:event_lnamefieldKeyReleased

    private void fnamefieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fnamefieldKeyReleased
        String names="";
        textBoxLabel();
        if(!fnamefield.getText().equals("Enter firstname.."))fnameC.setVisible(true);
        if(fnamefield.getText().equals("Enter firstname.."))fnameC.setVisible(false);
        if(!fnamefield.getText().equals("Enter firstname.."))
            names = fnamefield.getText();
        if(!mnamefield.getText().equals("Enter middlename.."))
            names = names + " " + mnamefield.getText();
        if(!lnamefield.getText().equals("Enter lastname.."))
            names = names + " " + lnamefield.getText();
        if(!extensionField.getSelectedItem().equals("Select"))
            names = names + extensionField.getSelectedItem().toString();
        displayName.setText(names);
    }//GEN-LAST:event_fnamefieldKeyReleased

    private void extensionFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_extensionFieldActionPerformed
        textBoxLabel();
        if(extensionField.getSelectedItem().toString().equals("Select")){
            displayName.setText(fnamefield.getText()+" "+mnamefield.getText()+" "+lnamefield.getText());
        }else{
            displayName.setText(fnamefield.getText()+" "+mnamefield.getText()+" "+lnamefield.getText()
            +" "+extensionField.getSelectedItem().toString());
        }
    }//GEN-LAST:event_extensionFieldActionPerformed

    private void yearlevelfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearlevelfieldActionPerformed
        redBorder();   
        crossImage();
        textBoxLabel();
        if(yearlevelfield.getSelectedItem().toString().equals("Select")){
            displayYear.setText("");yearlevelC.setVisible(false);
        }else{
            displayYear.setText(yearlevelfield.getSelectedItem().toString());yearlevelC.setVisible(true);
        }
    }//GEN-LAST:event_yearlevelfieldActionPerformed

    private void searchIdFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchIdFieldActionPerformed
        
    }//GEN-LAST:event_searchIdFieldActionPerformed

    private void searchIdFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchIdFieldKeyReleased
       
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            conn = DriverManager.getConnection(dbURL, USER, PASS);
            conn.setAutoCommit(false);
            String sql = "SELECT spi.lastname,spi.firstname,spi.middlename,spi.extension,spi.gender,"
                    + "spi.age,spi.birthdate,spi.birthplace,spi.address,si.yearlevel,si.course,si.semester,"
                    + "si.schoolyear,si.averagegrade,sc.email,sc.contact,g.name,g.contact,p.payment_type,"
                    + "mi.name,mi.contact,mi.address,mi.occupation,mi.monthly_income,fi.name,fi.contact,"
                    + "fi.address,fi.occupation,fi.monthly_income FROM student_personal_info AS spi,"
                    + "school_info AS si,student_contact AS sc,guardian AS g,payment AS p,mothers_info "
                    + "AS mi,fathers_info AS fi WHERE spi.studentId = '"+searchIdField.getText()+"' AND si.studentId = '"+searchIdField.getText()+"' AND "
                    + "sc.studentId = '"+searchIdField.getText()+"' AND g.studentId = '"+searchIdField.getText()+"' AND "
                    + "p.studentId = '"+searchIdField.getText()+"' AND mi.studentId = '"+searchIdField.getText()+"' "
                    + "AND fi.studentId = '"+searchIdField.getText()+"' ;";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(); 
            while(rs.next()){
                lnamefield.setText(rs.getString(1));
                lnamefield.setForeground(Color.black);
                lnameC.setVisible(true);
                fnamefield.setText(rs.getString(2));
                fnamefield.setForeground(Color.black);
                fnameC.setVisible(true);
                mnamefield.setText(rs.getString(3));
                mnamefield.setForeground(Color.black);
                mnameC.setVisible(true);
                if(rs.getString(4).equals(""))
                    extensionField.setSelectedItem("Select");
                else
                    extensionField.setSelectedItem(rs.getString(4));
                genderField.setSelectedItem(rs.getString(5));
                genderC.setVisible(true);
                agefield.setText(rs.getString(6));
                agefield.setForeground(Color.black);
                ageC.setVisible(true);
                bdatefield.setText(rs.getString(7));
                bdatefield.setForeground(Color.black);
                bdateC.setVisible(true);
                bplacefield.setText(rs.getString(8));
                bplacefield.setForeground(Color.black);
                bplaceC.setVisible(true);
                addressfield.setText(rs.getString(9));
                addressfield.setForeground(Color.black);
                addressC.setVisible(true);
                coursefield.setSelectedItem(rs.getString(11));
                coursefield.setForeground(Color.black);
                courseC.setVisible(true);
                emailfield.setText(rs.getString(15));
                emailfield.setForeground(Color.black);
                emailC.setVisible(true);
                contactfield.setText(rs.getString(16));
                contactfield.setForeground(Color.black);
                contactC.setVisible(true);
                displayEmail.setText(rs.getString(15));
                displayContact.setText(rs.getString(16));
                gnamefield.setText(rs.getString(17));
                gnamefield.setForeground(Color.black);
                gnameC.setVisible(true);
                gcontactfield.setText(rs.getString(18));
                gcontactfield.setForeground(Color.black);
                gcontactC.setVisible(true);
                mothernamefield.setText(rs.getString(20));
                mothernamefield.setForeground(Color.black);
                mothernameC.setVisible(true);
                mothermobilefield.setText(rs.getString(21));
                mothermobilefield.setForeground(Color.black);
                mothercontactC.setVisible(true);
                motheraddressfield.setText(rs.getString(22));
                motheraddressfield.setForeground(Color.black);
                motheraddressC.setVisible(true);
                motheroccupationfield.setText(rs.getString(23));
                motheroccupationfield.setForeground(Color.black);
                motheroccupationC.setVisible(true);
                motherincomefield.setSelectedItem(rs.getString(24));
                motherincomeC.setVisible(true);
                fathernamefield.setText(rs.getString(25));
                fathernamefield.setForeground(Color.black);
                fathernameC.setVisible(true);
                fathermobilefield.setText(rs.getString(26));
                fathermobilefield.setForeground(Color.black);
                fathermobileC.setVisible(true);
                fatheraddressfield.setText(rs.getString(27));
                fatheraddressfield.setForeground(Color.black);
                fatheraddressC.setVisible(true);
                fatheroccupationfield.setText(rs.getString(28));
                fatheroccupationfield.setForeground(Color.black);
                fatheroccupationC.setVisible(true);
                fatherincomefield.setSelectedItem(rs.getString(29));
                fatherincomeC.setVisible(true);
                conn.commit();
                searchOldStudent = true;
                textBoxLabel();
            }
            
        }catch(SQLException e){
            try{
                if(conn !=null){
                    conn.rollback();
                }
            }catch(SQLException s){
                System.out.println(s);
            }
                    System.out.println(e.getMessage());
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Wrong student id");
            System.out.println(e.getMessage());
        }finally{
                    try{
                        if(rs != null) rs.close();
                        if(ps != null) ps.close();
                        if(conn != null) conn.close();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                    return;
        }   
    }//GEN-LAST:event_searchIdFieldKeyReleased

    private void jLabel27MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseEntered
        jLabel27.setForeground(new Color(204,204,0));
    }//GEN-LAST:event_jLabel27MouseEntered

    private void jLabel27MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseExited
        jLabel27.setForeground(new Color(255,255,0));
    }//GEN-LAST:event_jLabel27MouseExited

    private void jLabel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseClicked
        lf = new loginForm();
        lf.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel27MouseClicked

    private void agefieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_agefieldKeyTyped
        char c = evt.getKeyChar();
        if(!((c>='0')&&(c<='9')||
                (c==KeyEvent.VK_SPACE)||
                (c==KeyEvent.VK_DELETE))){
            getToolkit().beep();
            evt.consume();}
    }//GEN-LAST:event_agefieldKeyTyped

    private void agradefieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_agradefieldKeyTyped
        char c = evt.getKeyChar();
        if(!((c>='0')&&(c<='9')||
                (c=='.')||
                (c==KeyEvent.VK_SPACE)||
                (c==KeyEvent.VK_DELETE))){
            getToolkit().beep();
            evt.consume();}
    }//GEN-LAST:event_agradefieldKeyTyped

    private void lnamefieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lnamefieldMouseClicked
        redBorder();crossImage();
        
    }//GEN-LAST:event_lnamefieldMouseClicked

    private void fnamefieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fnamefieldMouseClicked
        redBorder();crossImage();
        
    }//GEN-LAST:event_fnamefieldMouseClicked

    private void mnamefieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnamefieldMouseClicked
        redBorder();crossImage();
        
    }//GEN-LAST:event_mnamefieldMouseClicked

    private void extensionFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_extensionFieldMouseClicked
        redBorder();crossImage();
    }//GEN-LAST:event_extensionFieldMouseClicked

    private void genderFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_genderFieldMouseClicked
        if(!genderField.getSelectedItem().equals("Select"))genderC.setVisible(true);
        if(genderField.getSelectedItem().equals("Select"))genderC.setVisible(false);
        
        redBorder();crossImage();
    }//GEN-LAST:event_genderFieldMouseClicked

    private void agefieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_agefieldMouseClicked
        redBorder();crossImage();
        
    }//GEN-LAST:event_agefieldMouseClicked

    private void bdatefieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bdatefieldMouseClicked
        redBorder();crossImage();
        
    }//GEN-LAST:event_bdatefieldMouseClicked

    private void bplacefieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bplacefieldMouseClicked
        redBorder();crossImage();
        
    }//GEN-LAST:event_bplacefieldMouseClicked

    private void addressfieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addressfieldMouseClicked
        redBorder();crossImage();
        
    }//GEN-LAST:event_addressfieldMouseClicked

    private void yearlevelfieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_yearlevelfieldMouseClicked
        if(!yearlevelfield.getSelectedItem().equals("Select"))yearlevelC.setVisible(true);
        if(yearlevelfield.getSelectedItem().equals("Select"))yearlevelC.setVisible(false);
        redBorder();crossImage();textBoxLabel();
    }//GEN-LAST:event_yearlevelfieldMouseClicked

    private void coursefieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_coursefieldMouseClicked
        if(!coursefield.getSelectedItem().equals("Select"))courseC.setVisible(true);
        if(coursefield.getSelectedItem().equals("Select"))courseC.setVisible(false);
        redBorder();crossImage();textBoxLabel();
    }//GEN-LAST:event_coursefieldMouseClicked

    private void semesterfieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_semesterfieldMouseClicked
        if(!semesterfield.getSelectedItem().equals("Select"))semesterC.setVisible(true);
        if(semesterfield.getSelectedItem().equals("Select"))semesterC.setVisible(false);
        redBorder();crossImage();textBoxLabel();
    }//GEN-LAST:event_semesterfieldMouseClicked

    private void agradefieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_agradefieldMouseClicked
        redBorder();crossImage();textBoxLabel();
    }//GEN-LAST:event_agradefieldMouseClicked

    private void emailfieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_emailfieldMouseClicked
        redBorder();crossImage();
        
    }//GEN-LAST:event_emailfieldMouseClicked

    private void contactfieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contactfieldMouseClicked
        redBorder();crossImage();
        
    }//GEN-LAST:event_contactfieldMouseClicked

    private void gnamefieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gnamefieldMouseClicked
        redBorder();crossImage();
        
    }//GEN-LAST:event_gnamefieldMouseClicked

    private void gcontactfieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gcontactfieldMouseClicked
        redBorder();crossImage();
        
    }//GEN-LAST:event_gcontactfieldMouseClicked

    private void agefieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_agefieldKeyReleased
        textBoxLabel();
        if(!agefield.getText().equals("0"))ageC.setVisible(true);
        if(agefield.getText().equals("0"))ageC.setVisible(false);
    }//GEN-LAST:event_agefieldKeyReleased

    private void bdatefieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bdatefieldKeyReleased
        textBoxLabel();
        if(!bdatefield.getText().equals("Enter bdate.."))bdateC.setVisible(true);
        if(bdatefield.getText().equals("Enter bdate.."))bdateC.setVisible(false);
    }//GEN-LAST:event_bdatefieldKeyReleased

    private void bplacefieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bplacefieldKeyReleased
        
        textBoxLabel();
        if(!bplacefield.getText().equals("Enter bplace.."))bplaceC.setVisible(true);
        if(bplacefield.getText().equals("Enter bplace.."))bplaceC.setVisible(false);
    }//GEN-LAST:event_bplacefieldKeyReleased

    private void addressfieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addressfieldKeyReleased
        
        textBoxLabel();
        if(!addressfield.getText().equals("Enter address.."))addressC.setVisible(true);
        if(addressfield.getText().equals("Enter address.."))addressC.setVisible(false);
    }//GEN-LAST:event_addressfieldKeyReleased

    private void agradefieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_agradefieldKeyReleased
        textBoxLabel();
        if(!agradefield.getText().equals("0.0"))agradeC.setVisible(true);
        if(agradefield.getText().equals("0.0"))agradeC.setVisible(false);
    }//GEN-LAST:event_agradefieldKeyReleased

    private void gnamefieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gnamefieldKeyReleased
        
        textBoxLabel();
        if(!gnamefield.getText().equals("Enter guardian name.."))gnameC.setVisible(true);
        if(gnamefield.getText().equals("Enter guardian name.."))gnameC.setVisible(false);
    }//GEN-LAST:event_gnamefieldKeyReleased

    private void gcontactfieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gcontactfieldKeyReleased
              
        textBoxLabel();
        if(!gcontactfield.getText().equals("Enter guardian contact.."))gcontactC.setVisible(true);
        if(gcontactfield.getText().equals("Enter guardian contact.."))gcontactC.setVisible(false);
    }//GEN-LAST:event_gcontactfieldKeyReleased

    private void genderFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genderFieldActionPerformed
        textBoxLabel();
        if(!genderField.getSelectedItem().equals("Select"))genderC.setVisible(true);
        if(genderField.getSelectedItem().equals("Select"))genderC.setVisible(false);
        redBorder();crossImage();
    }//GEN-LAST:event_genderFieldActionPerformed

    private void coursefieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coursefieldActionPerformed
        textBoxLabel();
        if(!coursefield.getSelectedItem().equals("Select"))courseC.setVisible(true);
        if(coursefield.getSelectedItem().equals("Select"))courseC.setVisible(false);
        redBorder();crossImage();
    }//GEN-LAST:event_coursefieldActionPerformed

    private void semesterfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_semesterfieldActionPerformed
        textBoxLabel();
        if(!semesterfield.getSelectedItem().equals("Select"))semesterC.setVisible(true);
        if(semesterfield.getSelectedItem().equals("Select"))semesterC.setVisible(false);
        redBorder();crossImage();
    }//GEN-LAST:event_semesterfieldActionPerformed

    private void lnamefieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lnamefieldActionPerformed
        
    }//GEN-LAST:event_lnamefieldActionPerformed

    private void fnamefieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fnamefieldActionPerformed
        
    }//GEN-LAST:event_fnamefieldActionPerformed

    private void mnamefieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnamefieldActionPerformed
        
    }//GEN-LAST:event_mnamefieldActionPerformed

    private void agefieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agefieldActionPerformed
        
    }//GEN-LAST:event_agefieldActionPerformed

    private void bdatefieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bdatefieldActionPerformed
        
    }//GEN-LAST:event_bdatefieldActionPerformed

    private void bplacefieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bplacefieldActionPerformed
        
    }//GEN-LAST:event_bplacefieldActionPerformed

    private void addressfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressfieldActionPerformed
        
    }//GEN-LAST:event_addressfieldActionPerformed

    private void gnamefieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gnamefieldActionPerformed
        
    }//GEN-LAST:event_gnamefieldActionPerformed

    private void gcontactfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gcontactfieldActionPerformed
       
    }//GEN-LAST:event_gcontactfieldActionPerformed

    private void emailfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailfieldActionPerformed
    
    }//GEN-LAST:event_emailfieldActionPerformed

    private void contactfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactfieldActionPerformed
        
    }//GEN-LAST:event_contactfieldActionPerformed

    private void lnamefieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lnamefieldKeyPressed
        if(lnamefield.getText().equals("Enter lastname..")){
            lnamefield.setText("");
            lnamefield.setForeground(Color.black);
        }
        redBorder();crossImage();
    }//GEN-LAST:event_lnamefieldKeyPressed

    private void lnamefieldMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lnamefieldMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lnamefieldMouseEntered

    private void fnamefieldMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fnamefieldMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_fnamefieldMouseEntered

    private void paymentmethodfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentmethodfieldActionPerformed
        if(!paymentmethodfield.getSelectedItem().equals("Select"))paymentC.setVisible(true);
        if(paymentmethodfield.getSelectedItem().equals("Select"))paymentC.setVisible(false);
        redBorder();crossImage();
    }//GEN-LAST:event_paymentmethodfieldActionPerformed

    private void paymentmethodfieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paymentmethodfieldMouseClicked
        if(!paymentmethodfield.getSelectedItem().equals("Select"))paymentC.setVisible(true);
        if(paymentmethodfield.getSelectedItem().equals("Select"))paymentC.setVisible(false);
        redBorder();crossImage();
    }//GEN-LAST:event_paymentmethodfieldMouseClicked

    private void motherincomefieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_motherincomefieldActionPerformed
        if(!motherincomefield.getSelectedItem().equals("Select"))motherincomeC.setVisible(true);
        if(motherincomefield.getSelectedItem().equals("Select"))motherincomeC.setVisible(false);
        textBoxLabel();
        redBorder();crossImage();
    }//GEN-LAST:event_motherincomefieldActionPerformed

    private void motherincomefieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_motherincomefieldMouseClicked
        if(!motherincomefield.getSelectedItem().equals("Select"))motherincomeC.setVisible(true);
        if(motherincomefield.getSelectedItem().equals("Select"))motherincomeC.setVisible(false);
        redBorder();
    }//GEN-LAST:event_motherincomefieldMouseClicked

    private void motheroccupationfieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_motheroccupationfieldKeyReleased
        
        textBoxLabel();
        if(!motheroccupationfield.getText().equals("Enter mother occupation.."))motheroccupationC.setVisible(true);
        if(motheroccupationfield.getText().equals("Enter mother occupation.."))motheroccupationC.setVisible(false);
        crossImage();redBorder();
    }//GEN-LAST:event_motheroccupationfieldKeyReleased

    private void motheroccupationfieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_motheroccupationfieldMouseClicked
        
        if(!motheroccupationfield.getText().equals(""))motheroccupationC.setVisible(true);
        if(motheroccupationfield.getText().equals(""))motheroccupationC.setVisible(false);
        redBorder();crossImage();
    }//GEN-LAST:event_motheroccupationfieldMouseClicked

    private void mothermobilefieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mothermobilefieldKeyReleased
        
        textBoxLabel();
        if(!mothermobilefield.getText().equals("Enter mother contact.."))mothercontactC.setVisible(true);
        if(mothermobilefield.getText().equals("Enter mother contact.."))mothercontactC.setVisible(false);
        crossImage();redBorder();
    }//GEN-LAST:event_mothermobilefieldKeyReleased

    private void mothermobilefieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mothermobilefieldMouseClicked
        
        if(!mothermobilefield.getText().equals(""))mothercontactC.setVisible(true);
        if(mothermobilefield.getText().equals(""))mothercontactC.setVisible(false);
        redBorder();crossImage();
    }//GEN-LAST:event_mothermobilefieldMouseClicked

    private void motheraddressfieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_motheraddressfieldKeyReleased
        
        textBoxLabel();
        if(!motheraddressfield.getText().equals("Enter mother address.."))motheraddressC.setVisible(true);
        if(motheraddressfield.getText().equals("Enter mother address.."))motheraddressC.setVisible(false);
        crossImage();redBorder();
    }//GEN-LAST:event_motheraddressfieldKeyReleased

    private void motheraddressfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_motheraddressfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_motheraddressfieldActionPerformed

    private void motheraddressfieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_motheraddressfieldMouseClicked
        
        if(!motheraddressfield.getText().equals(""))motheraddressC.setVisible(true);
        if(motheraddressfield.getText().equals(""))motheraddressC.setVisible(false);
        redBorder();crossImage();
    }//GEN-LAST:event_motheraddressfieldMouseClicked

    private void mothernamefieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mothernamefieldKeyReleased
        
        textBoxLabel();
        if(!mothernamefield.getText().equals("Enter mother name.."))mothernameC.setVisible(true);
        if(mothernamefield.getText().equals("Enter mother name.."))mothernameC.setVisible(false);
        crossImage();redBorder();
    }//GEN-LAST:event_mothernamefieldKeyReleased

    private void mothernamefieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mothernamefieldMouseClicked
        
        if(!mothernamefield.getText().equals(""))mothernameC.setVisible(true);
        if(mothernamefield.getText().equals(""))mothernameC.setVisible(false);
        redBorder();crossImage();
    }//GEN-LAST:event_mothernamefieldMouseClicked

    private void fatherincomefieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fatherincomefieldActionPerformed
        if(!fatherincomefield.getSelectedItem().equals("Select"))fatherincomeC.setVisible(true);
        if(fatherincomefield.getSelectedItem().equals("Select"))fatherincomeC.setVisible(false);
        textBoxLabel();
        redBorder();
    }//GEN-LAST:event_fatherincomefieldActionPerformed

    private void fatherincomefieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fatherincomefieldMouseClicked
        if(!fatherincomefield.getSelectedItem().equals("Select"))fatherincomeC.setVisible(true);
        if(fatherincomefield.getSelectedItem().equals("Select"))fatherincomeC.setVisible(false);
        redBorder();textBoxLabel();
    }//GEN-LAST:event_fatherincomefieldMouseClicked

    private void fathermobilefieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fathermobilefieldKeyReleased
        
        textBoxLabel();
        if(!fathermobilefield.getText().equals("Enter father contact.."))fathermobileC.setVisible(true);
        if(fathermobilefield.getText().equals("Enter father contact.."))fathermobileC.setVisible(false);
        crossImage();redBorder();
    }//GEN-LAST:event_fathermobilefieldKeyReleased

    private void fathermobilefieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fathermobilefieldMouseClicked
        
        if(!fathermobilefield.getText().equals(""))fathermobileC.setVisible(true);
        if(fathermobilefield.getText().equals(""))fathermobileC.setVisible(false);
        redBorder();crossImage();
    }//GEN-LAST:event_fathermobilefieldMouseClicked

    private void fathernamefieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fathernamefieldKeyReleased
        
        textBoxLabel();
        if(!fatheroccupationfield.getText().equals("Enter father name.."))fatheroccupationC.setVisible(true);
        if(fatheroccupationfield.getText().equals("Enter father name.."))fatheroccupationC.setVisible(false);
        crossImage();redBorder();
    }//GEN-LAST:event_fathernamefieldKeyReleased

    private void fathernamefieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fathernamefieldMouseClicked
        
        if(!fathernamefield.getText().equals(""))fathernameC.setVisible(true);
        if(fathernamefield.getText().equals(""))fathernameC.setVisible(false);
        redBorder();crossImage();
    }//GEN-LAST:event_fathernamefieldMouseClicked

    private void fatheraddressfieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fatheraddressfieldKeyReleased
        
        textBoxLabel();
        if(!fatheraddressfield.getText().equals("Enter father address.."))fatheraddressC.setVisible(true);
        if(fatheraddressfield.getText().equals("Enter father address.."))fatheraddressC.setVisible(false);
        crossImage();redBorder();
    }//GEN-LAST:event_fatheraddressfieldKeyReleased

    private void fatheraddressfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fatheraddressfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fatheraddressfieldActionPerformed

    private void fatheraddressfieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fatheraddressfieldMouseClicked
        
        if(!fatheraddressfield.getText().equals(""))fatheraddressC.setVisible(true);
        if(fatheraddressfield.getText().equals(""))fatheraddressC.setVisible(false);
        redBorder();crossImage();
    }//GEN-LAST:event_fatheraddressfieldMouseClicked

    private void fatheroccupationfieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fatheroccupationfieldKeyReleased
        
        textBoxLabel();
        if(!fatheroccupationfield.getText().equals("Enter father occupation.."))fatheroccupationC.setVisible(true);
        if(fatheroccupationfield.getText().equals("Enter father occupation.."))fatheroccupationC.setVisible(false);
        crossImage();redBorder();
    }//GEN-LAST:event_fatheroccupationfieldKeyReleased

    private void fatheroccupationfieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fatheroccupationfieldMouseClicked
       
        if(!fatheroccupationfield.getText().equals(""))fatheroccupationC.setVisible(true);
        if(fatheroccupationfield.getText().equals(""))fatheroccupationC.setVisible(false);
        redBorder();crossImage();
    }//GEN-LAST:event_fatheroccupationfieldMouseClicked

    private void fathermobilefieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fathermobilefieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fathermobilefieldActionPerformed

    private void fnamefieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fnamefieldKeyPressed
        if(fnamefield.getText().equals("Enter firstname..")) {
            fnamefield.setText("");
            fnamefield.setForeground(Color.black);
        }
    }//GEN-LAST:event_fnamefieldKeyPressed

    private void mnamefieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mnamefieldKeyPressed
        if(mnamefield.getText().equals("Enter middlename..")){
            mnamefield.setText("");
            mnamefield.setForeground(Color.black);
        }
        redBorder();crossImage();
    }//GEN-LAST:event_mnamefieldKeyPressed

    private void agefieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_agefieldKeyPressed
        if(agefield.getText().equals("0")) {
            agefield.setText("");
            agefield.setForeground(Color.black);
        }
        redBorder();crossImage();
    }//GEN-LAST:event_agefieldKeyPressed

    private void bdatefieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bdatefieldKeyPressed
        if(bdatefield.getText().equals("Enter bdate..")){
            bdatefield.setText("");
            bdatefield.setForeground(Color.black);
        }
        redBorder();crossImage();
    }//GEN-LAST:event_bdatefieldKeyPressed

    private void bplacefieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bplacefieldKeyPressed
        if(bplacefield.getText().equals("Enter bplace..")) {
            bplacefield.setText("");
            bplacefield.setForeground(Color.black);
        }
        redBorder();crossImage();
    }//GEN-LAST:event_bplacefieldKeyPressed

    private void addressfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addressfieldKeyPressed
        if(addressfield.getText().equals("Enter address..")){
            addressfield.setText("");
            addressfield.setForeground(Color.black);
        }
        redBorder();crossImage();
    }//GEN-LAST:event_addressfieldKeyPressed

    private void generalPanelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_generalPanelKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_generalPanelKeyPressed

    private void agradefieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_agradefieldKeyPressed
        
        if(agradefield.getText().equals("0.0")){
            if((((evt.getKeyChar()>='0')&&(evt.getKeyChar()<='9')||
                (evt.getKeyChar()=='.')||
                (evt.getKeyChar()==KeyEvent.VK_SPACE)||
                (evt.getKeyChar()==KeyEvent.VK_DELETE)))){
                agradefield.setText("");
                agradefield.setForeground(Color.black);
            }else{
                agradefield.setText("0.0");
                agradefield.setForeground(new Color(153,153,153));
            }
        }
        redBorder();crossImage();
    }//GEN-LAST:event_agradefieldKeyPressed

    private void agradefieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agradefieldActionPerformed
       
    }//GEN-LAST:event_agradefieldActionPerformed

    private void emailfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emailfieldKeyPressed
        if(emailfield.getText().equals("Enter email..")){
            emailfield.setText("");
            emailfield.setForeground(Color.black);
        }
        redBorder();crossImage();
    }//GEN-LAST:event_emailfieldKeyPressed

    private void contactfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contactfieldKeyPressed
        if(contactfield.getText().equals("Enter contact..")){
            contactfield.setText("");
            contactfield.setForeground(Color.black);
        }
        redBorder();crossImage();
    }//GEN-LAST:event_contactfieldKeyPressed

    private void gnamefieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gnamefieldKeyPressed
        if(gnamefield.getText().equals("Enter guardian name..")) {
            gnamefield.setText("");
            gnamefield.setForeground(Color.black);
        }
        redBorder();crossImage();
    }//GEN-LAST:event_gnamefieldKeyPressed

    private void gcontactfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gcontactfieldKeyPressed
        if(gcontactfield.getText().equals("Enter guardian contact..")){
            gcontactfield.setText("");
            gcontactfield.setForeground(Color.black);
        } 
        redBorder();crossImage();
    }//GEN-LAST:event_gcontactfieldKeyPressed

    private void mothernamefieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mothernamefieldKeyPressed
        if(mothernamefield.getText().equals("Enter mother name..")){
            mothernamefield.setText("");
            mothernamefield.setForeground(Color.black);
        }
        redBorder();crossImage();
    }//GEN-LAST:event_mothernamefieldKeyPressed

    private void mothermobilefieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mothermobilefieldKeyPressed
        if(mothermobilefield.getText().equals("Enter mother contact..")){
            mothermobilefield.setText("");
            mothermobilefield.setForeground(Color.black);
        }
        redBorder();crossImage();
    }//GEN-LAST:event_mothermobilefieldKeyPressed

    private void motheraddressfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_motheraddressfieldKeyPressed
        if(motheraddressfield.getText().equals("Enter mother address..")){
            motheraddressfield.setText("");
            motheraddressfield.setForeground(Color.black);
        }
        redBorder();crossImage();
    }//GEN-LAST:event_motheraddressfieldKeyPressed

    private void motheroccupationfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_motheroccupationfieldKeyPressed
        if(motheroccupationfield.getText().equals("Enter mother occupation..")){
            motheroccupationfield.setText("");
            motheroccupationfield.setForeground(Color.black);
        }
        redBorder();crossImage();
    }//GEN-LAST:event_motheroccupationfieldKeyPressed

    private void fathernamefieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fathernamefieldKeyPressed
        if(fathernamefield.getText().equals("Enter father name..")){
            fathernamefield.setText("");
            fathernamefield.setForeground(Color.black);
        }
        redBorder();crossImage();
    }//GEN-LAST:event_fathernamefieldKeyPressed

    private void fathermobilefieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fathermobilefieldKeyPressed
        if(fathermobilefield.getText().equals("Enter father contact..")){
            fathermobilefield.setText("");
            fathermobilefield.setForeground(Color.black);
        }
        redBorder();crossImage();
    }//GEN-LAST:event_fathermobilefieldKeyPressed

    private void fatheraddressfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fatheraddressfieldKeyPressed
        if(fatheraddressfield.getText().equals("Enter father address..")){
            fatheraddressfield.setText("");
            fatheraddressfield.setForeground(Color.black);
        }
        redBorder();crossImage();
    }//GEN-LAST:event_fatheraddressfieldKeyPressed

    private void fatheroccupationfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fatheroccupationfieldKeyPressed
        if(fatheroccupationfield.getText().equals("Enter father occupation..")){
            fatheroccupationfield.setText("");
            fatheroccupationfield.setForeground(Color.black);
        }
        redBorder();crossImage();
    }//GEN-LAST:event_fatheroccupationfieldKeyPressed

    private void newstudentfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newstudentfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newstudentfieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(enrollmentInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(enrollmentInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(enrollmentInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(enrollmentInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new enrollmentInterface().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressC;
    private javax.swing.JLabel addressX;
    private javax.swing.JTextField addressfield;
    private javax.swing.JLabel ageC;
    private javax.swing.JLabel ageX;
    private javax.swing.JTextField agefield;
    private javax.swing.JLabel agradeC;
    private javax.swing.JLabel agradeX;
    private javax.swing.JTextField agradefield;
    private javax.swing.JLabel bdateC;
    private javax.swing.JLabel bdateX;
    private javax.swing.JTextField bdatefield;
    private javax.swing.JPanel billingBtn;
    private javax.swing.JPanel billingPanel;
    private javax.swing.JPanel board;
    private javax.swing.JLabel bplaceC;
    private javax.swing.JLabel bplaceX;
    private javax.swing.JTextField bplacefield;
    private javax.swing.JLabel closeButton;
    private javax.swing.JLabel contactC;
    private javax.swing.JLabel contactX;
    private javax.swing.JTextField contactfield;
    private javax.swing.JLabel courseC;
    private javax.swing.JLabel courseX;
    private javax.swing.JComboBox<String> coursefield;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JTextField displayContact;
    private javax.swing.JTextField displayEmail;
    private javax.swing.JTextField displayName;
    private javax.swing.JTextField displayYear;
    private javax.swing.JLabel emailC;
    private javax.swing.JLabel emailX;
    private javax.swing.JTextField emailfield;
    private javax.swing.JComboBox<String> extensionField;
    private javax.swing.JLabel fatheraddressC;
    private javax.swing.JLabel fatheraddressX;
    private javax.swing.JTextField fatheraddressfield;
    private javax.swing.JLabel fatherincomeC;
    private javax.swing.JLabel fatherincomeX;
    private javax.swing.JComboBox<String> fatherincomefield;
    private javax.swing.JLabel fathermobileC;
    private javax.swing.JLabel fathermobileX;
    private javax.swing.JTextField fathermobilefield;
    private javax.swing.JLabel fathernameC;
    private javax.swing.JLabel fathernameX;
    private javax.swing.JTextField fathernamefield;
    private javax.swing.JLabel fatheroccupationC;
    private javax.swing.JLabel fatheroccupationX;
    private javax.swing.JTextField fatheroccupationfield;
    private javax.swing.JLabel fnameC;
    private javax.swing.JLabel fnameX;
    private javax.swing.JTextField fnamefield;
    private javax.swing.JLabel gcontactC;
    private javax.swing.JLabel gcontactX;
    private javax.swing.JTextField gcontactfield;
    private javax.swing.JLabel genderC;
    private javax.swing.JComboBox<String> genderField;
    private javax.swing.JLabel genderX;
    private javax.swing.JPanel generalBtn;
    private javax.swing.JPanel generalPanel;
    private javax.swing.JLabel gnameC;
    private javax.swing.JLabel gnameX;
    private javax.swing.JTextField gnamefield;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lnameC;
    private javax.swing.JLabel lnameX;
    private javax.swing.JTextField lnamefield;
    private javax.swing.JLabel mnameC;
    private javax.swing.JLabel mnameX;
    private javax.swing.JTextField mnamefield;
    private javax.swing.JLabel motheraddressC;
    private javax.swing.JLabel motheraddressX;
    private javax.swing.JTextField motheraddressfield;
    private javax.swing.JLabel mothercontactC;
    private javax.swing.JLabel mothercontactX;
    private javax.swing.JLabel motherincomeC;
    private javax.swing.JLabel motherincomeX;
    private javax.swing.JComboBox<String> motherincomefield;
    private javax.swing.JTextField mothermobilefield;
    private javax.swing.JLabel mothernameC;
    private javax.swing.JLabel mothernameX;
    private javax.swing.JTextField mothernamefield;
    private javax.swing.JLabel motheroccupationC;
    private javax.swing.JLabel motheroccupationX;
    private javax.swing.JTextField motheroccupationfield;
    private javax.swing.JRadioButton newstudentfield;
    private javax.swing.JRadioButton oldstudentfield;
    private javax.swing.JLabel paymentC;
    private javax.swing.JLabel paymentX;
    private javax.swing.JComboBox<String> paymentmethodfield;
    private javax.swing.JPanel save;
    private javax.swing.JTextField schoolyearfield;
    private javax.swing.JTextField searchIdField;
    private javax.swing.JLabel semesterC;
    private javax.swing.JLabel semesterX;
    private javax.swing.JComboBox<String> semesterfield;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JLabel yearlevelC;
    private javax.swing.JLabel yearlevelX;
    private javax.swing.JComboBox<String> yearlevelfield;
    // End of variables declaration//GEN-END:variables
}
