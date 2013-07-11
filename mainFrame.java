//				********	THIS IS THE MAIN APPLICATION FILE	********				

/* author-  Anuj Pahuja
 * 			Aditya Raisinghani
 * 			Aradhya Saxena
 * 			from BITS Pilani, Pilani campus, Rajasthan.
 * Developed- July 2013.
 * For- BISAG, Gandhinagar
 * 
 * This code is free to use, distribute, 
 * and share as long as it meets the 
 * copyright requirements of the BISAG.
 */

import java.io.File;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.FileInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.ImageWriter;
import javax.imageio.IIOImage;
import javax.imageio.ImageTypeSpecifier;

//External JARs
import com.sun.media.imageio.plugins.tiff.TIFFImageWriteParam;
import com.sun.media.imageioimpl.plugins.tiff.TIFFImageWriterSpi;

public class mainFrame extends javax.swing.JFrame {

	/**
	 * Creates new form mainFrame
	 */

	File selectedFile, file, compressedFile;
	String filename, selectedFileName, changedFileName, compressedFileName,
			compressionType;
	float initial, compressed;
	int flag = 0;
	JFileChooser fileBrowser = new JFileChooser();
	int count = 0;

	// GUI Components
	private ButtonGroup buttonGroup2;
	private JButton selectButton;
	private JButton compressButton;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel initialLabel;
	private JLabel compressedLabel;
	private JLabel ratioLabel;
	private JLabel compressionTypeLabel;
	private JLabel filepathLabel;
	private JLabel errorLabel;
	private JPanel finalPanel;
	private JRadioButton lzwButton;
	private JRadioButton packbitsButton;
	private JRadioButton deflateButton;
	private JRadioButton zlibButton;

	// Constructor for the frame
	public mainFrame() {

		super("TIFFCompressor");
		initComponents();

		show();
	}

	// Main compressor function
	public void compressFunction() throws IOException {
		if (flag == 1) {
			if (filename != ""
					&& (filename.substring(filename.length() - 4,
							filename.length()).equalsIgnoreCase(".tif"))) {
				// Checking which compression type is selected
				if (lzwButton.isSelected())
					compressionType = "LZW";
				else if (packbitsButton.isSelected())
					compressionType = "PackBits";
				else if (deflateButton.isSelected())
					compressionType = "Deflate";
				else if (zlibButton.isSelected())
					compressionType = "ZLib";

				changedFileName = selectedFileName.substring(0,
						selectedFileName.length() - 4);
				compressedFileName = changedFileName + "_" + compressionType
						+ ".tif";
				compressedFile = new File(compressedFileName);
				ImageOutputStream ios;
				ios = ImageIO.createImageOutputStream(compressedFile);
				TIFFImageWriterSpi tiffspi = new TIFFImageWriterSpi();
				ImageWriter imageWriter = tiffspi.createWriterInstance();
				imageWriter.setOutput(ios);
				TIFFImageWriteParam writeParam = (TIFFImageWriteParam) imageWriter
						.getDefaultWriteParam();

				writeParam.setCompressionMode(2);
				writeParam.setCompressionType(compressionType); // Can be
																// LZW/Deflate/PackBits/ZLib

				try {
					imageWriter.prepareWriteSequence(null);
					BufferedImage img = ImageIO.read(file);
					ImageTypeSpecifier spec = ImageTypeSpecifier
							.createFromRenderedImage(img);
					javax.imageio.metadata.IIOMetadata metadata = imageWriter
							.getDefaultImageMetadata(spec, writeParam);
					IIOImage iioImage = new IIOImage(img, null, metadata);
					imageWriter.writeToSequence(iioImage, writeParam);
					img.flush();
					imageWriter.endWriteSequence();
					ios.flush();
					imageWriter.dispose();
					ios.close();
					compressed = (float) compressedFile.length() / 1024; // Calculates
																			// compressed
																			// File
																			// size
																			// in
																			// KB

					compressedLabel.setText(String.valueOf(compressed) + " KB");
					ratioLabel
							.setText(String
									.valueOf((1 - (float) (compressed / initial)) * 100)
									+ "%"); // Calculates Compression Ratio
					errorLabel.setVisible(false);

				} catch (Exception e) { // When file is not selected
					count = 1;

				}

			} else {
				errorLabel.setText("File not of proper format ");
				errorLabel.setVisible(true);

			}
			flag = 0;
		}

	}

