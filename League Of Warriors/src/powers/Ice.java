package powers;

import characters.Entity;

public class Ice extends Spell {
    public Ice() {
        super(15.0F, 7.5F);
    }


    @Override
    public boolean isEffectiveAgainst(Entity enemy) {
        return !enemy.isIceImmunity();
    }
}
