namespace XCaseFormsApplication
{
    using System;
    using log4net;
    using log4net.Appender;

    public class MemoryAppenderWithEvents : MemoryAppender
    {
        public event EventHandler Updated;

        protected override void Append(log4net.Core.LoggingEvent loggingEvent)
        {
            base.Append(loggingEvent);
            EventHandler handler = Updated;
            if (handler != null)
            {
                handler(this, new EventArgs());
            }
        }
    }
}