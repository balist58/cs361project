package cs361Project;

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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.plaf.basic.BasicArrowButton;

import org.omg.CORBA.TRANSACTION_MODE;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ChronoTimerGUI extends JFrame {

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
	private BasicArrowButton btnLeft;
	private BasicArrowButton btnRight;
	private BasicArrowButton btnUp;
	private JButton btnFunction;
	private BasicArrowButton btnDown;
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
	private JTextArea taDisplay;
	private JLabel lblTitle;
	private JPanel panelPrinter;
	private JButton btnNewButton;
	private JTextArea taPrinter;
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
	
	private String runnerNum="";

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
	 * Create the frame.
	 */
	public ChronoTimerGUI() {
		
		
		ChronoTimerControl ct = new ChronoTimerControl();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 837, 520);
		mainFrame = new JPanel();
		setContentPane(mainFrame);
		
		cmbxSensor = new JComboBox();
		cmbxSensor.setFont(new Font("Tahoma", Font.BOLD, 12));
		cmbxSensor.setMaximumRowCount(3);
		cmbxSensor.setModel(new DefaultComboBoxModel(new String[] {"Gate", "Eye", "Pad"}));
		
		lblTitle = new JLabel("CHRONOTIMER 1009");
		lblTitle.setFont(new Font("Tahoma", Font.ITALIC, 18));
		
		JPanel panelStart = new JPanel();
		
		panelFinish = new JPanel();
		
		panelTogEven = new JPanel();
		
		rbTogEven2 = new JRadioButton("");
		rbTogEven2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("tog 2");
			}
		});
		
		rbTogEven4 = new JRadioButton("");
		rbTogEven4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("tog 4");
			}
		});
		
		rbTogEven6 = new JRadioButton("");
		rbTogEven6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("tog 6");
			}
		});
		
		rbTogEven8 = new JRadioButton("");
		rbTogEven8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("tog 8");
			}
		});
		
		
		lblFinish = new JLabel("Finish");
		lblFinish.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		lblTogEven = new JLabel("Enable/Disable");
		lblTogEven.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		panelFinishbtn = new JPanel();
		
		btnFin2 = new JButton("");
		btnFin2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("TRIG 2");
				ct.execute("PRINT");
			}
		});
		
		btnFin4 = new JButton("");
		btnFin4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("TRIG 4");
			}
		});
		
		btnFin6 = new JButton("");
		btnFin6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("TRIG 6");
			}
		});
		
		btnFin8 = new JButton("");
		btnFin8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("TRIG 8");
			}
		});
		
		lbl2 = new JLabel("2");
		lbl2.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		lbl4 = new JLabel("4");
		lbl4.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		lbl6 = new JLabel("6");
		lbl6.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		lbl8 = new JLabel("8");
		lbl8.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		btnPower = new JButton("Power");
		
		taDisplay = new JTextArea();
		taDisplay.setWrapStyleWord(true);
		taDisplay.setLineWrap(true);
		taDisplay.setEditable(false);
		taDisplay.setBackground(Color.WHITE);
		taDisplay.setFont(new Font("Miriam Fixed", Font.BOLD, 13));
		
		
		btnPower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!ct.isEnabled())
				{
					ct.execute("ON");
					
					if(ct.isEnabled() && !ct.getSystem().isActive()){
						taDisplay.setText("USE RIGHT OR LEFT ARROWS TO DISPLAY OPTIONS\n\n Choose Race Type");
					}
				}
				else{
					ct.execute("RESET");
					ct.execute("OFF");
					resetFields();
				}
			}
		});
		btnPower.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		
		panelChannel = new JPanel();
		
		panelPrinter = new JPanel();
		
		panelCalculator = new JPanel();
		
		panelCalculator.setLayout(new GridLayout(4, 3, 0, 0));
		
		btnCalc1 = new JButton("1");
		btnCalc1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dispMessage = taDisplay.getText();
				String enterRun = "USE NUMBER PAD TO ENTER RACER NUMBER.\n\n PRESS POUND TO CONFIRM";
				
				if(dispMessage.equals(enterRun)){
					runnerNum += "1";
				}				
			}
		});
		btnCalc1.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalc1);
		
		btnCalc2 = new JButton("2");
		btnCalc2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dispMessage = taDisplay.getText();
				String enterRun = "USE NUMBER PAD TO ENTER RACER NUMBER.\n\n PRESS POUND TO CONFIRM";
				
				if(dispMessage.equals(enterRun)){
					runnerNum += "2";
				}
			}
		});
		btnCalc2.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalc2);
		
		btnCalc3 = new JButton("3");
		btnCalc3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dispMessage = taDisplay.getText();
				String enterRun = "USE NUMBER PAD TO ENTER RACER NUMBER.\n\n PRESS POUND TO CONFIRM";
				
				if(dispMessage.equals(enterRun)){
					runnerNum += "3";
				}
			}
		});
		btnCalc3.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalc3);
		
		btnCalc4 = new JButton("4");
		btnCalc4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dispMessage = taDisplay.getText();
				String enterRun = "USE NUMBER PAD TO ENTER RACER NUMBER.\n\n PRESS POUND TO CONFIRM";
				
				if(dispMessage.equals(enterRun)){
					runnerNum += "4";
				}
			}
		});
		btnCalc4.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalc4);
		
		btnCalc5 = new JButton("5");
		btnCalc5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dispMessage = taDisplay.getText();
				String enterRun = "USE NUMBER PAD TO ENTER RACER NUMBER.\n\n PRESS POUND TO CONFIRM";
				
				if(dispMessage.equals(enterRun)){
					runnerNum += "5";
				}
			}
		});
		btnCalc5.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalc5);
		
		btnCalc6 = new JButton("6");
		btnCalc6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dispMessage = taDisplay.getText();
				String enterRun = "USE NUMBER PAD TO ENTER RACER NUMBER.\n\n PRESS POUND TO CONFIRM";
				
				if(dispMessage.equals(enterRun)){
					runnerNum += "6";
				}
			}
		});
		btnCalc6.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalc6);
		
		btnCalc7 = new JButton("7");
		btnCalc7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dispMessage = taDisplay.getText();
				String enterRun = "USE NUMBER PAD TO ENTER RACER NUMBER.\n\n PRESS POUND TO CONFIRM";
				
				if(dispMessage.equals(enterRun)){
					runnerNum += "7";
				}
			}
		});
		btnCalc7.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalc7);
		
		btnCalc8 = new JButton("8");
		btnCalc8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dispMessage = taDisplay.getText();
				String enterRun = "USE NUMBER PAD TO ENTER RACER NUMBER.\n\n PRESS POUND TO CONFIRM";
				
				if(dispMessage.equals(enterRun)){
					runnerNum += "8";
				}
			}
		});
		btnCalc8.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalc8);
		
		btnCalc9 = new JButton("9");
		btnCalc9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dispMessage = taDisplay.getText();
				String enterRun = "USE NUMBER PAD TO ENTER RACER NUMBER.\n\n PRESS POUND TO CONFIRM";
				
				if(dispMessage.equals(enterRun)){
					runnerNum += "9";
				}
			}
		});
		btnCalc9.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalc9);
		
		btnCalcStar = new JButton("*");
		btnCalcStar.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalcStar);
		
		btnCalc0 = new JButton("0");
		btnCalc0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dispMessage = taDisplay.getText();
				String enterRun = "USE NUMBER PAD TO ENTER RACER NUMBER.\n\n PRESS POUND TO CONFIRM";
				
				if(dispMessage.equals(enterRun)){
					runnerNum += "0";
				}
			}
		});
		btnCalc0.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalc0);
		
		btnCalcPound = new JButton("#");
		btnCalcPound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dispMessage = taDisplay.getText();
				String enterRun = "USE NUMBER PAD TO ENTER RACER NUMBER.\n\n PRESS POUND TO CONFIRM";
				
				if(dispMessage.equals(enterRun) && !runnerNum.equals("")){
					ct.execute("NUM " + runnerNum);
					taDisplay.setText("Runner number " + runnerNum + " entered!");
					taDisplay.setText("USE RIGHT OR LEFT ARROWS TO DISPLAY OPTIONS\n\n Choose Race Type");
				}
			}
		});
		btnCalcPound.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCalculator.add(btnCalcPound);
		
		btnNewButton = new JButton("Printer Pwr");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		rbChan1 = new JRadioButton("");
		rbChan1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rbChan1.isSelected()){
					ct.execute("CONN 1 " + cmbxSensor.getSelectedItem().toString());
				}
				else
					ct.execute("DISC 1");
			}
		});
		
		rbChan3 = new JRadioButton("");
		rbChan3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rbChan1.isSelected()){
					ct.execute("CONN 3 " + cmbxSensor.getSelectedItem().toString());
				}
				else
					ct.execute("DISC 3");
			}
		});
		
		rbChan5 = new JRadioButton("");
		rbChan5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rbChan1.isSelected()){
					ct.execute("CONN 5 " + cmbxSensor.getSelectedItem().toString());
				}
				else
					ct.execute("DISC 5");
			}
		});
		
		rbChan7 = new JRadioButton("");
		rbChan7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rbChan1.isSelected()){
					ct.execute("CONN 7 " + cmbxSensor.getSelectedItem().toString());
				}
				else
					ct.execute("DISC 7");
			}
		});
		
		rbChan2 = new JRadioButton("");
		rbChan2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rbChan1.isSelected()){
					ct.execute("CONN 2 " + cmbxSensor.getSelectedItem().toString());
				}
				else
					ct.execute("DISC 2");
			}
		});
		
		rbChan4 = new JRadioButton("");
		rbChan4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rbChan1.isSelected()){
					ct.execute("CONN 4 " + cmbxSensor.getSelectedItem().toString());
				}
				else
					ct.execute("DISC 4");
			}
		});
		
		rbChan6 = new JRadioButton("");
		rbChan6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rbChan1.isSelected()){
					ct.execute("CONN 6 " + cmbxSensor.getSelectedItem().toString());
				}
				else
					ct.execute("DISC 6");
			}
		});
		
		rbChan8 = new JRadioButton("");
		rbChan8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rbChan1.isSelected()){
					ct.execute("CONN 8 " + cmbxSensor.getSelectedItem().toString());
				}
				else
					ct.execute("DISC 8");
			}
		});
		
		
		
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
		rbTog1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("TOG 1");
			}
		});
		
		rbTog3 = new JRadioButton("");
		rbTog3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("tog 3");
				
			}
		});
		
		rbTog5 = new JRadioButton("");
		rbTog5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("tog 5");
			}
		});
		
		rbTog7 = new JRadioButton("");
		rbTog7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("tog 7");
			}
		});
		
		btnStart1 = new JButton("");
		btnStart1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("TRIG 1");
			}
		});
		
		btnStart3 = new JButton("");
		btnStart3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("TRIG 3");
			}
		});
		
		btnStart5 = new JButton("");
		btnStart5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("TRIG 5");
			}
		});
		
		btnStart7 = new JButton("");
		btnStart7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ct.execute("TRIG 7");
			}
		});
		
		taPrinter = new JTextArea();
		taPrinter.setEditable(false);
		
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
		
		lblSensor = new JLabel("Sensor to Connect");
		lblSensor.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		btnFunction = new JButton("FUNCTION");
		btnFunction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dispMessage = taDisplay.getText();
				String newMessage1 = "USE RIGHT OR LEFT ARROWS TO DISPLAY OPTIONS\n\n Choose Race Type";
				String newMessage2 = "USE RIGHT OR LEFT ARROWS TO DISPLAY OPTIONS\n\n Enter Runner";
				String chooseRace1 = "USE UP OR DOWN ARROWS TO CHOOSE RACE\n\n IND";
				String chooseRace2 = "USE UP OR DOWN ARROWS TO CHOOSE RACE\n\n PARIND";
				String chooseRace3 = "USE UP OR DOWN ARROWS TO CHOOSE RACE\n\n GRP";
				String enterRun = "USE NUMBER PAD TO ENTER RACER NUMBER.\n\n PRESS POUND TO CONFIRM";

				
				
				//Choose Race
				if(dispMessage.equals(newMessage1)){
					taDisplay.setText(chooseRace1);
				}
				
				//Enter Runner
				else if(dispMessage.equals(newMessage2)){
					taDisplay.setText(enterRun);
				}	
				
				else if(dispMessage.equals(chooseRace1)){
					ct.execute("EVENT IND");
					ct.execute("NEWRUN");
					taDisplay.setText(newMessage1);
				}
				else if(dispMessage.equals(chooseRace2)){
					ct.execute("EVENT PARIND");
					ct.execute("NEWRUN");
					taDisplay.setText(newMessage1);
				}
				else if(dispMessage.equals(chooseRace3)){
					ct.execute("EVENT GRP");
					ct.execute("NEWRUN");
					taDisplay.setText(newMessage1);
				}
			}
		});
		btnFunction.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		btnLeft = new BasicArrowButton(BasicArrowButton.WEST);
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dispMessage = taDisplay.getText();
				String newMessage1 = "USE RIGHT OR LEFT ARROWS TO DISPLAY OPTIONS\n\n Choose Race Type";
				String newMessage2 = "USE RIGHT OR LEFT ARROWS TO DISPLAY OPTIONS\n\n Enter Runner";
				
				if(dispMessage.equals(newMessage1)){
					taDisplay.setText(newMessage2);
				}
				else if(dispMessage.equals(newMessage2)){
					taDisplay.setText(newMessage1);
				}
			}
		});
		
		btnRight = new BasicArrowButton(BasicArrowButton.EAST);
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dispMessage = taDisplay.getText();
				String newMessage1 = "USE RIGHT OR LEFT ARROWS TO DISPLAY OPTIONS\n\n Choose Race Type";
				String newMessage2 = "USE RIGHT OR LEFT ARROWS TO DISPLAY OPTIONS\n\n Enter Runner";
				
				if(dispMessage.equals(newMessage1)){
					taDisplay.setText(newMessage2);
				}
				else if(dispMessage.equals(newMessage2)){
					taDisplay.setText(newMessage1);
				}
	
			}
		});
		
		btnUp = new BasicArrowButton(BasicArrowButton.NORTH);
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dispMessage = taDisplay.getText();
				String chooseRace1 = "USE UP OR DOWN ARROWS TO CHOOSE RACE\n\n IND";
				String chooseRace2 = "USE UP OR DOWN ARROWS TO CHOOSE RACE\n\n PARIND";
				String chooseRace3 = "USE UP OR DOWN ARROWS TO CHOOSE RACE\n\n GRP";
				
				//choose race type
				if(dispMessage.equals(chooseRace1)){
					taDisplay.setText(chooseRace2);
				}
				else if(dispMessage.equals(chooseRace2)){
					taDisplay.setText(chooseRace3);
				}
				else if(dispMessage.equals(chooseRace3)){
					taDisplay.setText(chooseRace1);
				}
			}
		});
		
		btnDown = new BasicArrowButton(BasicArrowButton.SOUTH);
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dispMessage = taDisplay.getText();
				String chooseRace1 = "USE UP OR DOWN ARROWS TO CHOOSE RACE\n\n IND";
				String chooseRace2 = "USE UP OR DOWN ARROWS TO CHOOSE RACE\n\n PARIND";
				String chooseRace3 = "USE UP OR DOWN ARROWS TO CHOOSE RACE\n\n GRP";
				
				//choose race type
				if(dispMessage.equals(chooseRace1)){
					taDisplay.setText(chooseRace3);
				}
				else if(dispMessage.equals(chooseRace2)){
					taDisplay.setText(chooseRace1);
				}
				else if(dispMessage.equals(chooseRace3)){
					taDisplay.setText(chooseRace2);
				}
			}
		});

		GroupLayout gl_mainFrame = new GroupLayout(mainFrame);
		gl_mainFrame.setHorizontalGroup(
			gl_mainFrame.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainFrame.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_mainFrame.createParallelGroup(Alignment.LEADING)
						.addComponent(btnPower, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
						.addComponent(panelChannel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cmbxSensor, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSensor)
						.addGroup(gl_mainFrame.createSequentialGroup()
							.addComponent(btnLeft, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addGap(61)
							.addComponent(btnRight, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_mainFrame.createParallelGroup(Alignment.TRAILING)
							.addComponent(btnUp, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnFunction)
							.addComponent(btnDown, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_mainFrame.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_mainFrame.createSequentialGroup()
							.addGap(58)
							.addGroup(gl_mainFrame.createParallelGroup(Alignment.LEADING)
								.addComponent(panelFinish, GroupLayout.PREFERRED_SIZE, 339, GroupLayout.PREFERRED_SIZE)
								.addComponent(panelStart, GroupLayout.PREFERRED_SIZE, 339, GroupLayout.PREFERRED_SIZE)
								.addComponent(taDisplay, GroupLayout.PREFERRED_SIZE, 340, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_mainFrame.createSequentialGroup()
							.addGap(139)
							.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_mainFrame.createParallelGroup(Alignment.LEADING)
						.addComponent(panelPrinter, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
						.addComponent(panelCalculator, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		gl_mainFrame.setVerticalGroup(
			gl_mainFrame.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_mainFrame.createSequentialGroup()
					.addContainerGap(31, Short.MAX_VALUE)
					.addGroup(gl_mainFrame.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_mainFrame.createSequentialGroup()
							.addComponent(btnPower, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_mainFrame.createSequentialGroup()
							.addComponent(lblTitle)
							.addGap(18)))
					.addGroup(gl_mainFrame.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_mainFrame.createSequentialGroup()
							.addComponent(panelStart, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(panelFinish, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_mainFrame.createSequentialGroup()
							.addGap(21)
							.addComponent(btnFunction)
							.addGap(18)
							.addComponent(btnUp, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_mainFrame.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_mainFrame.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnDown, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED))
								.addGroup(Alignment.LEADING, gl_mainFrame.createSequentialGroup()
									.addGap(7)
									.addGroup(gl_mainFrame.createParallelGroup(Alignment.LEADING)
										.addComponent(btnLeft, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnRight, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED, 45, Short.MAX_VALUE)))
							.addComponent(lblSensor)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmbxSensor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_mainFrame.createParallelGroup(Alignment.LEADING)
						.addComponent(taDisplay, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
						.addComponent(panelChannel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
				.addGroup(gl_mainFrame.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelPrinter, GroupLayout.PREFERRED_SIZE, 204, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panelCalculator, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(28, Short.MAX_VALUE))
		);
		
		

		GroupLayout gl_panelPrinter = new GroupLayout(panelPrinter);
		gl_panelPrinter.setHorizontalGroup(
			gl_panelPrinter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelPrinter.createSequentialGroup()
					.addGroup(gl_panelPrinter.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelPrinter.createSequentialGroup()
							.addGap(53)
							.addComponent(btnNewButton))
						.addGroup(gl_panelPrinter.createSequentialGroup()
							.addGap(36)
							.addComponent(taPrinter, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(126, Short.MAX_VALUE))
		);
		gl_panelPrinter.setVerticalGroup(
			gl_panelPrinter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelPrinter.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(taPrinter, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(33, Short.MAX_VALUE))
		);
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
		
	}
	
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
		
	}
}