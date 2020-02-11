package gamer.bmagic.spell;

import org.bukkit.entity.Player;

public abstract class Spell {
    public int id = -1;

    public Spell(int id) {
        this.id = id;
    }

    public abstract boolean cast(Player player);
}
