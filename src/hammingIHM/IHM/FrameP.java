package hammingIHM.IHM;

import hammingIHM.Controleur;

import javax.swing.*;

public class FrameP extends JFrame
{
    private final Controleur ctrl;

    public FrameP(Controleur ctrl )
    {
        super("Hamming");

        this.ctrl = ctrl;

        // Code de fin du constructeur
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
