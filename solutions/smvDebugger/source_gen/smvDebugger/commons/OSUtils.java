package smvDebugger.commons;

/*Generated by MPS */

import java.util.EnumSet;

public class OSUtils {
  private static final EnumSet<OS> SUPPORTED_OS = EnumSet.<OS>of(OS.MAC_OS);

  public static OS getOS() {
    final String osName = System.getProperty("os.name").toLowerCase();
    if (osName.contains("mac os")) {
      return OS.MAC_OS;
    }
    if (osName.contains("windows")) {
      return OS.WINDOWS;
    }
    return OS.UNKNOWN;
  }

  public static boolean isSupportedOS(final OS os) {
    return SUPPORTED_OS.contains(os);
  }
}
