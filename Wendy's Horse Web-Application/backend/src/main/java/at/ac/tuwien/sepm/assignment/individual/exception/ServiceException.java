package at.ac.tuwien.sepm.assignment.individual.exception;

public class ServiceException extends RuntimeException{
        private static final long serialVersionUID = -5762187655103128692L;

        public ServiceException(String message) {
            super(message);
        }

        public ServiceException(Throwable cause) {
            super(cause);
        }

        public ServiceException(String message, Throwable cause) {
            super(message, cause);
        }



}
