import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
public class Scrab
{
    public static void main(String[]arg) throws FileNotFoundException
  {
    Scanner fileSOW = new Scanner(new File("SOWPOD.txt"));
    ArrayList<String> words = new ArrayList<String>();
    int j = 0;
    String word;
    int[] alph = new int[26];
    alph[0] = 0;
    int asci = 65;
    int k = 0;
    /*String test1 = " B ";
    char test2 = 'a';
    String word12 = test2 + test1;
    System.out.println(word12);*/
    
    while(fileSOW.hasNextLine())
    {
      
      word = fileSOW.nextLine();
      if(word.charAt(0) > asci)
      {
        asci++;
        k++;
        alph[k] = j;
      }
      j++;
      words.add(word);
      
    }

    fileSOW.close();
    Scanner filein = new Scanner(new File("test_rack_file.txt"));
    String holder;
    int score = 0;
    int highest = 0;
    int length = 0;
    ArrayList<String> racks = new ArrayList<String>();
    while(filein.hasNextLine()) 
    {
      holder = filein.nextLine();
      length++;
      racks.add(holder);  
  
    }
    filein.close();
    //System.out.println(length);
    for(int i = 0; i < length; i++)
    {
      long start = System.currentTimeMillis();
      char[] rackH = racks.get(i).toCharArray();
      System.out.print("Rack:");
      for(int e = 0; e < 7; e++)
      {
        int value = charValue(rackH[e]);
        score = score + value;
        System.out.print(" " + rackH[e]);
        
        if(value > highest)
        {
          highest = value;
        }
      }
      System.out.println();
      score = ((score + highest)*2);  
      //System.out.println("Score: "+score);
    
    
      Arrays.sort(rackH);
      intial(words, rackH, alph, start, score);
      score = 0;
      System.out.println("---------------------------");
    }

    
  }
    
    ////////////////////////////////////////////////////////////////////////////////// test rack
    public static void intial(ArrayList<String> words, char[] rack, int[] alpha, long start, int score)
    {
      String word = "";
      int loc = 0;
      String[] holder = new String[2];
      holder[0] = word;
      holder[1] = word;
      String[] largest = rec(words, rack, word, holder, alpha, loc, score);
      System.out.println("Word using rack: " + largest[1]);
      System.out.println("Word spelled out: "+largest[0]);
      long finish = System.currentTimeMillis();
      long timeElapsed = finish - start;
      System.out.println("Millis Time: "+ timeElapsed);
      print(largest[1]);
    }
    public static void print(String word1)
    {
      int leng1Trick = word1.length() - 5;
      int high1 = 0;
      int highestI = 0;
      for(int i = word1.length() -1; i >= 0; i--)
      {
        int score = 0;
        if(word1.length() > 4 && i == 3) //skips over nondouble point letters
        {
          i = leng1Trick;
        }
        for(int j =0; j< word1.length();  j++)
        {
          if(word1.length() > 4 && j == i)
          {
            score = score + (charValue(word1.charAt(j)) * 2);
          }
          else
          {
            score = score + (charValue(word1.charAt(j)));
          }
        }
        score = score *2;
        if(score > high1)
        {
          high1 = score;
          highestI = i + 1;
        }
      }
      if(word1.length() <= 4)
      {
        System.out.println("Column for starting letter: " + 8);
        System.out.println("Highest: " + high1);
        System.out.println("Small map: ");
        smallMap(8, word1);
      }
      else if (highestI > 4)
      {
        int col = (8+(5 - highestI));
        System.out.println("Column for starting letter: " +col);
        System.out.println("Highest: " + high1);
        System.out.println("Small map: ");
        smallMap(col, word1);
      }
      else
      {
        int col = (4 + (1-highestI));
        System.out.println("Column for starting letter: " +col);
        System.out.println("Highest: " + high1);
        System.out.println("Small map: ");
        smallMap(col, word1);
      }
    }
    public static void smallMap(int col, String word)
    {
      
      System.out.print("     ");
      for(int i = 1; i <= 15; i++)
      {
        if(i  == col)
        {
          
          for(int j = 0; j < word.length(); j++)
          {
            System.out.print(word.charAt(j) + "  ");
            
          }
          
          i = i + word.length() -1;
        }
        else 
        {
          System.out.print("*  ");
        }
        
      }
      System.out.println();
      System.out.print("Col: ");
      
      for(int i = 1; i <= 15; i++)
      {
        if(i>9)
          {
            System.out.print(i+ " ");
          }
          else
          {
          System.out.print(i+ "  ");
          }
      }
      System.out.println();
    }
    
    
    
