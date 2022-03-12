/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enrollmentsystem;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jomardon Refuerzo Gabitanan
 */
public class controller {
    private JPanel panel,btn;
    private JLabel label;
    private Boolean visible;
    private JComboBox box;
    public void setPanel(JPanel panel){//set the panel
        this.panel = panel;
    }
    public void setVisible(Boolean Visible){//set panel if visble
        panel.setVisible(visible);
    }
    public JPanel getPanel(){//return the selected panel 
        return panel;
    }
    public void setNewPanel(){
        
    }
    public void setUnshowPanel(){
        this.panel.setVisible(false);
    }
    public void setShowPanel(){
        this.panel.setVisible(true);
    }
    public void setButton(JPanel btn){//set the button
        this.btn=btn;
    }
    public void setJLabel(JLabel label){
        this.label=label;
    }
    public void setTextJLabel(String text){
        this.label.setText(text);
    }
    public void setJLabelShow(){
        this.label.setVisible(true);
    }
    public void setJLabelUnShow(){
        this.label.setVisible(false);
    }
    public void setJComboBox(JComboBox box){
        this.box=box;
    }
    public void setJComboBoxShow(){
        this.box.setVisible(true);
    }
    public void setJComboBoxUnShow(){
        this.box.setVisible(false);
    }
    public void setColorLabel(Color c){
        this.label.setForeground(c);
    }
    public JPanel getButton(){//return the selected button
        return btn;
    }
    public void setColorOfButton(Color c){
        btn.setBackground(c);
    }
    public void setBtnShow(){
        this.btn.setVisible(true);
    }
    public void setBtnUnShow(){
        this.btn.setVisible(false);
    }
    public boolean show(){//boolean expression that set true 
        visible = true;
        return visible;
    }
    public boolean unShow(){//boolean expression that set false;
        visible=false;
        return visible;
    }
    
    
}
