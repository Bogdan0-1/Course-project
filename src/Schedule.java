import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Schedule {

    public List<Session> allSessionn = new ArrayList<>();

    public List<Session> loadSessionList() {
        List<Session> allSession = new ArrayList<>();
        try (Connection con = DB_Connection.getConnection();
             CallableStatement stmt = con.prepareCall("{call ALL_SESSION_LIST()}");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("Сеанс_ID");
                String name = rs.getString("Назва");
                int hall = rs.getInt("Кінозал_ID");
                String date = rs.getString("Дата");
                String time = rs.getString("Час_початку");

                allSession.add(new Session(id, name, hall, date, time));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        allSessionn.clear();
        allSessionn.addAll(allSession);
        return allSession;
    }
    public List<Session> refreshF(String film) {

        List<Session> filtered = new ArrayList<>();
        for (Session session : allSessionn) {
            if (session.getNameF().toLowerCase().contains(film.toLowerCase())) {
                filtered.add(session);
            }
        }
        return filtered;
    }
    public List<Session> refreshD(String date, String time){
        List<Session> filtered = new ArrayList<>();
        for (Session session : allSessionn) {
            // Перевіряємо чи містить рядок назву фільму, дату і час
            if (
                    (date == null || session.getDate().contains(date)) &&
                    (time == null || session.getTime().contains(time))) {
                filtered.add(session);
            }
        }
        return filtered;
    }
}

class Session{
    private  int ID_Session;
    private  String nameF;
    private  int hall;
    private  String date;
    private  String time;

    Session(){

    }

    public Session(int ID_Session, String nameF, int hall, String date, String time) {
        this.ID_Session = ID_Session;
        this.nameF = nameF;
        this.hall = hall;
        this.date = date;
        this.time = time;
    }

    public String getNameF() {
        return nameF;
    }

    public int getHall() {
        return hall;
    }

    public int getID_Session() {
        return ID_Session;
    }

    public String  getDate() {
        return date;
    }

    public String  getTime() {
        return time;
    }

    @Override
    public String toString() {
        return nameF + " [" + hall + "] " + " [" + date + "] " + " [" + time + "]";
    }
}
