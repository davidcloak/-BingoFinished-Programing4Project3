/**
 ******************************************************************************
 *                        BINGO
 ******************************************************************************
 *
 * The standard bingo game.
 *
 *
 *
 *
 * Name:         David Cloak
 *
 * Date:         11/27/18
 * 
 *
 ******************************************************************************/


import java.util.*;
import java.io.*;

public class Bingo
{
	private Random rand = new Random();
	private int[][] card;       //Bingo card configuration
	private int[] stream;       //list of 75 integers
	private boolean[][] marks;  //simulates placing chips on a Bingo card

	public Bingo()
	{
		card   = new int[5][5];
		stream = new int[75];
		marks  = new boolean[5][5];
	}

 /**
   * This method writes a random Bingo card configuration and a stream of random
   * number between 1 and 75 to the output file.
   *
   * The first column in the table contains only integers between 1 and 15,
   * the second column numbers are all between 16 and 30, the third are 31 to 45,
   * the fourth 46-60, and the fifth 61-75.
   *
   * There are no duplicate numbers on a Bingo card.
   */
	public void write(String outputFile)  throws IOException
	{
            //used to make a random bingo card utilizing the shuffle method below
            //start
            ArrayList<Integer> holder = new ArrayList();//used to make things
                                                    //same value as shuffle input
            for(int x = 0; x < 15; x++){
                holder.add(x, x+1);
            }
            this.shuffle(holder);
            for(int x = 0; x < card.length; x++){
                card[x][0] = holder.get(x);
            }
            
            for(int x = 0; x < holder.size(); x++){
                holder.set(x, x+16);
            }
            this.shuffle(holder);
            for(int x = 0; x < card.length; x++){
                card[x][1] = holder.get(x);
            }
            
            for(int x = 0; x < holder.size(); x++){
                holder.set(x, x+31);
            }
            this.shuffle(holder);
            for(int x = 0; x < card.length; x++){
                card[x][2] = holder.get(x);
            }
            
            for(int x = 0; x < holder.size(); x++){
                holder.set(x, x+46);
            }
            this.shuffle(holder);
            for(int x = 0; x < card.length; x++){
                card[x][3] = holder.get(x);
            }
            
            for(int x = 0; x < holder.size(); x++){
                holder.set(x, x+61);
            }
            this.shuffle(holder);
            for(int x = 0; x < card.length; x++){
                card[x][4] = holder.get(x);
            }
            //end
            
            
            ArrayList<Integer> holder2 = new ArrayList();//used to make things
                                                    //same value as shuffle input
            //shuffles the 1-75 array
            for(int x = 0; x < this.stream.length; x++){
                holder2.add(x, x+1);
            }
            this.shuffle(holder2);
            for(int x = 0; x < stream.length; x++){
                stream[x] = holder2.get(x);
            }
            
            this.card[2][2] = 0;
            
            File file = new File (outputFile);
            BufferedWriter out = new BufferedWriter(new FileWriter(file)); 

            for(int x = 0; x < card.length; x++){//adds it all back to
                for(int y = 0; y < card[0].length; y++){// the text doc.
                    out.write((String)(card[x][y]+ " "));
                }
                out.newLine();
            }
            
            
            for(int x = 0; x < stream.length; x++){//adds it all back to
                out.write((String)(stream[x] + " "));

            }
            out.close();
	}

 /**
   *  Shuffles the list of numbers
   */
   public void shuffle(ArrayList<Integer> list)
   {
		//swaps k-th index with a random index
        int noOfElements = list.size();
        
        for(int x = 0; x < noOfElements; x++){
            int s = x + (int)(Math.random() *(noOfElements - x));
            int temp = list.get(s);
            list.set(s, list.get(x));
            list.set(x, temp);
        }
   }


 /**
   * This method reads a given inputFile that contains a Bingo card configuration and
   * a stream of numbers between 1 and 75.
   * .
   * A Bingo card configuration is stored in the card array.
   * A list of 75 integers is stored in the stream array.
   */
   public void read(String inputFile) throws IOException
   {
	File file = new File ("input.txt");
        String token1 = "";
        // create Scanner inFile1
        Scanner inFile1 = new Scanner(file);

        ArrayList<String> temps = new ArrayList<String>();

        // while loop
        while (inFile1.hasNext()) {
          // find next line
          token1 = inFile1.next();
          temps.add(token1);
        }
        inFile1.close();

        String[] tempsArray = temps.toArray(new String[0]);

        int s = 0;
        for(int x = 0; x < 5; x++){
            for(int y = 0; y < 5; y++){
                card[x][y] = Integer.parseInt(tempsArray[s]);
                s++;
            }
        }
        
        for(int x = 0; x < stream.length; x++){
            stream[x] = Integer.parseInt(tempsArray[s]);
            s++;
        }
    }


 /**
   * This method returns the first integer from the stream array that
   * gives you the earliest winning condition.
   *
   * - all the spots in a column are marked
   * - all the spots in a row are marked
   * - all the spots in either of the two diagonals are marked
   * - all four corner squares are marked
   */
   public int playGame()
   {
       this.marks[2][2] = true;//the center is given
       int s = 0;
       while(true){
           //checks the stream and compares to card
           for(int x = 0; x < card.length; x++){
               for(int y = 0; y < card[0].length; y++){
                   if(this.card[x][y] == this.stream[s]){
                       this.marks[x][y] = true;//if equial sets to true
                   }
               }
           }

           if(this.marks[0][0] && this.marks[0][4] && this.marks[4][0] && this.marks[4][4]){//checks the corners
               return stream[s];
           }else if(this.marks[0][0] && this.marks[1][0] && this.marks[2][0] && this.marks[3][0] && this.marks[4][0] || //checks first row
                    this.marks[0][1] && this.marks[1][1] && this.marks[2][1] && this.marks[3][1] && this.marks[4][1] || //
                    this.marks[0][2] && this.marks[1][2] && this.marks[2][2] && this.marks[3][2] && this.marks[4][2] || //checks rows in between
                    this.marks[0][3] && this.marks[1][3] && this.marks[2][3] && this.marks[3][3] && this.marks[4][3] || //
                    this.marks[0][4] && this.marks[1][4] && this.marks[2][4] && this.marks[3][4] && this.marks[4][4]){// checks last row
               return stream[s];
           }else if(this.marks[0][0] && this.marks[0][1] && this.marks[0][2] && this.marks[0][3] && this.marks[0][4] || //checks first column
                    this.marks[1][0] && this.marks[1][1] && this.marks[1][2] && this.marks[1][3] && this.marks[1][4] || //
                    this.marks[2][0] && this.marks[2][1] && this.marks[2][2] && this.marks[2][3] && this.marks[2][4] || //checks columns in between
                    this.marks[3][0] && this.marks[3][1] && this.marks[3][2] && this.marks[3][3] && this.marks[3][4] || //
                    this.marks[4][0] && this.marks[4][1] && this.marks[4][2] && this.marks[4][3] && this.marks[4][4]){  //checks last column
               return stream[s];
           }else if(this.marks[0][0] && this.marks[1][1] && this.marks[2][2] && this.marks[3][3] && this.marks[4][4] || // the diaganles
                    this.marks[4][0] && this.marks[3][1] && this.marks[2][2] && this.marks[1][3] && this.marks[0][4]){
               return stream[s];
           }
           s++;//s increases to go forwared in the stream
       }
    }
}


