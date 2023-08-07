package Pandemic;

import java.util.ArrayList;

public class City {
	String name;
	String City_color;
	int no_of_disease;
	ArrayList<String> connected_city;
	
	City(String data)
	{
		connected_city = new ArrayList<String>();
		no_of_disease = 0;
		name = data;
	}
}
