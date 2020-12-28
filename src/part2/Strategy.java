package part2;

import part1.Message;

import java.util.List;

public interface Strategy {

    String sendMail(String body);

    String getMail(String body);
}
