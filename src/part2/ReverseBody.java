package part2;

public class ReverseBody implements Strategy {
    @Override
    public String sendMail(String body) {
        StringBuilder output = new StringBuilder(body).reverse();

        return output.toString();
    }

    @Override
    public String getMail(String body) {
        StringBuilder output = new StringBuilder(body).reverse();

        return output.toString();
    }
}
