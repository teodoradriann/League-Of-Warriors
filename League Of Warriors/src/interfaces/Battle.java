package interfaces;

import characters.Entity;
import powers.Spell;

public interface Battle {
    void receiveDamage(float damage);
    float calculateDamage(boolean isNormalAttack, Spell spell);
    void attack(Entity enemy);
}
