## LogManager 
    logger工厂，负责配置Logger

## Logger
    Handler  logger默认没有handler
    ResouceBundle

logger有层次关系，默认借用parent的Handler处理log

rootLogger 由AndroidHandler定义，
    @Override
    public void publish(LogRecord record) {
        int level = getAndroidLevel(record.getLevel());
        String tag = DalvikLogging.loggerNameToTag(record.getLoggerName());
        if (!Log.isLoggable(tag, level)) {
            return;
        }

        try {
            String message = getFormatter().format(record);
            Log.println(level, tag, message);
        } catch (RuntimeException e) {
            Log.e("AndroidHandler", "Error logging message.", e);
        }
    }

    Log.isLoggable() 默认INFO以上的log才能显示， 所以，如果Logger没有自定义Handler，INFO以下的log无法显示
    >Checks to see whether or not a log for the specified tag is loggable at the specified level. The default level of any tag is set to INFO. This means that any level above and including INFO will be logged. Before you make any calls to a logging method you should check to see if your tag should be logged. You can change the default level by setting a system property: 'setprop log.tag.<YOUR_LOG_TAG> <LEVEL>' Where level is either VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT, or SUPPRESS. SUPPRESS will turn off all logging for your tag. You can also create a local.prop file that with the following in it: 'log.tag.<YOUR_LOG_TAG>=<LEVEL>' and place that in /data/local.prop.

    在运行时可以改变某tag的级别，setprop log.tag.<YOUR_LOG_TAG> <LEVEL>