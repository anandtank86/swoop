/*
 * Copyright (c) 2017, WestJet (Swoop-ULCC) Inc
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.swoop.demo.core.exceptions;

/**
 *
 * @author zo061e
 */
public class DEMOException extends Exception {

    /**
     *
     */
    protected int httpStatus;

    /**
     *
     */
    protected String code = null;

    /**
     *
     */
    protected String message = null;

    /**
     *
     */
    protected String error = null;

    /**
     *
     * @return
     */
    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     *
     * @return
     */
    public String getError() {
        return error;
    }

    /**
     *
     * @param code
     * @param message
     */
    public DEMOException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     *
     * @param code
     * @param message
     * @param error
     */
    public DEMOException(String code, String message, String error) {
        this.code = code;
        this.message = message;
        this.error = error;
    }

    /**
     *
     * @param code
     * @param message
     * @param t
     */
    public DEMOException(String code, String message, Throwable t) {
        super(t);
        this.code = code;
        this.message = message;
        this.error = t != null ? t.getMessage() : "No error details available";
    }
}
