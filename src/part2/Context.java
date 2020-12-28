package part2;

public class Context {
    private Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public String sendMailStrategy(String body){
        return strategy.sendMail(body);
    }

    public String getMailStrategy(String body){
        return strategy.getMail(body);
    }
}
