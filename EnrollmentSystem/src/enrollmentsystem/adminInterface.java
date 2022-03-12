/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enrollmentsystem;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Jomardon Refuerzo Gabitanan
 */
public final class adminInterface extends javax.swing.JFrame {

    /**
     * Creates new form adminInterface
     */
    private final String dbURL = "jdbc:mysql://localhost/aviation_enrollment_system" ;
    private final String USER = "root";
    private final String PASS = "";
    private controller controll;
    private dashBoard db;
    private listOfStudent lof;
    private subjectSchedule ss;
    private administrator a;
    private backup b;
    public adminInterface() {
        initComponents();
        defaultPanel();
        getCurrentDateandTime();
        frameLocation();
    }

    public void setUserFullName(String fname,String mname,String lname){
        this.jTextField1.setText("Welcome "+fname+" "+mname+" "+lname);
    }
    public String getUserFullName(){
        return this.jTextField1.getText();
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
    public void frameLocation(){
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
    public void panelPicker(){
        if(!controll.getPanel().equals(db)
                && dashboardBtn.equals(controll.getButton())){
            controll.setPanel(db);
            lof.setVisible(controll.unShow());
            ss.setVisible(controll.unShow());
            a.setVisible(controll.unShow());
            b.setVisible(controll.unShow());
            db.setVisible(controll.show());
        }
        else if(!controll.getPanel().equals(lof)
                && studentListBtn.equals(controll.getButton())){
            controll.getPanel().setVisible(controll.unShow());
            controll.setPanel(lof);
            db.setVisible(controll.unShow());
            ss.setVisible(controll.unShow());
            a.setVisible(controll.unShow());
            b.setVisible(controll.unShow());
            lof.setVisible(controll.show());
        }
        else if(!controll.getPanel().equals(ss)
                && subjectScheduleBtn.equals(controll.getButton())){
            controll.getPanel().setVisible(controll.unShow());
            controll.setPanel(ss);
            lof.setVisible(controll.unShow());
            db.setVisible(controll.unShow());
            a.setVisible(controll.unShow());
            b.setVisible(controll.unShow());
            ss.setVisible(controll.show());
        }
        else if(!controll.getPanel().equals(a)
                && administratorBtn.equals(controll.getButton())){
            controll.getPanel().setVisible(controll.unShow());
            controll.setPanel(a);
            lof.setVisible(controll.unShow());
            ss.setVisible(controll.unShow());
            db.setVisible(controll.unShow());
            b.setVisible(controll.unShow());
            a.setVisible(controll.show());
        }else if(!controll.getPanel().equals(b)
                && backupBtn.equals(controll.getButton())){
            controll.getPanel().setVisible(controll.unShow());
            controll.setPanel(b);
            lof.setVisible(controll.unShow());
            db.setVisible(controll.unShow());
            a.setVisible(controll.unShow());
            ss.setVisible(controll.unShow());
            b.setVisible(controll.show());
        }
    }
    public void defaultPanel(){
        jTextField1.setText("Welcome "+getUserFullName());
        controll = new controller();
        db = new dashBoard();
        lof = new listOfStudent();
        ss = new subjectSchedule();
        a = new administrator();
        b = new backup();
        controll.setButton(dashboardBtn);
        dashboardBtn.setBackground(new Color(51,51,51));
        controll.setPanel(db);
        db.setSize(815, 521);
        db.setLocation(1, 1);
        db.setVisible(controll.show());
        board.add(db);
        
        lof.setSize(815, 521);
        lof.setLocation(1, 1);
        lof.setVisible(controll.unShow());
        board.add(lof);
        
        ss.setSize(815, 521);
        ss.setLocation(1, 1);
        ss.setVisible(controll.unShow());
        board.add(ss);
        
        a.setSize(815, 521);
        a.setLocation(1, 1);
        a.setVisible(controll.unShow());
        board.add(a);
        
        b.setSize(815, 521);
        b.setLocation(1, 1);
        b.setVisible(controll.unShow());
        board.add(b);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        closeButton = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        about = new javax.swing.JPanel();
        menubar = new javax.swing.JPanel();
        logoutBtn = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        subjectScheduleBtn = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        administratorBtn = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        dashboardBtn = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        studentListBtn = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        backupBtn = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        board = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/philscaLogoHeader.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 23, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/headername.png"))); // NOI18N
        jLabel2.setText("jLabel2");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(147, 46, 795, 61));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(147, 141, 795, 10));

        jPanel2.setBackground(new java.awt.Color(153, 0, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel2.add(closeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 0, 51, 35));

        timeLabel.setForeground(new java.awt.Color(255, 255, 255));
        timeLabel.setText("Time");
        jPanel2.add(timeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        dateLabel.setForeground(new java.awt.Color(255, 255, 255));
        dateLabel.setText("Date");
        jPanel2.add(dateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, 560, -1));

        about.setBackground(new java.awt.Color(255, 255, 255));

        menubar.setBackground(new java.awt.Color(153, 0, 0));
        menubar.setVerifyInputWhenFocusTarget(false);
        menubar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logoutBtn.setBackground(new java.awt.Color(153, 0, 0));
        logoutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutBtnMouseExited(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Log out");

        javax.swing.GroupLayout logoutBtnLayout = new javax.swing.GroupLayout(logoutBtn);
        logoutBtn.setLayout(logoutBtnLayout);
        logoutBtnLayout.setHorizontalGroup(
            logoutBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logoutBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addContainerGap())
        );
        logoutBtnLayout.setVerticalGroup(
            logoutBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoutBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        menubar.add(logoutBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 260, 50));

        subjectScheduleBtn.setBackground(new java.awt.Color(153, 0, 0));
        subjectScheduleBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                subjectScheduleBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                subjectScheduleBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                subjectScheduleBtnMouseExited(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Subject & Schedule");

        javax.swing.GroupLayout subjectScheduleBtnLayout = new javax.swing.GroupLayout(subjectScheduleBtn);
        subjectScheduleBtn.setLayout(subjectScheduleBtnLayout);
        subjectScheduleBtnLayout.setHorizontalGroup(
            subjectScheduleBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subjectScheduleBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addContainerGap())
        );
        subjectScheduleBtnLayout.setVerticalGroup(
            subjectScheduleBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subjectScheduleBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        menubar.add(subjectScheduleBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 260, 50));

        administratorBtn.setBackground(new java.awt.Color(153, 0, 0));
        administratorBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                administratorBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                administratorBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                administratorBtnMouseExited(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Administrator");

        javax.swing.GroupLayout administratorBtnLayout = new javax.swing.GroupLayout(administratorBtn);
        administratorBtn.setLayout(administratorBtnLayout);
        administratorBtnLayout.setHorizontalGroup(
            administratorBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, administratorBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addContainerGap())
        );
        administratorBtnLayout.setVerticalGroup(
            administratorBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(administratorBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        menubar.add(administratorBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 260, 50));

        dashboardBtn.setBackground(new java.awt.Color(153, 0, 0));
        dashboardBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboardBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                dashboardBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                dashboardBtnMouseExited(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Dashboard");

        javax.swing.GroupLayout dashboardBtnLayout = new javax.swing.GroupLayout(dashboardBtn);
        dashboardBtn.setLayout(dashboardBtnLayout);
        dashboardBtnLayout.setHorizontalGroup(
            dashboardBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashboardBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addContainerGap())
        );
        dashboardBtnLayout.setVerticalGroup(
            dashboardBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        menubar.add(dashboardBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 260, 50));

        studentListBtn.setBackground(new java.awt.Color(153, 0, 0));
        studentListBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                studentListBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                studentListBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                studentListBtnMouseExited(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Student List");

        javax.swing.GroupLayout studentListBtnLayout = new javax.swing.GroupLayout(studentListBtn);
        studentListBtn.setLayout(studentListBtnLayout);
        studentListBtnLayout.setHorizontalGroup(
            studentListBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentListBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addContainerGap())
        );
        studentListBtnLayout.setVerticalGroup(
            studentListBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentListBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        menubar.add(studentListBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 260, 50));

        backupBtn.setBackground(new java.awt.Color(153, 0, 0));
        backupBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backupBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backupBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backupBtnMouseExited(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Backup");

        javax.swing.GroupLayout backupBtnLayout = new javax.swing.GroupLayout(backupBtn);
        backupBtn.setLayout(backupBtnLayout);
        backupBtnLayout.setHorizontalGroup(
            backupBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backupBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addContainerGap())
        );
        backupBtnLayout.setVerticalGroup(
            backupBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backupBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        menubar.add(backupBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 260, 50));

        javax.swing.GroupLayout aboutLayout = new javax.swing.GroupLayout(about);
        about.setLayout(aboutLayout);
        aboutLayout.setHorizontalGroup(
            aboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menubar, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
        );
        aboutLayout.setVerticalGroup(
            aboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aboutLayout.createSequentialGroup()
                .addComponent(menubar, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(about, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 190, 270, 540));

        mainPanel.setBackground(new java.awt.Color(51, 51, 51));
        mainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        board.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout boardLayout = new javax.swing.GroupLayout(board);
        board.setLayout(boardLayout);
        boardLayout.setHorizontalGroup(
            boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 815, Short.MAX_VALUE)
        );
        boardLayout.setVerticalGroup(
            boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 838, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                    .addComponent(board, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 23, Short.MAX_VALUE)))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(board, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(28, 28, 28)))
        );

