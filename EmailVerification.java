import java.util.regex.Pattern;

public class EmailVerification {
    private String email;

    public EmailVerification(String email) {
        this.email = email;
    }

    public boolean isValid() {
        String emailRegex = "^[a-zA-Z]+\\.\\d+@ncit\\.edu\\.np$";
        Pattern pat = Pattern.compile(emailRegex);
        return email != null && pat.matcher(email).matches();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}