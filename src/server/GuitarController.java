package server;

import java.util.LinkedList;
import java.util.List;

public class GuitarController {

    private List<Guitar> guitars;

    public GuitarController() {
        this.guitars = new LinkedList<Guitar>();
    }

    public void setGuitars(List<Guitar> guitars) {
        this.guitars = guitars;
    }

    public List<Guitar> getGuitars() {
        return guitars;
    }

    public void AddGuitar(String name, int price, String soundingBoardStuff) {
        guitars.add(new Guitar(name, price, soundingBoardStuff));
    }
}
