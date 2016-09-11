//DNA Splicing
//Rohan Menezes & Omkar Savant

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.util.*;
import javax.swing.*;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.ArrayList; 

/**
   This is the main class of the program. It contains only the main, which tests out 
   the Splicer methods for both the String and LinkedList implementations.
*/
public class DNASplicer_MenezesSavant extends JPanel{

   
   public JPanel spliceePanelS = new JPanel(new FlowLayout());
   public JPanel spliceePanelL = new JPanel(new FlowLayout());
   public JPanel buttonPanel = new JPanel(new FlowLayout()); 
   
   //This is a list of all of splices being done
      public ArrayList spliceeList = new ArrayList<BigDecimal>();
   
   //This is the list of all of the times it takes to do the splices
   public ArrayList timeList = new ArrayList<BigDecimal>(); 
   
   //The string output is an array of 2 arraylists. The first arrayList is the splicee numbers from the String
   // method, and the second arrayList is the times
   public ArrayList<BigDecimal>[] stringOutput = (ArrayList<BigDecimal>[])new ArrayList[3]; 
   
   //The linkedList output is an array of 2 arraylists. The first arrayList is the splicee numbers from the linkedList
   // method, and the second arrayList is the times
   public ArrayList<BigDecimal>[] linkedListOutput = (ArrayList<BigDecimal>[])new ArrayList[3]; 
   
   //This is a counter of how many DNA strands are analyzed
   public static int counter = 1;
   
   //This is a list of the average time taken to process each of the DNA strands
   public static ArrayList<BigDecimal> averageTimes = new ArrayList<BigDecimal> ();
   
   /**
      This is the main method. It allows the user to enter in their own dna 
      strand or use a default one provided by the program itself. It then 
      processes that string by calling testLengths on it. Technically, it just 
      creates an instace of the class called runner so that static methods can 
      be called from a non-static context. Initially the arguments are null, 
      null because no values from the splice have been created yet to feed in. 
      Inside the runner class, these values are created, and the displayGUI method 
      will be called with these new arrays of arrayLists as parameters. The method 
      allows the user to analyze many different DNA strand before displaying a final 
      analysis at the end.
   */
   public static void main(String[] args)
   {
        try{
        DNASplicer_MenezesSavant runner = new DNASplicer_MenezesSavant(null, null);
        runner.run();
        }
        catch(Exception e){
          e.printStackTrace(); 
        }
   }
   
