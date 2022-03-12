/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enrollmentsystem;

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
public final class subjectSchedule extends javax.swing.JPanel {

    /**
     * Creates new form subjectSchedule
     */
    private final String dbURL = "jdbc:mysql://localhost/aviation_enrollment_system" ;
    private final String USER = "root";
    private final String PASS = "";
    private String sqlWhere="";
    private DefaultTableModel model;
    public subjectSchedule() {
        initComponents();
        SectionName();
    }

    public void SectionName(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
                Connection con = DriverManager.getConnection(dbURL, USER, PASS);
                String sql = "SELECT name FROM section;";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while(rs.next()){
                    jComboBox1.addItem(rs.getString(1));
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
    private  ArrayList<subjects> getSubjects(){
        ArrayList<subjects> details = new ArrayList <subjects>();
        
        
        try{
            Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement("SELECT sec.sectionId, sched.coursecode, sched.subjecttitle,"
                    + "sched.lecture,sched.laboratory,sched.schedulledate,sched.room FROM schedulle AS sched,section "
                    + "AS sec "+sqlWhere);
            ResultSet result = stmt.executeQuery();
            
            subjects d;
            
            while(result.next()){
                d = new subjects(
                        result.getString(2),
                        result.getString(3),
                        result.getDouble(4),
                        result.getDouble(5),
                        result.getString(6),
                        result.getString(7)
                );      
                conn.commit();
                details.add(d);
                }
            try{
                if(result!=null)result.close();
                if(stmt!= null)result.close();
                if(conn!=null) conn.close();
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }catch(SQLException error){
            JOptionPane.showMessageDialog(this,error);
        }
        return details;
    }
    private void ShowTable(){
           ArrayList<subjects> show = getSubjects();
           model = (DefaultTableModel)jTable1.getModel();
           
           //String[] columnName = {"Recieve No", "Recieving Date", "Item No", "Item Name", "Description", "Expiry Date", "Quantity", "Buying Price", "Selling Price", "Total Price"};
        Object[] row = new Object[6];
        
           
           for(int j = 0; j < getSubjects().size(); j++){
            
            row[0] = getSubjects().get(j).getCode();
            row[1] = getSubjects().get(j).getSubjectTitle();
            row[2] = getSubjects().get(j).getLec();
            row[3] = getSubjects().get(j).getLab();
            row[4] = getSubjects().get(j).getSchedulle();
            row[5] = getSubjects().get(j).getRoom();
                             
                      
            model.addRow(row);
            jTable1.setRowHeight(30);
               
            }
            TableColumnModel tcmodel = jTable1.getColumnModel();
            tcmodel.getColumn(0).setPreferredWidth(150);
            tcmodel.getColumn(1).setPreferredWidth(680);
            tcmodel.getColumn(2).setPreferredWidth(130);
            tcmodel.getColumn(3).setPreferredWidth(130);
            tcmodel.getColumn(4).setPreferredWidth(780);
            tcmodel.getColumn(5).setPreferredWidth(430);
       }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel30 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(51, 51, 51));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 0));
        jLabel30.setText("Students Subject and Schedule");

        jPanel13.setBackground(new java.awt.Color(102, 102, 102));

        jPanel14.setBackground(new java.awt.Color(51, 51, 51));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 0));
        jLabel31.setText("Section Name : ");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jComboBox1MouseReleased(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel17.setBackground(new java.awt.Color(153, 0, 0));

        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane5.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Subject Title", "Lec", "Lab", "Schedule", "Room"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane5.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel30)
                .addContainerGap(510, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
       model = (DefaultTableModel)jTable1.getModel();
        model.setRowCount(0);
        sqlWhere = "WHERE sec.name='"+jComboBox1.getSelectedItem().toString()+"' "
                    + "AND sched.sectionId=sec.sectionId;";
        ShowTable();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseReleased
        
    }//GEN-LAST:event_jComboBox1MouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
