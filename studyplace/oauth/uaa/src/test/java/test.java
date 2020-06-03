import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @author WXJ
 * @descrption
 * @create 2020/6/3 10:48
 **/

@RunWith(JUnit4.class)
public class test {
    @Test
    public void testBCrypt(){
        String hashpw = BCrypt.hashpw("secret",BCrypt.gensalt());
        System.out.println(hashpw);
    }

}
