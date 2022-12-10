package grp16.tripmate.post.database;

import grp16.tripmate.db.connection.DatabaseConnection;
import grp16.tripmate.db.connection.IDatabaseConnection;
import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.post.feedback.model.Feedback;
import grp16.tripmate.post.model.*;
import grp16.tripmate.session.SessionManager;
import grp16.tripmate.user.model.UserDbColumnNames;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostDatabase implements IPostDatabase {
    private final ILogger logger = new MyLoggerAdapter(this);
    private final IPostsQueryGenerator queryGenerator;
    private final IDatabaseConnection dbConnection;

    public PostDatabase() {
        queryGenerator = PostsQueryGenerator.getInstance();
        dbConnection = new DatabaseConnection();
    }

    @Override
    public boolean createPost(Post post) throws Exception {
        post.setOwner((Integer) SessionManager.Instance().getValue(UserDbColumnNames.id));
        String query = queryGenerator.getCreatePostQuery(post);
        return executeQuery(query);
    }

    @Override
    public List<Post> getPostsByUserId(int userid) {
        String query = queryGenerator.getPostsByUserId(userid);
        return selectQueryExecute(query);
    }

    @Override
    public List<Post> getAllPosts() {
        String query = queryGenerator.getAllPosts();
        return selectQueryExecute(query);
    }

    @Override
    public Post getPostByPostId(int post_id) {
        String query = queryGenerator.getPostByPostId(post_id);
        List<Post> posts = selectQueryExecute(query);
        if (posts != null) {
            return posts.get(0);
        } else {
            return null;
        }
    }

    @Override
    public boolean updatePost(Post post) {
        String query = queryGenerator.getUpdatePostQuery(post);
        return executeQuery(query);
    }

    @Override
    public boolean deletePost(int post_id) {
        PostFactory.getInstance().getFeedbackDatabase().deleteFeedbackByPostId(post_id);
        String query = queryGenerator.deletePostQuery(post_id);
        return executeQuery(query);
    }

    @Override
    public boolean hidePost(int post_id) {
        String query = queryGenerator.hidePostQuery(post_id);
        return executeQuery(query);
    }

    @Override
    public List<Feedback> getFeedbacks(int post_id) {
        return PostFactory.getInstance().getFeedbackDatabase().getFeedbacksByPostId(post_id);
    }

    private boolean executeQuery(String query) {
        Connection connection;
        boolean isSuccess = false;
        try {
            connection = dbConnection.getDatabaseConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            isSuccess = true;
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return isSuccess;
    }

    private List<Post> selectQueryExecute(String query) {
        try {
            final Connection connection = dbConnection.getDatabaseConnection();
            final ResultSet postRS = connection.createStatement().executeQuery(query);
            List<Post> posts = resultSetToPosts(postRS);
            connection.close();
            return posts;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }


    public List<Post> resultSetToPosts(ResultSet rs) throws Exception {
        List<Post> results = new ArrayList<>();
        while (rs.next()) {
            Post post = (Post) PostFactory.getInstance().getNewPost();
            post.setId(rs.getInt(PostDbColumnNames.ID));
            post.setTitle(rs.getString(PostDbColumnNames.TITLE));
            post.setCapacity(rs.getInt(PostDbColumnNames.CAPACITY));
            post.setDescription(rs.getString(PostDbColumnNames.DESCRIPTION));
            post.setEndDate(rs.getDate(PostDbColumnNames.ENDDATE));
            post.setHidden(rs.getBoolean(PostDbColumnNames.ISHIDDEN));
            post.setDestination(rs.getString(PostDbColumnNames.DESTINATION));
            post.setMaxAge(rs.getInt(PostDbColumnNames.MAXAGE));
            post.setMinAge(rs.getInt(PostDbColumnNames.MINAGE));
            post.setStartDate(rs.getDate(PostDbColumnNames.STARTDATE));
            post.setSource(rs.getString(PostDbColumnNames.SOURCE));
            post.setOwner(rs.getInt(PostDbColumnNames.OWNER));
            results.add(post);
        }
        return results;
    }
}