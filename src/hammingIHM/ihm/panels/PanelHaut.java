package hammingIHM.ihm.panels;

import hammingIHM.ihm.FrameP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The type Panel haut.
 */
public class PanelHaut extends JPanel
{
	private FrameP       frame;
	private JTextField   txtfBinaire;
	private JButton      btnValider;
	private JPanel       pnlGauche;
	private JCheckBox    chkbxChoix;
	private JRadioButton calcul;
	private JRadioButton verification;

	/**
	 * Instantiates a new Panel haut.
	 *
	 * @param frame the frame
	 */
	public PanelHaut(FrameP frame)
	{
		this.setLayout(new BorderLayout());
		this.frame     = frame;

		this.pnlGauche = new JPanel(new GridLayout(2,1));

		this.txtfBinaire = new JTextField()
		{
			@Override
			public void paste()
			{

			}
		};
		this.txtfBinaire.setColumns(25);

		this.calcul       = new JRadioButton("calcul");
		this.verification = new JRadioButton("verif");

		ButtonGroup group = new ButtonGroup();
		group.add(this.calcul);
		group.add(this.verification);

		this.calcul.setSelected(true);

		this.txtfBinaire.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				char key = e.getKeyChar();

				if (key == KeyEvent.VK_ENTER)
				{
					PanelHaut.this.executerCode();

					e.consume();
					return;
				}

				if (key != '0' && key != '1')
				{
					try
					{
						if (key != KeyEvent.VK_BACK_SPACE)
							Toolkit.getDefaultToolkit().beep();
					} catch (Exception exception)
					{
						exception.printStackTrace();
					}

					e.consume();
				}
			}
		});

		this.btnValider = new JButton("GO");
		this.btnValider.addActionListener(e -> this.executerCode());

		this.chkbxChoix = new JCheckBox("Détail");



		this.pnlGauche.add(this.txtfBinaire);



		JPanel pnlRadioBtn = new JPanel();
		pnlRadioBtn.setLayout(new BoxLayout(pnlRadioBtn, BoxLayout.X_AXIS));

		pnlRadioBtn.add(this.calcul);
		pnlRadioBtn.add(this.verification);

		JPanel pnlBasGauche  = new JPanel(new BorderLayout());
		pnlBasGauche.add(this.chkbxChoix, BorderLayout.WEST);
		pnlBasGauche.add(pnlRadioBtn, BorderLayout.EAST);

		pnlGauche.add(pnlBasGauche);

		this.add(this.pnlGauche, BorderLayout.CENTER);
		this.add(this.btnValider, BorderLayout.EAST);
	}

    private void executerCode()
    {
        if( this.calcul.isSelected() )
        {
            String codeCorriger = this.frame.calculeCodeEmission(this.txtfBinaire.getText());

            this.frame.setResultat(codeCorriger, Integer.MIN_VALUE);
        }
        else if( this.verification.isSelected() )
        {
            int erreur = this.frame.isCodeCorrect(this.txtfBinaire.getText(), this.chkbxChoix.isSelected());

            this.frame.setResultat(this.retourStringBinaire(), erreur);
        }
    }

	/**
	 * Retour string binaire string.
	 *
	 * @return the string
	 */
	public String retourStringBinaire()
	{
		return txtfBinaire.getText();
	}
}
