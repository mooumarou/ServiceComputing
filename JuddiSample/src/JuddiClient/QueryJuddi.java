package JuddiClient;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.juddi.v3.client.transport.Transport;
import org.apache.juddi.v3.client.transport.TransportException;
import org.uddi.api_v3.Description;
import org.uddi.api_v3.FindQualifiers;
import org.uddi.api_v3.FindService;
import org.uddi.api_v3.GetServiceDetail;
import org.uddi.api_v3.Name;
import org.uddi.api_v3.ServiceDetail;
import org.uddi.api_v3.ServiceInfo;
import org.uddi.api_v3.ServiceInfos;
import org.uddi.api_v3.ServiceList;
import org.uddi.v3_service.DispositionReportFaultMessage;
import org.uddi.v3_service.UDDIInquiryPortType;

public class QueryJuddi {

	private Transport transport;

	public QueryJuddi(Transport transport) {
		this.transport = transport;

	}

	public List<QueryResult> query(String authToken, String query)
			throws TransportException, DispositionReportFaultMessage,
			RemoteException {

		List<QueryResult> services = new ArrayList<QueryResult>();

		services.addAll(findService(authToken, query));

		return services;
	}

	private List<QueryResult> findService(String authToken, String query)
			throws DispositionReportFaultMessage, RemoteException,
			TransportException {

		List<QueryResult> services = new ArrayList<QueryResult>(); // TODO
																	// string
																	// alone?

		FindService fs = new FindService();
		fs.setAuthInfo(authToken);
		fs.getName().add(getWildcardName(query)); // this returns all
		fs.setFindQualifiers(approximateQualifier()); // try different
														// qualifiers,
		// //http://svn.apache.org/viewvc/juddi/tags/juddi-3.1.3/juddi-core/src/main/java/org/apache/juddi/query/util/FindQualifiers.java?view=markup

		UDDIInquiryPortType uddiInquiryService = transport
				.getUDDIInquiryService();
		ServiceList foundServices = uddiInquiryService.findService(fs);
		ServiceInfos infos = foundServices.getServiceInfos();

		// uddiInquiryService.get

		// search code
		if (infos != null) {
			List<ServiceInfo> infoList = infos.getServiceInfo();
			for (ServiceInfo info : infoList) {
				QueryResult result = new QueryResult();
				result.setServiceKey(info.getServiceKey());

				GetServiceDetail gDetail = new GetServiceDetail();
				gDetail.getServiceKey().add(info.getServiceKey());
				ServiceDetail sDetail = uddiInquiryService
						.getServiceDetail(gDetail);

				String wsdl = sDetail.getBusinessService().get(0)
						.getBindingTemplates().getBindingTemplate().get(0)
						.getAccessPoint().getValue();
				result.setWsdl(wsdl);

				List<Description> lDescription = sDetail.getBusinessService()
						.get(0).getDescription();

				String description = "No description available";
				if (lDescription.size() > 0) {
					description = sDetail.getBusinessService().get(0)
							.getDescription().get(0).getValue();
				}
				result.setDescription(description);
				services.add(result);
			}
		}

		return services;
	}

	private Name getWildcardName(String query) {
		Name name = new Name();
		name.setValue("%" + query + "%");
		return name;
	}

	private Description getWildcardDescription(String query) {
		Description description = new Description();
		description.setValue("%" + query + "%");
		return description;
	}

	private FindQualifiers approximateQualifier() {
		FindQualifiers fq = new FindQualifiers();
		fq.getFindQualifier().add("approximateMatch");
		return fq;
	}
}
