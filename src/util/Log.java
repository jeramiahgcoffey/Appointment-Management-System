package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;

/**
 * Abstract class contains methods for writing to files.
 *
 * @author Jeramiah Coffey
 */
public abstract class Log {
    /**
     * Logs login attempts to a text file named 'login_attempts.txt'.
     *
     * @param username The username which was entered.
     * @param success  Represents if a login attempt was successful.
     */
    public static void loginAttempt(String username, boolean success) throws IOException {
        DateTimeValue now = DateTimeValue.now();
        String str = username + " - " + now.displayFormat() + " " + ZoneId.systemDefault() + " - Success: " + success;
        BufferedWriter writer = new BufferedWriter(new FileWriter(" login_attempts.txt", true));
        writer.append(str);
        writer.append("\n");

        writer.close();
    }
}
