package game;

public interface Player {

    public String getName();

    public void setName(String name);

    public int getPlace();

    public void setPlace(int place);

    Move move(Position position, Cell cell, int step);
}