package cat.tecnocampus.assignment1oaut2dsi2122omarjr.api;

import cat.tecnocampus.assignment1oaut2dsi2122omarjr.aplication.GithubController;
import cat.tecnocampus.assignment1oaut2dsi2122omarjr.aplication.dto.IssueDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@RestController
public class GithubRestController {
    private final GithubController githubController;

    public GithubRestController(GithubController githubController) {
        this.githubController = githubController;
    }

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

    @GetMapping("/repositories")
    public ResponseEntity<String> repositories(Principal principal) {
        return githubController.getRepositories(principal);
    }

    @GetMapping("/repositories/{owner}/{repo}/issues")
    public ResponseEntity<String> issues(Principal principal, @PathVariable String owner, @PathVariable String repo) {
        return githubController.getIssues(principal, owner, repo);
    }

    @PostMapping("/repositories/{owner}/{repo}/issues")
    public ResponseEntity<String> createIssue(Principal principal, @PathVariable String owner, @PathVariable String repo, @Valid @RequestBody IssueDTO issue) throws IOException {
        return githubController.createIssues(principal, issue, owner, repo);
    }


}
