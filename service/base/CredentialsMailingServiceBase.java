package adaptiveschool.service.base;

public interface CredentialsMailingServiceBase {
    void sendStudentsCredentials(Integer classId);
    void sendTeachersCredentials();
}
