package vn.elca.training.model;

import java.util.HashMap;
import java.util.Map;

public class SearchCriteriaVO {
    private Map<String, String> creteria = new HashMap<String, String>();

    public Map<String, String> getCreteria() {
        return creteria;
    }

    public void setCreteria(Map<String, String> creteria) {
        this.creteria = creteria;
    }
}
