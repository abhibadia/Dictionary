import java.io.*;
import java.util.*;

public class Dictionary
{
   public static void main(String[] args) 
   {
      Scanner infile = null;
      PrintWriter outputFile = null;
      try
      {
         infile = new Scanner(new File("spanglish.txt"));
         outputFile = new PrintWriter(new FileWriter("dictionaryOutput.txt"));
      }
      catch(Exception e)
      {
         System.out.println( e );
      }
      
      Map<String, Set<String>> eng2spn = makeDictionary( infile );
      outputFile.println("ENGLISH TO SPANISH");
      outputFile.println(display(eng2spn));
   
      Map<String, Set<String>>spn2eng = reverse(eng2spn);
      outputFile.println("SPANISH TO ENGLISH");
      outputFile.println(display(spn2eng));
      outputFile.close();
      
      System.out.println("File created.");
   }
   
   public static Map<String, Set<String>> makeDictionary(Scanner infile) {
        TreeMap<String, Set<String>> map = new TreeMap<>();
        
        while (infile.hasNext()) {
            String english = infile.next();
            String spanish = infile.next();
            
            map.computeIfAbsent(english, k -> new TreeSet<>()).add(spanish);
        }
        
        return map;
    }

   
   public static void add(Map<String, Set<String>> dictionary, String word, String translation) {
        dictionary.computeIfAbsent(word, k -> new TreeSet<>()).add(translation);
   }
   
   public static String display(Map<String, Set<String>> map) {
        StringBuilder result = new StringBuilder();
        
        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            Set<String> values = entry.getValue();
            StringBuilder formattedValues = new StringBuilder();
            
            for (String value : values) {
                formattedValues.append(value).append(", ");
            }
            
            if (formattedValues.length() > 0) {
                formattedValues.delete(formattedValues.length() - 2, formattedValues.length());
            }
            
            result.append("\t").append(key).append(" ").append(formattedValues).append("\n");
        }
        
        return result.toString();
   }
   
   public static Map<String, Set<String>> reverse(Map<String, Set<String>> dictionary) {
        Map<String, Set<String>> reversedDictionary = new TreeMap<>();
        
        for (Map.Entry<String, Set<String>> entry : dictionary.entrySet()) {
            String key = entry.getKey();
            Set<String> values = entry.getValue();
            
            for (String value : values) {
                if (!reversedDictionary.containsKey(value)) {
                    reversedDictionary.put(value, new TreeSet<>());
                }
                reversedDictionary.get(value).add(key);
            }
        }
        
        return reversedDictionary;
    }
}
