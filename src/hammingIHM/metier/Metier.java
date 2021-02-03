package hammingIHM.metier;

import hammingIHM.Controleur;

public class Metier
{
    private final Controleur ctrl;

    public Metier(Controleur controleur)
    {
        this.ctrl = controleur;
    }

    public String correctionPreEmission(String code)
    {
        String sRet;

        int nbBitsDebug = 0;
        for (int i = 0; Math.pow(2, i) <= code.length(); i++)
            nbBitsDebug++;

        //System.out.println(nbBitsDebug);

        //On allonge la chaine pour qu'elle fasse la taille de 2^nbBitsDebug-1-nbBitsDebug et on enleve les espaces
        if (code.length() != Math.pow(2, nbBitsDebug) - 1 - nbBitsDebug)
            code = String.format("%" + (int) (Math.pow(2, nbBitsDebug) - 1 - nbBitsDebug) + "s", code).replace(" ", "");

        StringBuffer code2 = new StringBuffer(code);

        //on insere aux emplacement voulu des bits de hamming a la valeur _ car n'etant pas set
        for (int i = 0; i < nbBitsDebug; i++)
        {
            int bitHamming = code2.length() - (int) Math.pow(2, i) + 1;
            code2.insert(bitHamming, '_');
        }


        //inversion du sens de la chaine pour etre naturel, on la reinversera a la fin
        StringBuilder str = new StringBuilder(code2);
        code2 = new StringBuffer(str.reverse().toString());

        //System.out.println(code2);

        sRet = nbBitsDebug + ":";


        //stockera chaque chaine lié a chaque bit de correction
        String[] tabBits = new String[nbBitsDebug];


        //Creations des chaines de bits, qui contiendront les bit determinant les bit correcteurs
        //par exemple 7 5 3, 7 6 3, 7 6 5, ...
        for (int i = 0; i < tabBits.length; i++)
        {
            StringBuilder bits = new StringBuilder();
            int nbBitsAPrendre = (int) Math.pow(2, i);
            int nbBitsPris = 0;
            boolean toutPris = false;

            //lectures des bits pour determiner les bit correcteurs via la methode Grey
            for (int k = (int) (Math.pow(2, 0) - 1); k < code2.length() && k < code2.length() - Math.pow(2, i); k++)
            {
                //System.out.println("\nk : " + k + "   " + nbBitsPris + "/" + nbBitsAPrendre + "  " + toutPris);
                if (toutPris && nbBitsPris == 0) toutPris = false;
                else if (toutPris && nbBitsPris > 0)
                {
                    nbBitsPris--;
                    if (nbBitsPris == 0) toutPris = false;
                }
                else if (nbBitsPris < nbBitsAPrendre)
                {
                    //System.out.print(code2.length()-1-k+1+" ");
                    bits.append(code2.charAt(code2.length() - 1 - k));
                    nbBitsPris++;
                    if (nbBitsPris == nbBitsAPrendre) toutPris = true;
                }

            }
            tabBits[i] = bits.toString();
            //System.out.println("\n" + tabBits[i] + "\n");
        }

        //on lit les chaines et on addition chaque bit
        int[] tabVal = new int[nbBitsDebug];
        for (int i = tabBits.length - 1; i >= 0; i--)
        {
            int val = 0;
            for (int j = 0; j < tabBits[i].length(); j++)
            {
                if (val == 0 && tabBits[i].charAt(j) == '1') val = 1;
                else if (val == 1 && tabBits[i].charAt(j) == '1') val = 0;
            }
            tabVal[i] = val;
            //System.out.println(val);
        }

        //System.out.println(code2);
        str = new StringBuilder(code2);

        //System.out.println(Arrays.toString(tabVal));


        //placement des bits dans la chaine de retour
        for (int k : tabVal)
        {
            char ch = Character.forDigit(k, 10);
            //System.out.println(ch);
            for (int j = 0; j < str.length(); j++)
            {
                if (str.charAt(j) == '_')
                {
                    str.setCharAt(j, ch);
                    //System.out.println("C'est un " + str.charAt(i) + "au lieu d'un " + ch);
                    break;
                }
            }
        }

        //on reinverse pour que le bit 1 soit a droite
        code2 = new StringBuffer(str.reverse().toString());
        //System.out.println(code2);

        sRet += code2.toString();

        return sRet;
    }

