package me.tss1410.RegionerPlus.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.tss1410.RegionerPlus.CuboidSelection;
import me.tss1410.RegionerPlus.RegionerPlus;
import me.tss1410.RegionerPlus.region.Region;

public class Reg implements CommandExecutor{
	
	public RegionerPlus pl;
	
	public Reg(RegionerPlus plugin){
		pl=plugin;
	}

	public boolean onCommand(CommandSender Sender, Command command, String commandLabel, String args[]){
		if(args.length == 0){
			Sender.sendMessage(ChatColor.GOLD + "Region hjelp:");
			Sender.sendMessage(ChatColor.GOLD + "/reg info " + ChatColor.GRAY + "Viser info om regionen du står i");
			Sender.sendMessage(ChatColor.GOLD + "/reg list " + ChatColor.GRAY + "Viser alle regionene");
			
			return false;
		}
		
		if(!(Sender instanceof Player)){
			Sender.sendMessage(ChatColor.RED + "Kun spillere kan utføre denne kommandoen");
			return false;
		}
		
		Player p = (Player) Sender;
		
		if(args.length == 1){
			if(args[0].equalsIgnoreCase("list")){
				
				if(!Sender.hasPermission("rp.list")){
					Sender.sendMessage(pl.noPerm);
					return false;
				}
				
				if(pl.regions.isEmpty()){
					Sender.sendMessage(ChatColor.GOLD + "Det finnes ingen regioner");
					return false;
				}
				StringBuffer me = new StringBuffer();
				for(Region r : pl.regions){
					me.append(r.name + ", ");
				}
				me.delete(me.length() - 2, me.length());
				Sender.sendMessage(ChatColor.GOLD + "Regionene:");
				Sender.sendMessage(ChatColor.GRAY + me.toString());


			} else if(args[0].equalsIgnoreCase("info")){

				if(!Sender.hasPermission("rp.info")){
					Sender.sendMessage(pl.noPerm);
					return false;
				}

				for(Region r : pl.regions){
					if(r.contains(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ())){
						Sender.sendMessage(ChatColor.GOLD + "Navn: " + ChatColor.GRAY + r.name);
						Sender.sendMessage(ChatColor.GOLD + "Eier: " + ChatColor.GRAY + pl.players.get(r.owner).name);
						Sender.sendMessage(ChatColor.GOLD + "Flags: " + ChatColor.GRAY + r.getFlags());
						if(r.joinMessage != null){
							Sender.sendMessage(ChatColor.GOLD + "Velkommen: " + ChatColor.GRAY + r.joinMessage);
						}
						if(r.leaveMessage != null){
							Sender.sendMessage(ChatColor.GOLD + "Farvell: " + ChatColor.GRAY + r.leaveMessage);
						}
						Sender.sendMessage(ChatColor.GOLD + "Medlemmer: " + ChatColor.GRAY + r.getMembers(pl));
					}
				}
			} 

		} else if(args.length == 3){
			if(args[0].equalsIgnoreCase("ny")){
				if(!Sender.hasPermission("rp.ny")){
					Sender.sendMessage(pl.noPerm);
					return false;
				}
				
				if(!pl.players.values().contains(args[2])){
					Sender.sendMessage(ChatColor.RED + "Kan ikke finne spilleren: " + args[2]);
					return false;
				}
				
				if(pl.selections.get(p.getUniqueId().toString()).getLocMax() == null){
					p.sendMessage(ChatColor.RED + "Du må sette posisjon 1");
					return false;
				}
				
				if(pl.selections.get(p.getUniqueId().toString()).getLocMin() == null){
					p.sendMessage(ChatColor.RED + "Du må sette posisjon 2");
					return false;
					
				}
				
				
				
				CuboidSelection c = pl.selections.get(p.getUniqueId().toString());
				
				if(c.getLocMax().getWorld().getName().equalsIgnoreCase(c.getLocMin().getWorld().getName())){
					Sender.sendMessage(ChatColor.RED + "Posisjon 1 og 2 er i forskjellig verden");
					return false;
				}
				
				int xmin = Math.min(c.getLocMin().getBlockX(), c.getLocMax().getBlockX());
				int xmax = Math.max(c.getLocMin().getBlockX(), c.getLocMax().getBlockX());
				int zmin = Math.min(c.getLocMin().getBlockZ(), c.getLocMax().getBlockZ());
				int zmax = Math.min(c.getLocMin().getBlockZ(), c.getLocMax().getBlockZ());
				int ymin = Math.min(c.getLocMin().getBlockY(), c.getLocMax().getBlockY());
				int ymax = Math.min(c.getLocMin().getBlockY(), c.getLocMax().getBlockY());
				
				Region region = new Region(args[1], pl.nameUuid.get(args[2]), c.getLocMax().getWorld().getName(), xmin, xmax, ymin, ymax, zmin, zmax);
				pl.regions.add(region);
				region.insert(pl);
				
			}
		}
		return false;
	}
}
