package me.tss1410.RegionerPlus.region;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Material;

import me.tss1410.RegionerPlus.RegionerPlus;

public class RegionHandler {
	
    public boolean isInteractable(Material m) {
        switch(m) {
        case ENCHANTMENT_TABLE:
        case ANVIL:
        case TRAPPED_CHEST:
        case CHEST:
        case BREWING_STAND:
        case NOTE_BLOCK:
        case CAULDRON:
        case FENCE_GATE:
        case BED_BLOCK:
        case BED:
        case ACACIA_DOOR:
        case BIRCH_DOOR:
        case DARK_OAK_DOOR:
        case JUNGLE_DOOR:
        case SPRUCE_DOOR:
        case TRAP_DOOR:
        case ACACIA_FENCE_GATE:
        case BIRCH_FENCE_GATE:
        case DARK_OAK_FENCE_GATE:
        case JUNGLE_FENCE_GATE:
        case SPRUCE_FENCE_GATE:
        case JUKEBOX:
        case STONE_PLATE:
        case GOLD_PLATE:
        case IRON_PLATE:
        case WOOD_PLATE:
        case FURNACE:
        case BURNING_FURNACE:
        case WORKBENCH:
        case LEVER:
        case STONE_BUTTON:
        case WOOD_BUTTON:
        case WOODEN_DOOR:
        case IRON_DOOR_BLOCK:
        case IRON_DOOR:
        case WOOD_DOOR:
        case SPRUCE_DOOR_ITEM:
        case ACACIA_DOOR_ITEM:
        case BIRCH_DOOR_ITEM:
        case JUNGLE_DOOR_ITEM:
        case DARK_OAK_DOOR_ITEM:
            return true;
         default:
        	 return false;   
        }
    }
    
    public void getFromSQL(RegionerPlus pl){
    	try {
			ResultSet rs = pl.getregions.executeQuery();
			while(rs.next()){
				Region r = new Region(rs.getString("name"), rs.getString("owner"), rs.getString("world"), rs.getInt("xmin"), rs.getInt("xmax"), rs.getInt("ymin"), rs.getInt("ymax"), rs.getInt("zmin"), rs.getInt("zmax"), rs.getBoolean("pvp"), rs.getBoolean("build"), rs.getBoolean("explode"), rs.getBoolean("entry"), rs.getString("joinmessage"), rs.getString("leavemessage"));
				pl.regions.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	addMembers(pl);
    }
    
    public void addMembers(RegionerPlus pl){
    	for(Region r : pl.regions){
    		try {
				pl.getmembers.setString(1, r.name);
				ResultSet rs = pl.getmembers.executeQuery();
				while(rs.next()){
					r.members.add(rs.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }
}
