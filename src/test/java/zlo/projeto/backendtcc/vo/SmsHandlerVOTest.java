package zlo.projeto.backendtcc.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

public class SmsHandlerVOTest {

    @Test
    public void testConstrutorEGetters() {
        int smsCode = 123;
        Timestamp sendDate = Timestamp.valueOf("2023-01-01 10:00:00");
        Timestamp returnDate = Timestamp.valueOf("2023-01-01 10:05:00");
        String phoneUser = "5511999999999";
        String cpfDep = "12345678901";

        SmsHandlerVO smsHandler = new SmsHandlerVO(smsCode, sendDate, returnDate, phoneUser, cpfDep);

        assertEquals(smsCode, smsHandler.getKey());
        assertEquals(sendDate, smsHandler.getSendDate());
        assertEquals(returnDate, smsHandler.getReturnDate());
        assertEquals(phoneUser, smsHandler.getPhoneUser());
        assertEquals(cpfDep, smsHandler.getCpfDep());
    }

    @Test
    public void testSetters() {
        SmsHandlerVO smsHandler = new SmsHandlerVO();

        int smsCode = 456;
        Timestamp sendDate = Timestamp.valueOf("2023-01-02 11:00:00");
        Timestamp returnDate = Timestamp.valueOf("2023-01-02 11:05:00");
        String phoneUser = "5511988888888";
        String cpfDep = "09876543210";

        smsHandler.setKey(smsCode);
        smsHandler.setSendDate(sendDate);
        smsHandler.setReturnDate(returnDate);
        smsHandler.setPhoneUser(phoneUser);
        smsHandler.setCpfDep(cpfDep);

        assertEquals(smsCode, smsHandler.getKey());
        assertEquals(sendDate, smsHandler.getSendDate());
        assertEquals(returnDate, smsHandler.getReturnDate());
        assertEquals(phoneUser, smsHandler.getPhoneUser());
        assertEquals(cpfDep, smsHandler.getCpfDep());
    }

    @Test
    public void testEqualsAndHashCode() {
        int smsCode = 789;
        Timestamp sendDate = Timestamp.valueOf("2023-01-03 12:00:00");
        Timestamp returnDate = Timestamp.valueOf("2023-01-03 12:05:00");
        String phoneUser = "5511977777777";
        String cpfDep = "11223344556";

        SmsHandlerVO smsHandler1 = new SmsHandlerVO(smsCode, sendDate, returnDate, phoneUser, cpfDep);
        SmsHandlerVO smsHandler2 = new SmsHandlerVO(smsCode, sendDate, returnDate, phoneUser, cpfDep);

        assertEquals(smsHandler1, smsHandler2);
        assertEquals(smsHandler1.hashCode(), smsHandler2.hashCode());
    }
}
