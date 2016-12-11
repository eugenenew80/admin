package kz.ecc.isbp.admin.common.exception;

public class RepositoryNotFoundException extends ApplicationException {
	private static final long serialVersionUID = 7805786431835780607L;
	
	public RepositoryNotFoundException() {
		super("Entity service not found");
	}
	
	public String getCode() { return code; }
	public int getStatusCode() { return statusCode; }

	private final String code = "entity-service-not-found";
	private final int statusCode = javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
}
