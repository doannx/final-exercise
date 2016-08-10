package vn.elca.training.model;

import java.io.Serializable;
import java.util.List;

public class SearchResultVO<T> implements Serializable {
    private static final long serialVersionUID = 8292117395966311904L;
    private long size;
    private List<T> lstResult;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public List<T> getLstResult() {
        return lstResult;
    }

    public void setLstResult(List<T> lstResult) {
        this.lstResult = lstResult;
    }

}
