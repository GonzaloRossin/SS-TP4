package ar.edu.itba.ss;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonPrinter {
    JSONArray dataArray;
    JSONArray errorArray;



    public JsonPrinter() {
        this.dataArray = new JSONArray();
        this.errorArray = new JSONArray();
    }

    public void createDataArray(DataAcumulator dataAccumulator){
        for(int i = 0; i < dataAccumulator.getTlist().size();i++){
            addStep(dataAccumulator, i);
        }

    }
    public void createErrorArray(DataAcumulator dataAccumulator){
        for(int i=0;i < dataAccumulator.getDeltas().size();i++){
            addErrorStep(dataAccumulator,i);
        }
    }

    public void addStep(DataAcumulator dataAcumulator, int iteration) {
        JSONObject step = new JSONObject();
        step.put("time", dataAcumulator.getTlist().get(iteration));
        step.put("pAnalytical", dataAcumulator.getPositions().get(Algorithm.ANALITYCAL).get(iteration));
        step.put("pVerlet", dataAcumulator.getPositions().get(Algorithm.VERLET).get(iteration));
        step.put("pBeeman", dataAcumulator.getPositions().get(Algorithm.BEEMAN).get(iteration));
        step.put("pGCP", dataAcumulator.getPositions().get(Algorithm.GCP).get(iteration));
        dataArray.add(step);
    }
    public void addErrorStep(DataAcumulator dataAcumulator,int iteration){
        JSONObject step = new JSONObject();
        step.put("delta", dataAcumulator.getDeltas().get(iteration));
        step.put("errorVerlet",dataAcumulator.getMeanCuadrticErrors().get(Algorithm.VERLET).get(iteration));
        step.put("errorBeeman",dataAcumulator.getMeanCuadrticErrors().get(Algorithm.BEEMAN).get(iteration));
        step.put("errorGCP",dataAcumulator.getMeanCuadrticErrors().get(Algorithm.GCP).get(iteration));
        errorArray.add(step);
    }

    public JSONArray getDataArray() {
        return dataArray;
    }
    public JSONArray getErrorArray(){
        return errorArray;
    }
}
