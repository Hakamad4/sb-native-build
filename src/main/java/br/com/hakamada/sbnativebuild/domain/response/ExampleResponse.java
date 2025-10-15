package br.com.hakamada.sbnativebuild.domain.response;

public class ExampleResponse {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ExampleResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