    public int isCodeCorrect(String code, boolean presenterEtapes)
    {
        int indexOf2ptPoint = code.indexOf(":");
        if( indexOf2ptPoint > -1 )
            code = code.substring(indexOf2ptPoint+1);

        StringBuilder details = new StringBuilder();

        int nbBitsDebug = 0;

        details.append("Recherche du nombres de bits de controle...\n");

        for (int i = 0; Math.pow(2, i) < code.length(); i++)
            nbBitsDebug++;

        details.append(nbBitsDebug).append(" bits de controle trouvés.\n");

        String bits = String.format("%" + nbBitsDebug + "s", "").replaceAll(" ", "0");
        // 0000 par exemple

        boolean[] tabBitsCorrecteur = new boolean[nbBitsDebug]; // true = 0, false = 1
        for (int i = 0; i < nbBitsDebug; i++)
            tabBitsCorrecteur[i] = true;

        StringBuilder tmp = new StringBuilder(bits);
        details.append("Debut de calcule pour chaque bits:\n");
        for (int i = 0; i < nbBitsDebug; i++)
        {
            boolean lastValueIsTested = false;

            details.append("pour le bis de controle n°").append(i+1).append(", les cases a vérifier sont: |b|");
            do
            {
                tmp.replace(nbBitsDebug - 1 - i, nbBitsDebug - i, "1");
                int pos = Integer.parseInt(tmp.toString(), 2);


                if( pos > code.length() ) break; // le code s'arrete avant d'avoir finit le tour.
                // 3 bits de debug = 111 au max. = 7 qui peut etre hors d'une chaine de 10111 (taille 5).
                // Le code n'est pas faux, mais doit s'arreter plus

                details.append(String.format("%02d", pos)).append(" ");

                if (!tmp.toString().contains("0") ) lastValueIsTested = true;
                if (code.charAt(code.length() - pos) != '0') tabBitsCorrecteur[i] = !tabBitsCorrecteur[i];

                String s = tmp.toString();
                tmp.delete(0, tmp.length());
                tmp.append(Metier.valBitsSuivant(s));
            } while (tmp.toString().contains("0") || !lastValueIsTested);

            details.append("\n");

            tmp.delete(0, tmp.length());
            tmp.append(bits);

            if (!tabBitsCorrecteur[i])
            {
                details.append("|r|Une erreurs a été trouvée, fin du script");
                tmp.replace(nbBitsDebug - 1 - i, nbBitsDebug - i, "1");

                try
                {
                    this.ctrl.setLblDetails(presenterEtapes ? details.toString() : null);
                }
                catch ( NullPointerException ignored )
                {
                    // pour eviter une erreur lors ce qu'on lance le main du Metier pour debug
                }

                return Integer.parseInt(tmp.toString(), 2);
            }
        }

        details.append("|g|Pas d'erreurs trouvées, fin du script");

        try
        {
            this.ctrl.setLblDetails(presenterEtapes ? details.toString() : null);
        }
        catch ( NullPointerException ignored )
        {
            // pour eviter une erreur lors ce qu'on lance le main du Metier pour debug
        }

        /*for (boolean bit : tabBitsCorrecteur)
            if( !bit ) return false;
        // pas opti
        */

        return -1;
    }

    private static String valBitsSuivant(String toString)
    {
        if (!toString.contains("0")) return toString;

        StringBuilder builder = new StringBuilder(toString);

        boolean retenue;
        int cpt = toString.length() - 1;


        retenue = builder.charAt(cpt) == '1';
        builder.replace(cpt, cpt-- + 1, retenue ? "0" : "1");

        while (retenue)
        {
            if (builder.charAt(cpt) == '0')
            {
                builder.replace(cpt, cpt-- + 1, "1");
                retenue = false;
            }
            else
            {
                builder.replace(cpt, cpt-- + 1, "0");
            }
        }

        return builder.toString();
    }

    public static void main(String[] args)
    {
        Metier m = new Metier(null);
        System.out.println(m.correctionPreEmission("1010"));
        System.out.println(m.correctionPreEmission("1011"));
        System.out.println(m.correctionPreEmission("10110111010"));

        System.out.println(m.isCodeCorrect("1010010", false)); // doit etre vrai
        System.out.println(m.isCodeCorrect("1101101", false)); // doit etre faux
        System.out.println(m.isCodeCorrect("101101111011011", false)); // doit etre vrai
        System.out.println(m.isCodeCorrect(m.correctionPreEmission("1010"), false)); // doit etre vrai
        System.out.println(m.isCodeCorrect(m.correctionPreEmission("1011"), false)); // doit etre faux
        System.out.println(m.isCodeCorrect(m.correctionPreEmission("10110111010"), false)); // doit etre vrai
    }
}
