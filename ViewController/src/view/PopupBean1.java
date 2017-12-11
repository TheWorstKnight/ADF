package view;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

public class PopupBean1 {
    public void setEmp(EmployeesEntity emp) {
        this.emp = emp;
    }

    public EmployeesEntity getEmp() {
        return emp;
    }
    private RichPopup deletePopup;
    private RichPopup updatePopup;
    private RichPopup createPopup;
    private EmployeesEntity emp = new EmployeesEntity();

    public PopupBean1() {
    }

    public void setDeletePopup(RichPopup deletePopup) {
        this.deletePopup = deletePopup;
    }

    public RichPopup getDeletePopup() {
        return deletePopup;
    }

    public String CloseDeletePopup() {
        deletePopup.hide();
        return null;
    }

    public String CloseUpdatePopup() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }

        updatePopup.hide();
        return null;
    }

    public String CloseCreatePopup() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        
        createPopup.hide();
        return null;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String DeleteAction() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }

        BindingContainer bindings2 = getBindings();
        OperationBinding operationBinding2 = bindings2.getOperationBinding("Commit");
        Object result2 = operationBinding2.execute();
        if (!operationBinding2.getErrors().isEmpty()) {
            return null;
        }

        deletePopup.hide();
        return null;

    }

    public void setUpdatePopup(RichPopup updatePopup) {
        this.updatePopup = updatePopup;
    }

    public RichPopup getUpdatePopup() {
        return updatePopup;
    }

    public String EditRow() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }

        updatePopup.hide();
        return null;
    }

    public void setCreatePopup(RichPopup createPopup) {
        this.createPopup = createPopup;
    }

    public RichPopup getCreatePopup() {
        return createPopup;
    }

    public String CreateRow() {
       
        DCBindingContainer bc = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bc.findIteratorBinding("EmployeesView1Iterator");
        ViewObject vo = iter.getViewObject();
        Row r = vo.createRow();
        r.setAttribute("FirstName", emp.getFirstName());
        r.setAttribute("LastName", emp.getLastName());
        r.setAttribute("PhoneNumber", emp.getPhoneNumber());
        r.setAttribute("JobId", emp.getJobId());
        r.setAttribute("HireDate", emp.getHireDate());
        r.setAttribute("Salary", emp.getSalary());
        r.setAttribute("ManagerId", emp.getManagerId());
        r.setAttribute("EmployeeId", emp.getEmployeeId());
        r.setAttribute("Email", emp.getMail());
        r.setAttribute("DepartmentId", emp.getDepartmentId());
        r.setAttribute("CommissionPct", emp.getCommissionPct());
        vo.insertRow(r);
        OperationBinding binding;
        binding = bc.getOperationBinding("Commit");
        binding.execute();

        emp = new EmployeesEntity();
        createPopup.hide();
        return null;

    }

}
