package m.todo.utils;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//public class PasswordEncoderImpl {
//    public static void main(String[] args) {
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
////        System.out.println(encoder.encode("u"));
//        System.out.println(encoder.encode("a"));
//    }
//}

import java.security.SecureRandom;
import java.util.Base64;

public class PasswordEncoderImpl {
    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[48]; // 384 bits
        secureRandom.nextBytes(key);
        String secretKey = Base64.getEncoder().encodeToString(key);
        System.out.println("Generated Secret Key: " + secretKey);
    }
}
