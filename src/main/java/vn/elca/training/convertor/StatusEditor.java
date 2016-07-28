package vn.elca.training.convertor;

import java.beans.PropertyEditorSupport;

import vn.elca.training.model.StatusVO;

public class StatusEditor extends PropertyEditorSupport {
    // This will be called when user HTTP Post to server a field bound to DepartmentVO
    @Override
    public void setAsText(String id) {
        StatusVO s;
        switch (id) {
        case "NEW":
            s = new StatusVO("NEW", "NEW");
            break;
        case "PLA":
            s = new StatusVO("PLA", "PLANNED");
            break;
        case "INP":
            s = new StatusVO("INP", "In progress");
            break;
        case "FIN":
            s = new StatusVO("FIN", "Finished");
            break;
        default:
            s = null;
        }
        this.setValue(s);
    }
}
