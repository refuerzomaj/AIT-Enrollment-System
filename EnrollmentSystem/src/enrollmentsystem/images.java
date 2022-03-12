/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enrollmentsystem;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Jomardon Refuerzo Gabitanan
 */
public class images {
    private String imgLocation;
    private BufferedImage bImg;
    private Image img;
    private ImageIcon imageIcon;
    private JLabel label;
    public void getConvertImageLabel(){
        try{
            bImg = null;
            bImg = ImageIO.read(new File(imgLocation));
            img = bImg.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(img);
            label.setIcon(imageIcon);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e, "Error : ", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void setConvertImageLabel(JLabel label,String imgLocation){
        this.imgLocation=imgLocation;
        this.label=label;
    }
}
