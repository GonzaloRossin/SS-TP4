package ar.edu.itba.ss;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonPrinter {
    JSONArray dataArray;



    public JsonPrinter() {
        this.dataArray = new JSONArray();
    }

    public void createArray(DataAcumulator dataAccumulator){
        for(int i = 0; i < dataAccumulator.getTlist().size();i++){
            addStep(dataAccumulator, i);
        }
    }

    public void addStep(DataAcumulator dataAcumulator, int iteration) {
        JSONObject step = new JSONObject();
        step.put("time", dataAcumulator.getTlist().get(iteration));
        step.put("pAnalytical", dataAcumulator.getPositions().get(Algorithm.ANALITYCAL).get(iteration));
        step.put("pVerlet", dataAcumulator.getPositions().get(Algorithm.VERLET).get(iteration));
        step.put("pBeeman", dataAcumulator.getPositions().get(Algorithm.BEEMAN).get(iteration));
        step.put("pGCP", dataAcumulator.getPositions().get(Algorithm.GCP).get(iteration));
        step.put("GCPerror", dataAcumulator.getMeanCuadrticErrors().get(Algorithm.GCP));
        step.put("beemanError", dataAcumulator.getMeanCuadrticErrors().get(Algorithm.BEEMAN));
        step.put("verletError", dataAcumulator.getMeanCuadrticErrors().get(Algorithm.VERLET));
        dataArray.add(step);
    }

    public JSONArray getDataArray() {
        return dataArray;
    }
}
