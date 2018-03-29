import java.io.*;
import java.lang.*;
import java.util.Random;

public class Snakes_and_Ladders
{// start class
    final static int WINPOINT = 36;
	static int gametotal=10000;
    static int UserWinner = 0, CompWinner = 0;
  	static int UserHitSnake = 0, UserHitLadder = 0, CompHitSnake = 0, CompHitLadder = 0;
  	static int UserRollCount = 0, CompRollCount = 0;
  	static int userPosition = 1; // sets the default location for user's piece to 1
    static int compPosition = 1; // sets the default location for computer's piece to 1
    static int userPositionSet = 1; // can change for each game.
    static int compPositionSet = 1; // can change for each game.
    static int userPositionInit = 1; //for recording and tracking
    static int compPositionInit = 1; //for recording and tracking
  	// declare all the snakes and ladders in a array
  	static int snakesStartArray [] = new int [5]; // create a 5 element array
  	static int snakesEndArray [] = new int [5]; // create a 5 element array
  	static int laddersStartArray [] = new int [5]; // create a 5 element array
  	static int laddersEndArray [] = new int [5]; // create a 5 element array
  	static double UserWinProb = 0.0;
    static double CompWinProb = 0.0;
	static String gametype = "null";
    static boolean firsttimein = true;   

    //Main Method
    public static void main (String [] args) throws IOException
    {// start main method
              

        //Welcome Screen
        
        // BufferedReader myInput2 = new BufferedReader (new InputStreamReader (System.in));
        // Print the welcome screen and instructions
        System.out.println ("Welcome To Snakes And Ladders\n");

		// Parse command line inputs and determine game type.
    	if (args.length == 0) {
        	System.out.println("Main: No arguments were given.");
        	// System.err.println("Usage: ParseCmdLine [-verbose] [-xn] [-output afile] filename");
        	System.exit(-1);
    	}
    	else {
        		for (String a : args) {

 					if (a.equals ("base"))
            		{
        				System.out.println("CLI input was: " + a);
						gametype = "base";
            		}
            		else if (a.equals ("50ladder"))
            		{
        				System.out.println("CLI input was: " + a);
						gametype = "50ladder";
            		}
					else if (a.equals ("changestartsquare"))
        			{
        				System.out.println("CLI input was: " + a);
						gametype = "changestartsquare";       
        			}
					else if (a.equals ("firstsnakeimmunity"))
					{
        				System.out.println("CLI input was: " + a);
						gametype = "firstsnakeimmunity";					
					}
            		else
            		{
                  		System.out.print ("\nMain: Game type error ");
        				System.out.println("CLI input was: " + a);
        				System.exit(-2);
            		}
    		 }
    	}
        
        // Initialize variables and game board
        int countsquares=WINPOINT; // sets the counter to be used in making the board
        int ireturn=0;
        ireturn = setupBoard ();
        
        String sGame = "y"; // declare variable used to ask user if he wants to play
        
        // System.out.print ("Do you want to play? Y or N     >  "); // ask user to play the game
        // sGame = myInput2.readLine (); // reads the user's input into the variable sGame
        System.out.print ("\n");
      
        if (gametype.equals ("base") || gametype.equals ("50ladder"))
        {
        	initAllGames();  
            System.out.println ("\nMain: Gametype = " + gametype + " Games to play = " + gametotal + "\n");
            sGame = playGames(gametype, gametotal);
			getStats(gametype);
		}
		else if (gametype.equals ("changestartsquare"))
        {        
            double closestodds [] = new double [20];
            double closestosquare [] = new double [20];
    		for (int il=1 ; il<20 ; il++){
         		initAllGames();  
  				userPositionSet = 1;
    			compPositionSet = il; 
        		userPosition = 1;
        		compPosition = il;
  				userPositionInit = 1;
    			compPositionInit = il; 
            	System.out.println ("\nMain: Gametype = " + gametype + " Games to play = " + gametotal + "\n");
            	sGame = playGames(gametype, gametotal);
				getStats(gametype);
				closestodds[il] = Math.abs(UserWinProb - CompWinProb);
				closestosquare[il] = compPositionInit;
   			}
   			    int minindex = findMinIdx(closestodds);
            	System.out.println ("\nPlayer2 start square with closest to equal odds = " + minindex + "\n");
        }
		else if (gametype.equals ("firstsnakeimmunity"))
		{
        	initAllGames();  
            System.out.println ("\nMain: Gametype = " + gametype + " Games to play = " + gametotal + "\n");
            sGame = playGames(gametype, gametotal);
			getStats(gametype);		            
        }
        else
        {
            System.out.print ("\nMain: Game type error ");
        	System.out.println("\nCLI input was: " + gametype);
        	System.exit(-3);
        }

        System.out.println ("\nSIMULATION ENDED\n");
        
        
    } //end main method

