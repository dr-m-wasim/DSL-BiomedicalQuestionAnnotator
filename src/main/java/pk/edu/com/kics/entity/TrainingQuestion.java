package pk.edu.com.kics.entity;

import java.util.ArrayList;
import java.util.List;

public class TrainingQuestion {

	private int idealLength;
	private int bodyLength;
	private String id;
	private String body;
	private String type;
	private List<Triples> triples;
	private List<String> documents;
	private List<String> concepts;
	private List<Snippet> snippets;
	private List<String> ideal_answer;
	private List<String> exact_answer;
	public TrainingQuestion()
	{
		triples = new ArrayList<>();
		documents = new ArrayList<>();
		concepts = new ArrayList<>();
		snippets = new ArrayList<>();
		ideal_answer = new ArrayList<>();
		exact_answer = new ArrayList<>();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBody() {
		return body;
	}
	public int getIdealLength() {
		return idealLength;
	}
	public void setIdealLength(int idealLength) {
		this.idealLength = idealLength;
	}
	public int getBodyLength() {
		return bodyLength;
	}
	public void setBodyLength(int bodyLength) {
		this.bodyLength = bodyLength;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Triples> getTriples() {
		return triples;
	}
	public void setTriples(List<Triples> triples) {
		this.triples = triples;
	}
	public List<String> getDocuments() {
		return documents;
	}
	public void setDocuments(List<String> documents) {
		this.documents = documents;
	}
	public List<String> getConcepts() {
		return concepts;
	}
	public void setConcepts(List<String> concepts) {
		this.concepts = concepts;
	}
	public List<Snippet> getSnippets() {
		return snippets;
	}
	public void setSnippets(ArrayList<Snippet> arrayList) {
		this.snippets = arrayList;
	}
	public List<String> getIdeal_answer() {
		return ideal_answer;
	}
	public void setIdeal_answer(List<String> ideal_answer) {
		this.ideal_answer = ideal_answer;
	}
	public List<String> getExact_answer() {
		return exact_answer;
	}
	public void setExact_answer(List<String> exact_answer) {
		this.exact_answer = exact_answer;
	}
}
