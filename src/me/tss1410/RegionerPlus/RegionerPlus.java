package me.tss1410.RegionerPlus;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.plugin.java.JavaPlugin;

import me.tss1410.RegionerPlus.region.Region;
import me.tss1410.RegionerPlus.region.RegionHandler;

public class RegionerPlus extends JavaPlugin{
	
	
	public PreparedStatement newregion;
	public PreparedStatement updateregion;
	public PreparedStatement deleteregion;
	
	public PreparedStatement newplayer;
	public PreparedStatement updateplayer;
	public PreparedStatement deleteplayer;
	
	public RegionHandler regionHandler;
	
	public MySQL sql;
	public HashMap<String, CuboidSelection> selections = new HashMap<String, CuboidSelection>();
	public HashSet<Region> regions = new HashSet<Region>();
	
	public HashMap<String, String> uuidName = new HashMap<String, String>(); //<UUID, Name>
	
	public void onEnable(){
	}
	
	public void setupMySQL(){
		sql = new MySQL(getConfig().getString("mysql.hostname"), getConfig().getString("mysql.port"), getConfig().getString("mysql.database"), getConfig().getString("mysql.username"), getConfig().getString("mysql.password"));

		if(sql.open() == null){
			System.out.println("MySQL Login Error");
		}
		
		try {
			sql.getConnection().createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `rp_regions` (`name` VARCHAR(255), `owner` VARCHAR(255) PRIMARY KEY, `xmin` INT, `ymin` INT, `zmin` INT, `xmax` INT, `ymax` INT, `zmax` INT, `explode` TINYINT DEFAULT 0, `pvp` TINYINT DEFAULT 0, `entry` TINYINT DEFAULT 1, `build` TINYINT DEFAULT 0, `joinmessage` VARCHAR(255), `leavemessage` VARCHAR(255))");
			sql.getConnection().createStatement().executeUpdate("CREATE TABLE IF NOT EXISIS `rp_players` (`name` VARCHAR(255), `uuid` VARCHAR(255))");
	        sql.getConnection().createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `medlemmer` (`name` VARCHAR(255), `uuid` VARCHAR(255))");

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			newregion = sql.getConnection().prepareStatement("INSERT INTO `rp_regions` (name, owner, xmin, xmax, ymin, ymax, zmin, zmax) VALUES (?,?,?,?,?,?,?,?)");
			updateregion = sql.getConnection().prepareStatement("UPDATE `rp_regions` SET `xmin`=?, `ymin`=?, `zmin`=?, `xmax`=?, `ymax`=?, `zmax`=?, `explode`=?, `pvp`=?, `entry`=?, `build`=?, `joinmessage`=?, `leavemessage`=? WHERE `name`");
			deleteregion = sql.getConnection().prepareStatement("DELETE FROM `rp_regions` WHERE `name`=?");
			
			newplayer = sql.getConnection().prepareStatement("INSERT INTO `rp_players` (name, uuid) VALUES (?,?)");
			updateplayer = sql.getConnection().prepareStatement("UPDATE `rp_players` SET `name`=? WHERE `uuid`=?");
			deleteplayer = sql.getConnection().prepareStatement("DELETE FROM `rp_players` WHERE `uuid`=?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
