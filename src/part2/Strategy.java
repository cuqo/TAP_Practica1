package part2;

public interface Strategy {

    /**
     * Method that will modify the body of the message with some strategy
     * @param body -> body of the message
     * @return body of the message modified
     */
    String sendMail(String body);

    /**
     * Method that will get back the original body of the message with some strategy
     * @param body -> body of the message modified
     * @return original body of the message
     */
    String getMail(String body);
}
