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

package me.figo.models;

import java.util.Date;

import com.google.gson.annotations.Expose;

/**
 * Represents the status of the synchonisation between figo and the bank servers
 * 
 * @author Stefan Richter
 */
public class SynchronizationStatus {
    /**
     * Internal figo status code
     */
    @Expose(serialize = false)
    private Integer code;

    /**
     * Human-readable error message.
     */
    @Expose(serialize = false)
    private String  message;

    /**
     * Timestamp of last synchronization
     */
    @Expose(serialize = false)
    private Date    synced_at;

    /**
     * Timestamp of last successful synchronization
     */
    @Expose(serialize = false)
    private Date    succeeded_at;

    public SynchronizationStatus() {
    }

    /**
     * @return the internal figo status code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @return the human-readable error message
     */
    public String getMessage() {
        return message;
    }

	public Date getSyncedAt() {
		return synced_at;
	}

	public Date getSucceededAt() {
		return succeeded_at;
	}
}
