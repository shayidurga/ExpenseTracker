package dao;

	import java.sql.*;
	import java.util.ArrayList;
	import java.util.List;
	import model.User;

	public class UserDAO {
	    private Connection connection;
	        public UserDAO() {
	            try {
	                // Establish database connection
	                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/journal", "root", "root");
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }

	        public void addUser(User user) {
	            String sql = "INSERT INTO user (full_name, username, email, password, gender) VALUES (?, ?, ?, ?, ?)";
	            try (PreparedStatement statement = connection.prepareStatement(sql)) {
	                statement.setString(1, user.getFullName());
	                statement.setString(2, user.getUsername());
	                statement.setString(3, user.getEmail());
	                statement.setString(4, user.getPassword());
	                statement.setString(5, user.getGender());
	                statement.executeUpdate();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }

	        public void updateUser(User user) {
	            String sql = "UPDATE user SET full_name = ?, username = ?, email = ?, gender = ? WHERE UserID = ?";
	            try (PreparedStatement statement = connection.prepareStatement(sql)) {
	                statement.setString(1, user.getFullName());
	                statement.setString(2, user.getUsername());
	                statement.setString(3, user.getEmail());
	                statement.setString(4, user.getGender());
	                statement.setInt(5, user.getUserID());
	                statement.executeUpdate();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }

	        public void deleteUser(int userID) {
	            String sql = "DELETE FROM user WHERE UserID = ?";
	            try (PreparedStatement statement = connection.prepareStatement(sql)) {
	                statement.setInt(1, userID);
	                statement.executeUpdate();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }

	        public User getUserByID(int userID) {
	            User user = null;
	            String sql = "SELECT * FROM user WHERE UserID = ?";
	            try (PreparedStatement statement = connection.prepareStatement(sql)) {
	                statement.setInt(1, userID);
	                ResultSet resultSet = statement.executeQuery();
	                if (resultSet.next()) {
	                    user = new User(
	                        resultSet.getInt("UserID"),
	                        resultSet.getString("full_name"),
	                        resultSet.getString("username"),
	                        resultSet.getString("email"),
	                        resultSet.getString("password"),
	                        resultSet.getString("gender"),
	                        resultSet.getTimestamp("created_at")
	                    );
	                }
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	            return user;
	        }

	        public List<User> getAllUsers() {
	            List<User> userList = new ArrayList<>();
	            String sql = "SELECT * FROM user";
	            try (Statement statement = connection.createStatement();
	                 ResultSet resultSet = statement.executeQuery(sql)) {
	                while (resultSet.next()) {
	                    User user = new User(
	                        resultSet.getInt("UserID"),
	                        resultSet.getString("full_name"),
	                        resultSet.getString("username"),
	                        resultSet.getString("email"),
	                        resultSet.getString("password"),
	                        resultSet.getString("gender"),
	                        resultSet.getTimestamp("created_at")
	                    );
	                    userList.add(user);
	                }
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	            return userList;
	        }
	    }
