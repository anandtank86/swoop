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
package com.swoop.demo.math.controller;

import com.swoop.demo.math.model.CalculationArguments;
import com.swoop.demo.math.service.MathService;
import com.swoop.demo.utils.JSONResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author
 */
@RestController
public class MathController {

    static Logger log = LogManager.getLogger();

    @Autowired
    MathService mathService;

    /**
     *
     * @param n1
     * @param n2
     * @return result
     * @throws java.lang.Exception
     */
    @GetMapping(path = "math/add")
    public JSONObject addNumbersGET(@RequestParam(value = "n1", required = true) Double n1,
            @RequestParam(value = "n2", required = true) Double n2) throws Exception {

        log.debug("Request received for adding numbers. n1 and n2 respective values are " + n1 + " and " + n2);

        Double result = mathService.addNumbers(n1, n2);

        log.debug("Request completed successfully for adding numbers. n1 and n2 respective values are " + n1 + " and " + n2);
        return JSONResponse.prepare(result);
    }

    /**
     *
     * @param mathArgs
     * @return
     * @throws Exception
     */
    @PostMapping(path = "math/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JSONObject addNumbersPOST(@RequestBody CalculationArguments mathArgs) throws Exception {

        log.debug("Request received for adding numbers. n1 and n2 respective values are " + mathArgs.getN1() + " and " + mathArgs.getN2());

        Double result = mathService.addNumbers(mathArgs.getN1(), mathArgs.getN2());

        log.debug("Request completed successfully for adding numbers. n1 and n2 respective values are " + mathArgs.getN1() + " and " + mathArgs.getN2());
        return JSONResponse.prepare(result);
    }

}
