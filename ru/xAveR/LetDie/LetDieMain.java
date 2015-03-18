package ru.xAveR.LetDie;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class LetDieMain extends JavaPlugin implements Listener{

	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {		
		getServer().getPluginManager().registerEvents(this, this);
		Runnable setThemOnFire = new Runnable() {
			public void run() {
				SetCreepersOnFire();
			}
		};
		getServer().getScheduler().scheduleAsyncRepeatingTask(this, setThemOnFire, 20, 20);
	}

	@Override
	public void onDisable() {

	}
	
	private void SetCreepersOnFire()
	{
		List<Creeper> allCreepers = GetCreepers();
		
		for (int i = 0; i < allCreepers.size(); i++)
		{
			if (allCreepers.get(i).getWorld().getTime() >= 0 && allCreepers.get(i).getWorld().getTime() <= 12000 && allCreepers.get(i).getWorld().getHighestBlockYAt(allCreepers.get(i).getLocation()) <= allCreepers.get(i).getLocation().getY()){
					if(!(allCreepers.get(i).getWorld().hasStorm())){
					if(allCreepers.get(i).getLocation().getBlock().getType() != Material.WATER){
						allCreepers.get(i).setFireTicks(20);
					}
				}
			}
		}
	}
	
	private List<Creeper> GetCreepers()
	{
		List<World> worlds = this.getServer().getWorlds();
		List<Creeper> creepers = new ArrayList<Creeper>();
		
		for (int i = 0; i < worlds.size(); i++)
		{
			List<Entity> entities = worlds.get(i).getEntities();
			for (int j = 0; j < entities.size(); j++)
			{
				if (entities.get(j) instanceof Creeper)
				{
					creepers.add((Creeper)entities.get(j));
				}
			}
		}
		return creepers;
	} 
	@EventHandler
	public void EntityTarget(EntityTargetEvent e){
		if(e.getTarget() instanceof Player){
		Player p = (Player) e.getTarget();
		if(p != null){
		if(p.isSneaking()){
			if(p.getLastDamage() == 0){
			e.setCancelled(true);
			}
			}
			}
		}
	}
}