   /**
      This is the run method that officially does all the things promised by the main
   */
   public void run (){
      boolean playAgain = true;
      Scanner in = new Scanner(System.in);
      System.out.println("Welcome to Rohan and Omkar's DNA Splicer!");
      while(playAgain)
      {
         System.out.println("If you would like to supply your own dna strand for the " + 
                            "compiler to process, \nenter it now. Otherwise, enter \"" +
                            "default\" and the program will provide a dna \nstrand for you");
                     
         String dnaChoice = in.nextLine();
         if(dnaChoice.equals("default"))
         {
            System.out.println("\nYour default dna strand is \"aatccgaattcgtatca\"\n");
            SimpleStrand dna = new SimpleStrand("aatccgaattcgtatca");
            stringOutput = dna.testLengths();
            System.out.println();
            
            
            LinkStrand DNA = new LinkStrand("aatccgaattcgtatca");
            linkedListOutput = DNA.testLengths();
         }
         else
         {
           
            SimpleStrand dna = new SimpleStrand(dnaChoice);
            stringOutput = dna.testLengths();
            System.out.println();
            LinkStrand DNA = new LinkStrand(dnaChoice);
            linkedListOutput = DNA.testLengths();
         }
         
         displayGUI(stringOutput, linkedListOutput);
         
         System.out.println("\n***CLICK BUTTON ON GUI TO SEE MORE RESULTS***\n");
         
         try {
             Thread.sleep(3000);
         } catch(InterruptedException ex) {
             Thread.currentThread().interrupt();
         }
         
         System.out.println("Would you like to test another strand?");
         String choice = in.nextLine();
         if(!(choice.toLowerCase().equals("yes")))
         {
            playAgain = false;
            displayFinalGUI();
            System.out.println("\n***CHECK GUI FOR FINAL RESULTS, THEN HIT \"ENTER\" TO EXIT***");
            String result = in.nextLine();
         }
         else
         {
            counter++;
         }
      }
      System.exit(0);
   }
   
   
   /**
      This tests whether a background image has been read in and set to the ImageIcon 
      variable, and sets it to the background
   */
       protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
               g.drawImage(backgroundImage, 0, 0, null);
      }
    }
   
   /*
      Creates the Splicer JFrame and adds to it a new instance of the tester class, 
      which runs the program to analyze a new DNA strand
   */
   public static void displayGUI(ArrayList<BigDecimal>[] strings, ArrayList<BigDecimal>[] linkedList){
      JFrame frame = new JFrame("DNA Splicer by Rohan and Omkar");
      frame.setLayout(new GridLayout());
         try {
            frame.getContentPane().add(new DNASplicer_MenezesSavant(strings, linkedList));
         } catch (MalformedURLException e) { // this is thrown if the URL read anywhere is badly formatted
            e.printStackTrace();
            System.exit(1);
         } catch (IOException e) { // thrown when the image cannot be read in an IO input.
            e.printStackTrace();
            System.exit(1);
         } catch (NullPointerException d) //This will be thrown if there is no internet connection
         {
            System.out.println(d);
         }
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.pack();
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);
   }// end of displayGUI
   
   /*
      Creates the Splicer JFrame and adds to it a new instance of the tester class, 
      which runs the program to display the final analysis
   */
   public static void displayFinalGUI(){
      JFrame frame = new JFrame("DNA Splicer by Rohan and Omkar");
      frame.setLayout(new GridLayout());
         try {
            frame.getContentPane().add(new DNASplicer_MenezesSavant());
         } 
         catch (NullPointerException d) //This will be thrown if there is no internet connection
         {
            System.out.println(d);
         }
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.pack();
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);
   }// end of displayGUI
   
   
   public static final String BACKGROUND_URL = "http://i.imgur.com/NgX8LbO.jpg";
   
   private BufferedImage backgroundImage;
   
   /**
      This is the part of the program that displays the final analysis (average 
      run times for all strands)
   */
   public DNASplicer_MenezesSavant()
   {
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.HORIZONTAL;
       
       gbc.weighty = 1.0;
       gbc.gridy = 3;
       add(spliceePanelS, gbc);
       
       gbc.weighty = 2.0;
       gbc.gridy = 4;
       add(spliceePanelL, gbc);
     
     //This is where the image link is read and set to a variable
     try{
     backgroundImage = ImageIO.read(new URL(BACKGROUND_URL));}
     catch (MalformedURLException e) { // this is thrown if the URL read anywhere is badly formatted
            e.printStackTrace();
            System.exit(1);
         } catch (IOException e) { // thrown when the image cannot be read in an IO input.
            e.printStackTrace();
            System.exit(1);
         }
     
     setBackground(Color.black);
     
     // The size of the JFrame is set to the size of the background picture. 
      setPreferredSize(new Dimension(backgroundImage.getWidth(),backgroundImage.getHeight() + 50));
      
    //The panels are colored
      spliceePanelS.setBackground(new Color(88,157,204));
      spliceePanelL.setBackground(new Color(88,157,204));  
          
     
    spliceePanelS.setLayout(new BoxLayout(spliceePanelS, BoxLayout.Y_AXIS));
    spliceePanelL.setLayout(new BoxLayout(spliceePanelL, BoxLayout.Y_AXIS)); 
    
    JLabel breakLine = new JLabel("<html> <br> </html>");
    JLabel breakLine2 = new JLabel("<html> <br> </html>");
    
    //This is the initial text of the splicee Panel
      JLabel spliceeNumberS = new JLabel("String Splicee Average Times: "); 
      spliceePanelS.add(spliceeNumberS); 
      
      
    //This is the initial text of the splicee Panel
      JLabel spliceeNumberL = new JLabel("LinkedList Splicee Average Times: "); 
      spliceePanelL.add(spliceeNumberL);
      
      spliceePanelL.add(breakLine);
      spliceePanelS.add(breakLine2);
       
      //This finally displays the average run times
      int count = 1;
      for(int i = 0; i < counter * 2; i = i + 2)
      {
         JLabel spliceeS = new JLabel("String " + count + ": " + averageTimes.get(i));
         spliceePanelS.add(spliceeS);
         JLabel spliceeL = new JLabel("LinkedList " + count + ": " + averageTimes.get(i+1));
         spliceePanelL.add(spliceeL);
         count++;
      }
   }

   /**
      This is the part of the program that creates the GUI for the analysis of 
      inputted DNA strands
   */
   public DNASplicer_MenezesSavant(ArrayList<BigDecimal>[] stringList, ArrayList<BigDecimal>[] linkedList) throws MalformedURLException, IOException{
      
      stringOutput = stringList; 
      linkedListOutput = linkedList; 
      
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.HORIZONTAL;
       gbc.weighty = 0.5;
       gbc.gridy = 0;
       add(buttonPanel, gbc);
       
       gbc.weighty = 1.0;
       gbc.gridy = 3;
       add(spliceePanelS, gbc);
       
       gbc.weighty = 2.0;
       gbc.gridy = 4;
       add(spliceePanelL, gbc);
     
     //This is where the image link is read and set to a variable
     backgroundImage = ImageIO.read(new URL(BACKGROUND_URL));
     
     setBackground(Color.black);
     
     // The size of the JFrame is set to the size of the background picture. 
      setPreferredSize(new Dimension(backgroundImage.getWidth(),backgroundImage.getHeight() + 50));
      
    //The panels are colored
      buttonPanel.setBackground(new Color(0,0,0));
      spliceePanelS.setBackground(new Color(88,157,204));
      spliceePanelL.setBackground(new Color(88,157,204));  
          
      JButton nextSpliceeButton = new JButton("Show Splicing and time"); 
    //Action listeners are added to each of the buttons to check for user clicks
      nextSpliceeButton.addActionListener(new nextSpliceeButton());
    
    //Buttons are added to the buttonPanel, which is a JPanel on the JFrame. 
      buttonPanel.add(nextSpliceeButton); 
      
    //This is the initial text of the splicee Panel
      JLabel spliceeNumberS = new JLabel("String Splicee: "); 
      spliceePanelS.add(spliceeNumberS); 
      
    //This is the initial text of the splicee Panel
      JLabel spliceeNumberL = new JLabel("LinkedList Splicee: "); 
      spliceePanelL.add(spliceeNumberL); 
     
   }
   
   /**
      This is the inner class that represents the button in our GUI. When clicked, 
      the button searches the DNA for certain patterns, adds in RNA at those patterns, 
      and measures how long the system takes to do so
   */
   class nextSpliceeButton implements ActionListener{
      
      int spliceListCounter = 0; 
      int timeListCounter = 0; 
      
      ArrayList localSpliceListS = new ArrayList<BigDecimal>();
      ArrayList localSpliceListL = new ArrayList<BigDecimal>();
      ArrayList localTimeListS = new ArrayList<BigDecimal>();
      ArrayList localTimeListL = new ArrayList<BigDecimal>();
      ArrayList localStringListS = new ArrayList<String>();
      ArrayList localStringListL = new ArrayList<String>();
      
      boolean stringAdded = false;
      boolean linkedListAdded = false;
      
      public nextSpliceeButton(){
    
      }
      
      //this method is what actually happens when the user presses the button
      public void actionPerformed(ActionEvent a){

         localSpliceListS = stringOutput[0]; 
         localSpliceListL = linkedListOutput[0]; 
         localTimeListS = stringOutput[1]; 
         localTimeListL = linkedListOutput[1]; 
         localStringListS = stringOutput[2];
         localStringListL = linkedListOutput[2];
         
         /**
            adds the average time for string and linked list to the ArrayList 
            tracking average times
         */
         if(!stringAdded)
         {
            BigDecimal sum = new BigDecimal(0);
            MathContext mc = new MathContext(15);
            for(int i = 0; i < localTimeListS.size(); i++)
            {
               sum = sum.add((BigDecimal)localTimeListS.get(i), mc);
            }
            BigDecimal average = sum.divide(new BigDecimal(localTimeListS.size()), 6, RoundingMode.CEILING);
            averageTimes.add(average);
            stringAdded = true;
         }
         
         if(!linkedListAdded)
         {
            BigDecimal sum = new BigDecimal(0);
            MathContext mc = new MathContext(15);
            for(int i = 0; i < localTimeListL.size(); i++)
            {
               sum = sum.add((BigDecimal)localTimeListL.get(i), mc);
            }
            BigDecimal average = sum.divide(new BigDecimal(localTimeListL.size()), 6, RoundingMode.CEILING);
            averageTimes.add(average);
            linkedListAdded = true;
         }
         
         spliceePanelS.setLayout(new BoxLayout(spliceePanelS, BoxLayout.Y_AXIS));
         spliceePanelL.setLayout(new BoxLayout(spliceePanelL, BoxLayout.Y_AXIS));

         //displays the splicee length in the GUI
         while(spliceListCounter < localSpliceListS.size()){
         JLabel spliceeUpdateS = new JLabel("<html><br>Splicee: " + localSpliceListS.get(spliceListCounter) + "</html>");
         JLabel spliceeUpdateL = new JLabel("<html><br>Splicee: " + localSpliceListL.get(spliceListCounter) + "</html>");
         
         
         spliceePanelS.add(spliceeUpdateS);
         
         spliceePanelL.add(spliceeUpdateL);
          
         spliceePanelS.updateUI();
         spliceePanelL.updateUI();
         
         spliceListCounter++; 
         
         //displays the splicee time in the GUI
         JLabel timeUpdateS = new JLabel("Time: " + localTimeListS.get(timeListCounter));
         JLabel timeUpdateL = new JLabel("Time: " + localTimeListL.get(timeListCounter));
         
         spliceePanelS.add(timeUpdateS);
         spliceePanelL.add(timeUpdateL);
         
         JLabel finalStringUpdateS = new JLabel("Final DNA Strand: " + localStringListS.get(timeListCounter));
         JLabel finalStringUpdateL = new JLabel("Final DNA Strand: " + localStringListL.get(timeListCounter));
         
         spliceePanelS.add(finalStringUpdateS);
         spliceePanelL.add(finalStringUpdateL);
        
         spliceePanelS.updateUI();
         spliceePanelL.updateUI();
         
         timeListCounter++; 
       }
      }
   }   
} 
      /**
         This is the SimpleStrand class that implements a dna strand using a string. 
         SimpleStrand allows the user to process different lengths of the given dna 
         strand (always powers of 2) by inserting rna strands at certain patterns.
         
         Output is an array of 2 arrayLists. One arraylist contains the number of the splice and the other contains
         the time it took for that splice. 
      */
      class SimpleStrand
      {
         ArrayList<BigDecimal>[] output = (ArrayList<BigDecimal>[])new ArrayList[3]; 
         
         // list of all the splicee lengths that can be accessed by the inner classes 
         ArrayList spliceeList = new ArrayList<BigDecimal>();
   
         /**
            list of all the times that it takes to do certain splicees, also 
            accessible by inner classes
         */
         ArrayList timeList = new ArrayList<BigDecimal>(); 
         
         ArrayList finalString = new ArrayList<String>();
         
         //instance variable
         private String dna;
         
         //default constructor
         public SimpleStrand()
         {
            dna = "";
            int rand1 = (int)(Math.random() * 100) + 1;
            for(int i = 0; i < rand1; i++)
            {
               int rand2 = (int)(Math.random() * 4);
               if(rand2 == 0)
               {
                  dna = dna + "a";
               }
               else if(rand2 == 1)
               {
                  dna = dna + "c";
               }
               else if(rand2 == 2)
               {
                  dna = dna + "g";
               }
               else
               {
                  dna = dna + "t";
               }
            }
         }
         
         //regular constructor
         public SimpleStrand(String newDNA)
         {
            dna = newDNA;
         }
         
         /**
            This method takes a dna strand and inserts an rna strand whenever it sees 
            a certain pattern. In this particular case, we calibrated the method to 
            search for the string "gaattc" and then insert "tgata" between the "g" 
            and the "a".
         */
         public String processDNA(String newDNA)
         { 
            for(int i = 0; i < newDNA.length()-5; i++)
            {
               if(newDNA.substring(i, i+6).equals("gaattc"))
               {
                  newDNA = newDNA.substring(0, i) + "g/split/aattc" + newDNA.substring(i+6);
               }
            }
            
            for(int i = 0; i < newDNA.length()-6; i++)
            {
               if(newDNA.substring(i, i+7).equals("/split/"))
               {
                  newDNA = newDNA.substring(0, i) + "tgata" + newDNA.substring(i+7);
               }
            }
            
            return newDNA;
         }
         
         /**
            This method takes the strand of dna and processes the first x characters. 
            For this particular assignment, x is always a  power of 2 (2, 4, 8, 16...). 
            This is to ensure that if the string is too long, we can at least test 
            some parts of it without getting a runtime error. When testing each string 
            length, the method prints out the length of the strand, the time taken for 
            testing, the length before, and the length after.
            
            
            The method returns an array of arrayLists, containing the number of the splice, and time for that splice
         */
         public ArrayList<BigDecimal>[] testLengths()
         {
            System.out.println("dna length = " + dna.length());
            int length = 2;
        
            while(length < dna.length())
            {
               BigDecimal before = new BigDecimal(System.nanoTime());
               String newDNA = processDNA(dna.substring(0,length));
               BigDecimal after = new BigDecimal(System.nanoTime());
               MathContext mc = new MathContext(15);
               BigDecimal difference = (after.subtract(before, mc)).divide(new BigDecimal(1000000), 6, RoundingMode.CEILING);
               spliceeList.add(new java.math.BigDecimal(String.valueOf(length))); 
               timeList.add(difference); 
               System.out.println("SimpleStrand:\tsplicee " + length + "\ttime " + difference + "\tbefore " + dna.length() + "\tafter " + dna.length() + " recomb " + (newDNA.length() - length + dna.length()));
               finalString.add(newDNA + dna.substring(length));
               length = length * 2;
            }
            BigDecimal before = new BigDecimal(System.nanoTime());
            String newDNA = processDNA(dna);
            BigDecimal after = new BigDecimal(System.nanoTime());
            MathContext mc = new MathContext(2);
            BigDecimal difference = (after.subtract(before, mc)).divide(new BigDecimal(1000000), 6, RoundingMode.CEILING);
            spliceeList.add(new java.math.BigDecimal(String.valueOf(dna.length())));  
            timeList.add(difference);
            System.out.println("SimpleStrand:\tsplicee " + dna.length() + "\ttime " + difference + "\tbefore " + dna.length() + "\tafter " + dna.length() + " recomb " + newDNA.length());
            System.out.println("Final DNA Strand: " + newDNA);
            finalString.add(newDNA);
            
            output[0] = spliceeList; 
            output[1] = timeList; 
            output[2] = finalString;
            
            return output; 
         }
      }
      
      /**
         This is the LinkStrand class that implements a dna strand using a LinkedList. 
         It defines the same methods as SimpleStrand to test different lengths of the 
         given dna strand.
         
         Output is an array of 2 arrayLists. One arraylist contains the number of the splice and the other contains
         the time it took for that splice. 
         
      */
      class LinkStrand{
         ArrayList<BigDecimal>[] output = (ArrayList<BigDecimal>[])new ArrayList[3]; 
         
         // list of all the splicee lengths that can be accessed by the inner classes 
         ArrayList spliceeList = new ArrayList<BigDecimal>();
   
         //list of all the times that it takes to do certain splicees, also accessible by inner classes
         ArrayList timeList = new ArrayList<BigDecimal>(); 
         
         ArrayList finalString = new ArrayList<String>();
         
         
         //instance variable
         private LinkedList<Base> dna;
         
         //default constructor
         public LinkStrand()
         {
            dna = new LinkedList<Base> ();
            int rand1 = (int)(Math.random() * 100) + 1;
            for(int i = 0; i < rand1; i++)
            {
               int rand2 = (int)(Math.random() * 4);
               if(rand2 == 0)
               {
                  dna.add(new Base("a"));
               }
               else if(rand2 == 1)
               {
                  dna.add(new Base("c"));
               }
               else if(rand2 == 2)
               {
                  dna.add(new Base("g"));
               }
               else
               {
                  dna.add(new Base("t"));
               }
            }
         }
         
         //regular constructor
         public LinkStrand(String dnaStrand)
         {
            dna = new LinkedList<Base> ();
            for(int i = 0; i < dnaStrand.length(); i++)
            {
               dna.add(new Base(dnaStrand.substring(i, i+1)));
            }
         }
         
         /**
            This method takes a dna strand and inserts an rna strand whenever it sees 
            a certain pattern. In this particular case, we calibrated the method to 
            search for the pattern "gaattc" and then insert "tgata" between the "g" 
            and the "a".
         */
         public LinkedList<Base> processDNA(int length, LinkedList<Base> newDNA)
         { 
            for(int i = 0; i < length-5; i++)
            {
               if(newDNA.get(i).equals(new Base("g")) && newDNA.get(i+1).equals(new Base("a")) 
                  && newDNA.get(i+2).equals(new Base("a")) && newDNA.get(i+3).equals(new Base("t"))
                  && newDNA.get(i+4).equals(new Base("t")) && newDNA.get(i+5).equals(new Base("c")))
               {
                  newDNA.add(i+1, new Base("t"));
                  newDNA.add(i+2, new Base("g"));
                  newDNA.add(i+3, new Base("a"));
                  newDNA.add(i+4, new Base("t"));
                  newDNA.add(i+5, new Base("a"));
               }
            }
            return newDNA;
         }
         
         /**
            This method takes the strand of dna and processes the first x characters. 
            For this particular assignment, x is always a  power of 2 (2, 4, 8, 16...). 
            This is to ensure that if the LinkedList is too long, we can at least test 
            some parts of it without getting a runtime error. When testing each LinkedList 
            length, the method prints out the length of the strand, the time taken for 
            testing, the length before, and the length after.
            
            The method returns an array of arrayLists, containing the number of the splice, and time for that splice
         */
         public ArrayList<BigDecimal>[] testLengths(){
            System.out.println("dna length = " + dna.size());
            int length = 2;
            while(length < dna.size()){
               BigDecimal before = new BigDecimal(System.nanoTime());
               LinkedList<Base> newDNA = (LinkedList)dna.clone();
               newDNA = processDNA(length, newDNA);
               BigDecimal after = new BigDecimal(System.nanoTime());
               MathContext mc = new MathContext(15);
               spliceeList.add(new java.math.BigDecimal(String.valueOf(length))); 
               BigDecimal difference = (after.subtract(before, mc)).divide(new BigDecimal(1000000), 6, RoundingMode.CEILING);
               timeList.add(difference);
               System.out.println("LinkStrand:\tsplicee " + length + "\ttime " + difference + "\tbefore " + dna.size() + "\tafter " + dna.size() + " recomb " + newDNA.size());
               finalString.add(newDNA.toString());
               length = length * 2;
            }
            BigDecimal before = new BigDecimal(System.nanoTime());
            LinkedList<Base> newDNA = processDNA(dna.size(), (LinkedList)dna.clone());
            BigDecimal after = new BigDecimal(System.nanoTime());
            MathContext mc = new MathContext(2);
            BigDecimal difference = (after.subtract(before, mc)).divide(new BigDecimal(1000000), 6, RoundingMode.CEILING);
            spliceeList.add(new java.math.BigDecimal(String.valueOf(dna.size())));  
            timeList.add(difference);
            System.out.println("LinkStrand:\tsplicee " + dna.size() + "\ttime " + difference + "\tbefore " + dna.size() + "\tafter " + dna.size() + " recomb " + newDNA.size());
            System.out.println("Final DNA Strand: " + newDNA.toString());
            finalString.add(newDNA.toString());
            
            output[0] = spliceeList; 
            output[1] = timeList; 
            output[2] = finalString;
         
            return output; 
         }
}



/**
   This is the Base class, which simple provides an Object for the LinkedList 
   implementation above. It allows the user to define any of the 4 biological 
   bases (A, C, G, and T).
*/
class Base
{
   //instance variable
   private String value;
   
   //constructor
   public Base(String val)
   {
      value = val;
   }
   
   //returns the value of the base string
   public String getValue()
   {
      return value;
   }
   
   //defines how to compare two bases for equality
   public boolean equals(Base e)
   {
      return value.equals(e.getValue());
   }
   
   //provides a printable version of the base
   public String toString()
   {
      return value;
   }
}