package JuddiClient;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.juddi.v3.client.transport.Transport;
import org.apache.juddi.v3.client.transport.TransportException;
import org.uddi.v3_service.DispositionReportFaultMessage;

public class App {

	/**
	 * @param args
	 * @throws ConfigurationException
	 * @throws TransportException
	 * @throws RemoteException
	 * @throws DispositionReportFaultMessage
	 */
	public static void main(String[] args) throws ConfigurationException,
			TransportException, DispositionReportFaultMessage, RemoteException {

		Transport transport = new BuildClient().buildClearkManager();
		String authToken = new Authenticate(transport).getRootAuthToken();

		CreateBusinessServicePOJO config = new CreateBusinessServicePOJO();
		config.setBusinessEntityName("g24's Business");

		config.setModelName("PaymentModel");
		config.setUrl("http://localhost:8080/axis2/services/PaymentImpl?wsdl");
		config.setBusinessServiceName("Payment");
		config.setEndpoint("http://localhost:8080/axis2/services/PaymentImpl.PaymentImplHttpSoap12Endpoint");

		RegisterService register = new RegisterService(transport);
		// register.registerService(authToken, config); // #TODO: actually, this
		// code can only
		// register a single
		// service, but should
		// register all service

		DeleteBusiness delService = new DeleteBusiness(transport);
		// delService.deleteBusiness(authToken, "g24's Business");

		QueryJuddi q = new QueryJuddi(transport);
		List<QueryResult> queryResults = q.query(authToken, "Paym Servic"); // TODO
		// only
		// returns
		// some
		// strings,
		// but it
		// should
		// also
		// return
		// the wsdl
		for (QueryResult found : queryResults) {
			System.out.println("Service " + (queryResults.indexOf(found) + 1)
					+ " " + found);
		}
		if (queryResults.size() == 0)
			System.out.println("nothing found");

	}
}
