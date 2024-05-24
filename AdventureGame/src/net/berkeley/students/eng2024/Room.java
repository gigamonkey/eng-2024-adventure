package net.berkeley.students.eng2024;

import java.util.ArrayList;
import java.util.List;

public class Room {

    // codeName should never be shown to the player at all
    // passageDescription is used for any things that connect to this room that the
    // player can see through, e.g. a broken window (so like, it would say There is
    // a broken window, and
    // you can see [passageDescription] on the other side.)
    private List<Entity> entities;
    private String description;
    private String codeName;
    private List<Passage> passages;
    private String passageDescription;

    public Room(String codeName, String passageDescription, String description, List<Passage> p,
            List<Entity> items) {
        this.codeName = codeName;
        this.description = description;
        this.passageDescription = passageDescription;
        passages = new ArrayList<>();
        this.entities = items;
        passages = p;
    }

    public Room(String codeName, String passageDescription, String description, List<Entity> items) {
        this.codeName = codeName;
        this.description = description;
        this.passageDescription = passageDescription;
        passages = new ArrayList<>();
        this.entities = items;
        passages = new ArrayList<>();
    }

    // describe the room itself and all the items and everythin
    public String describe() {
        String s = AdventureGame.format("longinfo",description);
        for (Entity entity : entities) {
            s += AdventureGame.format("notice", "There is a " + entity.name() + ".");
        }
        for (Passage p : passages) {
            if (AdventureGame.player.lastRoom() == p.notPlayerRoom()) {
                continue;
            }
            s += p.toString(this);
        }
        if(entities.size() > 0){
            s += "< In the room there is";
        } else{
            s+= "nothing is in the room.";
        }
        for(Entity i : entities){
            s += " a " + i.name();
            if(entities.indexOf(i) != entities.size()-1){
                s+= ",";
            }
            if(entities.indexOf(i) == entities.size()-2){
                s+= "and";
            }
            
        }
        s+= " >";
        return s;
    }

    public void addPassage(Passage passage) {
        passages.add(passage);
    }

    public List<Passage> getPassages() {
        return passages;
    }

    public Room getConnectingRoom(String passageName) {
        Passage passage = passages.stream().filter(p -> p.getName().toLowerCase().equals(passageName.toLowerCase()))
                .findFirst().get();
        return passage.getRoom1() == this ? passage.getRoom2() : passage.getRoom1();
    }

    public String getDescription() {
        return description;
    }

    public String getPassageDescription() {
        return passageDescription;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void removeEntity(Entity item) {
        entities.remove(item);
    }

    public void addEntity(Entity item) {
        entities.add(item);
    }

    public ArrayList<Npc> getNpcs(){
        ArrayList<Npc> ar = new ArrayList<Npc>();
        entities.forEach(e -> {
            if(e.isIntelligent()){
                ar.add((Npc)e);
            }
        });
        return ar;
    }

    public String getName() {
        return codeName;
    }

}