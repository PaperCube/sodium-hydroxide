package naoh.client;

public class CoverageException extends RuntimeException {
    public CoverageException() {
        super();
    }

    public CoverageException(String message) {
        super(message);
    }

    public CoverageException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoverageException(Throwable cause) {
        super(cause);
    }

    protected CoverageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static class AccessDenied extends CoverageException {
        public AccessDenied() {
            super();
        }

        public AccessDenied(String message) {
            super(message);
        }

        public AccessDenied(String message, Throwable cause) {
            super(message, cause);
        }

        public AccessDenied(Throwable cause) {
            super(cause);
        }

        protected AccessDenied(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
