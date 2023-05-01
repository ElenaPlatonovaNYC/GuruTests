package guru.project.logintesting;

public class IncorrectPassword {
    public static void main(String[] args) {
        CredentialsStorage credentials=new CredentialsStorage();
        Utils.testUnsuccessfulLoginAttempt(credentials.getLogin(), "12345");

    }

}

