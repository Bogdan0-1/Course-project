public class Cinemagoer {
    private int id;
    private String fullname;
    private String email;

    public Cinemagoer(String fullname, String email) {
        this.fullname = fullname;
        this.email = email;

    }

    public int getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }


}
