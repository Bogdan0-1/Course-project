import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Hall {
    private List<Seat> freeSeats;

    public List<Seat> refreshS(Session session){
        freeSeats = new ArrayList<>();
        int id =session.getID_Session();

        try (Connection con = DB_Connection.getConnection();
             CallableStatement stmt = con.prepareCall("{call ALL_FREE_SEATS_LIST(?)}"))
        {
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int seatID = rs.getInt("Місце_ID");
                int number = rs.getInt("Номер");
                String state = rs.getString("Стан");

                freeSeats.add(new Seat(seatID,number,state));
            }
            stmt.close();
            rs.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return freeSeats;
    }

}
class Seat {
    private int id;
    private int number;
    private String state;
    private int hall;

    public Seat(int id, int number, String state) {
        this.id = id;
        this.number = number;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public String getState() {
        return state;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setState(String state) {
        this.state = state;
    }

//    public void StateOFF(Seat seat){
//        try(Connection connection = DB_Connection.getConnection();
//            CallableStatement cstmt = connection.prepareCall("{call }")){
//
//        }
//    }

    @Override
    public String toString() {
        return "Місце # "+number;
    }
}
