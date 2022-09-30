package ar.edu.itba.ss;

import java.util.ArrayList;
import java.util.List;

public class DataAcumulator {
    private List<Double> Tlist;
    private List<Double> Plist;
    private List<Double> Error;

    public DataAcumulator() {
        Tlist = new ArrayList<>();
        Plist = new ArrayList<>();
        Error = new ArrayList<>();
    }

    public void addP(Double i) {
        this.Plist.add(i);
    }

    public void addT(Double i) {
        this.Tlist.add(i);
    }
    public void addError(Double i) {
        this.Error.add(i);
    }

}
