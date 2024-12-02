package characters;

public class Mage extends Character {

    public Mage(String name, Integer xp, Integer level) {
        super(name, xp, level);
    }

    @Override
    public String getProfession() {
        return "Mage";
    }


}
