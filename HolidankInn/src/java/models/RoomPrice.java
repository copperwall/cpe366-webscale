/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author scott
 */
public class RoomPrice extends DBO {
    
    public RoomPrice(int id) {
        super();
        
        // Our users table has the PK of userid
        this.setTable("room_prices", "room_priceid");
        
        this.bind("roomid", "roomid:integer");
        this.bind("start_date", "start_date:timestamp without time zone");
        this.bind("end_date", "end_date:timestamp without time zone");
        this.bind("price", "price:real");

        if (id != 0) {
            this.id = id;
            this.fromDb = true;
            this.load();
        }
    }
    
    public String getRoomDescription() {
        Room r = new Room(Integer.parseInt(this.get("roomid")));
        return r.get("number") + " - " + r.getDescription();
    }
    
}
