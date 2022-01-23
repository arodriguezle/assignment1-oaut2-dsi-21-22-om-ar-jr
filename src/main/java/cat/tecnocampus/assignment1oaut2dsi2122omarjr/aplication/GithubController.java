package cat.tecnocampus.assignment1oaut2dsi2122omarjr.aplication;
import cat.tecnocampus.assignment1oaut2dsi2122omarjr.aplication.dto.IssueDTO;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import java.io.IOException;
import java.security.Principal;

@Controller
public class GithubController {
    private final String url_GET_repositories = "https://api.github.com/user/repos";
    private final String url_issues = "https://api.github.com/repos/%s/%s/issues";
    private final OAuth2RestTemplate gitHubRestTemplate;

    public GithubController(OAuth2RestTemplate gitHubRestTemplate) {
        this.gitHubRestTemplate = gitHubRestTemplate;
    }

    public ResponseEntity<String> getRepositories(Principal principal) {
        ResponseEntity<String> response;
        response = gitHubRestTemplate.exchange(url_GET_repositories, HttpMethod.GET, null, String.class);
        return response;
    }

    public ResponseEntity<String> getIssues(Principal principal, String owner, String repo) {
        ResponseEntity<String> response;
        response = gitHubRestTemplate.exchange(String.format(url_issues, owner, repo), HttpMethod.GET, null, String.class);
        return response;
    }

    public ResponseEntity<String> createIssues(Principal principal, IssueDTO issue, String owner, String repo) throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String issueJson = ow.writeValueAsString(issue);
        HttpEntity<String> request = new HttpEntity(issueJson);
        ResponseEntity<String> response = gitHubRestTemplate.postForEntity(String.format(url_issues, owner, repo), request, String.class);
        return response;
    }
}
