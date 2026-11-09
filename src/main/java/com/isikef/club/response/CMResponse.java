package com.isikef.club.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class CMResponse {

    private CMQStatus status;
    private String message;
    private Object content;

    public static CMResponse of(Page rpage){
        CMResponse response = new CMResponse();
        response.setStatus(CMQStatus.SUCCESS);
        response.setMessage(null);
        response.setContent(RPage.of(rpage));
        return response;
    }

    public static CMResponse success(Object object){
        CMResponse response = new CMResponse();
        response.setStatus(CMQStatus.SUCCESS);
        response.setMessage(null);
        response.setContent(object);
        return response;
    }

    public static CMResponse failed(Object object){
        CMResponse response = new CMResponse();
        response.setStatus(CMQStatus.FAILED);
        response.setMessage(object.toString());
        response.setContent(null);
        return response;
    }
}
