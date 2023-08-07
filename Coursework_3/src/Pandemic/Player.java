package Pandemic;

import java.util.ArrayList;

public class Player {
	String Current_city;
	ArrayList<Cards> Card_in_hand;
	ArrayList<String> Cure_found;
	boolean Cure_available;
	
	Player()
	{
		this.Current_city = "Atlanta";
		this.Card_in_hand = new ArrayList<Cards>();
		this.Cure_found = new ArrayList<String>();
		this.Cure_available = false;
	}

}
