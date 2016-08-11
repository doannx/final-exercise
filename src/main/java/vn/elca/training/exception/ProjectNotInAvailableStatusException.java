package vn.elca.training.exception;

public class ProjectNotInAvailableStatusException extends Exception {
    private String errorPrjId = "";
    private static final long serialVersionUID = -2240759492958112384L;

    public ProjectNotInAvailableStatusException(String errorPrjId) {
        this.errorPrjId = errorPrjId;
    }

    @Override
    public String getMessage() {
        return errorPrjId;
    }
}