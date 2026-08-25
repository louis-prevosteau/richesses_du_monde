package core.models;

public class GoSquare implements ISquare {

    private int position;

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
        System.out.println(player.getName() + " vous êtes sur la case " + getName());
    }
}
