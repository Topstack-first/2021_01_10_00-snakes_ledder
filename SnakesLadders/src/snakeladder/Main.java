package snakeladder;

import java.rmi.server.ServerNotActiveException;
import java.util.Scanner;

import snakeladder.GamePane.Player;

public class Main {
	private static GamePane gamePane = new GamePane();
	
	
	public static void main(String[] args) {
		
		//numbers of biscuit and stick
		int numberOfBiscuits = 0;
		int numberOfSticks = 0;
		
		
		System.out.println("-------------------------------------");
		System.out.println("---------SNAKES AND LADDERS----------");
		System.out.println("-------------------------------------");
		
		//input scanner
		Scanner scanner = new Scanner(System.in);
		
		do
		{
			//initialize game
			System.out.println("");
			
			System.out.println("-------------------------------------");
			System.out.println("--------------GAME  OTIONS-----------");
			System.out.println("---------------a.NEW GAME------------");
			System.out.println("---------------b.QUIT GAME-----------");
			System.out.println("-------------------------------------");
			System.out.println("");
			
			//input game option
			String option = scanner.nextLine();
			if(option.equals("b"))
			{
				System.out.println("-------------------------------------");
				System.out.println("-------Quited Game Successfully-------");
				System.out.println("-------------------------------------");
				break;
			}
			
			//start new game
			if(option.equals("a"))
			{
				do {
					//input number of biscuits
					System.out.println("Input Number of Biscuits [ < 50 ]");
					if(scanner.hasNextInt())
					{
						numberOfBiscuits = scanner.nextInt();	
					}
					else if(scanner.hasNextLine())
					{
						System.out.println("error! input numbers only!");
						scanner.nextLine();
						continue;
					}
					if(numberOfBiscuits >0 && numberOfBiscuits <= 50)
					{
						break;
					}
					else
					{
						System.out.println("you cannot enter any number greater than 50, please try again");
					}
				}while(true );
				
				do {
					//input number of sticks
					System.out.println("Input Number of Sticks [ < 50 ]");
					if(scanner.hasNextInt())
					{
						
						numberOfSticks = scanner.nextInt();
					}
					else if(scanner.hasNextLine())
					{
						System.out.println("error! input numbers only!");
						scanner.nextLine();
						continue;
					}
					if(numberOfSticks >0 && numberOfSticks <= 50)
					{
						break;
					}
					else
					{
						System.out.println("you cannot enter any number greater than 50, please try again");
					}
				}while(true );
				
				
				//set game parameters
				gamePane.setGameSetting(numberOfBiscuits,numberOfSticks);
				
				//init game pane
				gamePane.init();
				
				//print game pane
				gamePane.printBoard();
				//number of players
				int numberOfPlayers = 0;
				do {
					System.out.println("");
					System.out.println("Input Number of Players [1 - 3]");
					System.out.println("");
					
					//input number of players
					if(scanner.hasNextInt())
					{
						numberOfPlayers = scanner.nextInt();
					}
					else if(scanner.hasNextLine())
					{
						System.out.println("error! input numbers only!");
						scanner.nextLine();
						continue;
					}
					if(numberOfPlayers >0 && numberOfPlayers <= 3)
					{
						break;
					}
					else
					{
						System.out.println("you cannot enter any number greater than 3, please try again");
					}
				}while(true );
				
				
				int i=0;
				
				//add players
				do
				{
					GamePane.Player player = gamePane.createNewPlayer();
					
					System.out.println("Input Player"+(i+1)+"'s Colour");
					
					player.colour = "";
					
					do
					{
						//input player's color
						player.colour = scanner.nextLine();	
					}
					while(player.colour == "");
					
					gamePane.addNewPlayer(player);
					i++;
				}
				while(i < numberOfPlayers);
				
				
				System.out.println("");
				System.out.println("-------------------------------------");
				System.out.println("----Started New Game Successfully----");
				System.out.println("-------------------------------------");
				System.out.println("");
				
				//game progress
				do
				{
					System.out.println("");
					System.out.println(gamePane.getCurrentColor().colour+"[Action=Enter]: roll dice to get dice number...");
					
					//roll dice
					scanner.nextLine();

					//play current player and get win state
					boolean winState = gamePane.playCurColor();
					System.out.println(gamePane.getCurrentColor().colour+" got "+gamePane.getRolledNumber()+"  From Rolling");
					System.out.println(" So Has gone From " +(gamePane.getCurrentColor().playingPosition-gamePane.getRolledNumber()+1)+" To "+(gamePane.getCurrentColor().playingPosition+1));
					
					//game ended
					if(winState)
					{
						System.out.println("");
						System.out.println(gamePane.getCurrentColor().colour+" Wins. He/She reached at "+(gamePane.getCurrentColor().playingPosition+1));
						System.out.println("");
						System.out.println("-------------------------------------");
						System.out.println("-------Ended Game Successfully-------");
						System.out.println("-------------------------------------");
						break;
					}
					//go to next player
					gamePane.next();
					
				}while(true);
				
			}
		}while(true);
	}

}