	// This function loads the image and calculates initial size
	public void loadImage(File f) throws IOException {
		selectedFile = f;
		filename = selectedFile.getAbsolutePath();

		selectedFileName = this.selectedFile.getAbsolutePath();

		initial = (float) selectedFile.length() / 1024; // Calculates
														// initial
														// file size in KB
		filepathLabel.setText(this.selectedFile.getAbsolutePath());
		filepathLabel.setVisible(true);
		initialLabel.setText(String.valueOf(initial) + " KB");

		if (selectedFile != null) {
			FileInputStream in = new FileInputStream(selectedFile);
		}

	}

	/**
	 * This method is called from within the constructor to initialize the
	 * variables. It sets the layout, position and design of all GUI components.
	 * WARNING: Do NOT modify this code. Modify only if you know how's it
	 * working.
	 */

	private void initComponents() {

		buttonGroup2 = new javax.swing.ButtonGroup();
		selectButton = new javax.swing.JButton();
		compressButton = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		initialLabel = new javax.swing.JLabel();
		compressedLabel = new javax.swing.JLabel();
		ratioLabel = new javax.swing.JLabel();
		finalPanel = new javax.swing.JPanel();
		lzwButton = new javax.swing.JRadioButton();
		packbitsButton = new javax.swing.JRadioButton();
		deflateButton = new javax.swing.JRadioButton();
		zlibButton = new javax.swing.JRadioButton();
		compressionTypeLabel = new javax.swing.JLabel();
		filepathLabel = new javax.swing.JLabel();
		errorLabel = new javax.swing.JLabel();
		new javax.swing.JMenuBar();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		selectButton.setFont(new java.awt.Font("Tahoma", 1, 18));
		selectButton.setText("Select file to compress");
		selectButton.setBorder(new javax.swing.border.SoftBevelBorder(
				javax.swing.border.BevelBorder.RAISED));
		selectButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				selectButtonActionPerformed(evt);
			}
		});

		compressButton.setFont(new java.awt.Font("Tahoma", 1, 18));
		compressButton.setText("Compress");
		compressButton.setBorder(new javax.swing.border.SoftBevelBorder(
				javax.swing.border.BevelBorder.RAISED));
		compressButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				compressButtonActionPerformed(evt);
			}
		});

		jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel1.setText("Original file size (KB) :");

		jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel2.setText("Compressed file size (KB) :");

		jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel3.setText("Compression percentage :");

		finalPanel.setLayout(new javax.swing.BoxLayout(finalPanel,
				javax.swing.BoxLayout.LINE_AXIS));

		lzwButton.setBackground(new java.awt.Color(255, 255, 204));
		buttonGroup2.add(lzwButton);
		lzwButton.setText("LZW");

		packbitsButton.setBackground(new java.awt.Color(255, 255, 204));
		buttonGroup2.add(packbitsButton);
		packbitsButton.setText("Packbits");

		deflateButton.setBackground(new java.awt.Color(255, 255, 204));
		buttonGroup2.add(deflateButton);
		deflateButton.setText("Deflate");

		zlibButton.setBackground(new java.awt.Color(255, 255, 204));
		buttonGroup2.add(zlibButton);
		zlibButton.setText("Zlib");

		compressionTypeLabel.setBackground(new java.awt.Color(153, 255, 153));
		compressionTypeLabel.setFont(new java.awt.Font("Tahoma", 0, 18));
		compressionTypeLabel.setText("Select Compression type");

		filepathLabel.setFont(new java.awt.Font("Tahoma", 0, 14));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(40, 40, 40)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		compressButton,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		153,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																										javax.swing.GroupLayout.DEFAULT_SIZE,
																										Short.MAX_VALUE)
																								.addComponent(
																										finalPanel,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										javax.swing.GroupLayout.DEFAULT_SIZE,
																										javax.swing.GroupLayout.PREFERRED_SIZE))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(47,
																										47,
																										47)
																								.addComponent(
																										errorLabel,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										250,
																										javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addGap(0,
																										0,
																										Short.MAX_VALUE))))
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(60,
																										60,
																										60)
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING,
																												false)
																												.addGroup(
																														layout.createSequentialGroup()
																																.addComponent(
																																		jLabel1)
																																.addGap(149,
																																		149,
																																		149)
																																.addComponent(
																																		initialLabel,
																																		javax.swing.GroupLayout.DEFAULT_SIZE,
																																		123,
																																		Short.MAX_VALUE))
																												.addGroup(
																														layout.createSequentialGroup()
																																.addGroup(
																																		layout.createParallelGroup(
																																				javax.swing.GroupLayout.Alignment.LEADING)
																																				.addComponent(
																																						jLabel3)
																																				.addComponent(
																																						jLabel2))
																																.addGap(122,
																																		122,
																																		122)
																																.addGroup(
																																		layout.createParallelGroup(
																																				javax.swing.GroupLayout.Alignment.LEADING,
																																				false)
																																				.addComponent(
																																						ratioLabel,
																																						javax.swing.GroupLayout.DEFAULT_SIZE,
																																						123,
																																						Short.MAX_VALUE)
																																				.addComponent(
																																						compressedLabel,
																																						javax.swing.GroupLayout.DEFAULT_SIZE,
																																						javax.swing.GroupLayout.DEFAULT_SIZE,
																																						Short.MAX_VALUE)))))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																												.addComponent(
																														selectButton,
																														javax.swing.GroupLayout.PREFERRED_SIZE,
																														227,
																														javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addGroup(
																														layout.createSequentialGroup()
																																.addComponent(
																																		compressionTypeLabel)
																																.addGap(34,
																																		34,
																																		34)
																																.addComponent(
																																		lzwButton)))
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																												.addGroup(
																														layout.createSequentialGroup()
																																.addComponent(
																																		packbitsButton)
																																.addPreferredGap(
																																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																.addComponent(
																																		deflateButton)
																																.addPreferredGap(
																																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																.addComponent(
																																		zlibButton,
																																		javax.swing.GroupLayout.PREFERRED_SIZE,
																																		49,
																																		javax.swing.GroupLayout.PREFERRED_SIZE))
																												.addGroup(
																														layout.createSequentialGroup()
																																.addGap(40,
																																		40,
																																		40)
																																.addComponent(
																																		filepathLabel,
																																		javax.swing.GroupLayout.PREFERRED_SIZE,
																																		224,
																																		javax.swing.GroupLayout.PREFERRED_SIZE)))))
																.addGap(136,
																		136,
																		Short.MAX_VALUE)))));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(25, 25, 25)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING,
												false)
												.addComponent(
														selectButton,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														35, Short.MAX_VALUE)
												.addComponent(
														filepathLabel,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
								.addGap(30, 30, 30)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														finalPanel,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(12, 12,
																		12)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						lzwButton)
																				.addComponent(
																						packbitsButton)
																				.addComponent(
																						deflateButton)
																				.addComponent(
																						zlibButton)
																				.addComponent(
																						compressionTypeLabel))))
								.addGap(27, 27, 27)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(compressButton)
												.addComponent(errorLabel))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel1)
												.addComponent(
														initialLabel,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														15,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel2)
												.addComponent(
														compressedLabel,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														15,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING,
												false)
												.addComponent(
														ratioLabel,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														jLabel3,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
								.addContainerGap(35, Short.MAX_VALUE)));

		pack();
	}

	// Opens the File Browser on Click
	private void selectButtonActionPerformed(java.awt.event.ActionEvent evt) {

		int returnVal = fileBrowser.showOpenDialog(mainFrame.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileBrowser.getSelectedFile();
			filename = file.getName();

			try {
				loadImage(file);
				if (!(filename.substring(filename.length() - 4,
						filename.length()).equalsIgnoreCase(".tif"))) {
					errorLabel.setText("File not of proper format");
					errorLabel.setVisible(true);

				} else {
					errorLabel.setVisible(false);
				}
			} catch (Exception e) {

			}

		}
	}

	// Performs compress function on Click
	private void compressButtonActionPerformed(java.awt.event.ActionEvent evt) {

		flag = 1;
		if (count == 1) {
			errorLabel.setVisible(false); // Hide error label when file is
											// selected

		}
		if (count == 0) {
			errorLabel.setText("File not selected ");
			errorLabel.setVisible(true);
		}
		try {

			compressFunction();
		} catch (Exception e) {

		}

	}

	// ******** MAIN FUNCTION ********
	public static void main(String args[]) {

		// UI Manager (NOT TO BE MODIFIED)
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {

			// Runs the application
			public void run() {
				new mainFrame().setVisible(true);
			}
		});

	}

}
