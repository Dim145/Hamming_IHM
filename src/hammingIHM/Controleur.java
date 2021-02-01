package hammingIHM;

import hammingIHM.ihm.FrameP;
import hammingIHM.metier.Metier;

public class Controleur
{
    private final FrameP ihm;
    private final Metier metier;

    public Controleur()
    {
        this.ihm = new FrameP(this);
        this.metier = new Metier();
    }

    public static void main(String[] args)
    {
        new Controleur();
    }
}
