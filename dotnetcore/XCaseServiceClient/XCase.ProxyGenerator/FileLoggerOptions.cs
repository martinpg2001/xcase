using System;

namespace XCase.ProxyGenerator
{

    /// <summary>
    /// Generic file logger options.
    /// </summary>
    public class FileLoggerOptions
	{

		public bool Append { get; set; } = true;

		/// <summary>
		/// Determines max size of the one log file.
		/// </summary>
		/// <remarks>If log file limit is specified logger will create new file when limit is reached. 
		/// For example, if log file name is 'test.log', logger will create 'test1.log', 'test2.log' etc.
		/// </remarks>
		public long FileSizeLimitBytes { get; set; } = 0;

		/// <summary>
		/// Determines max number of log files if <see cref="FileSizeLimitBytes"/> is specified.
		/// </summary>
		/// <remarks>If MaxRollingFiles is specified file logger will re-write previously created log files.
		/// For example, if log file name is 'test.log' and max files = 3, logger will use: 'test.log', then 'test1.log', then 'test2.log' and then 'test.log' again (old content is removed).
		/// </remarks>
		public int MaxRollingFiles { get; set; } = 0;

		/// <summary>
		/// Custom formatter for the log entry line. 
		/// </summary>
		public Func<LogMessage, string> FormatLogEntry { get; set; }
	}
}