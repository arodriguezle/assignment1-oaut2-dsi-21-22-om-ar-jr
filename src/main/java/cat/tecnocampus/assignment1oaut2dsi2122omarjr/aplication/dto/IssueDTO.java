package cat.tecnocampus.assignment1oaut2dsi2122omarjr.aplication.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class IssueDTO {
    @NotNull
    @NotBlank
    private String title;
    private String body;

    public IssueDTO() {
    }

    public IssueDTO(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
