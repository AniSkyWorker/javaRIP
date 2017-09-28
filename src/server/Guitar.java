package server;

public class Guitar {
    private String name;
    private int price;
    private String soundingBoardStuff;

    public Guitar(String name, int price, String soundingBoardStuff) {
        this.name = name;
        this.price = price;
        this.soundingBoardStuff = soundingBoardStuff;
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
}
