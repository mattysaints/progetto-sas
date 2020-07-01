package businesslogic.user;

import businesslogic.turn.Turn;
import businesslogic.turn.TurnBoard;
import javafx.collections.FXCollections;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class User {

    private static final Map<Integer, User> loadedUsers = FXCollections.observableHashMap();

    public enum Role {SERVIZIO, CUOCO, CHEF, ORGANIZZATORE}

    private int id;
    private String username;
    private final Set<Role> roles;
    private final Set<Turn> isAvailableTurn;

    public User() {
        id = 0;
        username = "";
        this.roles = new HashSet<>();
        this.isAvailableTurn = new HashSet<>();
    }

    public boolean isManager() {
        return roles.contains(Role.ORGANIZZATORE);
    }

    public boolean isChef() {
        return roles.contains(Role.CHEF);
    }

    public boolean isAvailableIn(Turn turn) {
        return isAvailableTurn.contains(turn) || roles.contains(Role.CHEF) || roles.contains(Role.ORGANIZZATORE);
    }

    public String getUserName() {
        return username;
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        String result = username;
        if (roles.size() > 0) {
            result += ": ";

            for (User.Role r : roles) {
                result += r.toString() + " ";
            }
        }
        return result;
    }

    public static List<User> loadCooks() {
        return loadUsers().stream().filter(User::isCook).collect(Collectors.toList());
    }

    private boolean isCook() {
        return roles.contains(Role.CUOCO);
    }

    public static ArrayList<User> loadUsers() {
        String usersQuery = "SELECT * FROM Users";
        PersistenceManager.executeQuery(usersQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                User load = new User();
                load.id = rs.getInt(1);
                load.username = rs.getString(2);
                loadedUsers.put(load.id, load);
            }
        });
        String roleQuery = "SELECT user_id, role_id FROM UserRoles";
        PersistenceManager.executeQuery(roleQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String role = rs.getString("role_id");
                User load = loadedUsers.get(rs.getInt(1));
                switch (role.charAt(0)) {
                    case 'c' -> load.roles.add(Role.CUOCO);
                    case 'h' -> load.roles.add(Role.CHEF);
                    case 'o' -> load.roles.add(Role.ORGANIZZATORE);
                    case 's' -> load.roles.add(Role.SERVIZIO);
                }
            }
        });

        String turnsQuery = "SELECT * FROM UserTurns";
        PersistenceManager.executeQuery(turnsQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int user_id = rs.getInt(1);
                int turn_id = rs.getInt(2);
                User load = loadedUsers.get(user_id);
                load.isAvailableTurn.add(TurnBoard.getInstance().getTurnById(turn_id));
            }
        });

        return new ArrayList<>(loadedUsers.values());
    }

    // STATIC METHODS FOR PERSISTENCE

    public static User loadUserById(int uid) {
        if (loadedUsers.containsKey(uid)) return loadedUsers.get(uid);

        User load = new User();
        String userQuery = "SELECT * FROM Users WHERE id='" + uid + "'";
        PersistenceManager.executeQuery(userQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                load.id = rs.getInt("id");
                load.username = rs.getString("username");
            }
        });
        if (load.id > 0) {
            loadedUsers.put(load.id, load);
            String roleQuery = "SELECT * FROM UserRoles WHERE user_id=" + load.id;
            PersistenceManager.executeQuery(roleQuery, new ResultHandler() {
                @Override
                public void handle(ResultSet rs) throws SQLException {
                    String role = rs.getString("role_id");
                    switch (role.charAt(0)) {
                        case 'c' -> load.roles.add(Role.CUOCO);
                        case 'h' -> load.roles.add(Role.CHEF);
                        case 'o' -> load.roles.add(Role.ORGANIZZATORE);
                        case 's' -> load.roles.add(Role.SERVIZIO);
                    }
                }
            });
            String turnsQuery = "SELECT * FROM UserTurns WHERE user_id ='" + load.id + "'";
            PersistenceManager.executeQuery(turnsQuery, new ResultHandler() {
                @Override
                public void handle(ResultSet rs) throws SQLException {
                    int user_id = rs.getInt(1);
                    int turn_id = rs.getInt(2);
                    User load = loadedUsers.get(user_id);
                    load.isAvailableTurn.add(TurnBoard.getInstance().getTurnById(turn_id));
                }
            });
        }
        return load;
    }

    public static User loadUser(String username) {
        User u = new User();
        String userQuery = "SELECT * FROM Users WHERE username='" + username + "'";
        PersistenceManager.executeQuery(userQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                u.id = rs.getInt("id");
                u.username = rs.getString("username");
            }
        });
        if (u.id > 0) {
            loadedUsers.put(u.id, u);
            String roleQuery = "SELECT * FROM UserRoles WHERE user_id=" + u.id;
            PersistenceManager.executeQuery(roleQuery, new ResultHandler() {
                @Override
                public void handle(ResultSet rs) throws SQLException {
                    String role = rs.getString("role_id");
                    switch (role.charAt(0)) {
                        case 'c':
                            u.roles.add(User.Role.CUOCO);
                            break;
                        case 'h':
                            u.roles.add(User.Role.CHEF);
                            break;
                        case 'o':
                            u.roles.add(User.Role.ORGANIZZATORE);
                            break;
                        case 's':
                            u.roles.add(User.Role.SERVIZIO);
                    }
                }
            });
        }
        return u;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                username.equals(user.username) &&
                roles.equals(user.roles) &&
                isAvailableTurn.equals(user.isAvailableTurn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, roles, isAvailableTurn);
    }
}
