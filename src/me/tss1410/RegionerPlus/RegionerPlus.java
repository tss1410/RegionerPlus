package me.tss1410.RegionerPlus;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.tss1410.RegionerPlus.command.Reg;
import me.tss1410.RegionerPlus.listener.BlockListener;
import me.tss1410.RegionerPlus.listener.PlayerListener;
import me.tss1410.RegionerPlus.player.Player;
import me.tss1410.RegionerPlus.player.PlayerHandler;
import me.tss1410.RegionerPlus.region.Region;
import me.tss1410.RegionerPlus.region.RegionHandler;

public class RegionerPlus extends JavaPlugin{
	
	
	public PreparedStatement newregion;
	public PreparedStatement updateregion;
	public PreparedStatement deleteregion;
	
	public PreparedStatement newplayer;
	public PreparedStatement updateplayer;
	public PreparedStatement deleteplayer;
	
	public PreparedStatement getregions;
	public PreparedStatement getplayers;
	
	public PreparedStatement getmembers;

	
	public MySQL sql;
	public HashMap<String, CuboidSelection> selections = new HashMap<String, CuboidSelection>();
	public HashSet<Region> regions = new HashSet<Region>();
	
	public HashMap<String, Player> players = new HashMap<String, Player>(); //<UUID, Name>
	public HashMap<String, String> nameUuid = new HashMap<String, String>();
	
	public PlayerHandler ph = new PlayerHandler();
	public RegionHandler regionHandler = new RegionHandler();

	
	public String noPerm = ChatColor.RED + "Du har ikke tilgang til denne kommandoen";
	
	public void onEnable(){
		setupConfig();
		setupMySQL();
		setupCommands();
		setupListeners();
		
	}
	
	public void setupMySQL(){
		sql = new MySQL(getConfig().getString("mysql.hostname"), getConfig().getString("mysql.port"), getConfig().getString("mysql.database"), getConfig().getString("mysql.username"), getConfig().getString("mysql.password"));

		if(sql.open() == null){
			System.out.println("MySQL Login Error");
		}
		
		try {
			sql.getConnection().createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `rp_regions` (`name` VARCHAR(255), `owner` VARCHAR(255) PRIMARY KEY, `world` VARCHAR(255), `xmin` INT, `ymin` INT, `zmin` INT, `xmax` INT, `ymax` INT, `zmax` INT, `explode` TINYINT DEFAULT 0, `pvp` TINYINT DEFAULT 0, `entry` TINYINT DEFAULT 1, `build` TINYINT DEFAULT 0, `joinmessage` VARCHAR(255), `leavemessage` VARCHAR(255))");
			sql.getConnection().createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `rp_players` (`name` VARCHAR(255), `uuid` VARCHAR(255))");
	        sql.getConnection().createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `rp_members` (`name` VARCHAR(255), `uuid` VARCHAR(255))");

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			newregion = sql.getConnection().prepareStatement("INSERT INTO `rp_regions` (name, owner, world, xmin, xmax, ymin, ymax, zmin, zmax) VALUES (?,?,?,?,?,?,?,?,?)");
			updateregion = sql.getConnection().prepareStatement("UPDATE `rp_regions` SET `world`=?, `xmin`=?, `ymin`=?, `zmin`=?, `xmax`=?, `ymax`=?, `zmax`=?, `explode`=?, `pvp`=?, `entry`=?, `build`=?, `joinmessage`=?, `leavemessage`=? WHERE `name`");
			deleteregion = sql.getConnection().prepareStatement("DELETE FROM `rp_regions` WHERE `name`=?");
			
			newplayer = sql.getConnection().prepareStatement("INSERT INTO `rp_players` (name, uuid) VALUES (?,?)");
			updateplayer = sql.getConnection().prepareStatement("UPDATE `rp_players` SET `name`=? WHERE `uuid`=?");
			deleteplayer = sql.getConnection().prepareStatement("DELETE FROM `rp_players` WHERE `uuid`=?");
			
			getregions = sql.getConnection().prepareStatement("SELECT * FROM `rp_regions`");
			getplayers = sql.getConnection().prepareStatement("SELECT * FROM `rp_players`");
			
			getmembers = sql.getConnection().prepareStatement("SELECT uuid FROM `rp_members` WHERE `name`=?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ph.getFromSQL(this);
		regionHandler.getFromSQL(this);
	}
	
	public void setupCommands(){
		getCommand("reg").setExecutor(new Reg(this));
	}
	
	public void setupListeners(){
		getServer().getPluginManager().registerEvents(new BlockListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
	}
	
	public void setupConfig(){
		getConfig().options().copyDefaults(true);
        getConfig().addDefault("mysql.hostname", "localhost");
        getConfig().addDefault("mysql.port", "3306");
        getConfig().addDefault("mysql.username", "root");
        getConfig().addDefault("mysql.password", "root");
        getConfig().addDefault("mysql.database", "SH");
        saveConfig();
        getConfig().options().copyDefaults(true);

	}
}
