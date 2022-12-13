package grp16.tripmate.post.database;

import grp16.tripmate.post.model.IPost;
import grp16.tripmate.post.model.Post;
import grp16.tripmate.post.model.factory.PostFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class PostsQueryGeneratorTest {

    IPostsQueryGenerator queryGenerator = PostsQueryGenerator.getInstance();

    @Test
    void getCreatePostQuery() {

    }

    @Test
    void getAllPosts() {
        String actual = "SELECT  id,  created_by,  title,  source_location,  destination_location,  start_ts,  end_ts,  min_age,  max_age,  capacity,  is_hidden,  description  FROM Post WHERE is_hidden != 1 AND created_by != 17";
        Assertions.assertEquals(actual, queryGenerator.getAllPosts(17));
    }

    @Test
    void getPostsByUserId() {
        String actual = "SELECT  id,  created_by,  title,  source_location,  destination_location,  start_ts,  end_ts,  min_age,  max_age,  capacity,  is_hidden,  description  FROM Post WHERE is_hidden != 1 AND  created_by = 17";
        Assertions.assertEquals(actual, queryGenerator.getPostsByUserId(17));
    }

    @Test
    void getPostByPostId() {
        String actual = "select * from VehicleBooking where Post_id  = 23;";
        Assertions.assertEquals(actual, queryGenerator.getPostByPostId(23));
    }

    @Test
    void getUpdatePostQuery() throws ParseException {
        String actual = "UPDATE Post    SET title='sharshil1299@gmail.com', source_location='source 2', destination_location='destination3', start_ts='2022-12-14', end_ts='2022-12-22', min_age=10, max_age=20, capacity=10, description='description 3'     WHERE id=23";
        Post post = (Post) PostFactory.getInstance().makeNewPost();
        post.setTitle("sharshil1299@gmail.com");
        post.setSource("source 2");
        post.setDestination("destination3");
        post.setStartDate("2022-12-14");
        post.setEndDate("2022-12-22");
        post.setMinAge(10);
        post.setMaxAge(20);
        post.setCapacity(10);
        post.setDestination("description 3");
        post.setId(23);
        Assertions.assertEquals(actual, queryGenerator.getUpdatePostQuery(post));
    }

    @Test
    void deletePostQuery() {
    }

    @Test
    void hidePostQuery() {
        String actual = "UPDATE Post     SET is_hidden=true     WHERE id=23";
        Assertions.assertEquals(actual, queryGenerator.hidePostQuery(23));
    }
}