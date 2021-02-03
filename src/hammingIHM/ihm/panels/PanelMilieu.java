package hammingIHM.ihm.panels;

import java.awt.*;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import hammingIHM.ihm.FrameP;

public class PanelMilieu extends JPanel
{
	private JCheckBox chkbxChoix;
	private JLabel    lblResultat;
	private JLabel    lblDetails;
	private FrameP    frame;

	public PanelMilieu(FrameP frame)
	{
		this.setLayout(new BorderLayout());

		this.frame       = frame;
		this.lblResultat = new JLabel();
		this.lblDetails  = new JLabel();

		this.add(lblDetails, BorderLayout.CENTER);
		this.add(lblResultat, BorderLayout.SOUTH);

		this.lblDetails.setVisible(false);
		this.lblResultat.setHorizontalAlignment(JLabel.CENTER);
	}

	public void setResulultatVerification( String code, int posErreur )
	{
		String res = "le code \"" + code + "\" ";

		if( posErreur == -1 )
			res += "ne contient pas d'erreurs";
		else
			res += "contient une erreur a la position " + posErreur;

		this.lblResultat.setForeground( posErreur == -1 ? Color.GREEN : Color.RED);
		this.lblResultat.setText(res);
	}

	public void setLblResultatCorriger( String codeCorriger )
	{
		this.lblResultat.setForeground(Color.BLACK);
		this.lblResultat.setText("Le code a émettre serais: " + codeCorriger);
	}

	public void setLblDetails( String details )
	{
		if( details == null || details.isEmpty() )
		{
			this.lblDetails.setVisible(false);
			return;
		}

		this.lblDetails.setText(details);
		this.lblDetails.setVisible(true);
	}
}
