package gui.pizza;

public class Configuration
{

    private String[] sizeNames;

    private int[] sizePrices;

    private String[] toppingNames;

    private int[] toppingPrices;

    private int numberOfDefaultToppings;

    public Configuration(String[] sizeNames, int[] sizePrices, String[] toppingNames, int[] toppingPrices, int numberOfDefaultToppings)
    {
        if (sizeNames.length != sizePrices.length) 
        {
            throw new IllegalArgumentException();
        }

        if (toppingNames.length != toppingPrices.length) 
        {
            throw new IllegalArgumentException();
        }
        if (numberOfDefaultToppings > toppingNames.length) 
        {
            throw new IllegalArgumentException();
        }
        this.sizeNames = sizeNames;
        this.sizePrices = sizePrices;
        this.toppingNames = toppingNames;
        this.toppingPrices = toppingPrices;
        this.numberOfDefaultToppings = numberOfDefaultToppings;
    }

    public String[] getSizeNames()
    {
        return this.sizeNames;
    }

    public int[] getSizePrices()
    {
        return this.sizePrices;
    }

    public String[] getToppingNames()
    {
        return this.toppingNames;
    }

    public int[] getToppingPrices()
    {
        return this.toppingPrices;
    }

    public int getNumberOfDefaultToppings()
    {
        return this.numberOfDefaultToppings;
    }

}
