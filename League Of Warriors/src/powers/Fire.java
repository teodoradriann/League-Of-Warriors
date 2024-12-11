package powers;

import characters.Entity;

public class Fire extends Spell {
    public Fire() {
        super(35.0F, 25.0F);
    }

    @Override
    public boolean isEffectiveAgainst(Entity enemy) {
        return !enemy.isFireImmunity();
    }
}