    //-------------------------------------------------------------------findMinIdx Method------------------------------------------------------------------------------

	public static int findMinIdx(double[] numbers) {
    	if (numbers == null || numbers.length == 0) return -1; // Saves time for empty array
    	// As pointed out by ZouZou, you can save an iteration by assuming the first index is the smallest
    	double  minVal = numbers[1]; // Keeps a running count of the smallest value so far
    	int minIdx = 1; // Will store the index of minVal
    	for(int idx=1; idx<numbers.length; idx++) {
        	if(numbers[idx] < minVal) {
            	minVal = numbers[idx];
            	minIdx = idx;
        	}
            // System.out.println ("\nfindMinIdx: minVal = " + minVal + " minIdx = " + minIdx + " idx = " + idx + " numbers[idx] = " + numbers[idx] + "\n");
    	}
    	return minIdx;
	}
    //-------------------------------------------------------------------getStats Method------------------------------------------------------------------------------

	public static void getStats(String gametype)
	{

        System.out.println ("\n----------------------------------------Start Game Stats-----------------------------------------------------------------\n");
        System.out.println ("Gametype = " + gametype + " Games to play = " + gametotal + "\n");
        //System.out.println ("UserPositionInit = " + UserPositionInit + " CompPositionInit = " + CompPositionInit + "\n");
        System.out.println ("UserPositionInit = " + userPositionInit + " CompPositionInit = " + compPositionInit + "\n");
        System.out.println ("User won = " + UserWinner);
        System.out.println ("Comp won = " + CompWinner);
        UserWinProb = (double) UserWinner / (double) gametotal;
        CompWinProb = (double) CompWinner/ (double) gametotal;
        System.out.printf("User Win Prob: %f Comp Win Prob: %f\n", UserWinProb, CompWinProb);// Output using string formatting.
        System.out.printf("Absolute value win prob difference: %f\n", Math.abs(UserWinProb - CompWinProb));

        System.out.println ("User snakes = " + UserHitSnake + " User ladders = " + UserHitLadder);
        System.out.println ("Comp snakes = " + CompHitSnake + " Comp ladders = " + CompHitLadder);
        double SnakesLandedOn = ((double) UserHitSnake + (double) CompHitSnake) / (double) gametotal ;
        double LaddersLandedOn = ((double) UserHitLadder + (double) CompHitLadder) / (double) gametotal ;
        System.out.println ("Snakes per game = " + SnakesLandedOn + " Ladders per game = " + LaddersLandedOn);
        System.out.println ("User rolls = " + UserRollCount + " Comp rolls = " + CompRollCount + " Total rolls = " + (UserRollCount + CompRollCount));
		double RollsPerGame = (double) (UserRollCount + CompRollCount) / (double) gametotal;
        System.out.printf("Average rolls per game: %f", RollsPerGame);// Output using string formatting.			
        System.out.println ("\n-----------------------------------------End Game Stats-----------------------------------------------------------------\n");

	}

