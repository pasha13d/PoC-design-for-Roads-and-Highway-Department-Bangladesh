package com.secl.eservice.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONObject;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.secl.eservice.util.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("responseFilter")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseFilter implements Filter {
	
	private final String METHOD_POST = "POST";
	
	@SuppressWarnings("unused")
	private FilterConfig filterConfig = null;

	private static class ByteArrayServletStream extends ServletOutputStream {
		
		ByteArrayOutputStream baos;

		ByteArrayServletStream(ByteArrayOutputStream baos) {
			this.baos = baos;
		}

		public void write(int param) throws IOException {
			baos.write(param);
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setWriteListener(WriteListener writeListener) {
			// TODO
		}
	}

	private static class ByteArrayPrintWriter {

		private ByteArrayOutputStream baos = new ByteArrayOutputStream();

		private PrintWriter pw = new PrintWriter(baos);

		private ServletOutputStream sos = new ByteArrayServletStream(baos);

		public PrintWriter getWriter() {
			return pw;
		}

		public ServletOutputStream getStream() {
			return sos;
		}

		byte[] toByteArray() {
			return baos.toByteArray();
		}
	}

	public class CharResponseWrapper extends HttpServletResponseWrapper {
		
		private ByteArrayPrintWriter output;
		private boolean usingWriter;

		public CharResponseWrapper(HttpServletResponse response) {
			super(response);
			usingWriter = false;
			output = new ByteArrayPrintWriter();
		}

		public byte[] getByteArray() {
			return output.toByteArray();
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			if (usingWriter) {
				super.getOutputStream();
			}
			usingWriter = true;
			return output.getStream();
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			if (usingWriter) {
				super.getWriter();
			}
			usingWriter = true;
			return output.getWriter();
		}

		public String toString() {
			return output.toString();
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void destroy() {
		this.filterConfig = null;
	}
	
	public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;        
		HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, Content-Length, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, X-Access-Token, XKey, Authorization");
        response.setHeader("Access-Control-Max-Age", "36000");
        
		DateTime requestTime = new DateTime();
		CharResponseWrapper wrappedResponse = new CharResponseWrapper((HttpServletResponse) response);
		if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
        	chain.doFilter(request, wrappedResponse);
        }
		
		byte[] bytes = wrappedResponse.getByteArray();
		
		if (METHOD_POST.equals(httpRequest.getMethod()) && response.getContentType().toString().contains(MediaType.APPLICATION_JSON_UTF8_VALUE)
			&& response.getContentType().toString().contains(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
			try {
				JSONObject resObj = new JSONObject(new String(bytes));
				DateTime responseTime = new DateTime();
				resObj.optJSONObject("header").put("requestReceivedTime", DateTimeFormat.forPattern(Constant.DATE_FORMAT).print(requestTime));
				resObj.optJSONObject("header").put("responseProcessingTimeInMs", new Period(requestTime, responseTime).getMillis());
				resObj.optJSONObject("header").put("responseTime", DateTimeFormat.forPattern(Constant.DATE_FORMAT).print(responseTime));
				resObj.getJSONObject("header").remove("requestSourceService");
				response.getOutputStream().write(resObj.toString().getBytes());
				if(Constant.PREETY_JSON){
					log.debug("Response sent against valid request : {}", resObj.toString(2));
				} else {
					log.debug("Response sent against valid request : {}", resObj.toString());
				}
	
			} catch (Exception ex) {
				log.error("An exception occurred while sending response : ", ex);
			} 
		} else {
			response.getOutputStream().write(bytes);
		}
	}

}
