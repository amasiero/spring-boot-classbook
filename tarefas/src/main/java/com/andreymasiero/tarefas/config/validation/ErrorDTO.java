package com.andreymasiero.tarefas.config.validation;

public class ErrorDTO {

    private String field;
    private String error;

    public ErrorDTO(String field, String error) {
        this.field = field;
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public String getError() {
        return error;
    }
}
