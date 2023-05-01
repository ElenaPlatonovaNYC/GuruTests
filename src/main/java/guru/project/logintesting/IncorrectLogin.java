package guru.project.logintesting;

public class IncorrectLogin {
    public static void main(String[] args) {
        CredentialsStorage credentials=new CredentialsStorage();
        Utils.testUnsuccessfulLoginAttempt("love", credentials.getPassword());
    }
}
