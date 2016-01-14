package me.tss1410.RegionerPlus.region;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.World;

import me.tss1410.RegionerPlus.RegionerPlus;

public class Region {
	
	public String name;
	public String owner;
	public String world;
	public int xmin, ymin, zmin, xmax, ymax, zmax;
	public String joinMessage;
	public String leaveMessage;
	public boolean pvp;
	public boolean explode;
	public boolean build;
	public boolean entry;
	public int priority;
	
	public ArrayList<String> members = new ArrayList<String>();
	
	public Region(String name, String owner, String world, int xmin, int xmax, int ymin , int ymax, int zmin, int zmax){
		this.name = name;
		this.owner = owner;
		this.world = world;
		
		this.xmin = xmin; this.xmax = xmax;
		this.ymin = ymin; this.ymax = ymax;
		this.zmin = zmin; this.zmax = zmax;
		
		this.pvp = false;
		this.explode = false;
		this.build = false;
		this.entry = true;
		
		this.joinMessage = null;
		this.leaveMessage = null;
		
		members.add(owner);
	}
	
	
	public void insert(RegionerPlus pl){
		
	}
	
	public void update(RegionerPlus pl){
		
	}
	
	public void delete(RegionerPlus pl){
		
	}
	
	public boolean contains(World w, int x, int y, int z){
		if(!w.getName().equalsIgnoreCase(world)){
			return false;
		}
		if(x > xmin && x < xmax && y > ymin && y < ymax && z > zmin && z < zmax){
			return true;
		}
		return false;
	}
	
	public String getFlags(){
		StringBuffer me = new StringBuffer();
		me.append("PvP: " + pvp + ", ");
		me.append("Eksplosjoner: " + explode + ", ");
		me.append("Bygge: " + build + ", ");
		me.append("Tilgang: " + entry);
		return me.toString().replaceAll(String.valueOf(true), "På").replaceAll(String.valueOf(false), "Av");
	}


	public String getMembers(RegionerPlus main) {
		StringBuffer me = new StringBuffer();
		for(String s : members){
			me.append(main.uuidName.get(s) + ", ");
		}
		me.delete(me.length() - 2, me.length());
		return me.toString();
	}
	
	

}
