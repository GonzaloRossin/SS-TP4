package ar.edu.itba.ss;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonPrinter {
    JSONArray dataArray;



    public JsonPrinter() {
        this.dataArray = new JSONArray();
    }

    public void addStep(double positionAnalytical, double posistionVerlet, double positionBeeman, double positionGCP, double T, double error) {
        JSONObject step = new JSONObject();
        step.put("time", T);
        step.put("pAnalytical", positionAnalytical);
        step.put("pVerlet", posistionVerlet);
        step.put("pBeeman", positionBeeman);
        step.put("pGCP", positionGCP);
        step.put("error", 0);
        dataArray.add(step);
    }

    public JSONArray getDataArray() {
        return dataArray;
    }
}
