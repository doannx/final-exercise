package vn.elca.training.convertor;

import java.beans.PropertyEditorSupport;

import vn.elca.training.model.GroupVO;

public class GroupEditor extends PropertyEditorSupport {
    // This will be called when user HTTP Post to server a field bound to DepartmentVO
    @Override
    public void setAsText(String id) {
        GroupVO d;
        switch (Integer.parseInt(id)) {
        case 1:
            d = new GroupVO(1, "Human Resource");
            break;
        case 2:
            d = new GroupVO(2, "Finance");
            break;
        case 3:
            d = new GroupVO(3, "Information Technology");
            break;
        default:
            d = null;
        }
        this.setValue(d);
    }
}
