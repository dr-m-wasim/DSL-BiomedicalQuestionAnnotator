package pk.edu.com.kics.util;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


public class SearchResult {
	
	private String ui;
	private String name;
	private String uri;
	private String semanticType;
	private String rootSource;
	
	//getters
    public String getUi() {
		
		return this.ui;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	
	public String getSemanticType() {
		return semanticType;
	}

	public void setSemanticType(String semanticType) {
		this.semanticType = semanticType;
	}

	public String getUri() {
		
		return this.uri;
	}
	
	public String getRootSource() {
		
		return this.rootSource;
	}

	//setters
	private void setUi(String ui) {
		
		this.ui = ui;
	}
	
	private void setName(String name) {
		
		this.name = name;
	}
	
	private void setUri(String uri) {
		
		this.uri = uri;
	}
	
	private void setRootSource(String rootSource) {
		
		this.rootSource = rootSource;
	}
}
