package ch.epfl.cs107.play.game.icwars.handler;

import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;

public interface ICWarsInteractionVisitor extends AreaInteractionVisitor {

    default void interactWith(RealPlayer other) {

    }

    default void interactWith(Unit other) {

    }

}