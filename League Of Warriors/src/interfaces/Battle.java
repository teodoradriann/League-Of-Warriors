package interfaces;

import characters.Entity;
import powers.Spell;

public interface Battle {
    void receiveDamage(float damage, boolean fromSpell);
    float getDamage(boolean isNormalAttack, Spell spell);
    void attack(Entity enemy);
}
