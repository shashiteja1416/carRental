import org.springframework.util.DigestUtils;

public class test {
    public static void main(String[] args){
        String newPass= "hello";
        byte[] by = newPass.getBytes();
        byte[] by1 = newPass.getBytes();
        String pw = DigestUtils.md5DigestAsHex(by);
        String pw1 = DigestUtils.md5DigestAsHex(by1);
        System.out.println(pw);
        System.out.println(pw1);
    }

}
