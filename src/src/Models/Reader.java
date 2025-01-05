package Models;


import Services.*;

public class Reader implements User {
    private String name;
    private String cardNo;


    public Reader(String cardNo, String name) {
        this.cardNo = cardNo;
        this.name = name;
    }


    @Override
    public String getName() {
        return name;
    }
    public String getCardNo() {
        return cardNo;
    }

    @Override
    public String getRole() {
        return "Reader";
    }

}