    //-------------------------------------------------------------------initAllGames Method------------------------------------------------------------------------------

	public static void initAllGames()
	{
	    UserWinner = 0;
	    CompWinner = 0;
  		UserHitSnake = 0;
  		UserHitLadder = 0;
  		CompHitSnake = 0; 
  		CompHitLadder = 0;
  		UserRollCount = 0;
  		CompRollCount = 0;
  		userPosition = 1;
        compPosition = 1;
    	firsttimein = true;
	}

    //-------------------------------------------------------------------initEachGame Method------------------------------------------------------------------------------

	public static void initEachGame()
	{
    	firsttimein = true;
	}
    //-------------------------------------------------------------------playGames Method------------------------------------------------------------------------------
    
    /*
    playGames method:
    
    This method is responsible for setting the game variables and running the games. 
    */
    public static String playGames (String gametype, int gametotal) throws IOException // Receives data from the main method
    {// start playGames method
        
        BufferedReader myInput = new BufferedReader (new InputStreamReader (System.in));
        
        // sets important variables for the game
        // NOTE: These variables will change as the game progresses
        
        int diceRoll = 0; // creates the die
        int userRoll = 1; // declares what the user rolled
        int compRoll = 1; // declares what the computer rolled
        int icount = 1;
        boolean winner = false;
        
        while (icount <= gametotal)
        {// Loop over all games.   
        
            initEachGame();         // Initialize each individual game.

			winner = false;
			while (!winner) {
			    
			    // Play each game until there is a winner    
            	// gets the dice roll for user and computer
            	userRoll =  getDice(diceRoll); 
            	UserRollCount++;
            	compRoll =  getDice(diceRoll); 
				CompRollCount++;
            	// get user position
            	userPosition = userPosition + userRoll;           
            	// get computer position
            	compPosition = compPosition + compRoll;            
            
               	// check to see if user landed on top of a snake or at the bottom of a ladder
               	userPosition = getUserPos(userPosition, userRoll);
               	// The same goes for compPosition.
               	compPosition = getCompPos(compPosition, compRoll);
                // System.out.println ("UserPosition = " + userPosition + " CompPosition = " + compPosition + "\n");

				// Winner is the one who makes it onto or past the last square.
            	if (userPosition >= WINPOINT || compPosition >= WINPOINT)
            	{
                	if (userPosition >= WINPOINT) 
                	{ 
                    	// System.out.print ("User won game: userPosition = " + userPosition + "\n");
                    	UserWinner++; 
                	}
                	else if (compPosition >= WINPOINT) 
                	{
                    	// System.out.print ("Comp won game: compPosition = " + compPosition + "\n");
                    	CompWinner++;
                	}
                	else
                	{
                  	System.out.print ("\nplayGames: Winner error ");
                	}
                	icount++;
                	winner = true;
                	userPosition = userPositionSet;
                	compPosition = compPositionSet;
            	}
            	else
            	{
                	// keep playing
             	}
            }// end of each game

        }// end While on gametotal
        
        return gametype; // returns parameter
    }// end playGames method
    
    
    //------------------------------------------------------------------getDice Method------------------------------------------------------------------------------
    
    /*
    getDice method:
    
    This method generates two random numbers between 1 and 6, then
    adds them to get a final roll. Next it returns the value to be
    displayed on the screen.
    */
    public static int getDice (int diceRoll)
    {// start getDice class
        diceRoll = (int)(Math.random()*6 )+1 ; //creates dice roll number 1
		int move = diceRoll;
        return move; // return parameter move: this will give the final dice roll back to playGames
    }// end getDice 

