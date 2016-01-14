package me.tss1410.RegionerPlus.player;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.tss1410.RegionerPlus.RegionerPlus;

public class PlayerHandler {
	
	public void getFromSQL(RegionerPlus pl){

		try {
			ResultSet rs = pl.getplayers.executeQuery();
			while(rs.next()){
				Player p = new Player(rs.getString("uuid"), rs.getString("name"));
				pl.players.put(p.uuid, p);
				pl.nameUuid.put(p.name, p.uuid);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
