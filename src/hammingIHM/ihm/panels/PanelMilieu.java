package hammingIHM.ihm.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import hammingIHM.ihm.FrameP;

public class PanelMilieu extends JPanel
{
	private JLabel    lblResultat;
	private JLabel    lblDetails;
	private FrameP    frame;

	public PanelMilieu(FrameP frame)
	{
		this.setLayout(new BorderLayout());
		
		JPanel pnlCheck  = new JPanel(new GridLayout(2,1));
		
		this.frame       = frame;
		this.lblResultat = new JLabel();
		this.lblDetails  = new JLabel();
		
		pnlCheck.add(lblResultat);
		this.add(pnlCheck, BorderLayout.NORTH);
		this.add(lblResultat, BorderLayout.CENTER);
		
	}
	
	
}
