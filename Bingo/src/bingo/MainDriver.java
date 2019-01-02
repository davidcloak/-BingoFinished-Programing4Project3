 /*****************************************************************************

               There is no need to modify this file.

 *****************************************************************************/


import java.io.*;
import java.util.*;

public class MainDriver
{
   public static void main (String[] args) throws IOException
   {
                Bingo game = new Bingo();
		game.write("input.txt");
		game.read("input.txt");
		int x = game.playGame();
		System.out.println("the winning number is " + x);
	}
}