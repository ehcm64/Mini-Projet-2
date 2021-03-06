package ch.epfl.cs107.play.game.icwars.actor.players;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public abstract class ICWarsPlayer extends ICWarsActor implements Interactor {
    protected State currentState;
    protected ArrayList<Unit> units = new ArrayList<Unit>();
    protected Sprite sprite;
    protected String name;
    protected Unit selectedUnit;
    protected Action act;
    protected boolean turnStarting;

    public enum State {
        IDLE,
        NORMAL,
        SELECT_CELL,
        MOVE_UNIT,
        ACTION_SELECTION,
        ACTION;
    }

    public ICWarsPlayer(Area area, DiscreteCoordinates position, Faction faction, ArrayList<Unit> units) {
        super(area, position, faction);
        this.selectedUnit = null;
        this.currentState = State.IDLE;
        addAllUnits(units);
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    @Override
    public void update(float deltaTime) {
        removeDeadUnits();
        super.update(deltaTime);
    }

    @Override
    public void enterArea(Area area, DiscreteCoordinates position) {
        for (Unit unit : this.units) {
            area.registerActor(unit);
        }
        area.registerActor(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());

    }

    @Override
    public void leaveArea() {
        for (Unit unit : units) {
            getOwnerArea().unregisterActor(unit);
        }
        getOwnerArea().unregisterActor(this);
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ICWarsInteractionVisitor) v).interactWith(this);
    }

    /** Do everything needed for the player to start his turn correctly.
     * 
     */
    public void startTurn() {
        this.currentState = State.NORMAL;
        centerCamera();
        for (Unit unit : this.units) {
            unit.setMoveState(false);
            unit.setActionState(false);
        }
        this.turnStarting = true;
    }

    /** Allow other classes to check in which state the player is.
     * 
     * @return (State): one of the player's possible states from the enum State.
     */
    public State getPlayerState() {
        return this.currentState;
    }

    /** Allow ICWars class to set the player's state.
     * 
     * @param state (State): one of the player's possible states from the enum State.
     */
    public void setPlayerState(State state) {
        this.currentState = state;
    }

    /** Add a list of units to the player's current units.
     * 
     * @param units (ArrayList<Unit>): a list of units to add to the player's list of units. 
     */
    public void addAllUnits(ArrayList<Unit> units) {
        for (Unit unit : units) {
            this.units.add(unit);
        }
    }

    /** Remove all units in the player's list of units.
     * 
     */
    public void removeAllUnits() {
        ArrayList<Unit> unitsToRemove = new ArrayList<Unit>();
        for (Unit unit : this.units) {
            unitsToRemove.add(unit);
        }
        if (unitsToRemove.size() != 0) {
            for (Unit unit : unitsToRemove) {
                this.units.remove(unit);
                this.getOwnerArea().unregisterActor(unit);
            }
        }
    }

    /** Remove all units in the player's list of units that are dead.
     * 
     */
    private void removeDeadUnits() {
        ArrayList<Unit> unitsToRemove = new ArrayList<Unit>();
        for (Unit unit : this.units) {
            if (unit.getHp() == 0)
                unitsToRemove.add(unit);
        }
        if (unitsToRemove.size() != 0) {
            for (Unit unit : unitsToRemove) {
                this.units.remove(unit);
                this.getOwnerArea().unregisterActor(unit);
            }
        }
    }

    /** Check if the player is defeated (all units are dead).
     * 
     * @return (boolean): true if the player is defeated or the opposite.
     */
    public boolean isDefeated() {
        if (this.units.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
