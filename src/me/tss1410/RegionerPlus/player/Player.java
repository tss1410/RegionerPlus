package me.tss1410.RegionerPlus.player;

import java.sql.SQLException;

import me.tss1410.RegionerPlus.RegionerPlus;

public class Player {
	
	public String uuid;
	public String name;
	
	public Player(String uuid, String name){
		this.uuid = uuid;
		this.name = name;
	}

	public void insert(RegionerPlus pl){
		try{
			pl.newplayer.setString(1, name);
			pl.newplayer.setString(2, uuid);
			pl.newplayer.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void update(RegionerPlus pl){
		try{
			pl.updateplayer.setString(1, name);
			pl.updateplayer.setString(2, uuid);
			pl.updateplayer.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void delete(RegionerPlus pl){
		try{
			pl.deleteplayer.setString(1, uuid);
			pl.deleteplayer.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}

}
