/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enrollmentsystem;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Jomardon Refuerzo Gabitanan
 */
public final class listOfStudent extends javax.swing.JPanel {

    /**
     * Creates new form listOfStudent
     */
    private final String dbURL = "jdbc:mysql://localhost/aviation_enrollment_system" ;
    private final String USER = "root";
    private final String PASS = "";
    private DefaultTableModel model;
    private String sqlWhere,currentSchoolYear;
    public listOfStudent() {
        initComponents();
        getCurrentSchoolYear();
        getYearLevel();
        getSpecificSchoolId();
        getAllSectionName();
        getSpecificSchoolYear();
        defaultPanel();
    }
    public void getAllSectionName(){
        try{
                Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
                conn.setAutoCommit(false);
                String sql = "SELECT DISTINCT name FROM section AS s, school_info AS si"
                        + " WHERE s.sectionId;";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet result = ps.executeQuery();
                while(result.next()){
                    jComboBox4.addItem(result.getString(1));
                    
                }
                if(result!=null){
                    try{
                        result.close();
                    }catch(SQLException ex){}
                }if(ps!=null){
                    try{
                        ps.close();
                    }catch(SQLException ex){}
                }if(conn!=null){
                    try{
                        conn.close();
                    }catch(SQLException ex){}
                }
                
        }catch(SQLException e){
            System.out.println(e);
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
                    currentSchoolYear = result.getInt(1)+"-"+toyear;
                }
                if(result!=null){
                    try{
                        result.close();
                    }catch(SQLException ex){}
                }if(ps!=null){
                    try{
                        ps.close();
                    }catch(SQLException ex){}
                }if(conn!=null){
                    try{
                        conn.close();
                    }catch(SQLException ex){}
                }
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public void getSpecificSchoolYear(){
        try{
                Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
                String sql = "SELECT DISTINCT schoolyear FROM school_info;";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet result = ps.executeQuery();
                while(result.next()){
                    jComboBox2.addItem(result.getString(1));
                }
                if(result!=null){
                    try{
                        result.close();
                    }catch(SQLException ex){}
                }if(ps!=null){
                    try{
                        ps.close();
                    }catch(SQLException ex){}
                }if(conn!=null){
                    try{
                        conn.close();
                    }catch(SQLException ex){}
                }
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    public void getSpecificSchoolId(){
        try{
                Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
                String sql = "SELECT studentId FROM student_personal_info;";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet result = ps.executeQuery();
                while(result.next()){
                    jComboBox5.addItem(result.getString(1));
                }
                if(result!=null){
                    try{
                        result.close();
                    }catch(SQLException ex){}
                }if(ps!=null){
                    try{
                        ps.close();
                    }catch(SQLException ex){}
                }if(conn!=null){
                    try{
                        conn.close();
                    }catch(SQLException ex){}
                }
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public void getYearLevel(){
        try{
                Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
                String sql = "SELECT DISTINCT yearlevel FROM school_info;";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet result = ps.executeQuery();
                while(result.next()){
                    jComboBox3.addItem(result.getString(1));
                }
                if(result!=null){
                    try{
                        result.close();
                    }catch(SQLException ex){}
                }if(ps!=null){
                    try{
                        ps.close();
                    }catch(SQLException ex){}
                }if(conn!=null){
                    try{
                        conn.close();
                    }catch(SQLException ex){}
                }
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    public void defaultPanel(){
        
        panel.setVisible(false);
        jComboBox2.setVisible(false);
        jComboBox3.setVisible(false);
        jComboBox4.setVisible(false);
        jComboBox5.setVisible(false);
    }
    private  ArrayList<students> getStudent(){
        ArrayList<students> details = new ArrayList <students>();
        
        
        try{
            Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement("SELECT spi.studentId, spi.firstname, spi.lastname,"
                    + " spi.middlename, spi.extension, spi.gender, spi.age, spi.birthdate, spi.birthplace, spi.address,"
                    + " si.yearlevel, si.course, si.schoolyear, si.sectionname, sc.contact, sc.email, g.name, g.contact,"
                    + " e.dateenrolled ,e.timeenrolled FROM student_personal_info AS spi,school_info AS si,student_contact"
                    + " AS sc,guardian AS g,enrolled AS e " +sqlWhere);
            ResultSet result = stmt.executeQuery();
            
            students d;
            
            while(result.next()){
                d = new students(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6),
                        result.getInt(7),
                        result.getString(8),
                        result.getString(9),
                        result.getString(10),
                        result.getString(11),
                        result.getString(12),
                        result.getString(13),
                        result.getString(14),
                        result.getString(15),
                        result.getString(16),
                        result.getString(17),
                        result.getString(18),
                        result.getString(19),
                        result.getString(20)
                        
                );      
                conn.commit();
                details.add(d);
                
                }
            if(result!=null){
                    try{
                        result.close();
                    }catch(SQLException ex){}
                }if(stmt!=null){
                    try{
                        stmt.close();
                    }catch(SQLException ex){}
                }if(conn!=null){
                    try{
                        conn.close();
                    }catch(SQLException ex){}
                }
        }catch(SQLException e){
            System.out.println(e);
        }
        return details;
    }
    private void ShowTable(){
           ArrayList<students> show = getStudent();
           model = (DefaultTableModel)studentTable.getModel();
           
           //String[] columnName = {"Recieve No", "Recieving Date", "Item No", "Item Name", "Description", "Expiry Date", "Quantity", "Buying Price", "Selling Price", "Total Price"};
        Object[] row = new Object[20];
        
           
           for(int j = 0; j < getStudent().size(); j++){
            
            row[0] = getStudent().get(j).getSId();
            row[1] = getStudent().get(j).getSFirstname();
            row[2] = getStudent().get(j).getSMiddlename();
            row[3] = getStudent().get(j).getSLastname();
            row[4] = getStudent().get(j).getExtension();
            row[5] = getStudent().get(j).getGender();
            row[6] = getStudent().get(j).getAge();
            row[7] = getStudent().get(j).getBDate();
            row[8] = getStudent().get(j).getBPlace();
            row[9] = getStudent().get(j).getAddress();
            row[10] = getStudent().get(j).getYearLevel();
            row[11] = getStudent().get(j).getCourse();
            row[12] = getStudent().get(j).getSection();
            row[13] = getStudent().get(j).getSchoolYear();
            row[14] = getStudent().get(j).getContact();
            row[15] = getStudent().get(j).getEmail();
            row[16] = getStudent().get(j).getGName();
            row[17] = getStudent().get(j).getGContact();
            row[18] = getStudent().get(j).getDEnrolled();
            row[19] = getStudent().get(j).getTimeEnrolled();
                             
                      
            model.addRow(row);
            studentTable.setRowHeight(30);
               
            }
            TableColumnModel tcmodel = studentTable.getColumnModel();
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
            tcmodel.getColumn(12).setPreferredWidth(100);
            tcmodel.getColumn(13).setPreferredWidth(120);
            tcmodel.getColumn(14).setPreferredWidth(160);
            tcmodel.getColumn(15).setPreferredWidth(200);
            tcmodel.getColumn(16).setPreferredWidth(200);
            tcmodel.getColumn(17).setPreferredWidth(220);
            tcmodel.getColumn(18).setPreferredWidth(180);
            tcmodel.getColumn(19).setPreferredWidth(180);
       }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        panel = new javax.swing.JPanel();
        specificLabel = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        studentTable = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(51, 51, 51));

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));

        jPanel7.setBackground(new java.awt.Color(51, 51, 51));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 0));
        jLabel13.setText("Sort by : ");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "All", "This School Year", "School Year", "Year Level", "Section", "Student ID" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        panel.setBackground(new java.awt.Color(51, 51, 51));

        specificLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        specificLabel.setForeground(new java.awt.Color(255, 255, 0));
        specificLabel.setText("Sort by : ");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        jComboBox3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox3MouseClicked(evt);
            }
        });
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        jComboBox4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox4MouseClicked(evt);
            }
        });
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        jComboBox5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox5MouseClicked(evt);
            }
        });
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addComponent(specificLabel)
                .addGap(12, 12, 12)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(specificLabel))
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel8.setBackground(new java.awt.Color(153, 0, 0));

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        studentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Firstname", "Middlename", "LastName", "Extension", "Gender", "Age", "Birthdate", "Birthplace", "Address", "Year Level", "Course", "Section", "School Year", "Contact", "Email", "Guardian Name", "Guardian Contact", "Enroll Date", "Enroll Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        studentTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane3.setViewportView(studentTable);
        if (studentTable.getColumnModel().getColumnCount() > 0) {
            studentTable.getColumnModel().getColumn(0).setResizable(false);
            studentTable.getColumnModel().getColumn(1).setResizable(false);
            studentTable.getColumnModel().getColumn(2).setResizable(false);
            studentTable.getColumnModel().getColumn(3).setResizable(false);
            studentTable.getColumnModel().getColumn(4).setResizable(false);
            studentTable.getColumnModel().getColumn(5).setResizable(false);
            studentTable.getColumnModel().getColumn(6).setResizable(false);
            studentTable.getColumnModel().getColumn(7).setResizable(false);
            studentTable.getColumnModel().getColumn(8).setResizable(false);
            studentTable.getColumnModel().getColumn(9).setResizable(false);
            studentTable.getColumnModel().getColumn(10).setResizable(false);
            studentTable.getColumnModel().getColumn(11).setResizable(false);
            studentTable.getColumnModel().getColumn(12).setResizable(false);
            studentTable.getColumnModel().getColumn(13).setResizable(false);
            studentTable.getColumnModel().getColumn(14).setResizable(false);
            studentTable.getColumnModel().getColumn(15).setResizable(false);
            studentTable.getColumnModel().getColumn(16).setResizable(false);
            studentTable.getColumnModel().getColumn(17).setResizable(false);
            studentTable.getColumnModel().getColumn(18).setResizable(false);
            studentTable.getColumnModel().getColumn(19).setResizable(false);
        }

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 0));
        jLabel14.setText("Enrolled Students");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addContainerGap(625, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        
        try{
        switch(jComboBox1.getSelectedItem().toString()){
            case "Select":
                panel.setVisible(false);
                model = (DefaultTableModel)studentTable.getModel();
                model.setRowCount(0);
                break;
            case "All":
                panel.setVisible(false);
                sqlWhere = "WHERE si.studentId = spi.studentId AND sc.studentId = spi.studentId"
                    + " AND g.studentId = spi.studentId AND e.paymentId=(SELECT paymentId FROM payment AS p WHERE"
                    + " p.studentId=spi.studentId);";
                model = (DefaultTableModel)studentTable.getModel();
                                    model.setRowCount(0);
                                    ShowTable();
                break;
            case "This School Year":
                System.out.println(currentSchoolYear);
                sqlWhere = "WHERE si.schoolyear='"+currentSchoolYear+"' AND si.studentId=spi.studentId AND "
                        + "sc.studentId = spi.studentId AND g.studentId = spi.studentId AND e.paymentId=(SELECT paymentId "
                        + "FROM payment AS p WHERE p.studentId=spi.studentId);";
                
                panel.setVisible(false);
                model = (DefaultTableModel)studentTable.getModel();
                model.setRowCount(0);
                ShowTable();
                break;
            case "School Year":
                jComboBox2.setVisible(true);
                jComboBox3.setVisible(false);
                jComboBox4.setVisible(false);
                jComboBox5.setVisible(false);
                panel.setVisible(true);
                specificLabel.setText("Specific School Year");
                model = (DefaultTableModel)studentTable.getModel();
                model.setRowCount(0);
                break;
            case "Year Level":
                jComboBox2.setVisible(false);
                jComboBox3.setVisible(true);
                jComboBox4.setVisible(false);
                jComboBox5.setVisible(false);
                panel.setVisible(true);
                specificLabel.setText("Specific Year Level");
                model = (DefaultTableModel)studentTable.getModel();
                model.setRowCount(0);
                break;
            case "Section":
                jComboBox2.setVisible(false);
                jComboBox3.setVisible(false);
                jComboBox4.setVisible(true);
                jComboBox5.setVisible(false);
                panel.setVisible(true);
                specificLabel.setText("Specific Section");
                model = (DefaultTableModel)studentTable.getModel();
                model.setRowCount(0);
                break;
            case "Student ID":
                jComboBox2.setVisible(false);
                jComboBox3.setVisible(false);
                jComboBox4.setVisible(false);
                jComboBox5.setVisible(true);
                panel.setVisible(true);
                specificLabel.setText("Specific Student ID");
                model = (DefaultTableModel)studentTable.getModel();
                model.setRowCount(0);
                break;
        }
        }catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        
        if(jComboBox2.isVisible()==true){
            System.out.println(2);
            sqlWhere = "WHERE si.schoolyear='"+jComboBox2.getSelectedItem().toString()+"' AND si.studentId=spi.studentId AND "
                + "sc.studentId = spi.studentId AND g.studentId = spi.studentId AND e.paymentId=(SELECT paymentId "
                + "FROM payment AS p WHERE p.studentId=spi.studentId);";
            model = (DefaultTableModel)studentTable.getModel();
            model.setRowCount(0);
            ShowTable();
        }
        
        
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        if(jComboBox4.isVisible()==true){
            System.out.println(4);
            sqlWhere = "WHERE si.sectionname='"+jComboBox4.getSelectedItem().toString()
                    +"' AND spi.studentId=si.studentId AND sc.studentId = spi.studentId AND g.studentId = spi.studentId "
                    + "AND e.paymentId=(SELECT paymentId FROM payment AS p WHERE p.studentId=spi.studentId);";
            model = (DefaultTableModel)studentTable.getModel();
            model.setRowCount(0);
            ShowTable();
        }
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox3MouseClicked
        
    }//GEN-LAST:event_jComboBox3MouseClicked

    private void jComboBox4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox4MouseClicked
        
    }//GEN-LAST:event_jComboBox4MouseClicked

    private void jComboBox5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox5MouseClicked
        
    }//GEN-LAST:event_jComboBox5MouseClicked

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        if(jComboBox3.isVisible()==true){
            System.out.println(3);
            sqlWhere = "WHERE si.yearlevel='"+jComboBox3.getSelectedItem().toString()+"' AND si.studentId=spi.studentId AND "
                + "sc.studentId = spi.studentId AND g.studentId = spi.studentId AND e.paymentId=(SELECT paymentId "
                + "FROM payment AS p WHERE p.studentId=spi.studentId);";
            model = (DefaultTableModel)studentTable.getModel();
            model.setRowCount(0);
            ShowTable();
        }
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        if(jComboBox5.isVisible()==true){
            System.out.println(5);
            sqlWhere = "WHERE si.studentId='"+jComboBox5.getSelectedItem().toString()+"' AND spi.studentId=si.studentId AND "
                + "sc.studentId = spi.studentId AND g.studentId = spi.studentId AND e.paymentId=(SELECT paymentId "
                + "FROM payment AS p WHERE p.studentId=spi.studentId);";
            model = (DefaultTableModel)studentTable.getModel();
            model.setRowCount(0);
            ShowTable();
        }
    }//GEN-LAST:event_jComboBox5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel panel;
    private javax.swing.JLabel specificLabel;
    private javax.swing.JTable studentTable;
    // End of variables declaration//GEN-END:variables
}
