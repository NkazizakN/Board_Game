package Pandemic;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;


public class Board {
	
	private static Cards city_cards[] = new Cards[48];
	private static Cards infection_cards[] = new Cards[48];
	private static int no_of_outbreaks=0;
	public static City[] cities = new City[48];
	private static Random rand = new Random();
	public static Scanner s = new Scanner(System.in);
	private static Player player = new Player();

	public static void main(String[] args) 
	{
		System.out.println("Welcome To the Pandemic Board Game....\n\n");
		read_city_file();
		Runner();
		
		
	}
	public static void Runner()
	{
		
		System.out.println("Hello I am Kasey I will be helping you through the game. You will start from Atlanta.");
		System.out.println("Your Objective is to find cure and treat diseases from all the citie that have been infected.");
		System.out.println("There are 4 different types of diseases. Once you find a cure for a disease there will be no more infections of that disease type.");
		System.out.println("You will have 2 moves in each turn and every turns a new infection will spread.");
		System.out.println("Tip : Its better to find a cure than treat every disease as there will new infections or perhaps an outbreak.");
		System.out.println("Start or Exit");
		String Choice = s.nextLine();
		if(Choice.trim().toLowerCase().equals("start"))
		{
			System.out.println("Seven cities are infected.\n");
			for(int i=0; i<7; i++)
			{
				int random = rand.nextInt(48);
				cities[random].no_of_disease++;
				//cities[random].no_of_disease++;
				System.out.println(cities[random].name);
			}
			System.out.println("\n");
			play();			
		}
		else
		{
			System.out.println("Thank you playing, GoodBye Now..");
			return;
		}
		

	}
	public static void All_cures_found()
	{
		int number_of_cures = 0;
		for(int cure=0; cure<player.Cure_found.size();cure++)
		{
			if(player.Cure_found.get(cure).equals("Black") || player.Cure_found.get(cure).equals("Blue") || player.Cure_found.get(cure).equals("Red")|| player.Cure_found.get(cure).equals("Yellow"))
			{
				number_of_cures++;
			}
		}
		if(number_of_cures == 4)
		{
			System.out.println("\n\n CONGRARULATIONS..!!! YOU HAVE FOUND CURES FOR ALL THE DISEASES. NO MORE NEW INFECTIONS CAN HAPPEN.");
			System.out.println("You have found all the cures. Rest of the disease will be trated automatically.");
			System.out.println("\n\n !_!_!_!_!_!_!_!_!_!_!_!_!_!_!_!_!CONGRATILATIONS!_!_!_!_!_!_!_!_!_!_!_!_!_!_!_!_!_!_!_!_! ");
			System.out.println("\n\n      <<<<<<<<<<<<<<<<<<<---------------- YOU WON ----------------->>>>>>>>>>>>>>>>>>> ");
			System.exit(2);

		}
	}
	public static void Is_game_end()
	{
		if(no_of_outbreaks>=7)
		{
			System.out.println("\n\nThere have been more than 6 outbreaks. You lost this time around. BETTER LUCK NEXT TIME.....\n\n");
			System.exit(0);
		}
		int flag_1 = 0;
		for(int city=0;city<48;city++)
		{
			if(city_cards[city].Name != " ")
			{
				flag_1++;
			}
		}
		if (flag_1==0)
		{
			System.out.println("\n\nYou have ran out of cards. You failed to fix the pandemic. BETTER LUCK NEXT TIME...\n\n");
			System.exit(1);
		}
		
		
	}
	public static void pick_a_card()
	{
		if(player.Card_in_hand.size()<7)
		{
			boolean ch =false;
			while(!ch)
			{
				int random = rand.nextInt(48);
				if(city_cards[random].Name != " " )
				{
					if(player.Card_in_hand.size()>6)
					{
						System.out.println("You are allowed 7 cards in your hand.");
						System.out.println("The current card is "+city_cards[random].Name + " and the color is "+ city_cards[random].Color);
						System.out.println("If you would like to keep this card then you must discard a card from hand");
						System.out.println("Would you like to keep this card (yes/no)");
						String choice = s.nextLine();
						if(choice.trim().toLowerCase().equals("yes"))
						{
							System.out.println("Current cards in hand are :");
							for(int cards=0;cards<player.Card_in_hand.size();cards++)
							{
								System.out.println(player.Card_in_hand.get(cards).Name + " Colour " + player.Card_in_hand.get(cards).Color);
							}
							System.out.print("Please enter the name of card you would like to discard : ");
							String dis_choice = s.nextLine();
							for(int cards=0; cards<player.Card_in_hand.size();cards++)
							{
								if(dis_choice.trim().equals(player.Card_in_hand.get(cards).Name.trim().toLowerCase()))
								{
									Cards obj = new Cards();
									obj.Name = city_cards[random].Name;
									obj.Color = city_cards[random].Color;
									player.Card_in_hand.set(cards, obj);
									ch = true;
									System.out.println("The card you picked in this turn is : " + city_cards[random].Name);
									city_cards[random].Name = " ";
									city_cards[random].Color = " ";
									
								}
							}
						}
						else 
						{
							city_cards[random].Name = " ";
							city_cards[random].Color = " ";
						}
					}
					else 
					{
						Cards obj = new Cards();
						obj.Name = city_cards[random].Name;
						obj.Color = city_cards[random].Color;
						player.Card_in_hand.add(obj);
						ch = true;
						System.out.println("The card you picked in this turn is : " + obj.Name);
						city_cards[random].Name = " ";
						city_cards[random].Color = " ";
						
					}
					
				}
				
			}
			
			
		}
		
	}
	public static void play()
	{
		String choice = "playing";
		int moves = 0;
		while(no_of_outbreaks<7)
		{
			moves++;
			All_cures_found();
			Is_game_end();
			pick_a_card();
			for(int turn=0; turn<4;turn++)
			{
				System.out.print("Your move...\n-> Drive\n-> Fly\n-> Treat Disease\n-> Find Cure\n-> Status\n-> Exit\n-->>");
				choice = s.nextLine();
				if(choice.trim().toLowerCase().equals("drive"))
				{
					Drive();
				}
				else if(choice.trim().toLowerCase().equals("fly"))
				{
					Fly();
				}
				else if(choice.trim().toLowerCase().equals("treat disease"))
				{
					Treat_disease();
				}
				else if(choice.trim().toLowerCase().equals("find cure"))
				{
					Find_cure();
				}
				else if(choice.trim().toLowerCase().equals("status"))
				{
					Status(moves,turn);
				}
				else if(choice.trim().toLowerCase().equals("exit"))
				{
					
					System.out.println("Better Luck Next Time. Thank you playing, GoodBye Now..");
					System.exit(0);
				}
				else
				{
					System.out.println("That was not a valid choice. Please choose an action..\n");
				}
			}
			pick_infection_card();
				

		}
		
	}
	public static void outbreak_at(String cty)
	{
		ArrayList<String> connected = new ArrayList<String>();
		for(int city=0; city<48;city++)
		{
			for(int b=0; b<cities[city].connected_city.size(); b++)
			{
				if(cities[city].connected_city.get(b).equals(player.Current_city))
				{
					connected.add(cities[city].name);
				}
			}
		}
		for(int k=0; k<48; k++)
		{
			if(cities[k].name.equals(player.Current_city))
			{
				for(int j=0; j<cities[k].connected_city.size();j++)
				{
					connected.add(cities[k].connected_city.get(j));
				}
			}
		}
		
		
	}
	public static void pick_infection_card()
	{
		int random = rand.nextInt(48);
		boolean ch = false;
		while(!ch)
		{
			if(infection_cards[random].Name!= " " )
			{
				for(int city=0; city < 48; city++)
				{
					if(cities[city].name.equals(city_cards[random].Name))
					{
						cities[city].no_of_disease++;
						System.out.println("\n\nThe infection card you have choose is " + cities[city].name);
						if(cities[city].no_of_disease > 3)
						{
							outbreak_at(cities[city].name);
							cities[city].no_of_disease = 2;
							
						}
						
						ch = true;
					}
				}
			}
		}
		
	}
	public static void Status(int moves,int turns)
	{
		System.out.println("\n\nCurrent game status -->");
		System.out.println("Your Current Location is : " + player.Current_city);
		System.out.println("Your total number of moves so far : " + moves + ". It's turn number "+turns + "in your current move.");
		System.out.println("Total number of outbreaks so far : " + no_of_outbreaks);
		System.out.println("Cures available : " + player.Cure_available);
		int total_diseases = 0;
		for(int city=0; city<48; city++)
		{
			total_diseases += cities[city].no_of_disease;
		}
		System.out.println("Total number of diseases on the board are : "+total_diseases);
		System.out.print("The cards in your hand are : ");
		for(int c=0;c<player.Card_in_hand.size();c++)
		{
			System.out.println(player.Card_in_hand.get(c).Name + "\t" + player.Card_in_hand.get(c).Color);
		}
		
		System.out.print("\nThe cures you have discovered are : ");
		int flag = 0;
		for(int card=0;card<player.Cure_found.size();card++)
		{
			flag++;
			System.out.println(player.Cure_found.get(card)+ "\t");
		}
		if(flag==0)
		{
			System.out.print("None");
		}
		int number_of_cards = 0;
		for(int card=0;card<city_cards.length ;card++)
		{
			if(city_cards[card].Name != " ")
			{
				number_of_cards++;
			}
		}
		System.out.println("\nNumber of cards left to be picked are " + number_of_cards);
		System.out.println("\n\nTip: If you run out of cards you loose.\nTip: If the number of outbreaks are more than 7 you loose.\n\n");
		System.out.println("\n Cities that are currently infected are : ");
		for(int city=0; city<cities.length;city++)
		{
			if(cities[city].no_of_disease > 0)
			{
				System.out.println(cities[city].name + "\t" + cities[city].no_of_disease + " diseases");
			}
		}
	}
	private static void remove_all_disease(String colour)
	{
		for(int city=0;city<48;city++)
		{
			if(infection_cards[city].Color.equals(colour))
			{
				infection_cards[city].Name = " ";
				infection_cards[city].Color = " ";
			}
		}
	}
	
