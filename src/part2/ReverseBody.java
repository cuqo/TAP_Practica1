package part2;

public class ReverseBody implements Strategy {

    /**
     * Method that reverses the body of the message
     * @param body -> body of the message
     * @return body of the message reversed
     */
    @Override
    public String sendMail(String body) {
        StringBuilder output = new StringBuilder(body).reverse();

        return output.toString();
    }

    /**
     * Method that reverses the body of the message
     * @param body -> body of the message ciphered
     * @return body of the message reversed
     */
    @Override
    public String getMail(String body) {
        StringBuilder output = new StringBuilder(body).reverse();

        return output.toString();
    }
}
