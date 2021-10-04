package ThreadModel;

public class Mark {
    private int valuesOfMark;

    public Mark(int valuesOfMark) {
        if (valuesOfMark>0&&valuesOfMark<=100)
        this.valuesOfMark = valuesOfMark;
        else System.out.println("Mark must be in 0 from 100 deapasone");
    }

    public int getValuesOfMark() {
        return valuesOfMark;
    }

    public void setValuesOfMark(int valuesOfMark) {
        this.valuesOfMark = valuesOfMark;
    }
}
