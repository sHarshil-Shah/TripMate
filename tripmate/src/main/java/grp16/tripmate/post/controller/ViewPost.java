package grp16.tripmate.post.controller;

import grp16.tripmate.db.connection.DatabaseConnection;
import grp16.tripmate.db.connection.DatabaseConnectionDAO;
import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLogger;
import grp16.tripmate.post.database.GetAllPostsQueryBuilder;
import grp16.tripmate.post.database.GetAllPostsQueryBuilderDAO;
import grp16.tripmate.post.model.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Controller
public class ViewPost {
    private final ILogger logger = new MyLogger(this);
    final GetAllPostsQueryBuilderDAO getAllPostsQueryBuilderDAO;
    final DatabaseConnectionDAO databaseConnectionDAO;

    ViewPost() {
        getAllPostsQueryBuilderDAO = GetAllPostsQueryBuilder.getInstance();
        databaseConnectionDAO = new DatabaseConnection();
    }

    @GetMapping("/viewpost/")
    public String getAllPosts(Model model, @RequestParam int postid) throws Exception {
        model.addAttribute("title", "View Post");
        final Connection connection = databaseConnectionDAO.getDatabaseConnection();
        Statement statement = connection.createStatement();
        String query = getAllPostsQueryBuilderDAO.getPostByPostId(postid);
        logger.info(query);
        final ResultSet postRS = statement.executeQuery(query);
        Post post = Post.resultSetToPosts(postRS).get(0);
        connection.close();
        model.addAttribute("post", post);
        return "viewpost";
    }
}