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
		
		JPanel pnlBas =	new JPanel();
		pnlBas.add(lblResultat);
		pnlBas.setBackground(Color.BLACK);

		this.add(lblDetails, BorderLayout.CENTER);
		this.add(pnlBas, BorderLayout.SOUTH);

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
		this.lblDetails.setVisible(false);
		this.lblResultat.setForeground(Color.WHITE);
		this.lblResultat.setText("Le code a émettre serais: " + codeCorriger);
	}

	public void setLblDetails( String details )
	{
		if( details == null || details.isEmpty() )
		{
			this.lblDetails.setVisible(false);
			return;
		}
		else
		{
			this.lblDetails.setVisible(true);
		}

		StringBuilder html = new StringBuilder("<html><body>");

		for (String s : details.split("\n") )
		{
			Object[] res = PanelMilieu.aUneCouleur(s);

			String color = (String) res[0];

			if( color == null )
			{
				int posb = s.indexOf("|b|");
				int posr = s.indexOf("|r|");
				int posg = s.indexOf("|g|");

				int pos = posb > -1 ? posb : posr > -1 ? posr : posg;

				if( pos > -1 )
					s = s.substring(0, pos) + "<i style=\"color: " + (posb > -1 ? "blue" : posr > -1 ? "red" : "green") + ";\">" + s.substring(pos+3) + "</i>";

			}
			else
			{
				if( ((Integer) res[1]) == 0 ) s = s.substring(3);
				if( ((Integer) res[1]) == 1 ) s = s.substring(0, s.length()-3);
			}

			html.append("<p style=\"color: ").append(color).append(";\">").append(s).append("</p>");
		}

		html.append("</body></html>");

		this.lblDetails.setText(html.toString());

		this.lblDetails.setVisible(true);
	}

	private static Object[] aUneCouleur(String s)
	{
		if ( s.startsWith("|r|") ) return new Object[]{"red"  , 0};
		if ( s.startsWith("|g|") ) return new Object[]{"green", 0};
		if ( s.startsWith("|b|") ) return new Object[]{"blue" , 0};

		if ( s.endsWith("|r|") ) return new Object[]{"red"  , 1};
		if ( s.endsWith("|g|") ) return new Object[]{"green", 1};
		if ( s.endsWith("|b|") ) return new Object[]{"blue" , 1};

		return new Object[]{null, null};
	}
}
