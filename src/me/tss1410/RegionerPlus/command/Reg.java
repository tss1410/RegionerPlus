package me.tss1410.RegionerPlus.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
				for(Region r : pl.regions){
					if(r.contains(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ())){
						Sender.sendMessage(ChatColor.GOLD + "Navn: " + ChatColor.GRAY + r.name);
						Sender.sendMessage(ChatColor.GOLD + "Eier: " + ChatColor.GRAY + pl.uuidName.get(r.owner));
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
		}
		return false;
	}
}
