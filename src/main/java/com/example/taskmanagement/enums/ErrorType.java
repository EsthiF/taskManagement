package com.example.taskmanagement.enums;

public enum ErrorType {
    CLIENT_NOT_FOUND(800, "Client not found", false),
    CLIENT_ALREADY_EXISTS(801, "Client already exists", false),
    INVALID_SECTOR(802, "Sector does not exist", false),
    GENERAL_ERROR(803, "An error has occurred while running", true),
    COMPANY_EMAIL_INVALID(804, "The company contact email entered is invalid", false),
    CLIENT_NAME_TOO_LONG(805, "The client name entered is too long", false),
    CLIENT_NAME_TOO_SHORT(806, "The client name entered is too short", false),
    NO_TASKS_FOUND  (807, "No tasks found", false),
    DUPLICATE_EMAIL(808, "The email entered is already in use", true);


    private int errorNum;
    private String errorMessage;
    private boolean isShowStackTrace;

    ErrorType(int errorNum, String errorMessage, boolean isShowStackTrace) {
        this.errorNum = errorNum;
        this.errorMessage = errorMessage;
        this.isShowStackTrace = isShowStackTrace;
    }

    public int getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isShowStackTrace() {
        return isShowStackTrace;
    }

    public void setShowStackTrace(boolean showStackTrace) {
        isShowStackTrace = showStackTrace;
    }
    }
