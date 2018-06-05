package naoh.installer;

public class InstallationException extends RuntimeException {
    InstallationException(Exception e){
        super(e);
    }
    public static InstallationException installFailed(Exception e){
        return new InstallationException(e);
    }
}
