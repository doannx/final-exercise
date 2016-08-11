package vn.elca.training.editor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;

import vn.elca.training.dom.Employee;
import vn.elca.training.service.IEmployeeService;

public class MemberCollectionEditor extends CustomCollectionEditor {
    public MemberCollectionEditor(Class<? extends Collection> collectionType) {
        super(collectionType);
    }

    @Autowired
    private IEmployeeService empService;

    protected Object convertElement(Object element) {
        if (element instanceof Employee) {
            return element;
        }
        if (element instanceof String) {
            Employee staff = this.empService.getById(Long.valueOf(String.valueOf(element)));
            return staff;
        }
        return null;
    }
}
