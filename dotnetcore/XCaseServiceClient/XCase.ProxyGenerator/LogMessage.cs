using System;
using System.Collections.Generic;
using System.Text;
using Microsoft.Extensions.Logging;

namespace XCase.ProxyGenerator
{
	public struct LogMessage
	{
		public readonly string Message;
		public readonly LogLevel LogLevel;
		public readonly EventId EventId;
		public readonly Exception Exception;

		internal LogMessage(LogLevel level, EventId eventId, string message, Exception ex)
		{
			Message = message;
			LogLevel = level;
			EventId = eventId;
			Exception = ex;
		}
	}
}