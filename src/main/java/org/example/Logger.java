package org.example;

import com.sun.jna.Library;
import com.sun.jna.Native;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    public interface SystemdLibrary extends Library {
        int sd_journal_send(String format, Object... args);
    }

    private  SystemdLibrary systemdLibrary;

    public Logger() {
        systemdLibrary = (SystemdLibrary) Native.loadLibrary("systemd", SystemdLibrary.class);
    }

    public int emerg(String message) {
        return this.send(message, Priority.LOG_EMERG);
    }

    public int alert(String message) {
        return this.send(message, Priority.LOG_ALERT);
    }

    public int crit(String message) {
        return this.send(message, Priority.LOG_CRIT);
    }

    public int error(String message) {
        return this.send(message, Priority.LOG_ERR);
    }

    public int warning(String message) {
        return this.send(message, Priority.LOG_WARNING);
    }

    public int notice(String message) {
        return this.send(message, Priority.LOG_NOTICE);
    }

    public int info(String message) {
        return this.send(message, Priority.LOG_INFO);
    }

    public int debug(String message) {
        return this.send(message, Priority.LOG_DEBUG);
    }

    public int send(String message, Priority priority) {
        List<Object> args = new ArrayList<>();
        args.add(message);
        args.add("PRIORITY=%d");
        args.add(priority.ordinal());
        args.add(null);
        return systemdLibrary.sd_journal_send("MESSAGE=%s", args.toArray());

    }
}
