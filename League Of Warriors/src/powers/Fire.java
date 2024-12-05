package powers;

import characters.Entity;

public class Fire extends Spell {
    public Fire() {
        super(40.0F, 25.0F);
    }

    @Override
    public boolean isEffectiveAgainst(Entity enemy) {
        return !enemy.isFireImmunity();
    }
}
