package JuddiClient;

import java.rmi.RemoteException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.juddi.v3.client.transport.Transport;
import org.apache.juddi.v3.client.transport.TransportException;
import org.uddi.api_v3.GetAuthToken;
import org.uddi.v3_service.DispositionReportFaultMessage;
import org.uddi.v3_service.UDDISecurityPortType;

public class Authenticate {

	private Transport transport;

	public Authenticate(Transport uclManager) {
		this.transport = uclManager;

	}

	public String getRootAuthToken() throws ConfigurationException,
			TransportException, DispositionReportFaultMessage, RemoteException {
		GetAuthToken getAuthToken = new GetAuthToken();
		getAuthToken.setUserID("uddi");
		getAuthToken.setCred("uddi");

		UDDISecurityPortType uddiSecurityService = transport
				.getUDDISecurityService();
		return uddiSecurityService.getAuthToken(getAuthToken).getAuthInfo();
	}
}
