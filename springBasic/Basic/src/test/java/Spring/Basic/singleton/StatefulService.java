package Spring.Basic.singleton;

public class StatefulService {

    private int price;

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice()
    {
        return price;
    }

}
