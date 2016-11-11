package Model;

public class Map {
	
	private Country countries[] = new Country[50];
	
	public Map()
	{
		fillCountries();
	}

	private void fillCountries()
	{
		//this method is going to suck
		countries[0] = new Country("The Wall", 6.75, 3.5, Continents.BLUE);
		countries[1] = new Country("Skagos", 10, 3, Continents.BLUE);
		countries[2] = new Country("Wolfswood", 3.75,7, Continents.BLUE);
		countries[3] = new Country("Winterfell", 7, 8.5, Continents.BLUE);
		countries[4] = new Country("The Rills", 2.5,10.5, Continents.BLUE);
		countries[5] = new Country("The Neck", 5.5,11.75, Continents.BLUE);
		countries[6] = new Country("The Flint Cliffs", 3.25,14, Continents.BLUE);
		countries[7] = new Country("The Grey Cliffs", 9.75,5.5, Continents.BLUE);
		countries[8] = new Country("The Vale", 8,18, Continents.GREEN);
		countries[9] = new Country("Riverlands",3.5,18.5,Continents.GREEN);
		countries[10] = new Country("Iron Islands",2,17,Continents.GREEN);
		countries[11] = new Country("Westerlands",3.5,21.75,Continents.GREEN);
		countries[12] = new Country("Crownlands",7.25,21.25,Continents.GREEN);
		countries[13] = new Country("The Reach",5.5,24,Continents.ORANGE);
		countries[14] = new Country("Shield Lands", 3,25,Continents.ORANGE);
		countries[15] = new Country("Whispering Sound", 2.75,28.5,Continents.ORANGE);
		countries[16] = new Country("Storm Lands", 7.75,25, Continents.ORANGE);
		countries[17] = new Country("Red Mountains", 5.5,28.5,Continents.ORANGE);
		countries[18] = new Country("Dorne", 7,30.75,Continents.ORANGE);
		countries[19] = new Country("Braavosi Coastland",13.5,17,Continents.PINK);
		countries[20] = new Country("Andalos",13.5,21.5,Continents.PINK);
		countries[21] = new Country("Hills of Norvos",15.5,19.25,Continents.PINK);
		countries[22] = new Country("Rhoyne Lands", 17.5,21.25,Continents.PINK);
		countries[23] = new Country("Forrest of Qohor", 19.5,21,Continents.PINK);
		countries[24] = new Country("The Golden Fields",15.25,25.75,Continents.PINK);
		countries[25] = new Country("The Disputed Lands", 14,29.5,Continents.PINK);
		countries[26] = new Country("Rhoynian Veld",19,24.75, Continents.RED);
		countries[27]= new Country("Sar Mell", 18.75,29.52, Continents.RED);
		countries[28] = new Country("Western Waste", 21,27,Continents.RED);
		countries[29] = new Country("Sea of Sighs", 21.5,29.75,Continents.RED);
		countries[30] = new Country("Elyria", 23,33.5,Continents.RED);
		countries[31] = new Country("Valyria", 22,37.5,Continents.RED);
		countries[32] = new Country("Sarnor",23,18.25,Continents.YELLOW);
		countries[33] = new Country("Parched Fields", 23.5,24,Continents.YELLOW);
		countries[34] = new Country("Abandoned Lands", 26,19.25,Continents.YELLOW);
		countries[35] = new Country("Western Grass Sea", 27.5,25.25,Continents.YELLOW);
		countries[36] = new Country("Kingdoms of the Jfeqevron", 29.25,20,Continents.YELLOW);
		countries[37] = new Country("Eastern Grass Sea", 31.5,23.75,Continents.YELLOW);
		countries[38] = new Country("The Footprint", 32.5,16.5,Continents.YELLOW);
		countries[39] = new Country("Vaes Dothrak", 33.5,20,Continents.YELLOW);
		countries[40] = new Country("Realms of Jhogrvin", 37.25,17.5,Continents.YELLOW);
		countries[41] = new Country("Ibben", 33.5,8.5,Continents.YELLOW);
		countries[42] = new Country("Painted Mountains", 25,27.5,Continents.BLACK);
		countries[43] = new Country("Slaver's Bay", 28.5,30.25,Continents.BLACK);
		countries[44] = new Country("Lhazar",31,28.75,Continents.BLACK);
		countries[45] = new Country("Samyrian Hills", 34.5,25.75,Continents.BLACK);
		countries[46] = new Country("Bayasabhad", 34.75,29.5,Continents.BLACK);
		countries[47] = new Country("Ghiscar", 28.75,34.75,Continents.BLACK);
		countries[48] = new Country("The Red Waste", 31.5,32.5,Continents.BLACK);
		countries[49] = new Country("Qarth", 35.25,32.75,Continents.BLACK);
	}
	
	public Country[] getCountries()
	{
		return countries;
	}
	
	

}