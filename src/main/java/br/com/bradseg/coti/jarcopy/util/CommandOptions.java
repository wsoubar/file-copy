package br.com.bradseg.coti.jarcopy.util;

import java.util.ArrayList;
import java.util.List;

//@SuppressWarnings("unchecked")
public class CommandOptions {
    protected List<String> arguments;

    public CommandOptions(String[] args) {
        parse(args);
    }

    public void parse(String[] args) {
        arguments = new ArrayList<String>();
        for (int i = 0; i < args.length; i++) {
            arguments.add(args[i]);
        }
    }

    public int size() {
        return arguments.size();
    }

    public boolean hasOption(String option) {
        boolean hasValue = false;
        String str;
        for (int i = 0; i < arguments.size(); i++) {
            str = arguments.get(i);
            if (true == str.equalsIgnoreCase(option)) {
                hasValue = true;
                break;
            }
        }
        return hasValue;
    }

    public String valueOf(String option) {
        String value = null;
        String str;
        for (int i = 0; i < arguments.size(); i++) {
            str = arguments.get(i);
            if (true == str.equalsIgnoreCase(option)) {
                value = arguments.get(i + 1);
                break;
            }
        }
        return value;
    }

}