	public static void Find_cure()
	{
		System.out.println("You need atleast 5 cards in your hand of same color to find a cure");
		int blue =0;
		int black =0;
		int red = 0;
		int yellow =0;
		for(int cards=0;cards<player.Card_in_hand.size();cards++)
		{
			if(player.Card_in_hand.get(cards).Color.equals("Blue"))
			{
				blue++;
			}
			else if(player.Card_in_hand.get(cards).Color.equals("Black"))
			{
				black++;
			}
			else if(player.Card_in_hand.get(cards).Color.equals("Yellow"))
			{
				yellow++;
			}
			else if(player.Card_in_hand.get(cards).Color.equals("Red"))
			{
				red++;
			}
		}
		if(blue>4)
		{
			player.Cure_found.add("Blue");
			remove_all_disease("Blue");
			System.out.println("\nCongratulations...!! You have found a cure for the Blue disease");
			System.out.println("Note: Even though no more Blue diseases will appear you will still have to treat the remaining Blue diseases on the board");
		}
		else if(black>4)
		{
			player.Cure_found.add("Black");
			remove_all_disease("Black");
			System.out.println("\nCongratulations...!! You have found a cure for the Black disease");
			System.out.println("Note: Even though no more Black diseases will appear you will still have to treat the remaining Black diseases on the board");

		}
		else if(red>4)
		{
			player.Cure_found.add("Red");
			remove_all_disease("Red");
			System.out.println("\nCongratulations...!! You have found a cure for the Red disease");
			System.out.println("Note: Even though no more Red diseases will appear you will still have to treat the remaining Red diseases on the board");

		}
		else if(yellow>4)
		{
			player.Cure_found.add("Yellow");
			remove_all_disease("Yellow");
			System.out.println("\nCongratulations...!! You have found a cure for the Yellow disease");
			System.out.println("Note: Even though no more Yellow diseases will appear you will still have to treat the remaining Yellow diseases on the board");
		
		}
		else
		{
			System.out.println("No cures are available at the time. Please check your status option for 'Cure Avaiable' indicator");
		}
		
		
	}
	public static void Treat_disease()
	{
		int pos = get_city_location_in_array(player.Current_city);
		System.out.println("You are currently in "+player.Current_city+". And there are "+cities[pos].no_of_disease+"disease");
		if(cities[pos].no_of_disease>0)
		{
			cities[pos].no_of_disease = cities[pos].no_of_disease -1;
			System.out.println("You have treated one instance of disease from this city.");
			System.out.println("No is disease in this city are now :" + cities[pos].no_of_disease);
		}
		else
		{
			System.out.println("The is no diseases to be trated in this city. You lost 1 turn. You can check status to find out more ");
		}
		
	}
	public static void Fly()
	{
		System.out.println("You can fly to cities of which card you have in hand. Current card in hand are :\n");
		
		for(int cards=0; cards<player.Card_in_hand.size();cards++)
		{
			System.out.println(player.Card_in_hand.get(cards).Name);
		}
		
		
		System.out.println("Which city would you like to fly to ?");
		String city_choice=s.nextLine();
		int flag=0;
		for(int cards=0; cards<player.Card_in_hand.size();cards++)
		{
			if(city_choice.trim().toLowerCase().equals(player.Card_in_hand.get(cards).Name.trim().toLowerCase()))
			{
				flag++;
				player.Current_city = player.Card_in_hand.get(cards).Name;
				System.out.println("you have flown to "+ player.Current_city);
			}
			
		}
		if(flag==0)
		{
			System.out.println("That was an invalid choice. You lost 1 turn");
		}
	}
	public static void Drive()
	{
		System.out.println("\nThe places you can drive from "+player.Current_city+" are :");
		ArrayList<String> connected = new ArrayList<String>();
		for(int city=0; city<48;city++)
		{
			for(int b =0; b<cities[city].connected_city.size(); b++)
			{
				if(cities[city].connected_city.get(b).equals(player.Current_city))
				{
					connected.add(cities[city].name);
					System.out.println(cities[city].name);
				}
			}
		}
		for(int k=0; k<48; k++)
		{
			if(cities[k].name.equals(player.Current_city))
			{
				for(int j=0; j<cities[k].connected_city.size();j++)
				{
					connected.add(cities[k].connected_city.get(j));
					System.out.println(cities[k].connected_city.get(j));
				}
			}
		}
		
		System.out.println("\nWhich city are you travelling to : ");
		String city_to=s.nextLine();
		int flag =0;
		String city_found = "";
		for(int i=0; i<connected.size();i++)
		{
			if(city_to.trim().toLowerCase().equals(connected.get(i).trim().toLowerCase()))
			{
				player.Current_city = connected.get(i);
				System.out.println("Player current city " + player.Current_city);
				city_found = connected.get(i);
				flag++;
			}
		}
		if(flag == 0)
		{
			System.out.println("That is an invalid move. You lost 1 turn");
		}
		else
		{
			player.Current_city = city_found;
		}
		
	}
	public static int get_city_location_in_array(String city)
	{
		for(int c=0; c<48;c++)
		{
			if(cities[c].name.trim().toLowerCase().equals(city.trim().toLowerCase()))
			{
				return c;
			}
		}
		return -1;
	}
	public static void read_city_file()
	{
		int count = 0;
	    int number_of_city = 0;
	   int number_of_connections = 0;
		try {
		      File myObj = new File("City.txt");
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) 
		      {
		    	  String data = myReader.nextLine();
		    	  ++count;
		    	  String color[] = {"Red","Blue","Yellow","Black"};
		    	  
		    	  if (count == 1)
		    	  {
		    		  number_of_city = Integer.parseInt(data);
		    	  }
		    	  else if(count == 2)
		    	  {
		    		  number_of_connections = Integer.parseInt(data);
		    	  }
		    	  //read city names from the file and populate the required arrays to hold the data from file 
		    	  else if(count <= number_of_city+2 && count>2)
		    	  {
		    		  int random = rand.nextInt(4);
		    		  City obj = new City(data);
		    		  obj.City_color = color[random];
		    		  obj.name = data;
		    		  Cards ob = new Cards();
		    		  ob.Name = data;
		    		  ob.Color = obj.City_color;
		    		  infection_cards[count-3] = ob;
		    		  city_cards[count-3] = ob;
		    		  cities[count-3] = obj;
		    		  
		    	  }
		    	  //updating each city object with the connectivity list
		    	  else if(count>=number_of_city)
		    	  {
		    		  String[] tokens=data.split(";");
		    		  for(int city =0; city<48; city++)
		    		  {
		    			  
		    			  if(cities[city].name.equals(tokens[0]))
		    			  {
		    				  cities[city].connected_city.add(tokens[1]);
		    			  }
		    			  
		    		  }
		    	  }
		      }
		      myReader.close();
		    } 
		catch (FileNotFoundException e) 
		{
			System.out.println("An error occurred.");
		    e.printStackTrace();
		}       
	}//end of read city file function
	
}
