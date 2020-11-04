package com.johnzaro.geographyquiz.core.helperClasses;

import java.util.Arrays;

/**
 * Created by John on 3/1/2017.
 */
public class GreekLanguageHelper
{
	private static final String[] masculineArticleWithN = {"Ο", "Του", "Τον", "Οι", "Των", "Τους"};
	private static final String[] masculineArticleWithoutN = {"Ο", "Του", "Το", "Οι", "Των", "Τους"};
	
	private static final String[] feminineArticleWithN = {"Η", "Της", "Την", "Οι", "Των", "Τις"};
	private static final String[] feminineArticleWithoutN = {"Η", "Της", "Τη", "Οι", "Των", "Τις"};
	
	private static final String[] neuterArticle = {"Το", "Του", "Το", "Τα", "Των", "Τα"};
	
	private static final String[] articleIn = {"Στον", "Στο", "Στην", "Στη", "Στο", "Στις", "Στα"};
	
	public static String getArticleIn(String nominative, boolean withN)
	{
		if(nominative.equals(masculineArticleWithN[0]))
		{
			if(withN) return articleIn[0];
			else return articleIn[1];
		}
		else if(nominative.equals(feminineArticleWithN[0]))
		{
			if(withN) return articleIn[2];
			else return articleIn[3];
		}
		else if(nominative.equals(neuterArticle[0])) return articleIn[4];
		else if(nominative.equals(feminineArticleWithN[3])) return articleIn[5];
		else if(nominative.equals(neuterArticle[3])) return articleIn[6];
		else return "";
	}
	
	public static String getGenitive(String nominative)
	{
		if(nominative.equals(masculineArticleWithN[0])) return masculineArticleWithN[1];
		else if(nominative.equals(feminineArticleWithN[0])) return feminineArticleWithN[1];
		else if(nominative.equals(neuterArticle[0])) return neuterArticle[1];
		else if(nominative.equals(feminineArticleWithN[3])) return feminineArticleWithN[4];
		else if(nominative.equals(neuterArticle[3])) return neuterArticle[4];
		else return "";
	}
	
	public static String getAccusative(String nominative, boolean withN)
	{
		if(nominative.equals(masculineArticleWithN[0]))
		{
			if(withN) return masculineArticleWithN[2];
			else return masculineArticleWithoutN[2];
		}
		else if(nominative.equals(feminineArticleWithN[0]))
		{
			if(withN) return feminineArticleWithN[2];
			else return feminineArticleWithoutN[2];
		}
		else if(nominative.equals(neuterArticle[0])) return neuterArticle[2];
		else if(nominative.equals(feminineArticleWithN[3])) return feminineArticleWithN[5];
		else if(nominative.equals(neuterArticle[3])) return neuterArticle[5];
		else return "";
	}
	
	public static boolean needsN(String word)
	{
		char c = word.charAt(0);
		c = Character.toLowerCase(c);
		
		switch (c)
		{
			case 'β':case 'γ':case 'δ':
			case 'φ':case 'θ':case 'χ':
			case 'μ':case 'ν':case 'λ':
			case 'ρ':case 'σ':case 'ζ':
				return false;
			default:
				return true;
		}
	}
	
	public static String getStringWithoutTones(String a)
	{
		char[] temp = a.toCharArray();
		
		for(int i = 0; i < temp.length; i++)
		{
			if(temp[i] == 'ά') temp[i] = 'α';
			else if(temp[i] == 'έ') temp[i] = 'ε';
			else if(temp[i] == 'ή') temp[i] = 'η';
			else if(temp[i] == 'ί') temp[i] = 'ι';
			else if(temp[i] == 'ό') temp[i] = 'ο';
			else if(temp[i] == 'ύ') temp[i] = 'υ';
			else if(temp[i] == 'ώ') temp[i] = 'ω';
			else if(temp[i] == 'ς') temp[i] = 'σ';
			else if(temp[i] == 'Ά') temp[i] = 'Α';
			else if(temp[i] == 'Έ') temp[i] = 'Ε';
			else if(temp[i] == 'Ή') temp[i] = 'Η';
			else if(temp[i] == 'Ί') temp[i] = 'Ι';
			else if(temp[i] == 'Ό') temp[i] = 'Ο';
			else if(temp[i] == 'Ύ') temp[i] = 'Υ';
			else if(temp[i] == 'Ώ') temp[i] = 'Ω';
		}
		
		return String.valueOf(temp);
	}
	
	public static String getEditedOriginalName(String name)
	{
		name = name.trim();
		String originalName;
		String editedName = "";
		
		if (!name.matches("[^α-ωΑ-Ω]"))
		{
			String[] words = name.split(" ");
			for (int i = 0; i < words.length; i++)
			{
				String noTones = getStringWithoutTones(words[i]);
				if (noTones.endsWith("ησ") || noTones.endsWith("ΗΣ") || noTones.endsWith("ασ") || noTones.endsWith("ΑΣ")
						|| noTones.equalsIgnoreCase("Αλεκοσ") || noTones.equalsIgnoreCase("Γιωργοσ") || noTones.equalsIgnoreCase("Θανοσ")
						|| noTones.equalsIgnoreCase("Κυριακοσ") || noTones.equalsIgnoreCase("Λαμπροσ") || noTones.equalsIgnoreCase("Μανθοσ")
						|| noTones.equalsIgnoreCase("Μαρκοσ") || noTones.equalsIgnoreCase("Νικοσ") || noTones.equalsIgnoreCase("Παυλοσ")
						|| noTones.equalsIgnoreCase("Πετροσ") || noTones.equalsIgnoreCase("Σπυροσ") || noTones.equalsIgnoreCase("Σταυροσ")
						|| noTones.equalsIgnoreCase("Στελιοσ") || noTones.equalsIgnoreCase("Στεργιοσ") || noTones.equalsIgnoreCase("Χρηστοσ"))
					words[i] = words[i].substring(0, name.length() - 1);
				else if (words[i].endsWith("ος")) words[i] = words[i].replace("ος", "ε");
				else if (words[i].endsWith("οσ")) words[i] = words[i].replace("οσ", "ε");
				else if (words[i].endsWith("ΟΣ")) words[i] = words[i].replace("ΟΣ", "Ε");
				else if (words[i].endsWith("ός")) words[i] = words[i].replace("ός", "έ");
				else if (words[i].endsWith("όσ")) words[i] = words[i].replace("όσ", "έ");
				else if (words[i].endsWith("ΌΣ")) words[i] = words[i].replace("ΌΣ", "Έ");
			}
			editedName = Arrays.toString(words).replace("[", "").replace("]", "");
		}
		originalName = name;
		if(editedName.equals("")) editedName = originalName;
		
		return editedName;
	}
	
	public static boolean isArticleInPlural(String article)
	{
		return article.equals("Οι") || article.equals("Τα");
	}
}
