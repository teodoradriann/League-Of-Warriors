package powers;

import characters.Entity;

public class Earth extends Spell {
    public Earth() {
        super(25.0F, 15.0F);
    }

    @Override
    public boolean isEffectiveAgainst(Entity enemy) {
        return !enemy.isEarthImmunity();
    }
}
