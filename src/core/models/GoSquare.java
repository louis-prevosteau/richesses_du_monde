package core.models;

public class GoSquare implements ISquare {

    private final int position;

    public GoSquare(int position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Départ";
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void landOn(Player player) {
        // Aucun effet lorsque le joueur s'arrête sur la case Départ.
    }
}