    //-------------------------------------------------------------------getUserPos Method------------------------------------------------------------------------------
    /*
    getUserPos method:
    
    This method is responsible for checking if the USER is on
    top of a snake, or at the bottom of a ladder, and then
    adjusting the user's position to match the snake or
    ladders value.
    */
    public static int getUserPos (int userPosition, int userRoll) throws IOException 
    {// start getUserPos
     boolean utakeladder = true;   
	 
	 Random rand = new Random();
	 if (rand.nextFloat() > 0.50)
	 {utakeladder = true;}
	 else
	 {utakeladder = false;}
	  
     for (int i = 0; i < snakesStartArray.length; i++) {

    	   
    	   if (gametype.equals ("50ladder"))
    	   {
    	      if(userPosition == snakesStartArray[i]) //if position equals a snake
   	   		  {
   	           	userPosition = snakesEndArray[i]; // set new position
   	         	UserHitSnake++;
   	     	  }
   	     	  else if (userPosition == laddersStartArray[i]) //if position equals a ladder
   	     	  {
   		            // System.out.println ("getUserPos: 50ladder: User on ladder square " + userPosition + " take = " + utakeladder);
        			if (utakeladder)
        			{
            			userPosition = laddersEndArray[i]; // set new position
            			UserHitLadder++;
            			utakeladder = false;
        			}
        			else   // do nothing
        			{
            			UserHitLadder++;
            			utakeladder = true; 
        			}
				}
    	    }   
    	    else if (gametype.equals ("firstsnakeimmunity"))
    	    {
    	      if(userPosition == snakesStartArray[i]) //if position equals a snake
   	   		  {
   	           	userPosition = snakesEndArray[i]; // set new position
   	         	UserHitSnake++;
   	     	  }
   	     	  else if (userPosition == laddersStartArray[i]) //if position equals a ladder
   	     	  {
            	userPosition = laddersEndArray[i]; // set new position
            	UserHitLadder++;
        	  }    	    
    	    }
    	    else   // All other game types.
    	    {
    	      if(userPosition == snakesStartArray[i]) //if position equals a snake
   	   		  {
   	           	userPosition = snakesEndArray[i]; // set new position
   	         	UserHitSnake++;
   	     	  }
   	     	  else if (userPosition == laddersStartArray[i]) //if position equals a ladder
   	     	  {
            	userPosition = laddersEndArray[i]; // set new position
            	UserHitLadder++;
        	  }
    	    }
    	    
    	    if (userPosition < 0 || userPosition > (WINPOINT+6)) 
    	    {
   		        System.out.println ("An error has occured please reset the game!!!!!!");
	        }

     }  // On all snakes and ladders.
                        
        return userPosition; // return the final position to playGames.
    }// end getUserPos
    
    
   //-------------------------------------------------------------------getCompPos Method------------------------------------------------------------------------------
    /*
    getCompPos method:
    
    This method is responsible for checking if the Comp is on
    top of a snake, or at the bottom of a ladder, and then
    adjusting the Comp's position to match the snake or
    ladders value.
    */
    public static int getCompPos (int compPosition, int compRoll) throws IOException 
    {// start getCompPos
     boolean ctakeladder = true;   

	 Random rand = new Random();
	 if (rand.nextFloat() > 0.50)
	 {ctakeladder = true;}
	 else
	 {ctakeladder = false;}

     for (int i = 0; i < snakesStartArray.length; i++) {

    	   
    	   if (gametype.equals ("50ladder"))
    	   {
    	      if(compPosition == snakesStartArray[i]) //if position equals a snake
   	   		  {
   	           	compPosition = snakesEndArray[i]; // set new position
   	         	CompHitSnake++;
   	     	  }
   	     	  else if (compPosition == laddersStartArray[i]) //if position equals a ladder
   	     	  {
   		            // System.out.println ("getCompPos: 50ladder: Computer on ladder square " + compPosition + " take = " + ctakeladder);
        			if (ctakeladder)
        			{
            			compPosition = laddersEndArray[i]; // set new position
            			CompHitLadder++;
            			ctakeladder = false;
        			}
        			else   // do nothing
        			{
            			CompHitLadder++;
            			ctakeladder = true; 
        			}
				}
    	    }   
    	    else if (gametype.equals ("firstsnakeimmunity"))
    	    {
    	      if(compPosition == snakesStartArray[i]) //if position equals a snake
   	   		  {
	        	if (firsttimein)
     	    	{  // immunity - do nothing
        	   	    firsttimein = false;
   	        		CompHitSnake++;
        		}
        		else   // regular play
   		        {
		           	compPosition = snakesEndArray[0];
   		        	CompHitSnake++;
	            }
   	     	  }
   	     	  else if (compPosition == laddersStartArray[i]) //if position equals a ladder
   	     	  {
            	compPosition = laddersEndArray[i]; // set new position
            	CompHitLadder++;
        	  }    	    
    	    }
    	    else   // All other game types.
    	    {
    	      if(compPosition == snakesStartArray[i]) //if position equals a snake
   	   		  {
   	           	compPosition = snakesEndArray[i]; // set new position
   	         	CompHitSnake++;
   	     	  }
   	     	  else if (compPosition == laddersStartArray[i]) //if position equals a ladder
   	     	  {
            	compPosition = laddersEndArray[i]; // set new position
            	CompHitLadder++;
        	  }
    	    }
    	    
    	    if (compPosition < 0 || compPosition > (WINPOINT+6)) 
    	    {
   		        System.out.println ("An error has occured please reset the game!!!!!!");
	        }

     }  // On all snakes and ladders.
                        
        return compPosition; // return the final position to playGames.
    }// end getCompPos   
       
