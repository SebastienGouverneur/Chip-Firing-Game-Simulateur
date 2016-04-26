package view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controler.ControlerMainFrame;

import java.awt.GridBagConstraints;

public class ViewAsynchrone {

	private JProgressBar progressBar;
	private int progress;
	private JButton launchButton;
	
	private ControlerMainFrame controlerMainFrame;
	private JButton cancelButton;

	public ViewAsynchrone(final ControlerMainFrame controlerMainFrame) {
		
		this.controlerMainFrame = controlerMainFrame;
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
				}

				progressBar = new JProgressBar(0, 100);
				progressBar.setBounds(71, 40, 148, 20);
				progressBar.setValue(0);
				progressBar.setStringPainted(true);

				launchButton = new JButton("Launch");
				launchButton.setBounds(48, 12, 85, 25);
				launchButton.setEnabled(false);

				final JFrame frame = new JFrame("Calcul in progress, please wait");
				frame.setPreferredSize(new Dimension(300, 100));
				launchButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						controlerMainFrame.graphTransButtonPerformed();
						
					}
				});
				frame.getContentPane().setLayout(null);
				frame.getContentPane().add(launchButton);
				frame.getContentPane().add(progressBar);
				
				cancelButton = new JButton("Cancel");
				cancelButton.setBounds(145, 12, 90, 25);
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
					}
				});
				frame.getContentPane().add(cancelButton);
				frame.pack();
				frame.setResizable(false);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);

				Timer timer = new Timer(50, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						progress += 1;
						if (progress >= 100) {
							progress = 100;
							((Timer) e.getSource()).stop();
							launchButton.setEnabled(true);
						}
						progressBar.setValue(progress);
					}
				});
				timer.start();
			}
		});
	}
}