package cs361Project;
/**
 * ChronoTimerGUI.Java
 * GUI for the Chronotimer system.
 * 
 * @author Matt Balistreri
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.plaf.basic.BasicArrowButton;

import org.omg.CORBA.TRANSACTION_MODE;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;

public class ChronoTimerGUI extends JFrame {
	private ChronoTimerControl ct;
	private CmdInterface cmd;
	private String[] printerLog;
	
	private JPanel mainFrame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnPower;
	private JPanel panelChannel;
	private JLabel lblNewLabel;
	private JRadioButton rbChan1;
	private JRadioButton rbChan3;
	private JRadioButton rbChan5;
	private JRadioButton rbChan7;
	private JLabel lblChan3;
	private JLabel lblChan5;
	private JLabel lblChan7;
	private JLabel lblChan2;
	private JLabel lblChan4;
	private JLabel lblChan6;
	private JLabel lblChan8;
	private JRadioButton rbChan2;
	private JRadioButton rbChan4;
	private JRadioButton rbChan6;
	private JRadioButton rbChan8;
	private JLabel lblChan;
	private JComboBox cmbxSensor;
	private JLabel lblSensor;
	private JPanel panelFinish;
	private JLabel lblFinish;
	private JLabel lblTogEven;
	private JPanel panelFinishbtn;
	private JPanel panelTogEven;
	private JButton btnFin2;
	private JButton btnFin4;
	private JButton btnFin6;
	private JButton btnFin8;
	private JRadioButton rbTogEven2;
	private JRadioButton rbTogEven4;
	private JRadioButton rbTogEven6;
	private JRadioButton rbTogEven8;
	private JLabel lbl2;
	private JLabel lbl4;
	private JLabel lbl6;
	private JLabel lbl8;
	private JLabel lblStart;
	private JLabel lblTogOdd;
	private JPanel panelStartbtn;
	private JButton btnStart1;
	private JButton btnStart3;
	private JButton btnStart5;
	private JButton btnStart7;
	private JRadioButton rbTog1;
	private JRadioButton rbTog3;
	private JRadioButton rbTog5;
	private JRadioButton rbTog7;
	private JLabel lbl1;
	private JLabel lbl3;
	private JLabel lbl5;
	private JLabel lbl7;
	private JLabel lblTitle;
	private JPanel panelPrinter;
	private JButton btnPrint;
	private JButton btnCalc1;
	private JButton btnCalc2;
	private JPanel panelCalculator;
	private JButton btnCalc3;
	private JButton btnCalc4;
	private JButton btnCalc5;
	private JButton btnCalc6;
	private JButton btnCalc7;
	private JButton btnCalc8;
	private JButton btnCalc9;
	private JButton btnCalcStar;
	private JButton btnCalc0;
	private JButton btnCalcPound;
	private JButton btnNewrun;
	private JButton btnEndrun;
	private JButton btnDnf;
	private JButton btnCancel;
	private JButton btnInd;
	private JButton btnParind;
	private JButton btnGrp;
	private JButton btnPargrp;
	private JButton btnSwap;
	private JButton btnExport;
	private JButton btnEnterNumber;
	private JScrollPane scrollPane;
	private JTextArea taDisplay;
	
	private boolean printerOn=false;
	private String runnerNum="";
	private String printerText="";
	private boolean enableKeys;
	private JScrollPane spPrinter;
	private JTextArea taPrinter;
	private JButton btnPrintfun;
	private final Timer updater;
	private JTextArea taSysevent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChronoTimerGUI frame = new ChronoTimerGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame and initialize all components.
	 */
	public ChronoTimerGUI() {
		
		//TODO: Implement Printer.
		//TODO: Find a way to display correct information on the display.
		
		
		//create CT control object to manipulate the system
		ct = new ChronoTimerControl();		//Implements the Controller as a persistent object
		ct.addSubscriber(this);				//Adds this GUI object to the Controller's subscribers list
		cmd = new CmdInterface(2, ct);		//Opens up an instance of the file parser to run through the console alongside the GUI
		printerLog = new String[6];
		enableKeys = false;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 913, 692);
		mainFrame = new JPanel();
		setContentPane(mainFrame);
		cmbxSensor = new JComboBox();
		cmbxSensor.setEnabled(false);
		cmbxSensor.setFont(new Font("Tahoma", Font.BOLD, 12));
		cmbxSensor.setMaximumRowCount(3);
		cmbxSensor.setModel(new DefaultComboBoxModel(new String[] {"Gate", "Eye", "Pad"}));
		lblTitle = new JLabel("CHRONOTIMER 1009");
		lblTitle.setFont(new Font("Tahoma", Font.ITALIC, 18));
		JPanel panelStart = new JPanel();
		panelFinish = new JPanel();
		panelTogEven = new JPanel();
		rbTogEven2 = new JRadioButton("");
		rbTogEven2.setEnabled(false);
		rbTogEven4 = new JRadioButton("");
		rbTogEven4.setEnabled(false);
		rbTogEven6 = new JRadioButton("");
		rbTogEven6.setEnabled(false);
		rbTogEven8 = new JRadioButton("");
		rbTogEven8.setEnabled(false);
		lblFinish = new JLabel("Finish");
		lblFinish.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTogEven = new JLabel("Enable/Disable");
		lblTogEven.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelFinishbtn = new JPanel();
		btnFin2 = new JButton("");
		btnFin2.setEnabled(false);
		btnFin4 = new JButton("");
		btnFin4.setEnabled(false);
		btnFin6 = new JButton("");
		btnFin6.setEnabled(false);
		btnFin8 = new JButton("");
		btnFin8.setEnabled(false);
		lbl2 = new JLabel("2");
		lbl2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl4 = new JLabel("4");
		lbl4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl6 = new JLabel("6");
		lbl6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl8 = new JLabel("8");
		lbl8.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnPower = new JButton("Power");
		btnPower.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelChannel = new JPanel();
		panelPrinter = new JPanel();
		panelCalculator = new JPanel();
		panelCalculator.setLayout(new GridLayout(4, 3, 0, 0));
		btnCalc1 = new JButton("1");
		btnCalc1.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalc1);
		btnCalc2 = new JButton("2");
		btnCalc2.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalc2);
		btnCalc3 = new JButton("3");
		btnCalc3.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalc3);
		btnCalc4 = new JButton("4");
		btnCalc4.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalc4);
		btnCalc5 = new JButton("5");
		btnCalc5.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalc5);
		btnCalc6 = new JButton("6");
		btnCalc6.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalc6);
		btnCalc7 = new JButton("7");
		btnCalc7.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalc7);
		btnCalc8 = new JButton("8");
		btnCalc8.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalc8);
		btnCalc9 = new JButton("9");
		btnCalc9.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalc9);
		btnCalcStar = new JButton("*");
		btnCalcStar.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalcStar);
		btnCalc0 = new JButton("0");
		btnCalc0.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalc0);
		btnCalcPound = new JButton("#");
		btnCalcPound.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalcPound);	
		btnPrint = new JButton("Printer Pwr");
		btnPrint.setEnabled(false);
		btnPrint.setFont(new Font("Tahoma", Font.BOLD, 12));	
		rbChan1 = new JRadioButton("");
		rbChan1.setEnabled(false);
		rbChan3 = new JRadioButton("");
		rbChan3.setEnabled(false);
		rbChan5 = new JRadioButton("");
		rbChan5.setEnabled(false);
		rbChan7 = new JRadioButton("");
		rbChan7.setEnabled(false);
		rbChan2 = new JRadioButton("");
		rbChan2.setEnabled(false);	
		rbChan4 = new JRadioButton("");
		rbChan4.setEnabled(false);	
		rbChan6 = new JRadioButton("");
		rbChan6.setEnabled(false);
		rbChan8 = new JRadioButton("");
		rbChan8.setEnabled(false);
		lblNewLabel = new JLabel("1");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblChan3 = new JLabel("3");
		lblChan3.setHorizontalAlignment(SwingConstants.CENTER);
		lblChan3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblChan5 = new JLabel("5");
		lblChan5.setHorizontalAlignment(SwingConstants.CENTER);
		lblChan5.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblChan7 = new JLabel("7");
		lblChan7.setHorizontalAlignment(SwingConstants.CENTER);
		lblChan7.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblChan2 = new JLabel("2");
		lblChan2.setHorizontalAlignment(SwingConstants.CENTER);
		lblChan2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblChan4 = new JLabel("4");
		lblChan4.setHorizontalAlignment(SwingConstants.CENTER);
		lblChan4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblChan6 = new JLabel("6");
		lblChan6.setHorizontalAlignment(SwingConstants.CENTER);
		lblChan6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblChan8 = new JLabel("8");
		lblChan8.setHorizontalAlignment(SwingConstants.CENTER);
		lblChan8.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblChan = new JLabel("CHAN");
		lbl1 = new JLabel("1");
		lbl1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl3 = new JLabel("3");
		lbl3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl5 = new JLabel("5");
		lbl5.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl7 = new JLabel("7");
		lbl7.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStart = new JLabel("Start");
		lblStart.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTogOdd = new JLabel("Enable/Disable");
		lblTogOdd.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelStartbtn = new JPanel();
		JPanel panelTogOdd = new JPanel();
		rbTog1 = new JRadioButton("");
		rbTog1.setEnabled(false);
		rbTog3 = new JRadioButton("");
		rbTog3.setEnabled(false);
		rbTog5 = new JRadioButton("");
		rbTog5.setEnabled(false);
		rbTog7 = new JRadioButton("");
		rbTog7.setEnabled(false);
		btnStart1 = new JButton("");
		btnStart1.setEnabled(false);
		btnStart3 = new JButton("");
		btnStart3.setEnabled(false);
		btnStart5 = new JButton("");
		btnStart5.setEnabled(false);	
		btnStart7 = new JButton("");
		btnStart7.setEnabled(false);
		lblSensor = new JLabel("Sensor to Connect");
		lblSensor.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewrun = new JButton("NewRun");
		btnNewrun.setEnabled(false);
		btnNewrun.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEndrun = new JButton("EndRun");
		btnEndrun.setEnabled(false);
		btnEndrun.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDnf = new JButton("DNF");
		btnDnf.setEnabled(false);
		btnDnf.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCancel = new JButton("Cancel");
		btnCancel.setEnabled(false);
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnInd = new JButton("IND");
		btnInd.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnInd.setEnabled(false);
		btnParind = new JButton("PARIND");
		btnParind.setEnabled(false);
		btnParind.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnGrp = new JButton("GRP");
		btnGrp.setEnabled(false);
		btnGrp.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPargrp = new JButton("PAGRP");
		btnPargrp.setEnabled(false);
		btnPargrp.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSwap = new JButton("Swap");
		btnSwap.setEnabled(false);
		btnSwap.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnExport = new JButton("Export");
		btnExport.setEnabled(false);
		btnExport.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEnterNumber = new JButton("Enter Number");
		btnEnterNumber.setEnabled(false);
		btnEnterNumber.setFont(new Font("Tahoma", Font.BOLD, 11));
		scrollPane = new JScrollPane();
		btnPrintfun = new JButton("Print");
		btnPrintfun.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPrintfun.setEnabled(false);
		
	/**
	 * Adjust layout	
	 */
		GroupLayout gl_panelTogEven = new GroupLayout(panelTogEven);
		gl_panelTogEven.setHorizontalGroup(
			gl_panelTogEven.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTogEven.createSequentialGroup()
					.addGap(15)
					.addComponent(rbTogEven2)
					.addGap(31)
					.addComponent(rbTogEven4, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
					.addComponent(rbTogEven6, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addGap(30)
					.addComponent(rbTogEven8, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addGap(23))
		);
		gl_panelTogEven.setVerticalGroup(
			gl_panelTogEven.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTogEven.createSequentialGroup()
					.addGap(4)
					.addGroup(gl_panelTogEven.createParallelGroup(Alignment.LEADING)
						.addComponent(rbTogEven2)
						.addComponent(rbTogEven4, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbTogEven6, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbTogEven8, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panelTogEven.setLayout(gl_panelTogEven);
		GroupLayout gl_panelFinishbtn = new GroupLayout(panelFinishbtn);
		gl_panelFinishbtn.setHorizontalGroup(
			gl_panelFinishbtn.createParallelGroup(Alignment.LEADING)
				.addGap(0, 214, Short.MAX_VALUE)
				.addGroup(gl_panelFinishbtn.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnFin2)
					.addGap(18)
					.addComponent(btnFin4, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnFin6, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnFin8, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(18, Short.MAX_VALUE))
		);
		gl_panelFinishbtn.setVerticalGroup(
			gl_panelFinishbtn.createParallelGroup(Alignment.LEADING)
				.addGap(0, 28, Short.MAX_VALUE)
				.addGroup(gl_panelFinishbtn.createSequentialGroup()
					.addGroup(gl_panelFinishbtn.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnFin8, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnFin6, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnFin4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnFin2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panelFinishbtn.setLayout(gl_panelFinishbtn);	
		GroupLayout gl_panelFinish = new GroupLayout(panelFinish);
		gl_panelFinish.setHorizontalGroup(
			gl_panelFinish.createParallelGroup(Alignment.LEADING)
				.addGap(0, 339, Short.MAX_VALUE)
				.addGroup(gl_panelFinish.createSequentialGroup()
					.addGroup(gl_panelFinish.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFinish, Alignment.TRAILING)
						.addComponent(lblTogEven, Alignment.TRAILING))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelFinish.createParallelGroup(Alignment.LEADING)
						.addComponent(panelFinishbtn, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE)
						.addComponent(panelTogEven, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(24, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_panelFinish.createSequentialGroup()
					.addContainerGap(122, Short.MAX_VALUE)
					.addComponent(lbl2)
					.addGap(42)
					.addComponent(lbl4)
					.addGap(45)
					.addComponent(lbl6)
					.addGap(44)
					.addComponent(lbl8, GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE)
					.addGap(54))
		);
		gl_panelFinish.setVerticalGroup(
			gl_panelFinish.createParallelGroup(Alignment.LEADING)
				.addGap(0, 101, Short.MAX_VALUE)
				.addGroup(gl_panelFinish.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelFinish.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl8, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbl6)
						.addComponent(lbl4)
						.addComponent(lbl2))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelFinish.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFinish)
						.addComponent(panelFinishbtn, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(gl_panelFinish.createParallelGroup(Alignment.LEADING)
						.addComponent(panelTogEven, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTogEven))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panelFinish.setLayout(gl_panelFinish);
		
		JScrollPane spSysevent = new JScrollPane();
		
		JLabel lblSysevent = new JLabel("System Messages:");
		lblSysevent.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		GroupLayout gl_mainFrame = new GroupLayout(mainFrame);
		gl_mainFrame.setHorizontalGroup(
			gl_mainFrame.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainFrame.createSequentialGroup()
					.addGroup(gl_mainFrame.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_mainFrame.createSequentialGroup()
							.addGap(12)
							.addGroup(gl_mainFrame.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_mainFrame.createSequentialGroup()
									.addGroup(gl_mainFrame.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_mainFrame.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_mainFrame.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_mainFrame.createSequentialGroup()
													.addGroup(gl_mainFrame.createParallelGroup(Alignment.TRAILING)
														.addComponent(btnDnf, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
														.addComponent(btnNewrun, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
													.addPreferredGap(ComponentPlacement.RELATED)
													.addGroup(gl_mainFrame.createParallelGroup(Alignment.LEADING)
														.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
														.addComponent(btnEndrun)))
												.addGroup(gl_mainFrame.createSequentialGroup()
													.addComponent(btnInd, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(btnParind, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_mainFrame.createSequentialGroup()
													.addComponent(btnGrp, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(btnPargrp, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))))
										.addGroup(gl_mainFrame.createSequentialGroup()
											.addGap(29)
											.addGroup(gl_mainFrame.createParallelGroup(Alignment.TRAILING)
												.addComponent(btnPower, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
												.addComponent(btnEnterNumber)))
										.addGroup(gl_mainFrame.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnSwap, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnExport, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_mainFrame.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_mainFrame.createParallelGroup(Alignment.LEADING)
												.addComponent(panelChannel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(cmbxSensor, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblSensor))))
									.addGap(49))
								.addGroup(gl_mainFrame.createSequentialGroup()
									.addComponent(lblSysevent)
									.addGap(18))))
						.addGroup(gl_mainFrame.createSequentialGroup()
							.addGap(35)
							.addComponent(btnPrintfun, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_mainFrame.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_mainFrame.createSequentialGroup()
							.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
							.addGap(18))
						.addGroup(gl_mainFrame.createSequentialGroup()
							.addGroup(gl_mainFrame.createParallelGroup(Alignment.LEADING)
								.addComponent(panelFinish, GroupLayout.PREFERRED_SIZE, 339, GroupLayout.PREFERRED_SIZE)
								.addComponent(panelStart, GroupLayout.PREFERRED_SIZE, 339, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_mainFrame.createParallelGroup(Alignment.TRAILING, false)
									.addGroup(Alignment.LEADING, gl_mainFrame.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(spSysevent))
									.addComponent(scrollPane, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 332, GroupLayout.PREFERRED_SIZE)))
							.addGap(27)))
					.addGap(15)
					.addGroup(gl_mainFrame.createParallelGroup(Alignment.LEADING)
						.addComponent(panelPrinter, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
						.addComponent(panelCalculator, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE))
					.addGap(50))
		);
		gl_mainFrame.setVerticalGroup(
			gl_mainFrame.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainFrame.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_mainFrame.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblTitle)
						.addComponent(btnPower, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addGroup(gl_mainFrame.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_mainFrame.createSequentialGroup()
							.addComponent(panelPrinter, GroupLayout.PREFERRED_SIZE, 204, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(panelCalculator, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_mainFrame.createSequentialGroup()
							.addComponent(btnEnterNumber)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_mainFrame.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnNewrun)
								.addComponent(btnEndrun))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_mainFrame.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnDnf)
								.addComponent(btnCancel))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_mainFrame.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnInd)
								.addComponent(btnParind))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_mainFrame.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnGrp)
								.addComponent(btnPargrp))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_mainFrame.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnSwap)
								.addComponent(btnExport))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnPrintfun, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addGap(27)
							.addComponent(lblSensor)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmbxSensor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(panelChannel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_mainFrame.createSequentialGroup()
							.addComponent(panelStart, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(panelFinish, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_mainFrame.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSysevent)
						.addComponent(spSysevent, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		taSysevent = new JTextArea();
		taSysevent.setLineWrap(true);
		taSysevent.setFont(new Font("Monospaced", Font.PLAIN, 12));
		spSysevent.setViewportView(taSysevent);
		
		taDisplay = new JTextArea();
		taDisplay.setFont(new Font("Miriam Fixed", Font.BOLD, 13));
		taDisplay.setForeground(Color.GREEN);
		taDisplay.setBackground(Color.DARK_GRAY);
		taDisplay.setEditable(false);
		taDisplay.setLineWrap(true);
		scrollPane.setViewportView(taDisplay);
		
		spPrinter = new JScrollPane();
		
		GroupLayout gl_panelPrinter = new GroupLayout(panelPrinter);
		gl_panelPrinter.setHorizontalGroup(
			gl_panelPrinter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelPrinter.createSequentialGroup()
					.addGroup(gl_panelPrinter.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelPrinter.createSequentialGroup()
							.addGap(53)
							.addComponent(btnPrint))
						.addGroup(gl_panelPrinter.createSequentialGroup()
							.addGap(26)
							.addComponent(spPrinter, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(26, Short.MAX_VALUE))
		);
		gl_panelPrinter.setVerticalGroup(
			gl_panelPrinter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelPrinter.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnPrint, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(spPrinter, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(15, Short.MAX_VALUE))
		);
		
		taPrinter = new JTextArea();
		taPrinter.setEditable(false);
		taPrinter.setFont(new Font("Monospaced", Font.PLAIN, 10));
		spPrinter.setViewportView(taPrinter);
		panelPrinter.setLayout(gl_panelPrinter);
		lblChan.setFont(new Font("Tahoma", Font.BOLD, 12));
		GroupLayout gl_panelChannel = new GroupLayout(panelChannel);
		gl_panelChannel.setHorizontalGroup(
			gl_panelChannel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelChannel.createSequentialGroup()
					.addContainerGap(20, Short.MAX_VALUE)
					.addGroup(gl_panelChannel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelChannel.createSequentialGroup()
							.addGroup(gl_panelChannel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(rbChan1, Alignment.LEADING))
							.addGap(18)
							.addGroup(gl_panelChannel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelChannel.createSequentialGroup()
									.addComponent(rbChan3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(rbChan5, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(rbChan7, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panelChannel.createSequentialGroup()
									.addComponent(lblChan3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblChan5, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblChan7, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_panelChannel.createSequentialGroup()
							.addComponent(lblChan2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblChan4, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblChan6, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblChan8, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelChannel.createSequentialGroup()
							.addComponent(rbChan2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(rbChan4, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(rbChan6, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(rbChan8, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblChan))
					.addGap(10))
		);
		gl_panelChannel.setVerticalGroup(
			gl_panelChannel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelChannel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblChan)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelChannel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(lblChan3, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblChan5, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblChan7, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelChannel.createParallelGroup(Alignment.LEADING)
						.addComponent(rbChan7, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbChan5, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbChan3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbChan1))
					.addGap(25)
					.addGroup(gl_panelChannel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblChan2, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblChan4, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblChan6, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblChan8, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelChannel.createParallelGroup(Alignment.TRAILING)
						.addComponent(rbChan4, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbChan2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbChan6, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbChan8, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(12, Short.MAX_VALUE))
		);
		panelChannel.setLayout(gl_panelChannel);
		GroupLayout gl_panelStart = new GroupLayout(panelStart);
		gl_panelStart.setHorizontalGroup(
			gl_panelStart.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelStart.createSequentialGroup()
					.addGroup(gl_panelStart.createParallelGroup(Alignment.LEADING)
						.addComponent(lblStart, Alignment.TRAILING)
						.addComponent(lblTogOdd, Alignment.TRAILING))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelStart.createParallelGroup(Alignment.LEADING)
						.addComponent(panelStartbtn, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE)
						.addComponent(panelTogOdd, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(24, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_panelStart.createSequentialGroup()
					.addContainerGap(122, Short.MAX_VALUE)
					.addComponent(lbl1)
					.addGap(42)
					.addComponent(lbl3)
					.addGap(45)
					.addComponent(lbl5)
					.addGap(44)
					.addComponent(lbl7, GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE)
					.addGap(54))
		);
		gl_panelStart.setVerticalGroup(
			gl_panelStart.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelStart.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelStart.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl7, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbl5)
						.addComponent(lbl3)
						.addComponent(lbl1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelStart.createParallelGroup(Alignment.LEADING)
						.addComponent(lblStart)
						.addComponent(panelStartbtn, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(gl_panelStart.createParallelGroup(Alignment.LEADING)
						.addComponent(panelTogOdd, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTogOdd))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		GroupLayout gl_panelTogOdd = new GroupLayout(panelTogOdd);
		gl_panelTogOdd.setHorizontalGroup(
			gl_panelTogOdd.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTogOdd.createSequentialGroup()
					.addGap(15)
					.addComponent(rbTog1)
					.addGap(31)
					.addComponent(rbTog3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
					.addComponent(rbTog5, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addGap(30)
					.addComponent(rbTog7, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addGap(23))
		);
		gl_panelTogOdd.setVerticalGroup(
			gl_panelTogOdd.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTogOdd.createSequentialGroup()
					.addGap(4)
					.addGroup(gl_panelTogOdd.createParallelGroup(Alignment.LEADING)
						.addComponent(rbTog7, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbTog5, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbTog3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbTog1))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panelTogOdd.setLayout(gl_panelTogOdd);
		
		
		GroupLayout gl_panelStartbtn = new GroupLayout(panelStartbtn);
		gl_panelStartbtn.setHorizontalGroup(
			gl_panelStartbtn.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelStartbtn.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnStart1)
					.addGap(18)
					.addComponent(btnStart3, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnStart5, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnStart7, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(18, Short.MAX_VALUE))
		);
		gl_panelStartbtn.setVerticalGroup(
			gl_panelStartbtn.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelStartbtn.createSequentialGroup()
					.addGroup(gl_panelStartbtn.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnStart7, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnStart5, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnStart3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnStart1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panelStartbtn.setLayout(gl_panelStartbtn);
		panelStart.setLayout(gl_panelStart);
		mainFrame.setLayout(gl_mainFrame);	
		
/**
 * Add Action Listeners
 */
		
		//TODO:Add System Message bar to GUI
			updater = new Timer(50, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ct.execute("TIME");
				update();
			}
		});
		
		rbTogEven2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("tog 2");
			}
		});
		
		rbTogEven4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("tog 4");
			}
		});
		
		rbTogEven6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("tog 6");
			}
		});
		
		rbTogEven8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("tog 8");
			}
		});
		
		btnFin2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ct.getSystem().isActive()){
					ct.execute("TIME");
					ct.execute("TRIG 2");
					if(!ct.getSystem().getRun().isActive() && !ct.getSystem().getEvent().equalsIgnoreCase("GRP")){
						updater.stop();
					}
				}
			}
		});
		
		btnFin4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ct.getSystem().isActive()){
					ct.execute("TIME");
					ct.execute("TRIG 4");
					if(!ct.getSystem().getRun().isActive()){
						updater.stop();
					}
				}
			}
		});
		
		btnFin6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ct.getSystem().isActive()){
					ct.execute("TIME");
					ct.execute("TRIG 6");
					if(!ct.getSystem().getRun().isActive()){
						updater.stop();
					}
				}
			}
		});
		
		btnFin8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ct.getSystem().isActive()){
					ct.execute("TIME");
					ct.execute("TRIG 8");
					if(!ct.getSystem().getRun().isActive()){
						updater.stop();
					}
				}
			}
		});
		
		btnPower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!ct.isEnabled())
				{
					ct.execute("ON");
					enableFields();
					if(ct.isEnabled() && !ct.getSystem().isActive()){
						taSysevent.setText("Choose Race or press New Run to start IND run");
					}
				}
				else{
					ct.execute("RESET");
					ct.execute("OFF");
					resetFields();
				}
			}
		});
		
		btnCalc1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				if(enableKeys){
					runnerNum += "1";
				}				
			}
		});
		
		btnCalc2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(enableKeys){
					runnerNum += "2";
				}
			}
		});
		
		btnCalc3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(enableKeys){
					runnerNum += "3";
				}
			}
		});
		
		btnCalc4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(enableKeys){
					runnerNum += "4";
				}
			}
		});
		
		btnCalc5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(enableKeys){
					runnerNum += "5";
				}
			}
		});
		
		btnCalc6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(enableKeys){
					runnerNum += "6";
				}
			}
		});
		
		rbTog1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("TIME");
				ct.execute("TOG 1");
			}
		});
		
		btnCalc7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(enableKeys){
					runnerNum += "7";
				}
			}
		});
		
		btnCalc8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(enableKeys){
					runnerNum += "8";
				}
			}
		});
		
		btnCalc9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(enableKeys){
					runnerNum += "9";
				}
			}
		});
		
		btnCalcPound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(enableKeys && !runnerNum.equals("")){
					ct.execute("NUM " + runnerNum);
					enableKeys = false;
					taSysevent.setText("Runner " + runnerNum + " entered.");
					runnerNum = "";
				}
			}
		});
		
		btnCalc0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(enableKeys){
					runnerNum += "0";
				}
			}
		});
		
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(printerOn && ct.isEnabled()){
					printerOn = false;
					printerText = "";
					taPrinter.setText("");
					taPrinter.repaint();
					for(int i = 0; i < printerLog.length; ++i) printerLog[i] = null;
				}
				else if(ct.isEnabled()){
					printerOn = true;
					taPrinter.setText("Printing enabled");
					taPrinter.repaint();
				}
			}
		});
		
		rbTog3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("TIME");
				ct.execute("tog 3");
				
			}
		});
		
		rbTog5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("TIME");
				ct.execute("tog 5");
			}
		});
		
		rbChan1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rbChan1.isSelected()){
					ct.execute("CONN " + cmbxSensor.getSelectedItem().toString() + " 1");
				}
				else
					ct.execute("DISC 1");
			}
		});
		
		rbTog7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("TIME");
				ct.execute("tog 7");
			}
		});
		
		rbChan3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rbChan1.isSelected()){
					ct.execute("CONN " + cmbxSensor.getSelectedItem().toString() + " 3");
				}
				else
					ct.execute("DISC 3");
			}
		});
		
		btnStart1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("TIME");
				ct.execute("TRIG 1");
				if(ct.getSystem().isActive()){
					updater.start();
				}
			}
		});
		
		btnStart3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("TIME");
				ct.execute("TRIG 3");
				if(!updater.isRunning() && ct.getSystem().isActive())
				{
					updater.start();
				}
			}
		});
		
		rbChan5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rbChan1.isSelected()){
					ct.execute("CONN " + cmbxSensor.getSelectedItem().toString() + " 5");
				}
				else
					ct.execute("DISC 5");
			}
		});
		
		rbChan7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rbChan1.isSelected()){
					ct.execute("CONN " + cmbxSensor.getSelectedItem().toString() + " 7");
				}
				else
					ct.execute("DISC 7");
			}
		});
		
		btnStart5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("TIME");
				ct.execute("TRIG 5");
				if(!updater.isRunning() && ct.getSystem().isActive())
				{
					updater.start();
				}
			}
		});
		
		btnStart7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("TIME");
				ct.execute("TRIG 7");
				if(!updater.isRunning() && ct.getSystem().isActive())
				{
					updater.start();
				}
			}
		});
		
		btnNewrun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("NEWRUN");
				if(ct.getSystem().getRun()!=null){
					taSysevent.setText("New Run started for event: " + ct.getSystem().getEvent());
				}
			}
		});
		
		rbChan2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rbChan1.isSelected()){
					ct.execute("CONN " + cmbxSensor.getSelectedItem().toString() + " 2");
				}
				else
					ct.execute("DISC 2");
			}
		});
		
		btnEndrun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("ENDRUN");
				updater.stop();
				taDisplay.setText("Run Ended.\nChoose a Command.");
				taDisplay.repaint();
			}
		});
		
		btnDnf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("DNF");
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("CANCEL");
			}
		});
		
		btnInd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("EVENT IND");
			}
		});
		
		btnParind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("EVENT PARIND");
			}
		});
		
		btnGrp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("EVENT GRP");
			}
		});
		
		rbChan4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rbChan1.isSelected()){
					ct.execute("CONN " + cmbxSensor.getSelectedItem().toString() + " 4");
				}
				else
					ct.execute("DISC 4");
			}
		});
		
		btnPargrp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("EVENT PARGRP");
			}
		});
		
		btnSwap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("SWAP");
			}
		});
		
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("EXPORT");
			}
		});
		
		btnEnterNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enterRun = "USE NUMBER PAD TO ENTER RACER NUMBER.\nPRESS POUND TO CONFIRM";
				
				if(ct.getSystem().isActive()){
					taSysevent.setText(enterRun);
					enableKeys = true;
				}			
			}
		});
		
		rbChan6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rbChan1.isSelected()){
					ct.execute("CONN " + cmbxSensor.getSelectedItem().toString() + " 6");
				}
				else
					ct.execute("DISC 6");
			}
		});
		
		rbChan8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rbChan1.isSelected()){
					ct.execute("CONN " + cmbxSensor.getSelectedItem().toString() + " 8");
				}
				else
					ct.execute("DISC 8");
			}
		});
		
		btnPrintfun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ct.execute("PRINT");
			}
		});
	}
	
	
	
	/**
	 * Helper function to reset all fields.
	 **/
	private void resetFields(){
		taDisplay.setText("");
		rbChan1.setSelected(false);
		rbChan2.setSelected(false);
		rbChan3.setSelected(false);
		rbChan4.setSelected(false);
		rbChan5.setSelected(false);
		rbChan6.setSelected(false);
		rbChan7.setSelected(false);
		rbChan8.setSelected(false);
		rbTogEven2.setSelected(false);
		rbTogEven4.setSelected(false);
		rbTogEven6.setSelected(false);
		rbTogEven8.setSelected(false);
		rbTog1.setSelected(false);
		rbTog3.setSelected(false);
		rbTog5.setSelected(false);
		rbTog7.setSelected(false);
		taPrinter.setText("");
		printerOn = false;
		enableKeys = false;
		rbChan1.setEnabled(false);
		rbChan2.setEnabled(false);
		rbChan3.setEnabled(false);
		rbChan4.setEnabled(false);
		rbChan5.setEnabled(false);
		rbChan6.setEnabled(false);
		rbChan7.setEnabled(false);
		rbChan8.setEnabled(false);
		rbTogEven2.setEnabled(false);
		rbTogEven4.setEnabled(false);
		rbTogEven6.setEnabled(false);
		rbTogEven8.setEnabled(false);
		rbTog1.setEnabled(false);
		rbTog3.setEnabled(false);
		rbTog5.setEnabled(false);
		rbTog7.setEnabled(false);
		btnDnf.setEnabled(false);
		btnNewrun.setEnabled(false);
		btnCancel.setEnabled(false);
		btnEndrun.setEnabled(false);
		btnInd.setEnabled(false);
		btnParind.setEnabled(false);
		btnGrp.setEnabled(false);
		btnSwap.setEnabled(false);
		btnExport.setEnabled(false);
		btnEnterNumber.setEnabled(false);
		btnFin2.setEnabled(false);
		btnFin4.setEnabled(false);
		btnFin6.setEnabled(false);
		btnFin8.setEnabled(false);
		btnStart1.setEnabled(false);
		btnStart3.setEnabled(false);
		btnStart5.setEnabled(false);
		btnStart7.setEnabled(false);
		btnPrint.setEnabled(false);
		cmbxSensor.setSelectedIndex(0);
		cmbxSensor.setEnabled(false);
		btnPrintfun.setEnabled(false);
		updater.stop();
		btnPargrp.setEnabled(false);
		taSysevent.setText("");
	}
	
	/**
	 * Helper method to enable all fields.
	 */
	private void enableFields(){
		rbChan1.setEnabled(true);
		rbChan2.setEnabled(true);
		rbChan3.setEnabled(true);
		rbChan4.setEnabled(true);
		rbChan5.setEnabled(true);
		rbChan6.setEnabled(true);
		rbChan7.setEnabled(true);
		rbChan8.setEnabled(true);
		rbTogEven2.setEnabled(true);
		rbTogEven4.setEnabled(true);
		rbTogEven6.setEnabled(true);
		rbTogEven8.setEnabled(true);
		rbTog1.setEnabled(true);
		rbTog3.setEnabled(true);
		rbTog5.setEnabled(true);
		rbTog7.setEnabled(true);
		btnDnf.setEnabled(true);
		btnNewrun.setEnabled(true);
		btnCancel.setEnabled(true);
		btnEndrun.setEnabled(true);
		btnInd.setEnabled(true);
		btnParind.setEnabled(true);
		btnGrp.setEnabled(true);
		btnSwap.setEnabled(true);
		btnExport.setEnabled(true);
		btnEnterNumber.setEnabled(true);
		btnFin2.setEnabled(true);
		btnFin4.setEnabled(true);
		btnFin6.setEnabled(true);
		btnFin8.setEnabled(true);
		btnStart1.setEnabled(true);
		btnStart3.setEnabled(true);
		btnStart5.setEnabled(true);
		btnStart7.setEnabled(true);
		btnPrint.setEnabled(true);
		cmbxSensor.setEnabled(true);
		btnPrintfun.setEnabled(true);
		btnPargrp.setEnabled(true);
	}
	
	public void update(){
		taDisplay.setText(ct.displayUpdate());
		taDisplay.repaint();
	}
	
	public void printerUpdate(){
		if(printerOn){
			taPrinter.setText(ct.printerUpdate());
			taPrinter.repaint();
		}
	}
	public void printerUpdate(String toDisplay){
		if(printerOn){
			for(int i = 1; i < printerLog.length; ++i){
				if(printerLog[i] != null) printerLog[i-1] = printerLog[i];
			}
			printerLog[printerLog.length -1] = toDisplay;
			String toPrinter = "";
			for(String s : printerLog){
				if(s == null) toPrinter += "\n";
				else toPrinter += (s + "\n");
			}
			taPrinter.setText(toPrinter);
			taPrinter.repaint();
		}
	}
}
