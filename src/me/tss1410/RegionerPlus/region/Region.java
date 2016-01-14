package me.tss1410.RegionerPlus.region;

import java.sql.SQLException;
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

		try {
			pl.newregion.setString(1, name);
			pl.newregion.setString(2, owner);
			pl.newregion.setString(3, world);
			pl.newregion.setInt(4, xmin);
			pl.newregion.setInt(5, xmax);
			pl.newregion.setInt(6, ymin);
			pl.newregion.setInt(7, ymax);
			pl.newregion.setInt(8, zmin);
			pl.newregion.setInt(9, zmax);
			pl.newregion.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(RegionerPlus pl){
		
		try{
			pl.updateregion.setString(1, world);
			pl.updateregion.setInt(2, xmin);
			pl.updateregion.setInt(3, xmax);
			pl.updateregion.setInt(4, ymin);
			pl.updateregion.setInt(5, ymax);
			pl.updateregion.setInt(6, zmin);
			pl.updateregion.setInt(7, zmax);
			pl.updateregion.setBoolean(8, explode);
			pl.updateregion.setBoolean(9, pvp);
			pl.updateregion.setBoolean(10, entry);
			pl.updateregion.setBoolean(11, build);
			pl.updateregion.setString(12, joinMessage);
			pl.updateregion.setString(13, leaveMessage);
			pl.updateregion.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
	public void delete(RegionerPlus pl){
		try {
			pl.deleteregion.setString(1, name);
			pl.deleteregion.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
			me.append(main.players.get(s).name + ", ");
		}
		me.delete(me.length() - 2, me.length());
		return me.toString();
	}
	
	

}
