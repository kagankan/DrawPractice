package DrawVenn;

public class Edge<E>{
	private E from;
	private E to;
	
	public Edge(E from, E to) {
		this.from = from;
		this.to= to;
	}
	
	public E getFrom(){ return from;}
	public E getTo(){ return to;}
	public void setFrom(E from) {this.from = from;}
	public void setTo(E to) {this.to = to;}
	
}