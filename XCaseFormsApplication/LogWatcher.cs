namespace XCaseFormsApplication
{
    using System;
    using System.Text;
    using log4net;
    using log4net.Appender;
    using log4net.Core;

    public class LogWatcher
    {
        private string logContent;
        private MemoryAppenderWithEvents memoryAppender;
        public event EventHandler Updated;

        public string LogContent
        {
            get { return logContent; }
            set { logContent = value; }
        }

        public LogWatcher()
        {
            this.memoryAppender = (MemoryAppenderWithEvents)Array.Find(LogManager.GetRepository().GetAppenders(), GetMemoryAppender);
            this.logContent = GetEvents(memoryAppender);
            this.memoryAppender.Updated += HandleUpdate;
        }

        public void Clear()
        {
            this.logContent = string.Empty;
            this.memoryAppender.Clear();
        }

        public void HandleUpdate(object sender, EventArgs e)
        {
            this.logContent += GetEvents(memoryAppender);
            EventHandler handler = Updated;
            if (handler != null)
            {
                handler(this, new EventArgs());
            }
        }

        private static bool GetMemoryAppender(IAppender appender)
        {
            if (appender.Name != null && appender.Name.Equals("MemoryAppender"))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        public string GetEvents(MemoryAppenderWithEvents memoryAppender)
        {
            StringBuilder output = new StringBuilder();
            LoggingEvent[] loggingEventArray = memoryAppender.GetEvents();
            if (loggingEventArray != null && loggingEventArray.Length > 0)
            {
                memoryAppender.Clear();
                foreach (LoggingEvent loggingEvent in loggingEventArray)
                {
                    string line = loggingEvent.TimeStamp.ToString("yyyy-MM-dd HH:mm:ss,fff") + " [" + loggingEvent.ThreadName + "] " + loggingEvent.Level + " " + loggingEvent.LoggerName + ": " + loggingEvent.RenderedMessage + "\r\n";
                    output.Append(line);
                }
            }

            return output.ToString();
        }
    }
}