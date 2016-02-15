package log;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter
{
  public String format(LogRecord record)
  {
    String msg = "";
    if (record.getMessage() != null) {
      msg = msg + record.getMessage();
    }
    if (record.getThrown() != null) {
      msg = msg + record.getThrown().getMessage();
    }
    return "=====================\nLevel : " + record.getLevel() + '\n' + "LoggerName : " + record.getLoggerName() + '\n' + "Message : " + msg + '\n' + "Class : " + record.getSourceClassName() + '\n' + "Method : " + record.getSourceMethodName() + "\n\n";
  }
}