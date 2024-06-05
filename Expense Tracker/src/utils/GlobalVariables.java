package utils;

public class GlobalVariables {
	    private static int userID;
	    private static String username;

	    public static int getUserID() {
	        return userID;
	    }

	    public static void setUserID(int userID) {
	        GlobalVariables.userID = userID;
	    }

	    public static String getUsername() {
	        return username;
	    }

	    public static void setUsername(String username) {
	        GlobalVariables.username = username;
	    }
	}

