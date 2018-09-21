package net.jbock.cp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Generated;

@Generated("net.jbock.compiler.Processor")
public final class ArgsParser {
  private static final Set<String> LONG_FLAGS;

  private static final Set<String> SHORT_FLAGS;

  private static final Set<String> LONG_NAMES;

  private static final Set<String> SHORT_NAMES;

  static {
    Set<String> longFlags = new HashSet<>();
    Set<String> shortFlags = new HashSet<>();
    Set<String> longNames = new HashSet<>();
    Set<String> shortNames = new HashSet<>();
    shortFlags.add("r");
    shortNames.add("f");
    longNames.add("path");
    LONG_FLAGS = Collections.unmodifiableSet(longFlags);
    SHORT_FLAGS = Collections.unmodifiableSet(shortFlags);
    LONG_NAMES = Collections.unmodifiableSet(longNames);
    SHORT_NAMES = Collections.unmodifiableSet(shortNames);
  }

  public final Map<String, String> longOptions;

  public final Map<String, String> shortOptions;

  private ArgsParser(Map<String, String> longOptions, Map<String, String> shortOptions) {
    this.longOptions = Collections.unmodifiableMap(longOptions);
    this.shortOptions = Collections.unmodifiableMap(shortOptions);
  }

  public Args parse() {
    return new Args(
        param("path", "f"),
        getBool(null, "r"));
  }

  private String param(String longName, String shortName) {
    String longValue = null, shortValue = null;
    if (longName != null) {
      longValue = this.longOptions.get(longName);
    }
    if (shortName != null) {
      shortValue = this.shortOptions.get(shortName);
    }
    if (longValue == null) {
      return shortValue;
    }
    else if (shortValue == null) {
      return longValue;
    }
    else {
      throw new IllegalArgumentException("Competing arguments: --" + longName + " versus -" + shortName);
    }
  }

  private boolean getBool(String longName, String shortName) {
    return Boolean.valueOf(param(longName, shortName));
  }

  private static void addNext(Set<String> set, Map<String, String> m, String s,
      Iterator<String> it) {
    if (!set.contains(s)) {
      return;
    }
    if (m.containsKey(s)) {
      throw new IllegalArgumentException("Duplicate argument: " + s);
    }
    if (!it.hasNext()) {
      throw new IllegalArgumentException("Expecting argument value: " + s);
    }
    m.put(s, it.next());
  }

  public List<Argument> summary() {
    List<Argument> arguments = new ArrayList<>(2);
    arguments.add(new Argument("path", "f", false, "The file", param("path", "f")));
    arguments.add(new Argument(null, "r", true, "Is it recursive?", param(null, "r")));
    return arguments;
  }

  public static ArgsParser init(String[] args) {
    Map<String, String> longOpts = new HashMap<>();
    Map<String, String> shortOpts = new HashMap<>();
    Iterator<String> it = Arrays.stream(args).iterator();
    while (it.hasNext()) {
      String s = it.next();
      if (s.startsWith("--")) {
        if (LONG_FLAGS.contains(s.substring(2))) {
          longOpts.put(s.substring(2), "true");
        }
        else {
          addNext(LONG_NAMES, longOpts, s.substring(2), it);
        }
      }
      else if (s.startsWith("-")) {
        if (SHORT_FLAGS.contains(s.substring(1))) {
          shortOpts.put(s.substring(1), "true");
        }
        else {
          addNext(SHORT_NAMES, shortOpts, s.substring(1), it);
        }
      }
    }
    return new ArgsParser(longOpts, shortOpts);
  }

  public static final class Argument {
    public final String longName;

    public final String shortName;

    public final boolean flag;

    public final String description;

    public final String value;

    private Argument(String longName, String shortName, boolean flag, String description,
        String value) {
      this.longName = longName;
      this.shortName = shortName;
      this.flag = flag;
      this.description = description;
      this.value = value;
    }

    public String describe() {
      StringBuilder sb = new StringBuilder();
      if (flag) {
        if (longName != null && shortName != null) {
          sb.append('-').append(shortName);
          sb.append(',').append(' ').append('-').append('-').append(longName);
        }
        else if (longName != null) {
          sb.append('-').append('-').append(longName);
        }
        else {
          sb.append('-').append(shortName);
        }
      }
      else {
        if (longName != null && shortName != null) {
          sb.append('-').append(shortName);
          sb.append(',').append(' ').append('-').append('-').append(longName).append(' ').append("VAL");
        }
        else if (longName != null) {
          sb.append('-').append('-').append(longName).append(' ').append("VAL");
        }
        else {
          sb.append('-').append(shortName).append(' ').append("VAL");
        }
      }
      sb.append('\n').append("  ");
      sb.append(description);
      return sb.toString();
    }
  }
}