    //-------------------------------------------------------------------setupBoard Method------------------------------------------------------------------------------
    
    /*
    setupBoard method:
    */
    public static int setupBoard () throws IOException
    {// start setupBoard method    
        /*
        This while loop makes the board for the player to visualize what the
        game looks like, it uses a counter to increment or decrement by 1.

        */
        int irow = 0;
        
        System.out.println ("------------------------------------------------Game Board------------------------------------------------------------------------");
        for (int icount = 1; icount <= WINPOINT; icount++) {
            if (icount%6 == 0)
                {
                  irow = irow + 1;
                  System.out.print(icount + "\n"); // prints out the count with a line break
                }
                else
                {
                    System.out.print(icount+"\t");
                }
        }// end while

        // store the snakes and ladders location in the array
        snakesStartArray [0] = 12;
        snakesStartArray [1] = 14;
        snakesStartArray [2] = 17;
        snakesStartArray [3] = 31;
        snakesStartArray [4] = 35;
        laddersStartArray [0] = 3;
        laddersStartArray [1] = 5;
        laddersStartArray [2] = 15;
        laddersStartArray [3] = 18;
        laddersStartArray [4] = 21;

        snakesEndArray [0] = 2;
        snakesEndArray [1] = 11;
        snakesEndArray [2] = 4;
        snakesEndArray [3] = 19;
        snakesEndArray [4] = 22;
        laddersEndArray [0] = 16;
        laddersEndArray [1] = 7;
        laddersEndArray [2] = 25;
        laddersEndArray [3] = 20;
        laddersEndArray [4] = 32;

        System.out.println();
        System.out.println ("--------------------------------------Ladders and Snakes Locations ----------------------------------------------------------------");
        System.out.printf("SnakeStart  SnakeEnd  LadderStart  LadderEnd \n");
        for (int i = 0; i < snakesStartArray.length; i++) {
         	if (i > 0) {
           	   System.out.print("\n");
        	}
        	   System.out.printf("%8d",snakesStartArray[i]);
        	   System.out.printf("%10d",snakesEndArray[i]);
        	   System.out.printf("%12d",laddersStartArray[i]);
        	   System.out.printf("%14d",laddersEndArray[i]);

        }

        System.out.println();
        System.out.println ("----------------------------------------------------------------------------------------------------------------------------------");
	return 0;
	}// end setupBoard       
    
}//end class
