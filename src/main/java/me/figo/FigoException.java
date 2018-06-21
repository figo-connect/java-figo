//
// Copyright (c) 2013 figo GmbH
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
//

package me.figo;

import java.util.Map;

import com.google.gson.annotations.Expose;

/***
 * Base Class for all figo Exceptions. It extends the normal Java exceptions with an error_code field, which carries the computer readable error reason.
 *
 * @author Stefan Richter
 */
public class FigoException extends Exception {

    private static final long serialVersionUID = -3645017096212930985L;

    private final String error_message;
    private final String error_description;

    public FigoException(String error_message, String error_description) {
        super(error_message);

        this.error_message = error_message;
        this.error_description = error_description;
    }

    public FigoException(String error_message, String error_description, Throwable exc) {
        super(error_message, exc);

        this.error_message = error_message;
        this.error_description = error_description;
    }

    public FigoException(ErrorResponse response) {
        this(response.getError().getCode(), response.getError().getDescription());
    }

    public String getErrorMessage() {
        return error_message;
    }

    public String getErrorDescription() {
        return error_description;
    }

    public static class ErrorResponse {

        @Expose
        private ErrorObject error;

        public ErrorResponse() {
        }

        public ErrorObject getError() {
            return error;
        }
    }

    public static class ErrorObject	{
    	
		@Expose
		private String code;

		@Expose
		private Map<String, String[]> data;

		@Expose
		private String description;
		
		@Expose
		private String group;

    	public ErrorObject()	{
    	}

    	public String getDescription()	{
    		return description;
    	}

		public String getCode() {
			return code;
		}

		public String getGroup() {
			return group;
		}

		public Map<String, String[]> getData() {
			return data;
		}

		@Override
		public String toString() {
			return "ErrorObject [" + (code != null ? "code=" + code + ", " : "")
					+ (data != null ? "data=" + data + ", " : "")
					+ (description != null ? "description=" + description + ", " : "")
					+ (group != null ? "group=" + group : "") + "]";
		}
    }
}
