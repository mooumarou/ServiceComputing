package JuddiClient;

public class QueryResult {

	private String wsdl;
	private String serviceKey;
	private String description;

	public String getWsdl() {
		return wsdl;
	}

	public void setWsdl(String wsdl) {
		this.wsdl = wsdl;
	}

	public String getServiceKey() {
		return serviceKey;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		String result = "*******************************************\n";
		result += "Service Key: " + getServiceKey() + "\n";
		result += "WSDL: " + getWsdl() + "\n";
		result += "Description: " + getDescription() + "\n";
		return result;
	}
}
