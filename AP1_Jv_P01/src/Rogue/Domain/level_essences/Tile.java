package Domain.level_essences;

public class Tile {
    private boolean blocked;
    private boolean blocked_sight;
    private boolean isVisited;
    private String symb;
    private int color;

    public int getColor() {
        return color;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getSymb() {
        return symb;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isBlocked_sight() {
        return blocked_sight;
    }
    
    public void setSymb(String symb) {this.symb = symb;}
    public void setBlocked_sight(boolean blocked_sight) {
        this.blocked_sight = blocked_sight;
        if (!blocked_sight) {
            this.isVisited = true;
        }
    }

    public Tile() {
        this.blocked = true;
        this.blocked_sight = true;
        this.isVisited = false;
        this.symb = "#";
        this.color = 4;
    }

}