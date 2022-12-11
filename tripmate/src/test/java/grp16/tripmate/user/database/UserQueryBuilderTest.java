package grp16.tripmate.user.database;

import grp16.tripmate.user.model.User;
import grp16.tripmate.user.model.UserDbColumnNames;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

class UserQueryBuilderTest {


    IUserQueryBuilder queryBuilder = UserQueryBuilder.getInstance();

    @Test
    void getUserByUsername() {

        String expectedQuery = "SELECT `id`," +
                "    `firstname`," +
                "    `lastname`," +
                "    `email`," +
                "    `password`," +
                "    `birthdate`," +
                "    `gender` " +
                "FROM `User` where email = \"" + "email@mail.com" + "\"";


        Assertions.assertEquals(expectedQuery, queryBuilder.getUserByUsername("email@mail.com"));
    }

    @Test
    void expectedQuery() {
        String expectedQuery = "SELECT `id`," +
                "    `firstname`," +
                "    `lastname`," +
                "    `email`," +
                "    `password`," +
                "    `birthdate`," +
                "    `gender`" +
                "FROM `User` where id = " + 1;
        Assertions.assertEquals(expectedQuery, queryBuilder.getUserByUserID(1));

    }

    @Test
    void createUser() throws NoSuchAlgorithmException, ParseException {
        User user = new User();

        user.setId(1);
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setUsername("username");
        user.setPassword("password");
        user.setBirthDate("1999-02-01");
        user.setGender("Male");

        String expectedQuery = "INSERT INTO `User`" +
                "(" + UserDbColumnNames.id + "," +
                UserDbColumnNames.firstname + "," +
                UserDbColumnNames.lastname + "," +
                UserDbColumnNames.username + "," +
                UserDbColumnNames.password + "," +
                UserDbColumnNames.birthDate + "," +
                UserDbColumnNames.gender + ") " +
                "VALUES" +
                " (\"" + 1 + "\"," +
                "\"" + "firstname" + "\"," +
                "\"" + "lastname" + "\"," +
                "\"" + "username" + "\"," +
                "\"" + user.getPassword() + "\"," +
                "\"" + user.dateToSQLDate(user.getBirthDate()) + "\"," +
                "\"" + "Male" + "\");";


        Assertions.assertEquals(expectedQuery, queryBuilder.createUser(user));


    }

    @Test
    void changeUserDetails() throws NoSuchAlgorithmException {
        User user = new User();
        user.setPassword("password");
        user.setGender("Female");
        user.setFirstname("first_name");
        user.setLastname("last_name");
        user.setId(1);

        String expectedQuery = "update " +
                UserDbColumnNames.tableName + " set " +
                UserDbColumnNames.password + " = '" + user.getPassword() + "'" + "," +
                UserDbColumnNames.gender + " = '" + "Female" + "'" + "," +
                UserDbColumnNames.firstname + " = '" + "first_name" + "'" + "," +
                UserDbColumnNames.lastname + " = '" + "last_name" + "'" +
                " where " +
                UserDbColumnNames.id + " = " + 1;


        Assertions.assertEquals(expectedQuery, queryBuilder.changeUserDetails(user));
    }
}