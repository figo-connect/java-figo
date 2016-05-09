package me.figo.internal;

import com.google.gson.annotations.Expose;

public class VisitedRequest {

	@Expose
	private boolean visited;
	
	public VisitedRequest(boolean visited)	{
		this.visited = visited;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}
