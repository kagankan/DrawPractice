package DrawVenn;

public class NumberGraph extends Graph{
	public NumberGraph(){
		this.add(new Edge<String>("2n", "2"));
		this.add(new Edge<String>("2n", "4"));
		this.add(new Edge<String>("2n", "6"));
		this.add(new Edge<String>("2n", "8"));
		this.add(new Edge<String>("2n", "10"));
		this.add(new Edge<String>("2n", "12"));
		this.add(new Edge<String>("2n", "14"));
		/*
		this.add(new Edge<String>("3n", "3"));
		this.add(new Edge<String>("3n", "6"));
		this.add(new Edge<String>("3n", "9"));
		this.add(new Edge<String>("3n", "12"));
		this.add(new Edge<String>("3n", "15"));
		this.add(new Edge<String>("4n", "4"));
		this.add(new Edge<String>("4n", "8"));
		this.add(new Edge<String>("4n", "12"));
		*/
		this.add(new Edge<String>("5n", "5"));
		this.add(new Edge<String>("5n", "10"));
		this.add(new Edge<String>("5n", "15"));
	}	
	
}

