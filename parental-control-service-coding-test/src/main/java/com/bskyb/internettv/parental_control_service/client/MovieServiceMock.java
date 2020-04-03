package com.bskyb.internettv.parental_control_service.client;

import com.bskyb.internettv.thirdparty.MovieService;
import com.bskyb.internettv.thirdparty.TechnicalFailureException;
import com.bskyb.internettv.thirdparty.TitleNotFoundException;

public class MovieServiceMock implements MovieService {

    public String getParentalControlLevel(String titleId) throws TitleNotFoundException, TechnicalFailureException {
        switch (titleId) {
            case "TitleNotFoundException":
                throw new TitleNotFoundException();
            case "TechnicalFailureException":
                throw new TechnicalFailureException();
        }

        return titleId.substring(0, titleId.indexOf("-"));
    }

}

