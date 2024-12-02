package characters;

public class Rogue extends Character {

    public Rogue(String name, Integer xp, Integer level) {
        super(name, xp, level);
    }

    @Override
    public String getProfession() {
        return "Rogue";
    }
}
