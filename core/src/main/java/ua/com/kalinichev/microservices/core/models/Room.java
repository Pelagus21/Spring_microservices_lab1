package ua.com.kalinichev.microservices.core.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.io.Serializable;

@JsonIgnoreProperties(value={ "type" }, allowGetters=true)
public class Room implements Serializable {

    @Getter
    private String room;
    @Getter
    private RoomType type;

    public Room(){
        this.type = RoomType.REMOTELY;
        this.room = "Remotely";
    }

    public Room(String room) {
        setRoom(room);
        this.type = RoomType.ROOM;
    }

    public Room setRoom(String room) {
        this.room = room;
        return this;
    }

    @Override
    public String toString() {
        return room;
    }

    @JsonIgnore
    public String getTypeOrName()
    {
        if(this.type == RoomType.REMOTELY)
            return "REMOTELY";
        return this.room;
    }

    public boolean equalsByString(String room)
    {
        if(this.type == RoomType.REMOTELY)
            return room.equals("REMOTELY");
        return room.equals(this.room);
    }

    public enum RoomType{
        REMOTELY, ROOM;
    }
}
