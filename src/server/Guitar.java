package server;

import java.util.Date;

public class Guitar {
    private String name;
    private int price;
    private String soundingBoardStuff;
    private Date manufactureDate;

    public Guitar(String name, int price, String soundingBoardStuff, Date manufactureDate) {
        this.name = name;
        this.price = price;
        this.soundingBoardStuff = soundingBoardStuff;
        this.manufactureDate = manufactureDate;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getSoundingBoardStuff() {
        return soundingBoardStuff;
    }

    public Date getManufatureDate() { return manufactureDate; }
}
