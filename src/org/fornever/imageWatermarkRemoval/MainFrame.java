package org.fornever.imageWatermarkRemoval;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jleth.projects.imagemarkremoval.ImageRewriter;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileFilter;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4637625150648783265L;
	private JPanel contentPane;
	private JTextField txtUseToRemove;
	private ImageRewriter rewriter = new ImageRewriter();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
					MainFrame frame = new MainFrame();
					int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
					int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
					frame.setLocation(x, y);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(getClass().getResource("/org/fornever/imageWatermarkRemoval/icon.png")));
		setResizable(false);
		setTitle("WatermarkRemover");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 282, 124);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton button = new JButton("Directory");
		button.setBackground(Color.WHITE);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fChooser = new JFileChooser();
				fChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fChooser.showSaveDialog(null);
				File[] files = fChooser.getSelectedFile().listFiles(new FileFilter() {
					@Override
					public boolean accept(File pathname) {
						if (pathname.getName().indexOf("_new.png") < 0 && pathname.getName().indexOf(".png") > 0)
							return true;
						else
							return false;
					}
				});
				if (files != null) {
					try {
						for (File fs : files) {
							rewriter.unMarkImage(fs.getAbsolutePath());
						}
						JOptionPane.showMessageDialog(null, String.format("removed %d files watermark", files.length));
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Error happend in remove process");
					}
				}
			}
		});
		button.setBounds(34, 51, 87, 23);
		contentPane.add(button);

		JButton button_1 = new JButton("File");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fChooser = new JFileChooser();
				fChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {

					@Override
					public String getDescription() {
						return "Png image";
					}

					@Override
					public boolean accept(File f) {
						if (f.isDirectory())
							return true;
						if (f.getName().indexOf("_new.png") < 0 && f.getName().indexOf(".png") > 0)
							return true;
						else
							return false;
					}
				});

				fChooser.showSaveDialog(null);
				File f = fChooser.getSelectedFile();
				if (f != null) {
					try {
						rewriter.unMarkImage(f.getAbsolutePath());
						JOptionPane.showMessageDialog(null, String.format("removed %s watermark", f.getName()));
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Error happend in remove process");
					}
				}
			}
		});

		button_1.setBackground(Color.WHITE);
		button_1.setBounds(183, 51, 57, 23);
		contentPane.add(button_1);

		txtUseToRemove = new JTextField();
		txtUseToRemove.setBackground(Color.WHITE);
		txtUseToRemove.setHorizontalAlignment(SwingConstants.CENTER);
		txtUseToRemove.setEditable(false);
		txtUseToRemove.setText("Use to remove iconfinder watermark");
		txtUseToRemove.setBounds(0, 0, 276, 21);
		contentPane.add(txtUseToRemove);
		txtUseToRemove.setColumns(10);
	}
}
