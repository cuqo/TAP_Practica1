package part2;

public class Context {
    private final Strategy strategy;

    /**
     * Constructor that defines the strategy is going to be followed
     * @param strategy -> strategy object to be used
     */
    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public String sendMailStrategy(String body) {
        return strategy.sendMail(body);
    }

    public String getMailStrategy(String body) {
        return strategy.getMail(body);
    }
}