    public static String[] rec(ArrayList<String> words, char[] rack, String word, String[] holder, int[]alph, int loc, int score)
    {
      //System.out.println(word);
      int[] markers = charMarkers(loc, word, words, alph);
      
      /*if(word.equals("Riddle"))
      {
      /*for(int i = 0; i < markers.length; i++)
      {
        System.out.println(words.get(markers[i]));
      }//////////////////////////////////new issue point return system
      }*/
      String[] largest = new String[2];
      largest[1] = "!";
      String excl = "!";
      String[] holderL = new String[2];
      holderL[1] = "!";
      
      //do string to char array System.out.println(holder[1]);

      for(int i = 0; i < rack.length; i++)
      {
        //System.out.println(rack[i]);
        if(i != 0)
        {
          if(rack[i] != rack[i-1])
          {
            if(rack[i] == '_')
            {
              //System.out.println("Check");
              for(int j = 0; j < 26; j++)
              {
                //System.out.println(markers[j]);
                if(markers[j] >= 0)
                {
                  holderL[0] = word + (char)(j + 65);;
                  holderL[1] = holder[1] + rack[i];
                  char[] rackH = new char[rack.length - 1];
                  int m = 0;
                  for(int k = 0; k < rack.length; k++)
                  {
                    if(k != i)
                    {
                      rackH[m] = rack[k];
                      m++;
                    }
                  }
                  //System.out.println(markers[markers[j]]);
                  
                holderL = newArray(words, rackH, holderL[0], holderL, alph, markers[j], score); //this is a problem the rack
              }
              }
            }
            else
            {
              if(markers[(int)rack[i] - 65] >= 0)
              {
                
               // String wordadd = word + rack[i];
                holderL[0] = word + rack[i];
                holderL[1] = holder[1] + rack[i];
                
                char[] rackH= new char[rack.length - 1];
                int m = 0;
                for(int k = 0; k < rack.length; k++)
                {
                  if(k != i)
                  {
                    rackH[m] = rack[k];
                    m++;
                  }
                }
                //System.out.println(holderL[0]);
                holderL = newArray(words, rackH, holderL[0], holderL, alph, markers[(int)rack[i] - 65], score);
              }
            }
          }
        }
        else
        {
          if(rack[i] == '_')
          {
            for(int j = 0; j < 26; j++)
            {
              if(markers[j] >= 0)
              {
                holderL[0] = word + (char)(j + 65);;
                holderL[1] = holder[1] + rack[i];
                char[] rackH= new char[rack.length - 1];
                int m = 0;
                for(int k = 0; k < rack.length; k++)
                {
                  if(k != i)
                  {
                    rackH[m] = rack[k];
                    m++;
                  }
                }
                holderL = newArray(words, rackH, holderL[0], holderL, alph, markers[j], score);
              }
            }
          }
          else
          {
            if(markers[(int)rack[i] - 65] >= 0)
            {
             // String wordadd = word + rack[i];
              holderL[0] = word + rack[i];
              holderL[1] = holder[1] + rack[i];
              char[] rackH= new char[rack.length - 1];
              int m = 0;
              for(int k = 0; k < rack.length; k++)
              {
                if(k != i)
                {
                  rackH[m] = rack[k];
                  m++;
                }
              }
              holderL = newArray(words, rackH, holderL[0], holderL, alph, markers[(int)rack[i] - 65], score);
            }
          }
        }
        /*Point Comparison Here*/
        if(largest[1].equals(excl))
        {
          largest[0] = holderL[0];
          largest[1] = holderL[1];
        }
        else if(pointComparison(largest[1], holderL[1]))//if true holder replaces largest
        {
          largest[0] = holderL[0];
          largest[1] = holderL[1];
        }
        if(highPoint(largest[1]) == score) //best case for max length
        {
          return largest;
        }
      }
      if(words.contains(word) && largest[1].equals(excl))//no root possible check source root
      {
        return holder;
      }
      return largest;
      /*Remmeber charmarker is used for addition checking list has to be check again for word, use contains//*/
    }
    public static int highPoint(String word1)
    {
      int leng1Trick = word1.length() - 5;
      int high1 = 0;
      for(int i = word1.length() -1; i >= 0; i--)
      {
        int score = 0;
        if(word1.length() > 4 && i == 3) //skips over nondouble point letters
        {
          i = leng1Trick;
        }
        for(int j =0; j< word1.length();  j++)
        {
          if(word1.length() > 4 && j == i)
          {
            score = score + (charValue(word1.charAt(j)) * 2);
          }
          else
          {
            score = score + (charValue(word1.charAt(j)));
          }
        }
        score = score *2;
        if(score > high1)
        {
          high1 = score;
        }
      }
      return high1;
    }
    public static boolean pointComparison(String word1, String word2)/*Finds highest point location and compares both returns the highest  
     *use holderL[1] 
     */
    {
      int leng1Trick = word1.length() - 5;
      int leng2Trick = word2.length() - 5;
      //find highest point for word1
      int high1 = 0;
      
      for(int i = word1.length() -1; i >= 0; i--)
      {
        int score = 0;
        if(word1.length() > 4 && i == 3) //skips over nondouble point letters
        {
          i = leng1Trick;
        }
        for(int j =0; j< word1.length();  j++)
        {
          if(word1.length() > 4 && j == i)
          {
            score = score + (charValue(word1.charAt(j)) * 2);
          }
          else
          {
            score = score + (charValue(word1.charAt(j)));
          }
        }
        score = score *2;
        if(score > high1)
        {
          high1 = score;
        }
      }
      int high2 = 0;
      
      for(int i = word2.length() -1; i >= 0; i--)
      {
        int score = 0;
        if(word2.length() > 4 && i == 3) //skips over nondouble point letters
        {
          i = leng2Trick;
        }
        for(int j =0; j < word2.length();  j++)
        {
          if(word2.length() > 4 && j == i)
          {
            score = score + (charValue(word2.charAt(j)) * 2);
          }
          else
          {
            score = score + (charValue(word2.charAt(j)));
          }
        }
        score = score *2;
        if(score > high2)
        {
          high2 = score;
        }
      }
      //System.out.println(high1 + " " + word1);
      if(high2 > high1)
      {
        return true;
      }
      return false;
      /*String tochararrayy*/
    }
    
    
    public static int charValue(char x)//returns scrabble value for char
    {
      if(x == 'A' || x == 'E'|| x == 'I' || x == 'O'|| x == 'U' || x == 'L'|| x=='N'||x== 'S'|| x== 'T'|| x== 'R')
      {
        return 1;
      }
      if(x == 'D' || x == 'G')
      {
        return 2;
      }
      if(x == 'B' || x == 'C'|| x == 'M' || x == 'P')
      {
        return 3;
      }
      if(x == 'F' || x == 'H'|| x == 'V' || x == 'W'|| x == 'Y')
      {
        return 4;
      }
      if(x == 'K')
      {
        return 5;
      }
      if(x == 'J' || x == 'X')
      {
        return 8;
      }
      if(x == 'Q' || x == 'Z')
      {
        return 10;
      }
      return 0;
    }
    
    
    
    
    
    
    
    
    public static String[] newArray(ArrayList<String> words, char[] rack, String word, String[] holder, int[]alph, int loc, int score)
    {
      //System.out.println("new Array");
      String[] copy = new String[2];
      copy[0] = holder[0];
      copy[1] = holder[1];
      word = holder[0];
    //  System.out.println(word);
     // System.out.println(loc);
      return rec(words, rack, word, holder, alph, loc, score);
    }
    
    
    public static int[] charMarkers(int intialLoc, String word, ArrayList<String> words, int[] alph)
    {
     /* if(word.equals("D"))
      {
        System.out.println("check");
        System.out.println(intialLoc);
        System.out.println(words.get(intialLoc)); //Problem with loc
      }*/
      if(word.length() == 0)
      {
        return alph;
      }
      int wordLeng = word.length();
      int fail = 267751 ;
      if((intialLetterCheck(word)) < 25)
      {
      fail = alph[((intialLetterCheck(word)) + 1)];
      }
      
      int[] markers = new int[26];
      boolean end = false;
      int j = 0;
      for(int i = intialLoc;( i < fail) && end != true; i++)
      {
        /*Will only find words greater than oringnal word do word check again*/
        //System.out.println(words.get(i).length());
        //System.out.println(word.length());
        if(words.get(i).length() >= word.length())
        {
          
          String holder = words.get(i).substring(0,wordLeng);
          //System.out.println((holder.substring(0, wordLeng)));
          if(holder.equals(word))
          {
            //System.out.println(words.get(i)+ " " + j);
            if(words.get(i).length() > word.length())
            {
            if(((int)(words.get(i).charAt(wordLeng))- 65) >= j)
            {
              for(int k = j; k <=  ((int)(words.get(i).charAt(wordLeng))- 65) ; k++)
              {
                //System.out.println(words.get(i)+ " " + j);
                if(((int)(words.get(i).charAt(wordLeng)) - 65) == j)
                {
                  //System.out.println(words.get(i)+ " " + j);
                  markers[k] = i;
                  j++;
                }
                else
                {
                  //System.out.println(words.get(i)+ " " + j);
                  markers[k] = -1;
                  j++;
                }
              }
            }
          }
          }
          else
          {
            end = true;
          }
        }
        if(j != 26)
        {
          for(int k = j; k < 26; k++)
          {
            markers[k] = -1; 
          }
        }
      }
      return markers;
    }
      
      
    public static int intialLetterCheck(String word)
    {
      char letter = word.charAt(0);
      int numb = ((int)letter) - 65;
      return numb;
      /*Turn char into number to use array to find search location*/
    }
}