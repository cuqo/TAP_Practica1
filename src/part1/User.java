package part1;

public class User {
    private final String username;
    private final String name;
    private final int yearBirth;

    public User(String username, String name, int yearBirth) {
        this.username = username;
        this.name = name;
        this.yearBirth = yearBirth;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public int getYearBirth() {
        return yearBirth;
    }
}
