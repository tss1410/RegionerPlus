package me.tss1410.RegionerPlus.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import me.tss1410.RegionerPlus.CuboidSelection;
import me.tss1410.RegionerPlus.RegionerPlus;
import me.tss1410.RegionerPlus.player.Player;
import me.tss1410.RegionerPlus.region.Region;

public class PlayerListener implements Listener{
	
	public RegionerPlus pl;
	
	public PlayerListener(RegionerPlus plugin){
		pl=plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		if(!pl.players.containsKey(e.getPlayer().getUniqueId().toString())){
			Player p = new Player(e.getPlayer().getUniqueId().toString(), e.getPlayer().getName());
			p.insert(pl);
			pl.players.put(p.uuid, p);
			pl.nameUuid.put(p.name, p.uuid);
		}
		
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		if (((e.getAction() == Action.RIGHT_CLICK_BLOCK) || (e.getAction() == Action.LEFT_CLICK_BLOCK)) && 
				(e.getPlayer().getItemInHand().getType() == Material.GOLD_AXE))
		{
			if (e.getPlayer().hasPermission("rp.tool"))
			{
				if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) || (e.getAction() == Action.LEFT_CLICK_BLOCK))
				{
					e.setCancelled(true);
					if (pl.selections.get(e.getPlayer().getUniqueId().toString()) == null)
					{
						pl.selections.put(e.getPlayer().getUniqueId().toString(), new CuboidSelection());
					}
				}
				if (e.getAction() == Action.LEFT_CLICK_BLOCK)
				{
					((CuboidSelection)pl.selections.get(e.getPlayer().getUniqueId().toString())).setLocMin(e.getClickedBlock().getLocation());
					e.getPlayer().sendMessage(ChatColor.GOLD + "Posisjon 1 satt. X: " + e.getClickedBlock().getLocation().getX() + " Y: " + e.getClickedBlock().getLocation().getY() + " Z: " + e.getClickedBlock().getLocation().getZ());
				}
				if (e.getAction() == Action.RIGHT_CLICK_BLOCK)
				{
					((CuboidSelection)pl.selections.get(e.getPlayer().getUniqueId().toString())).setLocMax(e.getClickedBlock().getLocation());
					e.getPlayer().sendMessage(ChatColor.GOLD + "Posisjon 2 satt. X: " + e.getClickedBlock().getLocation().getX() + " Y: " + e.getClickedBlock().getLocation().getY() + " Z: " + e.getClickedBlock().getLocation().getZ());
				}
				Bukkit.broadcastMessage(pl.players.values().toString());
				Bukkit.broadcastMessage(pl.nameUuid.values().toString());
			}
		}

	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		if(e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockY() == e.getTo().getBlockY() && e.getFrom().getBlockZ() == e.getTo().getBlockZ()){
			return;
		}
		if(e.getPlayer().hasPermission("rp.bypass.entry")){
			return;
		}
		for(Region r : pl.regions){
			if(r.contains(e.getPlayer().getLocation())){
				if(r.owner.equalsIgnoreCase(e.getPlayer().getUniqueId().toString())){
					return;
				}
				
				if(r.members.contains(e.getPlayer().getUniqueId().toString())){
					return;
				}
				e.setCancelled(true);
				e.getPlayer().sendMessage(ChatColor.RED + "Du har ikke tilgang til dette området!");
				
				e.getPlayer().teleport(e.getFrom());
			}
		}
	}
}
