package part1;

public class User {
    private final String username;
    private final String name;
    private final int yearBirth;

    /**
     * Constructor
     * @param username -> username
     * @param name -> name of user
     * @param yearBirth -> birth year
     */
    public User(String username, String name, int yearBirth) {
        this.username = username;
        this.name = name;
        this.yearBirth = yearBirth;
    }

    /**
     * Returns username from User
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns name from User
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns birth year from User
     * @return birth year
     */
    public int getYearBirth() {
        return yearBirth;
    }

    /**
     * Method that print user
     * @return print user
     */
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", yearBirth=" + yearBirth +
                '}';
    }
}
