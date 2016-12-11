package kz.ecc.isbp.admin.common.webapi.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class ExceptionMapperImpl implements ExceptionMapper<Throwable> { 
	
    @Override
    public Response toResponse(Throwable exc) {
    	String message = exc.getMessage();
    	
    	if (exc.getCause()!=null) {
    		message = exc.getCause().getMessage();
    		
    		if (exc.getCause() instanceof ConstraintViolationException) {
        		message = "Bean validation exception: ";
        		for (ConstraintViolation<?> v: ((ConstraintViolationException) exc.getCause()).getConstraintViolations()) { 
    	    		message += v.getPropertyPath() + ": " +v.getMessage() + "; ";    	
        		}    				
    		}
    		else {
	    		if (exc.getCause().getCause()!=null) 
	    			message = exc.getCause().getCause().getMessage();
    		}
    	}

    	
    	if (message==null || message.equals(""))
    		message = exc.getClass().getName();
    	
    	return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorMessage(message))
                .build();
    }
}
