package dk.sdc.rpg;


public class Orc extends Creature {
	public Orc(int health, int agility, int strength, int intelligence) {
		super(health, agility, strength, intelligence);
	}

	@Override
	public void onCreate() {
        setBitmap(Creature.ORC);
	}

	@Override
	public void takeDamage(int damage) {
		super.takeDamage(damage);
	}
}
