package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;

public abstract class Log {
    public static void loginAttempt(String username, boolean success) throws IOException {
        TimestampValue now = TimestampValue.now();
        String str = username + " - " + now.displayFormat() + " " + ZoneId.systemDefault() + " - Success: " + success;
        BufferedWriter writer = new BufferedWriter(new FileWriter(" login_attempts.txt", true));
        writer.append(str);
        writer.append("\n");

        writer.close();
    }
}
