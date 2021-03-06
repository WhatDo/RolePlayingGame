package dk.sdc.rpg;


public class Opgave {

	/**
	 * Denne metode skal returnere en instans af et creature.
	 * Da Human, Orc og Drage nedarver fra Creature, kan i returnere en instans af dem.
	 * F.eks "return new Dragon(50, 10, 15, 25)"
	 */
	public Creature createCreature() {
		return null;
	}

	/**
	 * Denne metode skal returnere den skade characteren giver
	 * ved brug af charaterens attributter som strength og agility.
	 * Brug metodekald som "creature.getStrength()" og
	 * "creature.getAgility()"
	 */
	public int calculateDamage(Creature creature) {
		return 0;
	}


	/**
	 * Denne metode bruges, når man tager skade. Man kan f.eks. bruge agility
	 * til at beregne om man undgik slaget, eller som armor.
	 */
	public int calculateDamageTaken(Creature creature, int rawDamage) {
		return rawDamage;
	}


	/**
	 * Denne metode definerer hvad der sker når der sker et angreb.
	 * Der returneres false så længe spillet fortsætter.
	 *
	 * Brug "defender.takeDamage(5)"
	 * 		"attacker.getDamage()"
	 * 		"defender.getHealth()"
	 */
	public boolean attack(Creature attacker, Creature defender) {
		return false;
	}


	/**
	 * Beregn skaden der bliver givet, hvis det er et strong attack
	 */
	public int calculateStrongDamage(Creature creature) {
		return 0;
	}


	/**
	 * Denne metode er den samme som attack. Her er der blot trykket
	 * på strong attack knappen i stedet.
	 */
	public boolean strongAttack(Creature attacker, Creature defender) {
		return false;
	}
}
