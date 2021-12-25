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

    public int info(String message) {
        List<Object> args = new ArrayList<>();
        args.add(message);
        args.add("PRIORITY=%d");
        args.add(6);
        args.add(null);
        return systemdLibrary.sd_journal_send("MESSAGE=%s", args.toArray());
    }
}
