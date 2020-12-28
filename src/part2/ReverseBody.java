package part2;

import part1.Message;

import java.util.List;

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
