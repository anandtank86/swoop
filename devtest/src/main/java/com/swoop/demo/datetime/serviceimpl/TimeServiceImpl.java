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
package com.swoop.demo.datetime.serviceimpl;

import com.swoop.demo.core.config.SecurityConfig;
import com.swoop.demo.core.exceptions.DEMOException;
import com.swoop.demo.core.exceptions.ResultNotFoundException;
import com.swoop.demo.datetime.service.TimeService;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author zo061e
 */
@Component
public class TimeServiceImpl implements TimeService {

    static Logger log = LogManager.getLogger();

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    SecurityConfig securityConfig;

    /**
     *
     * @return
     */
    @Override
    public String getWaitTimeNowAtYYC() throws DEMOException {
        return invokeAPI();
    }

    private String invokeAPI() throws DEMOException {

        String apiPath = "http://api.openweathermap.org/data/2.5/weather";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiPath)
                // Add query parameter
                .queryParam("q", "London,uk")
                .queryParam("APPID", securityConfig.getWeatherAPIKey());

        System.out.println(builder.build().toUriString());

        Map<String, Map<String, Integer>> responseEntity = restTemplate.getForObject(builder.build().toUriString(), Map.class);

        Integer data;
        if (responseEntity == null || responseEntity.isEmpty()) {
            throw new ResultNotFoundException("result not found");
        } else {
            data = responseEntity.get("sys").get("sunrise");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(data);
        calendar.toString();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        int mMM = calendar.get(Calendar.MINUTE);
        int mHH = calendar.get(Calendar.HOUR_OF_DAY);
        String result = mDay + "-" + mMonth + "-" + mYear + " time " + mHH + ":" + mMM;
        return result;

    }
}
