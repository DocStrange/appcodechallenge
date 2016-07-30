package mock;

import com.shaunmccready.dto.*;
import com.shaunmccready.entity.Account;
import com.shaunmccready.entity.ErrorCodes;
import com.shaunmccready.entity.Status;
import com.shaunmccready.entity.User;

import java.util.Date;

public class MockEntities {

    public static String getGoodUrl() { return "https://www.appdirect.com/api/integration/v1/events/1596ffae-4080-4875-bcd6-4b6e6e690969"; }
    public static String getBadUrl(){
        return "https://www.testurl.com";
    }

    public static Integer ACTIVE_STATUS = 5;
    public static String  ACTIVE_STRING = "ACTIVE";
    public static Integer CANCELLED_STATUS = 7;
    public static String  CANCELLED_STRING = "CANCELLED";


    public static ResponseDTO getSuccessResponseDTO(){
        final ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setAccountIdentifier("123456");

        return responseDTO;
    }


    public static ResponseDTO getFailedResponseDTO(){
        final ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(false);
        responseDTO.setErrorCode(ErrorCodes.UNKNOWN_ERROR.toString());
        responseDTO.setMessage("the operation could not be completed. Bad data");

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


    public static String appDirectXmlResponseChange(){
        return "";
    }

    public static Status getStatus(String statusString){
        final Status status = new Status();

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
        final Status status = getStatus(statusString);

        final Account account = new Account();
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

    public static EventDTO getCreatedEventDTO(Integer status){
        final EventDTO eventDTO = new EventDTO();
        eventDTO.setType("SUBSCRIPTION_ORDER");
        eventDTO.setMarketplace(getMarketPlaceDTO());
        eventDTO.setCreator(getCreatorDTO());
        eventDTO.setPayload(getPayloadDTO(status));
        return eventDTO;
    }

    public static MarketplaceDTO getMarketPlaceDTO(){
        final MarketplaceDTO marketplaceDTO = new MarketplaceDTO();
        marketplaceDTO.setBaseUrl("https://www.appdirect.com");
        marketplaceDTO.setPartner("APPDIRECT");
        return marketplaceDTO;
    }

    public static CreatorDTO getCreatorDTO(){
        final CreatorDTO creatorDTO = new CreatorDTO();
        creatorDTO.setAddress(getAddressDTO());
        creatorDTO.setLastName("McCready");
        creatorDTO.setFirstName("Shaun");
        creatorDTO.setEmail("test@test.com");
        creatorDTO.setLanguage("EN");
        creatorDTO.setOpenId("test");
        creatorDTO.setUuid("unique-creator-id");
        return creatorDTO;
    }

    public static AddressDTO getAddressDTO(){
        final AddressDTO addressDTO = new AddressDTO();
        addressDTO.setFirstName("Shaun");
        addressDTO.setLastName("McCready");
        addressDTO.setFullName("Shaun McCready");
        return addressDTO;
    }

    public static PayloadDTO getPayloadDTO(Integer status){
        final PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setCompany(getCompanyDTO());
        payloadDTO.setOrder(getOrderDTO());
        payloadDTO.setAccount(getAccountDTO(status));
        return payloadDTO;
    }

    public static CompanyDTO getCompanyDTO(){
        final CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setCountry("US");
        companyDTO.setName("Shaun Test Industries");
        companyDTO.setPhoneNumber("514-555-1234");
        companyDTO.setUuid("unique-company-id");
        companyDTO.setWebsite("www.test.com");
        return companyDTO;
    }

    public static OrderDTO getOrderDTO(){
        final OrderDTO orderDTO = new OrderDTO();
        orderDTO.setEditionCode("FREE");
        orderDTO.setPricingDuration("MONTHLY");
        return orderDTO;
    }

    public static AccountDTO getAccountDTO(Integer status){
        final AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(0);
        accountDTO.setCreated(new Date());
        accountDTO.setModified(new Date());
        accountDTO.setPricingDuration("MONTHLY");
        accountDTO.setEditionCode("FREE");
        accountDTO.setAccountIdentifier("unique-account-id");
        accountDTO.setNumberOfUsers(-1);
        accountDTO.setStatusId(status);
        return accountDTO;
    }


    public static UserDTO getUserDTO(final AccountDTO dto){
        final UserDTO userDTO = new UserDTO();
        userDTO.setCreated(new Date());
        userDTO.setAccountId(0);
        userDTO.setAccount(dto);
        userDTO.setLastName("McCready");
        userDTO.setFirstName("Shaun");
        userDTO.setEmail("test@test.com");
        userDTO.setLanguage("EN");
        userDTO.setOpenId("test");
        userDTO.setUuid("unique-user-id");
        return userDTO;
    }

    public static User getUser(final Account account){
        final User user = new User();
        user.setCreated(new Date());
        user.setAccountId(0);
        user.setAccount(account);
        user.setLastName("McCready");
        user.setFirstName("Shaun");
        user.setEmail("test@test.com");
        user.setLanguage("EN");
        user.setOpenId("test");
        user.setUuid("unique-user-id");
        return user;
    }

}
