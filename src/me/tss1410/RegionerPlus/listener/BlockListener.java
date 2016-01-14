package me.tss1410.RegionerPlus.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.tss1410.RegionerPlus.RegionerPlus;
import me.tss1410.RegionerPlus.region.Region;

public class BlockListener implements Listener{
	
	public RegionerPlus pl;
	
	public BlockListener(RegionerPlus plugin){
		pl=plugin;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		for(Region r : pl.regions){

			if(r.contains(e.getPlayer().getWorld(), e.getPlayer().getLocation().getBlockX(), e.getPlayer().getLocation().getBlockY(), e.getPlayer().getLocation().getBlockZ())){

				if(r.build){
					return;
				}
				if(r.owner.equalsIgnoreCase(e.getPlayer().getUniqueId().toString())){
					return;
				}
				
				if(r.members.contains(e.getPlayer().getUniqueId().toString())){
					return;
				}
				
				e.getPlayer().sendMessage(ChatColor.RED + "Du kan ikke bygge her!");
				e.setCancelled(true);

			}
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		for(Region r : pl.regions){

			if(r.contains(e.getPlayer().getWorld(), e.getPlayer().getLocation().getBlockX(), e.getPlayer().getLocation().getBlockY(), e.getPlayer().getLocation().getBlockZ())){

				if(r.build){
					return;
				}
				if(r.owner.equalsIgnoreCase(e.getPlayer().getUniqueId().toString())){
					return;
				}
				
				if(r.members.contains(e.getPlayer().getUniqueId().toString())){
					return;
				}
				
				e.getPlayer().sendMessage(ChatColor.RED + "Du kan ikke bygge her!");
				e.setCancelled(true);

			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		for(Region r : pl.regions){

			if(r.contains(e.getPlayer().getWorld(), e.getPlayer().getLocation().getBlockX(), e.getPlayer().getLocation().getBlockY(), e.getPlayer().getLocation().getBlockZ())){

				if(r.build){
					return;
				}
				if(r.owner.equalsIgnoreCase(e.getPlayer().getUniqueId().toString())){
					return;
				}
				
				if(r.members.contains(e.getPlayer().getUniqueId().toString())){
					return;
				}
				
				if(pl.regionHandler.isInteractable(e.getClickedBlock().getType())){
					e.getPlayer().sendMessage(ChatColor.RED + "Du kan ikke bruke denne her!");
					e.setCancelled(true);
				}
			}
		}
	}
}
