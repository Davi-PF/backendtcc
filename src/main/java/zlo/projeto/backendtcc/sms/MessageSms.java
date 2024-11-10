package zlo.projeto.backendtcc.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import static zlo.projeto.backendtcc.sms.codeGenerator.CodeGenerator.gerarCodigoSMS;

public class MessageSms {
    private static final String ACCOUNT_SID = ""; // Codigos da conta Twilio que n podem ser expostos
    private static final String AUTH_TOKEN = "";

    private Integer codigoSMS;

    public Integer getCodigoSMS() {
        return codigoSMS;
    }

    public void setCodigoSMS() {
        this.codigoSMS = Integer.valueOf(gerarCodigoSMS(8));
    }

    public void sendSms(String phoneUser) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(phoneUser), // receive SMS
                        new com.twilio.type.PhoneNumber(""), // send SMS
                        "Security code: " + codigoSMS)
                .create();

        System.out.println(message.getSid());
    }

}
