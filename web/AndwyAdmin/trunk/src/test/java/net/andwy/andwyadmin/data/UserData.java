package net.andwy.andwyadmin.data;
import net.andwy.andwyadmin.entity.admin.User;
import org.springside.modules.test.data.RandomData;

public class UserData {
    public static User randomNewUser() {
        User user = new User();
        user.setLoginName(RandomData.randomName("user"));
        user.setName(RandomData.randomName("User"));
        user.setPlainPassword(RandomData.randomName("password"));
        return user;
    }
}
