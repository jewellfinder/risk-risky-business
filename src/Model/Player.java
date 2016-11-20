package Model;

import java.util.ArrayList;

public abstract class Player {
	private String name;
	private Faction faction;
	private int availTroops;
	private ArrayList<Country> myCountries;
	private ArrayList<Card> myCards;
//  private Continent[] allContinents; TODO	
	

	private void getTroops(){
		int addedTroops = myCountries.size()/3;
		//For when the continent class is finished
		//the added troops changes based on how many
		//continents you play
		/*for (Continent con : allContinents){
			if (this.equals(con.getOccupier()))
				addedTroops += con.getOwnershipBonus();
		}*/
	}//end getTroops
	
	public Player()
	{
		this.name = null;
		this.faction = null;
		this.availTroops = 0;
		this.myCountries = new ArrayList<>();
		this.myCards = new ArrayList<>();
	}

	public void occupyCountry(Country occupyMe)
	{
		myCountries.add(occupyMe);
	}
	
	public abstract ArrayList<Card> playCards();
	public abstract Country attack();
	public abstract void rearrangeTroops();
}
