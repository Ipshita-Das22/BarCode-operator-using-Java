package com.codeBar;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Writer;

public class makeBar extends JFrame{
	
	private JLabel read,write,pv;
	private JTextField r1,w1;
	private JButton re,wr,cl,close;
	private JFileChooser rec,sav;

	
	String opath,wpath;
	
	public makeBar(){
		setVisible(true);
		setBounds(400,80,600,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("BARCODE-OPERATOR");
		
		
		Container c = this.getContentPane();
		c.setLayout(null);
		c.setBackground(Color.BLACK);
		
		read = new JLabel("Read");
		read.setBounds(10,10,100,30);
		read.setFont(new Font("arial",Font.BOLD,15));
		read.setForeground(Color.WHITE);
		
		r1 = new JTextField();
		r1.setBounds(60,10,400,30);
		r1.setFont(new Font("arial",Font.BOLD,15));
		r1.setEditable(false);
		r1.setBackground(Color.white);
		
		re = new JButton("Read");
		re.setBounds(470,10,100,30);
		re.setFont(new Font("arial",Font.BOLD,15));
		re.setCursor(new Cursor(Cursor.HAND_CURSOR));
		re.setBackground(Color.WHITE);
		
		write = new JLabel("Write");
		write.setBounds(10,50,100,30);
		write.setFont(new Font("arial",Font.BOLD,15));
		write.setForeground(Color.WHITE);
		
		w1 = new JTextField();
		w1.setBounds(60,50,400,30);
		w1.setFont(new Font("arial",Font.BOLD,15));
		w1.setBackground(Color.white);
		
		wr = new JButton("Write");
		wr.setBounds(470,50,100,30);
		wr.setFont(new Font("arial",Font.BOLD,15));
		wr.setCursor(new Cursor(Cursor.HAND_CURSOR));
		wr.setBackground(Color.WHITE);
		
		pv = new JLabel();
		pv.setBounds(60,100,400,400);
		pv.setBackground(Color.WHITE);
		pv.setOpaque(true);
		
		cl = new JButton("Clear");
		cl.setBounds(190,520,100,30);
		cl.setFont(new Font("arial",Font.BOLD,15));
		cl.setCursor(new Cursor(Cursor.HAND_CURSOR));
		cl.setBackground(Color.WHITE);
		
		close = new JButton("Close");
		close.setBounds(300,520,100,30);
		close.setFont(new Font("arial",Font.BOLD,15));
		close.setCursor(new Cursor(Cursor.HAND_CURSOR));
		close.setBackground(Color.WHITE);
		
		c.add(read);
		c.add(r1);
		c.add(re);
		c.add(write);
		c.add(w1);
		c.add(wr);
		c.add(pv);
		c.add(cl);
		c.add(close);
		
		
		cl.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				w1.setText("");
				r1.setText("");
				opath = "";
				wpath="";
				pv.setIcon(null);
				
				
			}
		});
		
		close.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				System.exit(0);
			}
		});
		
		re.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				rec = new JFileChooser();
				rec.setAcceptAllFileFilterUsed(false);
				rec.addChoosableFileFilter(new FileNameExtensionFilter("JPEG files", "jpeg"));
				rec.addChoosableFileFilter(new FileNameExtensionFilter("PNG files", "png"));
				rec.addChoosableFileFilter(new FileNameExtensionFilter("JPG files", "jpg"));
				int ap = rec.showOpenDialog(null);
					if(ap == JFileChooser.APPROVE_OPTION){
						opath = rec.getSelectedFile().getAbsolutePath();
							try{
								
								BufferedImage bf = ImageIO.read(new File(opath));
								BinaryBitmap bp = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bf)));
								
								Result rs = new MultiFormatReader().decode(bp);
								r1.setText(rs.getText());
								ImageIcon ic = new ImageIcon(opath);
								pv.setIcon(ic);
							
								
							}catch(Exception e1){
								
								JOptionPane.showMessageDialog(null, "Please Choose Valid BarCode","Error",JOptionPane.ERROR_MESSAGE);
							}
						
					}
			}
		});
		
		wr.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				String check = w1.getText();
				if(check.isEmpty()){
					JOptionPane.showMessageDialog(null, "Text Field Cannot Be Empty","Error",JOptionPane.ERROR_MESSAGE);
				}else{
						sav = new JFileChooser();
						sav.setAcceptAllFileFilterUsed(false);
						sav.addChoosableFileFilter(new FileNameExtensionFilter("JPEG files", "jpeg"));
						sav.addChoosableFileFilter(new FileNameExtensionFilter("PNG files", "png"));
						sav.addChoosableFileFilter(new FileNameExtensionFilter("JPG files", "jpg"));
							
							int ap = sav.showSaveDialog(null);
							if(ap == JFileChooser.APPROVE_OPTION){
								
								wpath = sav.getSelectedFile().getAbsolutePath();
								
								try{
									
										Code128Writer ww = new Code128Writer();
										BitMatrix mat = ww.encode(w1.getText(),BarcodeFormat.CODE_128,400,400);
										MatrixToImageWriter.writeToPath(mat, "jpeg", Paths.get(wpath));
										w1.setText("");
										ImageIcon ic = new ImageIcon(wpath);
										pv.setIcon(ic);
										JOptionPane.showMessageDialog(null, "Created Successfully","Message",JOptionPane.INFORMATION_MESSAGE);
										
									
								}catch(Exception e1){
									
									JOptionPane.showMessageDialog(null, "Cannot Be Created","Error",JOptionPane.ERROR_MESSAGE);
								}
							}else{
								w1.setText("");
							}
						
					}
			}
		});
		
	}
	
	public static void main(String... args){
		new makeBar();
	}

}
