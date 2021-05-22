package snakeladder;


import java.util.Random;
import java.util.ArrayList;


public class GamePane {
	//dice generator
	public DiceNumberGenerator diceGenerator = null;
	
	//all players in game
	public ArrayList<Player> allPlayers = null;
	
	//game pane
	public int[] pane = null;
	
	//number of snakes
	public int numberOfSnakes = 8;
	public int lengthOfSnake = 20;
	
	//number of ladders
	public int numberOfLadders = 9;
	public int lengthOfLadder = 21;
	
	//all biscuits
	public ArrayList<Integer> biscuits = new ArrayList<Integer>();
	public int numberOfBiscuits = 0;
	
	//all big sticks
	public ArrayList<Integer> sticks = new ArrayList<Integer>();
	public int numberOfSticks = 0;
	
	//current player's color
	public int currentColor = 0;
	
	//constructor
	public GamePane() 
	{
		diceGenerator = new DiceNumberGenerator();
		allPlayers = new ArrayList<Player>();
	}
	
	//init function
	public void init()
	{
		pane = new int[100];
		
		//create random class
		Random randomNumberGenerator = new Random();	
		
		//init game pane
		for(int index=0;index<100;index++)
		{
			pane[index] = index;
		}
		//clear all players
		allPlayers.clear();
		
		currentColor = 0;
		
		
		//add all biscuits in pane
		for(int i=0;i<this.numberOfBiscuits;i++)
		{
			int rand = Math.abs(randomNumberGenerator.nextInt(99));
			this.biscuits.add(rand);
		}
		//add all big sticks in pane
		for(int i=0;i<this.numberOfSticks;i++)
		{
			int rand = Math.abs(randomNumberGenerator.nextInt(99));
			this.sticks.add(rand);
		}
		
		//add snakes
		if(!(lengthOfSnake >= 99))
		{
			
			for(int index=0; index < numberOfSnakes; index++)
			{
				
				int randTail = Math.abs(randomNumberGenerator.nextInt(99 - lengthOfSnake));
				
				int randHead = randTail + lengthOfSnake;
				
				if(pane[randTail] == randTail && pane[randHead] == randHead)
				{
					pane[randHead] = randTail;
				}	
			}	
		}
		//add ladders
		if(!(lengthOfLadder >= 99))
		{
			
			for(int index=0; index < numberOfLadders; index++)
			{
				
				int randBttom = Math.abs(randomNumberGenerator.nextInt(99 - lengthOfLadder));
				
				int randTop = randBttom + lengthOfLadder;
				
				if(pane[randBttom] == randBttom && pane[randTop] == randTop)
				{
					pane[randBttom] = randTop;
				}	
			}	
		}
	}
	//print game pane to console
	public void printBoard()
	{
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				System.out.print((pane[i * 10 + j]+1)+",");
			}
			System.out.print("\n");
		}
		
	}

	//set game difficult level by number of biscuits and number of sticks
	public void setGameSetting(int paramBiscuitsCount, int paramSticksCount)
	{
		
		this.numberOfBiscuits = paramBiscuitsCount;
		this.numberOfSticks = paramSticksCount;
	}
	//go to next player
	public void next()
	{
		//if dice number is 6, the player continues
		if(diceGenerator.getRolledNumber() < 6)
		{
			int numberOfPlayers = allPlayers.size();
			currentColor = (currentColor + 1) % numberOfPlayers;			
		}
	}
	//get current player
	public Player getCurrentColor() {
		return allPlayers.get(currentColor);
	}
	//add new player to game 
	public void addNewPlayer(Player player)
	{
		allPlayers.add(player);
	}
	//play current player
	public boolean playCurColor() {
		//get current player
		Player currentColor = getCurrentColor();
		
		//position of current player
		int posOfCurrentColor = currentColor.playingPosition;
		
		//roll dice
		int rolledNum = diceGenerator.roll();
		
		//value of current player's pos
		int valueOnPlacedPos = posOfCurrentColor + rolledNum;
		
		
		if(valueOnPlacedPos > 99)
		{
			return false;
		}
		
		//value of pane
		int panePosVal = pane[valueOnPlacedPos];
		posOfCurrentColor = panePosVal;
		
		//check sticks
		if(this.sticks.contains(valueOnPlacedPos))
		{
			System.out.println(currentColor.colour+" got a stick");
			currentColor.hasStick = true;
		}
		//check biscuits
		if(this.biscuits.contains(valueOnPlacedPos))
		{
			System.out.println(currentColor.colour+" got a biscuit");
			currentColor.hasBiscuit = true;
		}
		
		//met snake
		if(panePosVal < valueOnPlacedPos)
		{
			System.out.println("there is a snake from "+(valueOnPlacedPos+1)+" to " + (panePosVal+1)+" in here!");
			if(currentColor.hasBiscuit)
			{
				posOfCurrentColor = valueOnPlacedPos;
				currentColor.hasBiscuit = false;
				System.out.println(currentColor.colour+" lost his/her biscuit");
			}
		}
		
		//met ladder
		if(panePosVal > valueOnPlacedPos)
		{
			System.out.println("there is a ladder from "+(valueOnPlacedPos+1)+" to " + (panePosVal+1));
			if(currentColor.hasStick && panePosVal < 90)
			{
				posOfCurrentColor += 10;
				currentColor.hasStick = false;
				System.out.println(currentColor.colour+" lost his/her stick");
			}
		}
		//set current player's position
		currentColor.playingPosition = posOfCurrentColor;
		
		//game ends
		if(currentColor.playingPosition == 99)
		{
			return true;
		}
	
		return false;
	}
	//get rolled dice number
	public int getRolledNumber() {
		return diceGenerator.getRolledNumber();
	}
	
	//class for dice number generator
	private class DiceNumberGenerator {
		private int number = 1;
		public int getRolledNumber() {
			return number;
		}
		public int roll() {
			Random randomGenerator = new Random();
			
			number = Math.abs(randomGenerator.nextInt()) % 6 + 1;
			
			return number;
		}
	}
	//class for player
	public class Player {
		public String colour = "player";
		public int playingPosition = -1;
		public boolean hasBiscuit = false;
		public boolean hasStick = false;
		
	}
	//create new player
	public Player createNewPlayer()
	{
		return new Player();
	}
}
