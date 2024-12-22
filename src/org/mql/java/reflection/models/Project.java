package org.mql.java.reflection.models;

import java.util.List;

public class Project {
	private String name;
	List<PackageInfo> packages;
	
	public Project() {}
	
	public Project(String name, List<PackageInfo> packages) {
		this.name = name;
		this.packages = packages;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PackageInfo> getPackages() {
		return packages;
	}

	public void setPackages(List<PackageInfo> packages) {
		this.packages = packages;
	}


}
