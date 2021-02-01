package hammingIHM.metier;

import java.util.Arrays;

public class Metier
{
	public Metier()
	{

	}

	public String correctionPreEmission(String code)
	{
		String sRet;

		int nbBitsDebug = 0;
		for (int i = 0; Math.pow(2, i) <= code.length(); i++)
			nbBitsDebug++;
		
		//System.out.println(nbBitsDebug);
		
		//On allonge la chaine pour qu'elle fasse la taille de 2^nbBitsDebug-1-nbBitsDebug
		if(code.length() != Math.pow(2, nbBitsDebug)- 1 - nbBitsDebug)
			code = String.format("%" + (int)(Math.pow(2, nbBitsDebug) - 1 - nbBitsDebug) + "s", code);
		
		StringBuffer code2 = new StringBuffer(code);
		
		//on insere aux emplacement voulu des bits de hamming a la valeur _ car n'etant pas set
		for (int i = 0; i < nbBitsDebug; i++) {
			int bitHamming = code2.length() - (int)Math.pow(2, i) + 1;
			code2.insert(bitHamming, '_');
		}
		
		
		//inversion du sens de la chaine pour etre naturel, on la reinversera a la fin
		StringBuilder str = new StringBuilder(code2);
		code2 = new StringBuffer(str.reverse().toString());
		
		//System.out.println(code2);
		
		sRet = nbBitsDebug +":";
		
		
		//stockera chaque chaine lié a chaque bit de correction
		String[] tabBits = new String[nbBitsDebug];
		
		
		//Creations des chaines de bits, qui contiendront les bit determinant les bit correcteurs
		//par exemple 7 5 3, 7 6 3, 7 6 5, ...
		for (int i = 0; i < tabBits.length; i++)
		{
			String  bits = "";
			int     j = i;
			int     nbBitsAPrendre = (int)Math.pow(2, i);
			int     nbBitsPris     = 0;
			boolean toutPris       = false;
			
			//lectures des bits pour determiner les bit correcteurs via la methode Grey
			for(int k=(int)(Math.pow(2, 0)-1); k<code2.length() && k< code2.length() - Math.pow(2, i); k++)
			{
				//System.out.println("\nk : " + k + "   " + nbBitsPris + "/" + nbBitsAPrendre + "  " + toutPris);
				if(toutPris == true && nbBitsPris == 0)
					toutPris = false;
				else
					if(toutPris == true && nbBitsPris > 0)
					{
						nbBitsPris--;
						if(nbBitsPris == 0)
							toutPris = false;
					}
				else
					if(nbBitsPris < nbBitsAPrendre)
					{
						//System.out.print(code2.length()-1-k+1+" ");
						bits += code2.charAt(code2.length()-1-k)+"";
						nbBitsPris++;
						if(nbBitsPris == nbBitsAPrendre)
							toutPris = true;
					}
					
			}
			tabBits[i] = bits;
			//System.out.println("\n" + tabBits[i] + "\n");
		}
		
		//on lit les chaines et on addition chaque bit
		int[] tabVal = new int[nbBitsDebug];
		for (int i = tabBits.length-1; i >= 0; i--)
		{
			int val = 0;
			for (int j = 0; j < tabBits[i].length(); j++)
			{
				if(val == 0 && tabBits[i].charAt(j) == '1')
					val = 1;
				else
					if(val == 1 && tabBits[i].charAt(j) == '1')
						val = 0;
			}
			tabVal[i] = val;
			//System.out.println(val);
		}
		
		//System.out.println(code2);
		str = new StringBuilder(code2);
		
		//System.out.println(Arrays.toString(tabVal));
		
		
		//placement des bits dans la chaine de retour
		for (int i = 0; i < tabVal.length ; i++)
		{
			char ch = Character.forDigit(tabVal[i], 10);
			//System.out.println(ch);
			for (int j = 0; j < str.length(); j++)
			{
				if(str.charAt(j) == '_')
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

	public boolean isCodeCorrect(String code, boolean presenterEtapes)
	{
		int nbBitsDebug = 0;

		for (int i = 0; i < code.length(); i++)
			if (Math.pow(2, i) > code.length())
			{
				nbBitsDebug = i;
				break;
			}

		for (int i = 0; i < nbBitsDebug; i++)
			if (code.charAt((int) Math.pow(2, i)) != '0')
				return false;

		return true;
	}
	
	public static void main(String[] args)
	{
		Metier m = new Metier();
		System.out.println(m.correctionPreEmission("1010"));
		System.out.println(m.correctionPreEmission("1011"));
		System.out.println(m.correctionPreEmission("10110111010"));
	}
}
