package gamer.bmagic;

import gamer.bmagic.crafting.CraftManager;
import gamer.bmagic.spell.scroll.ScrollUtils;
import gamer.bmagic.spell.scroll.ScrollSpell;
import gamer.bmagic.spell.Spell;
import gamer.bmagic.spell.scroll.impl.AlchemyScroll;
import gamer.bmagic.spell.scroll.impl.TPScroll;
import gamer.bmagic.spell.scroll.impl.TeleportationScroll;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends JavaPlugin {
    public static final List<Spell> SPELLS = new ArrayList<>();
    public static final List<ScrollSpell> SCROLLS = new ArrayList<>();
    public static final List<ScrollSpell> HOOK = new ArrayList<>();


    public static Main INSTANCE;


    @Override
    public void onEnable() {
        INSTANCE = this;
        new ScrollUtils();

        SCROLLS.add(new TeleportationScroll());
        SCROLLS.add(new TPScroll());


        HOOK.add(new AlchemyScroll());

        SPELLS.addAll(SCROLLS);

        getServer().getPluginManager().registerEvents(new CraftManager(), this);
        getServer().getPluginManager().registerEvents(new EventListener(), this);


        HOOK.stream().filter(scroll -> Arrays.asList(scroll.getClass().getInterfaces()).contains(Listener.class)).forEach(scroll -> {
            getServer().getPluginManager().registerEvents((Listener) scroll, this);
        });

    }
}
