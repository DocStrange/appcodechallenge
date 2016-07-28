package mock;

import com.shaunmccready.dto.EventDTO;
import com.shaunmccready.dto.ResponseDTO;
import com.shaunmccready.entity.Account;
import com.shaunmccready.entity.Status;

import java.util.Date;

public class MockEntities {


    public static String getUrl(){
        return "https://www.testurl.com";
    }


    public static ResponseDTO getResponseDTO(){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setAccountIdentifier("123456");

        return responseDTO;
    }

    public static String appDirectXmlResponseCreate(){
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><event xmlns:atom=\"http://www.w3.org/2005/Atom\">" +
                "<type>SUBSCRIPTION_ORDER</type><marketplace><baseUrl>https://www.appdirect.com</baseUrl><partner>APPDIRECT</partner>" +
                "</marketplace><flag>DEVELOPMENT</flag><creator><address><firstName>Shaun</firstName><fullName>Shaun McCready</fullName>" +
                "<lastName>McCready</lastName></address><email>shaunwmccready@gmail.com</email><firstName>Shaun</firstName><language>en</language>" +
                "<lastName>McCready</lastName><openId>https://www.appdirect.com/openid/id/59455f79-b290-4449-a575-2d6464acdd72</openId>" +
                "<uuid>59455f79-b290-4449-a575-2d6464acdd72</uuid></creator><payload><company><country>US</country><name>Shaun McCready</name>" +
                "<phoneNumber>514-817-3556</phoneNumber><uuid>2a31de68-1a05-4db8-8e23-f6ced01acbbc</uuid><website>www.shaunmccready.com</website>" +
                "</company><configuration/><order><editionCode>FREE</editionCode><pricingDuration>MONTHLY</pricingDuration></order></payload></event>";
    }


    public static String appDirectXmlResponseCancel(){
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><event xmlns:atom=\"http://www.w3.org/2005/Atom\"><type>SUBSCRIPTION_CANCEL</type>" +
                "<marketplace><baseUrl>https://www.appdirect.com</baseUrl><partner>APPDIRECT</partner></marketplace><flag>DEVELOPMENT</flag><creator><address>" +
                "<firstName>Shaun</firstName><fullName>Shaun McCready</fullName><lastName>McCready</lastName></address><email>shaunwmccready@gmail.com</email>" +
                "<firstName>Shaun</firstName><language>en</language><lastName>McCready</lastName>" +
                "<openId>https://www.appdirect.com/openid/id/59455f79-b290-4449-a575-2d6464acdd72</openId><uuid>59455f79-b290-4449-a575-2d6464acdd72</uuid>" +
                "</creator><payload><account><accountIdentifier>1</accountIdentifier><status>ACTIVE</status></account><configuration/></payload></event>";
    }

    public static Status getStatus(String statusString){
        Status status = new Status();

        switch(statusString.toUpperCase()){
            case "ACTIVE":
                status.setId(5);
                status.setName("ACTIVE");
                break;
            case "CANCELLED":
                status.setId(7);
                status.setName("CANCELLED");
                break;
            default:
                status.setId(1);
                status.setName("INITIALIZED");
                break;
        }

        return status;
    }

    public static Account getAccount(String statusString){
        Status status = getStatus(statusString);

        Account account = new Account();
        account.setId(0);
        account.setNumberOfUsers(-1);
        account.setAccountIdentifier("unique-id-test");
        account.setStatus(status);
        account.setStatusId(status.getId());
        account.setEditionCode("FREE");
        account.setPricingDuration("MONTHLY");
        account.setModified(new Date());
        account.setCreated(new Date());

        return account;
    }

    public static EventDTO getCreatedEventDTO(){
        EventDTO eventDTO = new EventDTO();
        eventDTO.setType("SUBSCRIPTION_ORDER");

        //TODO: continue filling in...
        return eventDTO;
    }

}
