package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0 extends ICWarsArea {

	@Override
	public String getTitle() {
		return "icwars/Level0";
	}

	@Override
	public DiscreteCoordinates getPlayerSpawnPosition() {
		return new DiscreteCoordinates(0, 0);
	}

	@Override
	public DiscreteCoordinates getEnemySpawnPosition() {
		return new DiscreteCoordinates(7, 4);
	}

	@Override
	protected void createArea() {
		registerActor(new Background(this));
	}
}