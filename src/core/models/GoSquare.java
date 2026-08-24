package core.models;

public class GoSquare implements ISquare {

    @Override
    public String getName() {
        return "Départ";
    }

    @Override
    public int getPosition() {
        return 0;
    }

    @Override
    public void landOn(Player player) {
        System.out.println(player.getName() + " vous êtes sur la case " + getName());
    }
}