        jPanel1.add(mainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 840, 540));

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Email: ssc_vab@hotmail");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, -1, 30));

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Contact: -401-5447, -425-5281, -425-5291");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 104, -1, 20));

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Address: Piccio Garden Villamor Pasay City");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 104, -1, 20));

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(51, 51, 51));
        jTextField1.setForeground(new java.awt.Color(255, 255, 0));
        jTextField1.setText("Welcome");
        jTextField1.setBorder(null);
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 740, 500, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void dashboardBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardBtnMouseEntered
        if(!controll.getButton().equals(dashboardBtn)){
            dashboardBtn.setBackground(new Color(204,0,0));
        }
    }//GEN-LAST:event_dashboardBtnMouseEntered

    private void dashboardBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardBtnMouseExited
        if(!controll.getButton().equals(dashboardBtn)){
            dashboardBtn.setBackground(new Color(153,0,0));
        }
    }//GEN-LAST:event_dashboardBtnMouseExited

    private void dashboardBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardBtnMouseClicked
        
        controll.setColorOfButton(new Color(153,0,0));
        controll.setButton(dashboardBtn);
        panelPicker();
        controll.setColorOfButton(new Color(51,51,51));
    }//GEN-LAST:event_dashboardBtnMouseClicked

    private void studentListBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentListBtnMouseClicked
        controll.setColorOfButton(new Color(153,0,0));
        controll.setButton(studentListBtn);
        panelPicker();
        controll.setColorOfButton(new Color(51,51,51));
    }//GEN-LAST:event_studentListBtnMouseClicked

    private void studentListBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentListBtnMouseEntered
        if(!controll.getButton().equals(studentListBtn)){
            studentListBtn.setBackground(new Color(204,0,0));
        }
    }//GEN-LAST:event_studentListBtnMouseEntered

    private void studentListBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentListBtnMouseExited
        if(!controll.getButton().equals(studentListBtn)){
            studentListBtn.setBackground(new Color(153,0,0));
        }
    }//GEN-LAST:event_studentListBtnMouseExited

    private void subjectScheduleBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subjectScheduleBtnMouseClicked
        controll.setColorOfButton(new Color(153,0,0));
        controll.setButton(subjectScheduleBtn);
        panelPicker();
        controll.setColorOfButton(new Color(51,51,51));
    }//GEN-LAST:event_subjectScheduleBtnMouseClicked

    private void subjectScheduleBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subjectScheduleBtnMouseEntered
        if(!controll.getButton().equals(subjectScheduleBtn)){
            subjectScheduleBtn.setBackground(new Color(204,0,0));
        }
    }//GEN-LAST:event_subjectScheduleBtnMouseEntered

    private void subjectScheduleBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subjectScheduleBtnMouseExited
        if(!controll.getButton().equals(subjectScheduleBtn)){
            subjectScheduleBtn.setBackground(new Color(153,0,0));
        }
    }//GEN-LAST:event_subjectScheduleBtnMouseExited

    private void administratorBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_administratorBtnMouseClicked
        controll.setColorOfButton(new Color(153,0,0));
        controll.setButton(administratorBtn);
        panelPicker();
        controll.setColorOfButton(new Color(51,51,51));
    }//GEN-LAST:event_administratorBtnMouseClicked

    private void administratorBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_administratorBtnMouseEntered
        if(!controll.getButton().equals(administratorBtn)){
            administratorBtn.setBackground(new Color(204,0,0));
        }
    }//GEN-LAST:event_administratorBtnMouseEntered

    private void administratorBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_administratorBtnMouseExited
        if(!controll.getButton().equals(administratorBtn)){
            administratorBtn.setBackground(new Color(153,0,0));
        }
    }//GEN-LAST:event_administratorBtnMouseExited

    private void logoutBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutBtnMouseClicked
        controll.setColorOfButton(new Color(51,51,51));
        loginForm lf = new loginForm();
        lf.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_logoutBtnMouseClicked

    private void logoutBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutBtnMouseEntered
        logoutBtn.setBackground(new Color(204,0,0));
    }//GEN-LAST:event_logoutBtnMouseEntered

    private void logoutBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutBtnMouseExited
        logoutBtn.setBackground(new Color(153,0,0));
    }//GEN-LAST:event_logoutBtnMouseExited

    private void backupBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backupBtnMouseClicked
        controll.setColorOfButton(new Color(153,0,0));
        controll.setButton(backupBtn);
        panelPicker();
        controll.setColorOfButton(new Color(51,51,51));
    }//GEN-LAST:event_backupBtnMouseClicked

    private void backupBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backupBtnMouseEntered
        if(!controll.getButton().equals(backupBtn)){
            backupBtn.setBackground(new Color(204,0,0));
        }
    }//GEN-LAST:event_backupBtnMouseEntered

    private void backupBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backupBtnMouseExited
        if(!controll.getButton().equals(backupBtn)){
            backupBtn.setBackground(new Color(153,0,0));
        }
    }//GEN-LAST:event_backupBtnMouseExited

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
            java.util.logging.Logger.getLogger(adminInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(adminInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(adminInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(adminInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new adminInterface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel about;
    private javax.swing.JPanel administratorBtn;
    private javax.swing.JPanel backupBtn;
    private javax.swing.JPanel board;
    private javax.swing.JLabel closeButton;
    private javax.swing.JPanel dashboardBtn;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel logoutBtn;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel menubar;
    private javax.swing.JPanel studentListBtn;
    private javax.swing.JPanel subjectScheduleBtn;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables
}
